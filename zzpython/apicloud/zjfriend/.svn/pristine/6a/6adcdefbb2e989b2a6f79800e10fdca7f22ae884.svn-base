<!DOCTYPE html>
<html lang='zh-CN'>
    <head>
        <meta charset='utf-8'>
        <meta name='viewport' content='maximum-scale=1.0,minimum-scale=1.0,user-scalable=no,width=device-width,initial-scale=1.0'/>
        <link rel='stylesheet' type='text/css' href='../css/bootstrap.min.css' />
		<link rel='stylesheet' href='../css/github.css'>
		<style type='text/css'>
		  .diy-btn-group-test {margin : 10px 0px 0px 0px;}
		</style>
		
    </head>
    <body>
<div class="panel panel-info">
  <div class="panel-heading">
    <h3 class="panel-title">open 打开</h3>
  </div>
  <div class="panel-body">
		<pre class="pre-scrollable">
var tabBarMenu = api.require("tabBarMenu");

var theme = "simple"; // 可以支持自定义主题,示例内置两种风格主题:simple,night.

tabBarMenu.open({ // 打开标签菜单.
    defaultBarSelect: 1, // 默认选中的标签栏按钮
    autoLayout: true, // 是否自动调整当前页面网页视图的位置.
    barConfig: { // 标签栏通用配置信息.
        bgImg: "widget://image/tabBarMenu/" + theme + "/tabbar_bg.png"    // 背景图片路径.
    },
    barItemConfig: { // 标签栏按钮的通用配置信息.
        titleColor: "#ffffff",      // 文本颜色, 格式为#fff、#ffffff、rgba(r,g,b,a)等
        titleSelectColor: "#ffffff",    //  选中状态时,按钮文本的颜色, 默认与titleColor相同.
        fontSize: 11.0,        // 文字大小.
        textMarginTop: 41.0,   // 文本距离按钮上边界的距离.
        primaryItem: 2      // 激活弹出菜单的标签栏按钮的下标.
    },
    barItems: [ // 标签栏各按钮的信息.
        {
            title: "动态",          // 标题.
            bgImg: "widget://image/tabBarMenu/" + theme + "/tabbar_icon_auth.png",          // 背景图片路径.
            bgImgClick: "widget://image/tabBarMenu/" + theme + "/tabbar_icon_auth_click.png"      // 被点击时的背景图片路径.
        },
        {
            title: "与我有关",          // 标题.
            bgImg: "widget://image/tabBarMenu/" + theme + "/tabbar_icon_at.png",          // 背景图片路径.
            bgImgClick: "widget://image/tabBarMenu/" + theme + "/tabbar_icon_at_click.png"      // 被点击时的背景图片路径.
        },
        {
            title: "",          // 标题.
            bgImg: "widget://image/tabBarMenu/" + theme + "/tabbar_btn.png",          // 背景图片路径.
            bgImgClick: "widget://image/tabBarMenu/" + theme + "/tabbar_btn_click.png"      // 被点击时的背景图片路径.
        },
        {
            title: "玩吧",          // 标题.
            bgImg: "widget://image/tabBarMenu/" + theme + "/tabbar_icon_more.png",          // 背景图片路径.
            bgImgClick: "widget://image/tabBarMenu/" + theme + "/tabbar_icon_more_click.png"      // 被点击时的背景图片路径.
        },
        {
            title: "空间",          // 标题.
            bgImg: "widget://image/tabBarMenu/" + theme + "/tabbar_icon_space.png",          // 背景图片路径.
            bgImgClick: "widget://image/tabBarMenu/" + theme + "/tabbar_icon_space_click.png"      // 被点击后的背景图片路径.
        }
    ],
    menuConfig: { // 菜单栏通用配置.
        coverBgColor: "#000000",     // 遮罩的背景色, 格式为#fff、#ffffff、rgba(r,g,b,a)等.
        coverAlpha: 0.8,       // 遮罩的透明度, 取值范围0.0~1.0.
        rows: 4        // 单页菜单每行显示的按钮数.
    },
    menuItemConfig: { // 菜单栏各按钮通用配置信息.
        titleColor: "#ffffff",     // 文本颜色, 格式为#fff、#ffffff、rgba(r,g,b,a)等.
        titleSelectColor:"#ffffff",	//  选中状态时,按钮文本的颜色, 默认与titleColor相同.
        fontSize: 11.0,       // 字体大小.
        textMarginTop: 90.0   // 文本距离按钮上边界的距离.
    },
    menuItems: [ // 菜单栏各按钮的信息.
        {
            title: "说说",      //标题.
            bgImg: "widget://image/tabBarMenu/" + theme + "/tabbar_btn_popup_talk.png",      // 背景图片.
            bgImgClick: "widget://image/tabBarMenu/" + theme + "/tabbar_btn_popup_talk_click.png"  // 被点击时的背景图片.
        },
        {
            title: "照片",      //标题.
            bgImg: "widget://image/tabBarMenu/" + theme + "/tabbar_btn_popup_transferphotos.png",      // 背景图片.
            bgImgClick: "widget://image/tabBarMenu/" + theme + "/tabbar_btn_popup_transferphotos_click.png"  // 被点击时的背景图片.
        },
        {
            title: "水印相机",      //标题.
            bgImg: "widget://image/tabBarMenu/" + theme + "/tabbar_btn_popup_watermarkcamera.png",      // 背景图片.
            bgImgClick: "widget://image/tabBarMenu/" + theme + "/tabbar_btn_popup_watermarkcamera_click.png"  // 被点击时的背景图片.
        },
        {
            title: "视频",      //标题.
            bgImg: "widget://image/tabBarMenu/" + theme + "/tabbar_btn_popup_video.png",      // 背景图片.
            bgImgClick: "widget://image/tabBarMenu/" + theme + "/tabbar_btn_popup_video_click.png"  // 被点击时的背景图片.
        },
        {
            title: "签到",      //标题.
            bgImg: "widget://image/tabBarMenu/" + theme + "/tabbar_btn_popup_registration.png",      // 背景图片.
            bgImgClick: "widget://image/tabBarMenu/" + theme + "/tabbar_btn_popup_registration_click.png"  // 被点击时的背景图片.

        },
        {
            title: "连拍",      //标题.
            bgImg: "widget://image/tabBarMenu/" + theme + "/tabbar_btn_popup_continuousshooting.png",      // 背景图片.
            bgImgClick: "widget://image/tabBarMenu/" + theme + "/tabbar_btn_popup_continuousshooting_click.png"  // 被点击时的背景图片.
        },
        {
            title: "日志",      //标题.
            bgImg: "widget://image/tabBarMenu/" + theme + "/tabbar_btn_popup_journal.png",      // 背景图片.
            bgImgClick: "widget://image/tabBarMenu/" + theme + "/tabbar_btn_popup_journal_click.png"  // 被点击时的背景图片.
        },
        {
            title: "二维码",      //标题.
            bgImg: "widget://image/tabBarMenu/" + theme + "/tabbar_btn_popup_2dbarcode.png",      // 背景图片.
            bgImgClick: "widget://image/tabBarMenu/" + theme + "/tabbar_btn_popup_2dbarcode_click.png"  // 被点击时的背景图片.
        },
        {
            title: "语音相机",      //标题.
            bgImg: "widget://image/tabBarMenu/" + theme + "/tabbar_btn_popup_videocamera.png",      // 背景图片.
            bgImgClick: "widget://image/tabBarMenu/" + theme + "/tabbar_btn_popup_videocamera_click.png"  // 被点击时的背景图片.
        },
        {
            title: "位置",      //标题.
            bgImg: "widget://image/tabBarMenu/" + theme + "/tabbar_btn_popup_place.png",      // 背景图片.
            bgImgClick: "widget://image/tabBarMenu/" + theme + "/tabbar_btn_popup_place_click.png"  // 被点击时的背景图片.
        }
    ]
}, function(ret, err){ // 点击标签栏或菜单栏按钮时的回调方法.点击激活菜单栏的标签栏按钮时不会触发此方法.
    if(ret){
        var item = ret.item;
		
		if("menu" == item.type){
			tabBarMenu.hideMenu(); // 隐藏弹出菜单.
		}
		
        api.alert({title: "提示", msg: "您点击了 " + item.type + " 上,第 " + item.index + " 个按钮!", buttons: ["确定"]});
    }
});
	</pre>
