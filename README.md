## Android 自定义WheelView控件 学习参考
<video width="320" height="240" loop autoplay>
    <source src="screen.webm" type="video/webm">
</video>

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
implementation 'me.jiahuan.android.wheelview:wheel-view:0.3.2'
```



### 自定义选项
```xml
<attr name="unselected_text_color" format="color" /> // 未选中颜色
<attr name="selected_text_color" format="color" /> // 选中颜色

<attr name="text_size" format="dimension" />   // 文字大小

<attr name="horizontal_text_space" format="dimension" /> // 水平间距
<attr name="vertical_text_space" format="dimension" />  // 垂直间距

<attr name="display_item_count" format="integer" />  // 显示的item数目，基数

<attr name="component_bound_width" format="dimension" /> // 控件边框宽度
<attr name="component_bound_color" format="color" />  // 控件边框颜色
<attr name="component_selected_item_up_down_line_width" format="dimension" />  // 控件中间项上下线条
<attr name="component_selected_item_up_down_line_color" format="color" />
<attr name="component_background_color" format="color" /> // 控件背景色

<attr name="cycle" format="boolean" />
```
