#import xlsxwriter #可以向excel2007+中写text，numbers，formulas 公式以及hyperlinks超链接
import os
import re #正则匹配模块
import matplotlib.pyplot as plt
from pylab import *
mpl.rcParams['font.sans-serif'] = ['SimHei']
srcFile=input("请输入源文件:")
with open(srcFile,encoding='utf-8') as f:
	data=f.read()
	pa=re.compile(r"\d{4}-\d{2}-\d{2}.*\n.*")
	records=re.findall(pa,data)
	#print(len(records))
	#print("done");
# workbook=xlsxwriter.Workbook('chatRecords.xlsx') #新建excel表
# worksheet=workbook.add_worksheet()#新建sheet,无名称
# worksheet.set_column('A:A',20)#设置表格宽度
# worksheet.set_column('B:B',20)
# worksheet.set_column('C:C',20)
# worksheet.set_column('D:D',50)
# worksheet.set_column('E:E',100)
i=0;
dates=[]
times=[]
qqNums=[]
names=[]
contents=[]
#将数据存入列表
for record in records:
	pa_date=re.compile(r"\d{4}-\d{2}-\d{2}")
	pa_time=re.compile(r"\d+:\d\d:\d\d")
	pa_qqNum=re.compile(r"\(\d{5,}\)|<.*>")
	pa_name=re.compile(r":\d\d .*\(|:\d\d \(|:\d\d .*<")
	pa_content=re.compile(r"\)\n.*|>\n.*")
	date=re.findall(pa_date,record)
	time=re.findall(pa_time,record)
	qqNum=re.findall(pa_qqNum,record)
	name=re.findall(pa_name,record)
	content=re.findall(pa_content,record)
	qqNum[0]=qqNum[0][1:len(qqNum[0])-1]
	name[0]=name[0][4:len(name[0])-1]
	content[0]=content[0][2:len(content[0])]
	# worksheet.write(i,0,date[0])
	# worksheet.write(i,1,time[0])
	# worksheet.write(i,2,qqNum[0])
	# worksheet.write(i,3,name[0])
	# worksheet.write(i,4,content[0])
	dates.append(date[0])
	times.append(time[0])
	qqNums.append(qqNum[0])
	names.append(name[0])
	contents.append(content[0])
	i=i+1
with open('text.txt', 'w',encoding='utf-8') as f:
	for items in contents:
		f.write(items)
# workbook.close()
#数据挖掘
#1.发言人数与时间段
#1.1数据处理
time_nums=[]#发言时间段人数
time_list=[]#发言时间段
for i in range(0,24):
	time_nums.append(0)
for i in range(0,24):
	time_list.append(i)
for i in range(0,len(times)):
	temp=times[i][0:times[i].rfind(':')-3]
	index=int(temp)
	time_nums[index]=time_nums[index]+1
#1.2数据可视化
plt.figure(figsize=(10, 6))
plt.plot(time_list,time_nums,marker='o',linewidth=2)
plt.xticks([x for x in range(max(time_list) + 1)])#横坐标步长为1
for x,y in zip(time_list,time_nums):
	plt.text(x,y,y,ha="right",va="bottom")
plt.title(dates[0]+" "+times[0]+"~"+dates[len(dates)-1]+" "+times[len(times)-1]+"发言时间段人数",fontsize=15)
plt.xlabel("时间段",fontsize=12)
plt.ylabel("人数",fontsize=12)
plt.tick_params(axis='both')
plt.savefig('chatTime.png')
print("'chatTime.png' done'")
#2.发言人与发言次数
#2.1数据处理
dict={}
for i in range(0,len(qqNums)):
	if qqNums[i] in dict:
		dict[qqNums[i]]=dict[qqNums[i]]+1
	else:
		dict[qqNums[i]]=1
del dict['10000']#删除掉系统消息发言
z=sorted(zip(dict.values(),dict.keys()))
stmt_nums=[]
stmt_ids=[]
for i in z:
	stmt_nums.append(i[0])
	stmt_ids.append(i[1])


#数据可视化
plt.figure(figsize=(10, 6))
plt.bar(stmt_ids[len(stmt_ids):len(stmt_ids)-11:-1],stmt_nums[len(stmt_nums):len(stmt_nums)-11:-1])
#plt.xticks([x for x in range(max(time_list) + 1)])#横坐标步长为1
for x,y in zip(stmt_ids,stmt_nums):
	plt.text(x,y,y,ha="center",va="bottom")
plt.title(dates[0]+" "+times[0]+"~"+dates[len(dates)-1]+" "+times[len(times)-1]+"前十发言次数",fontsize=15)
plt.xlabel("发言人",fontsize=12)
plt.ylabel("次数",fontsize=12)
plt.tick_params(axis='both')
plt.savefig('stmtNums.png')
print("'stmtNums.png' done")
os.system("python contentCloud.py")