</div>
<div class="panel-footer">	
	<div class="btn-group btn-group-justified">
	  <div class="btn-group">
	    <button role="run" type="button" class="btn btn-primary">运行</button>
	  </div>	  
	  <div class="btn-group">
	    <button  role="edit" type="button" class="btn btn-info">编辑</button>
	  </div>
	  <div class="btn-group">
	    <button  role="restore" type="button" class="btn btn-warning">还原</button>
	  </div>
	</div>
</div>
</div>

<div class="panel panel-info">
    <div class="panel-heading">
        <h3 class="panel-title">hide 隐藏</h3>
    </div>
    <div class="panel-body">
		<pre class="pre-scrollable">
var tabBarMenu = api.require("tabBarMenu");

tabBarMenu.hide();
	</pre>
    </div>
    <div class="panel-footer">
        <div class="btn-group btn-group-justified">
            <div class="btn-group">
                <button role="run" type="button" class="btn btn-primary">运行</button>
            </div>
            <div class="btn-group">
                <button  role="edit" type="button" class="btn btn-info">编辑</button>
            </div>
            <div class="btn-group">
                <button  role="restore" type="button" class="btn btn-warning">还原</button>
            </div>
        </div>
    </div>
</div>

<div class="panel panel-info">
    <div class="panel-heading">
        <h3 class="panel-title">show 显示</h3>
    </div>
    <div class="panel-body">
		<pre class="pre-scrollable">
