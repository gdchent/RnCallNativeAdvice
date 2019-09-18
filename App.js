/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow
 */

import React from 'react';
import { Platform, StyleSheet, Text, View, FlatList, TouchableOpacity, NativeModules, Dimensions } from 'react-native';

const instructions = Platform.select({
  ios: 'Press Cmd+R to reload,\n' + 'Cmd+D or shake for dev menu',
  android:
    'Double tap R on your keyboard to reload,\n' +
    'Shake or press menu button for dev menu',
});
export const { width, height } = Dimensions.get('window')

// 关闭警告
console.disableYellowBox = true

type Props = {};
export default class App extends React.PureComponent<Props> {
  render() {
    return (
      <View style={styles.container}>


        <FlatList
          data={[
            { index: 0, key: 'Banner广告-->原生BannerAct' },
            { index: 1, key: '原生Banner的广告' },
          ]}
          renderItem={this.renderItem}
          _keyExtractor = {(item, index) => index.toString() } //这里要调用toString方法 否则会抛出警告 查看控制台
          ItemSeparatorComponent={() => {
            return (
              <View style={{ width: width, height: 1, backgroundColor: 'red' }}></View>
            )
          }}
        />

      </View>
    );
  }
  //渲染界面       
  renderItem = ({ item }) => {
    return (
      <TouchableOpacity
        key={item.index}
        onPress={
          () => {
            this.rnCallNative(item)
          }
        }
        style={{ paddingVertical: 10, width: width, alignItems: 'center' }}
      >
        <Text>{item.key}</Text>
      </TouchableOpacity>
    )
  }


  //呼叫原生
  rnCallNative = (item) => {
    console.log(item)
    if (typeof item == 'object' && item != null) { //如果是对象
      let nativeObj = item
      NativeModules.AdViceModule.rnCallNative(JSON.stringify(nativeObj)); //这里注意传字符串 传对象藐视会出问题 可以一试
    }

  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },

});
