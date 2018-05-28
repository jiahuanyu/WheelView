## Android 自定义WheelView控件 学习参考
![](./screen.gif)

### 使用
根工程中的`build.gradle`添加maven仓库
```
...
 maven {
    url 'https://dl.bintray.com/vendor-yu/maven'
 }
...
```


工程目录中的`build.gradle`添加实现
```
implementation 'me.jiahuan.android.wheelview:wheel-view:0.5.0'
```


在布局文件中添加
```xml
<me.jiahuan.android.wheelview.WheelView
    android:id="@+id/id_wheel_view"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:cycle="true"
    app:display_item_count="3"
    app:selected_text_color="@android:color/holo_red_light"
    app:unselected_text_color="@android:color/holo_blue_light" />
```



### 自定义控件属性
```xml
<attr name="unselected_text_color" format="color" /> // 未选中颜色
<attr name="selected_text_color" format="color" /> // 选中颜色

<attr name="text_size" format="dimension" />   // 文字大小

<attr name="horizontal_text_space" format="dimension" /> // 水平间距
<attr name="vertical_text_space" format="dimension" />  // 垂直间距

<attr name="display_item_count" format="integer" />  // 显示的item数目，基数

<attr name="component_bound_width" format="dimension" /> // 控件边框宽度
<attr name="component_bound_color" format="color" />  // 控件边框颜色
<attr name="component_selected_item_up_down_line_width" format="dimension" />  // 控件中间项上下线宽度
<attr name="component_selected_item_up_down_line_color" format="color" />// 控件中间项上下线颜色
<attr name="component_background_color" format="color" /> // 控件背景色

<attr name="cycle" format="boolean" /> // 是否数据循环
```


### API
```java
WheelView.bindData(List<String>); // 绑定数据源
WheelView.notifyDataChanged();  // 数据源数据发生变化
WheelView.setSelectedIndex(int); // 设置选中项
WheelView.smoothScrollToSelectedIndex(int); // 光滑滑动到选中项
```