var tabBarMenu = api.require("tabBarMenu");

tabBarMenu.show();
	</pre>
    </div>
    <div class="panel-footer">
        <div class="btn-group btn-group-justified">
            <div class="btn-group">
                <button role="run" type="button" class="btn btn-primary">运行</button>
            </div>
            <div class="btn-group">
                <button  role="edit" type="button" class="btn btn-info">编辑</button>
            </div>
            <div class="btn-group">
                <button  role="restore" type="button" class="btn btn-warning">还原</button>
            </div>
        </div>
    </div>
</div>

<div class="panel panel-info">
    <div class="panel-heading">
        <h3 class="panel-title">setBarSelect 模拟点击标签栏</h3>
    </div>
    <div class="panel-body">
		<pre class="pre-scrollable">
var tabBarMenu = api.require("tabBarMenu");

tabBarMenu.setBarSelect({ // 设置当前选中的按钮.
    index: 3 // 要设置选中状态的按钮的下标.
});
	</pre>
    </div>
    <div class="panel-footer">
        <div class="btn-group btn-group-justified">
            <div class="btn-group">
                <button role="run" type="button" class="btn btn-primary">运行</button>
            </div>
            <div class="btn-group">
                <button  role="edit" type="button" class="btn btn-info">编辑</button>
            </div>
            <div class="btn-group">
                <button  role="restore" type="button" class="btn btn-warning">还原</button>
            </div>
        </div>
    </div>
</div>

<div class="panel panel-info">
    <div class="panel-heading">
        <h3 class="panel-title">setBadge 设置徽章</h3>
    </div>
    <div class="panel-body">
		<pre class="pre-scrollable">
var tabBarMenu = api.require("tabBarMenu");

tabBarMenu.setBadge({ // 设置徽章.
    item: {
        type: "bar",	    //按钮所属控件,可选”bar”, “menu”
        index: 0		//按钮的下标. 标签栏和菜单部分的按钮的下标均分别从 0 开始计数
    },
    title: "", // 要设置的按钮的内容.
    type: "center", // 徽章风格,可选”left”, “center”, “right”.
    bgColor: "#ff0000",    // 徽章的背景色,格式为#fff、#ffffff、rgba(r,g,b,a)等.
    titleColor: "#ffffff",  // 文本颜色, 格式为#fff、#ffffff、rgba(r,g,b,a)等.
    fontSize: 11.0,   // 字体大小.
    marginTop: 17.0       // 徽章距离按钮上边缘的距离.
});

tabBarMenu.setBadge({ // 设置徽章.
    item: {
        type: "bar",	    //按钮所属控件,可选”bar”, “menu”
        index: 3		//按钮的下标. 标签栏和菜单部分的按钮的下标均分别从 0 开始计数
    },
    title: "NEW", // 要设置的按钮的内容.
    type: "right", // 徽章风格,可选”left”, “center”, “right”.
    bgColor: "#ff0000",    // 徽章的背景色,格式为#fff、#ffffff、rgba(r,g,b,a)等.
    titleColor: "#ffffff",  // 文本颜色, 格式为#fff、#ffffff、rgba(r,g,b,a)等.
    fontSize: 11.0,   // 字体大小.
    marginTop: 17.0       // 徽章距离按钮上边缘的距离.
});

