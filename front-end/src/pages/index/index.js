import Taro, { Component } from '@tarojs/taro'
import { View, Image } from '@tarojs/components'
import { AtTabs, AtTabsPane } from 'taro-ui'
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
      list: []
    }
  }
  handleClick(value) {
    this.setState({
      current: value
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
    return (
      <View className='body'>
        <View className='container'>
          <View className='wrap'>
            <AtTabs current={this.state.current} tabList={tabList} onClick={this.handleClick.bind(this)} className='tabs'>
              <AtTabsPane current={this.state.current} index={0} >
                <View className='tabContent'>

                </View>
              </AtTabsPane>
              <AtTabsPane current={this.state.current} index={1}>
                <View className='tabContent'>
                  {this.state.list.length === 0 ?
                    emptyView
                    :
                    <View> 1</View>}
                </View>
              </AtTabsPane>
            </AtTabs>
          </View>
        </View>
      </View>
    )
  }
}
