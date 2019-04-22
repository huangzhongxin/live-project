import Taro, { Component } from '@tarojs/taro'
import { View, Image, Text, ScrollView } from '@tarojs/components'
import { AtInput, AtTabs, AtTabsPane, AtSwitch, AtButton, AtCard } from 'taro-ui'
import './index.scss'

import empty from '../../assert/empty.png'

export default class Index extends Component {

  config = {
    navigationBarTitleText: '首页'
  }
  constructor() {
    super(...arguments)
    this.state = {
      current: 0,
      result: [
        {
          "name": "laptop",
          "number": 1,
          "list": [
            {
              "qq": "2759947912",
              "name": "计三-张沐栎"
            }
          ]
        },
        {
          "name": "mobile phone",
          "number": 2,
          "list": [
            {
              "qq": "724043054",
              "name": "计4－后敬甲"
            },
            {
              "qq": "1357670946",
              "name": "计4-蒋熊"
            }
          ]
        },
        {
          "name": "snack",
          "number": 3,
          "list": [
            {
              "qq": "i@cstdio.cn",
              "name": "计4-苏路明"
            },
            {
              "qq": "563541595",
              "name": "计二-郭俊彦"
            },
            {
              "qq": "835599660",
              "name": "计5 陈俞辛"
            }
          ]
        }
      ],
      filterTeacher: false,
      filtercountLimit: false,
      filterRepeat: false,
      awardIndexList: [0, 1, 2],
      award: [
        { name: '', number: 0 },
        { name: '', number: 0 },
        { name: '', number: 0 }
      ],
      requesting: false,
      start: '',
      end: '',
      kw: '',
      writting: '',
      countLimit: 0
    }
  }
  verifyDateTime = (value) => {
    const dt = value.split(" ");
    if (dt.length !== 2) return false
    return this.verifyDate(dt[0]) && this.verifyTime(dt[1])
  }

  verifyTime = (value) => {
    if (!/^\d{1,2}:\d{1,2}$/.test(value)) return false

    const time = value.split(':').map(num => +num)

    if (time[0] < 0 || time[0] > 23) return false
    if (time[1] < 0 || time[1] > 59) return false

    return true
  }

  verifyDate = (date) => {
    if (!date) return false
    date = new Date(date.replace(/-/g, '/'))
    // eslint-disable-next-line no-restricted-globals
    return isNaN(date.getMonth()) ? false : date
  }

  handleInputChange(name, value) {
    switch (name) {
      case 'writting':
        this.setState({
          writting: value
        });
        break;
      case 'kw':
        this.setState({
          kw: value
        });
        break;
      case 'countLimit':
        this.setState({
          countLimit: value
        });
        break;
      case 'repeat':
        this.setState({
          countLimit: value
        });
        break;
      case 'start':
        this.setState({
          start: value
        });
        break;
      case 'end':
        this.setState({
          end: value
        });
        break;
    }
    return value
  }

  handleInputChange2(name, index, value) {
    const award = this.state.award
    switch (name) {
      case 'awardm':
        award[index].name = value
        this.setState({
          award: award
        });
        break;
      case 'awardr':
        award[index].number = value
        this.setState({
          award: award
        });
        break;
    }
    return value
  }
  handleClick(value) {
    this.setState({
      current: value
    })
  }
  handleSwitchChange = (type) => {
    switch (type) {
      case 'teacher':
        this.setState({
          filterTeacher: !this.state.filterTeacher
        })
        break
      case 'countLimit':
        if (this.state.filtercountLimit === true) {
          this.setState({
            filtercountLimit: !this.state.filtercountLimit,
            countLimit: 0
          })
        } else {
          this.setState({
            filtercountLimit: !this.state.filtercountLimit
          })
        }
        break
    }
  }
  handleawardList = (type) => {
    const awardList = this.state.awardIndexList;
    const award = this.state.award
    switch (type) {
      case 'add':
        awardList.push(award.length)
        award.push({ name: '', number: 0 })
        this.setState({
          awardIndexList: awardList,
          award: award
        })
        break;
      case 'del':
        if (awardList.length === 1) {
          Taro.showModal({
            title: '无法删除',
            content: '至少需要一个奖项'
          })
          return;
        }
        awardList.pop()
        award.pop()
        this.setState({
          awardIndexList: awardList,
          award: award
        })
        break;
    }
  }

