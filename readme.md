##### Android原生控件封装给RN作组件使用效果图

![image](https://github.com/gdchent/RnCallNativeAdvice/blob/master/effectImg/effectImg.png)

##### RN采坑记

手贱多删除了一行原生代码报如下错：

##### no viewmanager defined for class RNSVGline

解决地址：

https://stackoverflow.com/questions/50298404/getting-an-error-no-viewmanager-defined-for-class-rctrawtext

总结：就是在MainApplication里面的Packager里面删除了一个MainReactPackage

##### 运行测试

如果出现白屏，建议首先查看自己手机应用悬浮窗口是否打开（新版本一般不用了）

##### RN与android互相通信几种方式笔记

https://github.com/gdchent/NavigationApp/

##### 原生安卓给RN作控件使用 踩坑链接

https://stackoverflow.com/questions/39836356/react-native-resize-custom-ui-component

##### 关于Android自定义控件的一些基础知识：

https://www.cnblogs.com/zquan/p/9623990.html