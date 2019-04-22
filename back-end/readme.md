# README

### 怎么运行

- clone 项目

  ```shell
  git clone https://github.com/kkkwwwmmm/live-project.git
  ```

- 进入 目录

  ```shell
  cd live-project
  ```

- 启动服务

  ```shell
  mvn spring-boot:run
  ```

- 访问 [localhost:8080/lottery/hello](http://localhost:8080/lottery/hello)

  如果出现，说明OK

  ```json
  { 
      "content": "hello world"
  }
  ```

- 访问[localhost:8080/lottery](http://localhost:8080/lottery)

  ```json
  {
  	"startTime":"2022-08-01 09:00",
  	"endTime":"2022-12-12 09:00",
  	"filterTeacher":true,
  	"countLimit":0,
  	"keyWord":"我要红包",
  	"writting":"",
  	"filterRepeat":true,
  	"award":[
  		{
  			"name":"laptop",
  			"number":1
  		},
  		{
  			"name":"mobile phone",
  			"number":2
  		},
  		{
  			"name":"snack",
  			"number":3
  		}
  	]
  }
  ```

  得到如下结果：

  ```json
  {
      "success": true,
      "code": 200,
      "errMsg": "",
      "data": {
          "winners": [
              {
                  "name": "laptop",
                  "number": 1,
                  "list": [
                      {
                          "qq": "2739294368",
                          "name": "计2，黄正"
                      }
                  ]
              },
              {
                  "name": "mobile phone",
                  "number": 2,
                  "list": [
                      {
                          "qq": "i@cstdio.cn",
                          "name": "计4-苏路明"
                      },
                      {
                          "qq": "1072797239",
                          "name": "计一-何宇恒"
                      }
                  ]
              },
              {
                  "name": "snack",
                  "number": 3,
                  "list": [
                      {
                          "qq": "ibelove@foxmail.com",
                          "name": "计1-胡青元"
                      },
                      {
                          "qq": "563541595",
                          "name": "计二-郭俊彦"
                      },
                      {
                          "qq": "2329677953",
                          "name": "计3-林燊"
                      }
                  ]
              }
          ],
          "file": "C:/Users/KWM/Desktop/winner.csv"
      }
  }
  ```



## 配置文件 application.properties

```properties
# excel 路径
excel.path=C:/Users/KWM/Desktop/chatRecords.xlsx
# 输出文件路径
output.path=C:/Users/KWM/Desktop/winner.csv
```

#### 输出文件格式

[奖品],[昵称],[qq号]

```
laptop,计2，黄正,2739294368
mobile phone,计4-苏路明,i@cstdio.cn
mobile phone,计一-何宇恒,1072797239
snack,计1-胡青元,ibelove@foxmail.com
snack,计二-郭俊彦,563541595
snack,计3-林燊,2329677953
```