  handleBlur = (type, value) => {
    if (!this.verifyDateTime(value)) {
      Taro.showModal({
        title: '时间格式有误!',
        content: '请确保时间格式为 \r\nYYYY-MM-DD HH:SS，如：2019-04-23 12:00'
      })
      this.setState({
        [`${type}`]: ''
      })
      return;
    }
  }

  request = () => {
    if (this.state.kw.trim() === '') {
      Taro.showModal({
        title: '输入不完整!',
        content: '请输入关键词!'
      });
      return;
    }
    this.state.kw.replace('#', ''); // 去除#
    if (this.state.writting.trim() === '') {
      Taro.showModal({
        title: '输入不完整!',
        content: '请输入活动文案!'
      });
      return;
    }
    
    if (!this.verifyDateTime(this.state.start)) {
      Taro.showModal({
        title: '开始时间格式有误!',
        content: '请确保时间格式为 \r\nYYYY-MM-DD HH:SS，如：2019-04-23 12:00'
      })
      return;
    }
    if (!this.verifyDateTime(this.state.end)) {
      Taro.showModal({
        title: '结束时间格式有误!',
        content: '请确保时间格式为 \r\nYYYY-MM-DD HH:SS，如：2019-04-23 12:00'
      })
      return;
    }
    
    var flag = true;
    this.state.award.forEach((value, index) => {
      if (value.name.trim() === '') {
        Taro.showModal({
          title: '输入不完整!',
          content: `请输入奖项${index}的名称!`
        });
        flag = false;
        return;
      } 
      if (/^\+?[1-9][0-9]*$/.test(value.number)) {
        Taro.showModal({
          title: '输入有误!',
          content: `奖项${index}的奖品数不为正整数!`
        });
        flag = false;
        return;
      }
    })
    if (!flag) return;

    this.setState({
      requesting: true
    })
    const { kw, writting, filterRepeat, filterTeacher, countLimit, start, end, award } = this.state
    Taro.request({
      url: 'http://127.0.0.1:8080',
      data: {
        keyWord: kw,
        writting: writting,
        filterTeacher: filterTeacher,
        countLimit: countLimit,
        filterRepeat: filterRepeat,
        startTime: start,
        endTime: end,
        award: JSON.stringify(award)
      }
    }).then(({ data }) => {
      this.setState({
        downloadUrl: data.file,
        result: data.winners,
        current: 1
      })
      Taro.showToast({
        title: '抽奖完成'
      })
    })
  }
  download = () => {
    Taro.redirectTo({ url: this.state.downloadUrl })
  }

