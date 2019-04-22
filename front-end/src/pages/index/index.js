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
        { name: 'xxx', qq: '111111111', jx: 'xxx' },
        { name: 'xxx', qq: '111111111', jx: 'xxx' },
        { name: 'xxx', qq: '111111111', jx: 'xxx' },
        { name: 'xxx', qq: '111111111', jx: 'xxx' },
        { name: 'xxx', qq: '111111111', jx: 'xxx' },
        { name: 'xxx', qq: '111111111', jx: 'xxx' },
        { name: 'xxx', qq: '111111111', jx: 'xxx' },
        { name: 'xxx', qq: '111111111', jx: 'xxx' },
        { name: 'xxx', qq: '111111111', jx: 'xxx' },
        { name: 'xxx', qq: '111111111', jx: 'xxx' },
        { name: 'xxx', qq: '111111111', jx: 'xxx' },
        { name: 'xxx', qq: '111111111', jx: 'xxx' },
        { name: 'xxx', qq: '111111111', jx: 'xxx' },
        { name: 'xxx', qq: '111111111', jx: 'xxx' },
      ],
      filterTeacher: false,
      filterUnActive: false,
      filterRepeat: false,
      jxIndexList: [0, 1, 2],
      jx: [
        { name: '', number: 0 },
        { name: '', number: 0 },
        { name: '', number: 0 }
      ],
      requesting: false,
      start: '',
      end: '',
      kw: '',
      wenan: '',
      unactive: 0
    }
  }
  handleInputChange(name, value) {
    switch (name) {
      case 'wenan':
        this.setState({
          wenan: value
        });
        break;
      case 'kw':
        this.setState({
          kw: value
        });
        break;
      case 'unactive':
        this.setState({
          unactive: value
        });
        break;
      case 'repeat':
        this.setState({
          unactive: value
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
    console.log([`${this.state.jx[index].number}`])
    const jx = this.state.jx
    switch (name) {
      case 'jxm':
        jx[index].name = value
        this.setState({
          jx: jx
        });
        break;
      case 'jxr':
        jx[index].number = value
        this.setState({
          jx: jx
        });
        break;
    }
    // 在小程序中，如果想改变 value 的值，需要 `return value` 从而改变输入框的当前值
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
      case 'unActive':
        this.setState({
          filterUnActive: !this.state.filterUnActive
        })
        break
    }
  }
  handleJxList = (type) => {
    const jxList = this.state.jxIndexList;
    const jx = this.state.jx
    switch (type) {
      case 'add':
        jxList.push(jx.length)
        jx.push({ name: '', number: 0 })
        this.setState({
          jxIndexList: jxList,
          jx: jx
        })
        break;
      case 'del':
        if (jxList.length === 1) {
          Taro.showModal({
            title: '无法删除',
            content: '至少需要一个奖项'
          })
          return;
        }
        jxList.pop()
        jx.pop()
        this.setState({
          jxIndexList: jxList,
          jx: jx
        })
        break;
    }
  }

  request = () => {
    this.setState({
      requesting: true
    })
    const { kw, wenan, filterRepeat, filterTeacher, filterUnActive, unactive, start, end, jx } = this.state
    Taro.request({
      url: 'http://127.0.0.1:8080',
      data: {
        keyword: kw,
        wenan: wenan,
        filterTeacher: filterTeacher,
        filterUnActive: filterUnActive,
        unactive: unactive,
        filterRepeat: filterRepeat,
        start: start,
        end: end,
        jx: JSON.stringify(jx)
      }
    }).then((data) => {
      this.setState({
        result: data,
        current: 1
      })
      Taro.showToast({
        title: '抽奖完成'
      })
    })
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
        fontSize='20'
        name='value'
        title='去掉活跃度低于'
        type='number'
        placeholder='0'
        value={this.state.unactive}
        onChange={this.handleInputChange.bind(this, 'unactive')}
      />
    )
    const jxView = (
      this.state.jxIndexList.map((index) => {
        return (
          <View key={index}>
            <AtInput
              border={false}
              name='value'
              title={'奖项' + index}
              type='text'
              placeholder={'请输入奖项' + index}
              value={this.state.jx[index]['name']}
              onChange={this.handleInputChange2.bind(this, 'jxm', index)}
            />
            <AtInput
              border={false}
              name='value'
              title={'奖项' + index + '人数'}
              type='number'
              placeholder={'请输入奖项' + index + '人数'}
              value={this.state.jx[index]['number']}
              onChange={this.handleInputChange2.bind(this, 'jxr', index)}
            />
          </View>
        )
      }))

    const resultView = (
      <ScrollView
        className='tabContent'
        scrollY
        scrollWithAnimation
        lowerThreshold='20'
        upperThreshold='20'
        scrollTop='0'
        style='height: 500px;'
      >{
          this.state.result.map((value => {
            return (
              <View key={value.qq} style='margin-bottom: 20px'>
                <AtCard
                  extra={value.qq}
                  title={value.name}
                >
                  {value.jx}
                </AtCard>
              </View>
            )
          }))
        }
      </ScrollView>
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
                    value={this.state.wenan}
                    onChange={this.handleInputChange.bind(this, 'wenan')}
                  />
                  <AtSwitch title='过滤教师/助教' checked={this.state.filterTeacher} onChange={this.handleSwitchChange.bind(this, 'teacher')} border={false} />
                  <AtSwitch title='过滤活跃度低的人' checked={this.state.filterUnActive} onChange={this.handleSwitchChange.bind(this, 'unActive')} border={false} />
                  {
                    this.state.filterUnActive === true
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
                    <Text onClick={this.handleJxList.bind(this, 'add')} className='change' >          加一个   </Text>
                    <Text onClick={this.handleJxList.bind(this, 'del')} className='change' >   减一个</Text>
                  </View>
                  {jxView}
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
