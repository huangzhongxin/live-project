import os
from wordcloud import WordCloud,STOPWORDS
import matplotlib.pyplot as plt
from pylab import *
mpl.rcParams['font.sans-serif'] = ['SimHei']
import chardet
f = open(u'text.txt','r',encoding="utf-8").read()
sw = set(STOPWORDS) 
sw.add("表情")
sw.add("图片")
sw.add("加入本群")
sw.add("大家好")
sw.add("谢谢")
#wordcloud = WordCloud(background_color="white",width=1000, height=860, margin=2,stopwords=sw).generate(f)

# width,height,margin可以设置图片属性

wordcloud = WordCloud(scale=10,background_color="white",font_path = r"C:\Windows\Fonts\STFANGSO.TTF",stopwords=sw).generate(f)
# 你可以通过font_path参数来设置字体集

#background_color参数为设置背景颜色,默认颜色为黑色
plt.title("发言内容云图")
plt.imshow(wordcloud)
plt.axis("off")
plt.savefig('contentCloud.png')
os.remove('text.txt')
print("'contentCloud.png' done")