import xlsxwriter #可以向excel2007+中写text，numbers，formulas 公式以及hyperlinks超链接
import re #正则匹配模块
srcFile=input("请输入源文件:");
with open(srcFile,encoding='utf-8') as f:
	data=f.read()
	pa=re.compile(r"\d{4}-\d{2}-\d{2}.*\n.*")
	records=re.findall(pa,data)
	#print(len(records))
	#print("done");
workbook=xlsxwriter.Workbook('chatRecords.xlsx') #新建excel表
worksheet=workbook.add_worksheet()#新建sheet,无名称
worksheet.set_column('A:A',20)#设置表格宽度
worksheet.set_column('B:B',20)
worksheet.set_column('C:C',20)
worksheet.set_column('D:D',50)
worksheet.set_column('E:E',100)
i=0;
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
	worksheet.write(i,0,date[0])
	worksheet.write(i,1,time[0])
	worksheet.write(i,2,qqNum[0])
	worksheet.write(i,3,name[0])
	worksheet.write(i,4,content[0])
	i=i+1
workbook.close()