tabBarMenu.setBadge({ // 设置徽章.
    item: {
        type: "menu",	    //按钮所属控件,可选”bar”, “menu”
        index: 3		//按钮的下标. 标签栏和菜单部分的按钮的下标均分别从 0 开始计数
    },
    title: "NEW", // 要设置的按钮的内容.
    type: "right", // 徽章风格,可选”left”, “center”, “right”.
    bgColor: "#ff0000",    // 徽章的背景色,格式为#fff、#ffffff、rgba(r,g,b,a)等.
    titleColor: "#ffffff",  // 文本颜色, 格式为#fff、#ffffff、rgba(r,g,b,a)等.
    fontSize: 11.0,   // 字体大小.
    marginTop: 15.0       // 徽章距离按钮上边缘的距离.
});
	</pre>
    </div>
    <div class="panel-footer">
        <div class="btn-group btn-group-justified">
            <div class="btn-group">
                <button role="run" type="button" class="btn btn-primary">运行</button>
            </div>
            <div class="btn-group">
                <button  role="edit" type="button" class="btn btn-info">编辑</button>
            </div>
            <div class="btn-group">
                <button  role="restore" type="button" class="btn btn-warning">还原</button>
            </div>
        </div>
    </div>
</div>

<div class="panel panel-info">
    <div class="panel-heading">
        <h3 class="panel-title">removeBadge 移除徽章</h3>
    </div>
    <div class="panel-body">
		<pre class="pre-scrollable">
var tabBarMenu = api.require("tabBarMenu");

tabBarMenu.removeBadge({ // 移除徽章.
    type: "bar",	//按钮所属控件,可选”bar”, “menu”
    index: 3		//按钮的下标. 标签栏和菜单部分的按钮的下标均分别从 0 开始计数
});

tabBarMenu.removeBadge({ // 移除徽章.
    type: "menu",	//按钮所属控件,可选”bar”, “menu”
    index: 3		//按钮的下标. 标签栏和菜单部分的按钮的下标均分别从 0 开始计数
});
	</pre>
    </div>
    <div class="panel-footer">
        <div class="btn-group btn-group-justified">
            <div class="btn-group">
                <button role="run" type="button" class="btn btn-primary">运行</button>
            </div>
            <div class="btn-group">
                <button  role="edit" type="button" class="btn btn-info">编辑</button>
            </div>
            <div class="btn-group">
                <button  role="restore" type="button" class="btn btn-warning">还原</button>
            </div>
        </div>
    </div>
</div>

<div class="panel panel-info">
    <div class="panel-heading">
        <h3 class="panel-title">showMenu 显示弹出菜单</h3>
    </div>
    <div class="panel-body">
		<pre class="pre-scrollable">
var tabBarMenu = api.require("tabBarMenu");
tabBarMenu.showMenu();
	</pre>
    </div>
    <div class="panel-footer">
        <div class="btn-group btn-group-justified">
            <div class="btn-group">
                <button role="run" type="button" class="btn btn-primary">运行</button>
            </div>
            <div class="btn-group">
                <button  role="edit" type="button" class="btn btn-info">编辑</button>
            </div>
            <div class="btn-group">
                <button  role="restore" type="button" class="btn btn-warning">还原</button>
            </div>
        </div>
    </div>
</div>

<div class="panel panel-info">
    <div class="panel-heading">
        <h3 class="panel-title">hideMenu 隐藏弹出菜单</h3>
    </div>
    <div class="panel-body">
		<pre class="pre-scrollable">
// 注意: 此方法的作用参见 open 方法的回调函数. 
var tabBarMenu = api.require("tabBarMenu");

tabBarMenu.hideMenu();
	</pre>
    </div>
    <div class="panel-footer">
        <div class="btn-group btn-group-justified">
            <div class="btn-group">
                <button role="run" type="button" class="btn btn-primary">运行</button>
            </div>
            <div class="btn-group">
                <button  role="edit" type="button" class="btn btn-info">编辑</button>
            </div>
            <div class="btn-group">
                <button  role="restore" type="button" class="btn btn-warning">还原</button>
            </div>
        </div>
    </div>
</div>

<div class="panel panel-info">
    <div class="panel-heading">
        <h3 class="panel-title">close 关闭</h3>
    </div>
    <div class="panel-body">
		<pre class="pre-scrollable">
var tabBarMenu = api.require("tabBarMenu");

tabBarMenu.close();
	</pre>
    </div>
    <div class="panel-footer">
        <div class="btn-group btn-group-justified">
            <div class="btn-group">
                <button role="run" type="button" class="btn btn-primary">运行</button>
            </div>
            <div class="btn-group">
                <button  role="edit" type="button" class="btn btn-info">编辑</button>
            </div>
            <div class="btn-group">
                <button  role="restore" type="button" class="btn btn-warning">还原</button>
            </div>
        </div>
    </div>
</div>
    </body>
	<script src="../script/jquery-1.11.1.min.js"></script>
	<script src='../script/highlight.pack.js'></script>
	<script src='../script/demo2.js'></script>
</html>