  render() {
    const tabList = [{ title: '开始抽奖' }, { title: '抽奖结果' }]
    const emptyView = (
      <View>
        <Image src={empty} className='empty' />
        <View style='font-size: 20px; text-align: center'>还没有抽奖~</View>
      </View>
    )
    const inputAvtive = (
      <AtInput
        name='value'
        title='过滤发言数少于的人'
        type='number'
        placeholder='0'
        value={this.state.countLimit}
        onChange={this.handleInputChange.bind(this, 'countLimit')}
      />
    )
    const awardView = (
      this.state.awardIndexList.map((index) => {
        return (
          <View key={index}>
            <AtInput
              border={false}
              name='value'
              title={'奖项' + index}
              type='text'
              placeholder={'请输入奖项' + index}
              value={this.state.award[index]['name']}
              onChange={this.handleInputChange2.bind(this, 'awardm', index)}
            />
            <AtInput
              border={false}
              name='value'
              title={'奖项' + index + '人数'}
              type='number'
              placeholder={'请输入奖项' + index + '人数'}
              value={this.state.award[index]['number']}
              onChange={this.handleInputChange2.bind(this, 'awardr', index)}
            />
          </View>
        )
      }))

    const resultView = (
      <View className='result'>
        <View onClick={this.download} className='download'>下载抽奖结果</View>
        <ScrollView
          className='tabContent'
          scrollY
          scrollWithAnimation
          lowerThreshold='20'
          upperThreshold='20'
          scrollTop='0'
          style='height: 430px;'
        >{
            this.state.result.map((value => {
              return (
                <View key={value.qq} style='margin-bottom: 20px'>
                  <AtCard
                    extra={'中奖人数：' + value.number}
                    title={'奖项：' + value.name}
                  >
                    {value.list.map((winners) => {
                      return (
                        <View key={winners.qq} >{winners.name}({winners.qq})</View>
                      )
                    })}
                  </AtCard>
                </View>
              )
            }))
          }
        </ScrollView>
      </View>
    )



    return (
      <View className='body'>
        <View className='container'>
          <View>
            <AtTabs current={this.state.current} tabList={tabList} onClick={this.handleClick.bind(this)} className='tabs'>
              <AtTabsPane current={this.state.current} index={0} >
                <ScrollView
                  className='tabContent'
                  scrollY
                  scrollWithAnimation
                  lowerThreshold='20'
                  upperThreshold='20'
                  scrollTop='0'
                  style='height: 430px;'
                >
                  <AtInput
                    border={false}
                    name='value'
                    title='关键词'
                    type='text'
                    placeholder='请输入关键词'
                    value={this.state.kw}
                    onChange={this.handleInputChange.bind(this, 'kw')}
                  />
                  <AtInput
                    border={false}
                    name='value'
                    title='活动文案'
                    type='text'
                    placeholder='请输入文案'
                    value={this.state.writting}
                    onChange={this.handleInputChange.bind(this, 'writting')}
                  />
                  <AtSwitch title='过滤教师/助教' checked={this.state.filterTeacher} onChange={this.handleSwitchChange.bind(this, 'teacher')} border={false} />
                  <AtSwitch title='过滤活跃度低的人' checked={this.state.filtercountLimit} onChange={this.handleSwitchChange.bind(this, 'countLimit')} border={false} />
                  {
                    this.state.filtercountLimit === true
                      ? inputAvtive
                      : null
                  }
                  <AtSwitch title='过滤重复发言' checked={this.state.filterRepeat} onChange={this.handleSwitchChange.bind(this, 'repeat')} border={false} />

                  <AtInput
                    border={false}
                    name='value'
                    title='开始时间'
                    type='text'
                    placeholder='格式： 2019-04-23 12:00'
                    value={this.state.start}
                    onChange={this.handleInputChange.bind(this, 'start')}
                    onBlur={this.handleBlur.bind(this, 'start')}
                  />
                  <AtInput
                    name='value'
                    title='结束时间'
                    type='text'
                    placeholder='格式： 2019-04-23 12:00'
                    value={this.state.end}
                    onChange={this.handleInputChange.bind(this, 'end')}
                  />
                  <View style='font-size: 16px; margin-left: 18px'>
                    <Text>奖品内容</Text>
                    <Text onClick={this.handleawardList.bind(this, 'add')} className='change' >          加一个   </Text>
                    <Text onClick={this.handleawardList.bind(this, 'del')} className='change' >   减一个</Text>
                  </View>
                  {awardView}
                </ScrollView>
                <AtButton type='primary' customStyle='margin-top: 20px;width: 80%' onClick={this.request} loading={this.state.requesting} disabled={this.state.requesting}>开始抽奖</AtButton>
              </AtTabsPane>
              <AtTabsPane current={this.state.current} index={1}>
                <View className='tabContent'>
                  {
                    this.state.result.length === 0
                      ? emptyView
                      : resultView
                  }
                </View>
              </AtTabsPane>
            </AtTabs>
          </View>
        </View>
      </View>
    )
  }
}
