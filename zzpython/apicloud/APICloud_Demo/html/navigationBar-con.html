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
    <h3 class="panel-title">打开-->显示-->隐藏-->关闭</h3>
  </div>
  <div class="panel-body">
		<pre class="pre-scrollable">
/* 打开默认配置的导航条;
3秒后,自动隐藏;
又3秒,自动显示;
又3秒,自动关闭,关闭前会有"确认关闭"的提示;
在此期间,用户点击除第一个以外的按钮,会弹窗提示被点击按钮的内容;
点击按钮,按钮将尝试居中显示;
在此期间,用户点击第一个按钮的行为,不做观察;
 */
var navigationBar = api.require("navigationBar");

var params = {
    h: 44,
    itemSize: {
        w: api.frameWidth / 4.0
    },
    items: [
        {
            title:"开发讨论"
        },
        {
            title:"模板分享"
        },
        {
            title:"BUG提交"
        },
        {
            title:"建议反馈"
        },
        {
            title:"谈天说地"
        },
        {
            title:"官方公告"
        }
    ],
    fixedOn: api.frameName
};

function callback(ret, err){
    if(! ret){ // 出错了.
        api.alert({title: "出错了", msg: err["msg"]});
        return;
    }

        var idx = ret["index"];
        var content = params["items"][ret["index"]]["title"];

        api.alert({title: "提示", msg: "您选择了: " + content});

    /* 3秒后自动隐藏. */
    var timeout = 3000;

    setTimeout(function(){
        navigationBar.hide({id: ret["id"]});
    }, timeout);

    /* 又3秒, 自动显示. */
    timeout += 3000;

    setTimeout(function(){
        navigationBar.show({id: ret["id"]});
    }, timeout);

    /* 又3秒,自动关闭. */
    timeout += 3000;

    setTimeout(function(){
        api.alert({
            title: "提示",
            msg: "导航条即将关闭!",
            buttons: ["确定"]
        }, function (alertRet,alertErr) {
            navigationBar.close({id: ret["id"]});
        });
    }, timeout);
}

navigationBar.open(params, callback);
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
        <h3 class="panel-title">打开一个自定义的导航条.</h3>
    </div>
    <div class="panel-body">
		<pre class="pre-scrollable">
/* 打开一个自定义的竖向导航条;
 导航条背景和导航项背景均为自定义图片;
 点击导航项会有相应文本提示;
 默认选中第三个导航项;
 导航项被选中时,将自动尝试移动到导航条中部,文本,背景,字号等会相应变化;
 点击最后一个导航项,将关闭导航条,关闭前有提示;
 */

var navigationBar = api.require("navigationBar");
var params = {
    x: 0.0,
    y: 0.0,
    w: 88.0,
    h: api.frameHeight,
    style: "up_to_down",
    itemSize: {
        w: 100.0,
        h: 44
    },
    items: [
        {
            title:"端设置",
            titleSelected:"APICloud",
            bg:"widget://image/navigationBar/item.png",
            alpha: 0.5,
            bgSelected:"#ff0000"
        },
        {
            title:"证书",
            titleSelected:"APICloud",
            bg:"widget://image/navigationBar/item.png",
            alpha: 0.5,
            bgSelected:"#ff0000"
        },
        {
            title:"代码",
            titleSelected:"APICloud",
            bg:"widget://image/navigationBar/item.png",
            alpha: 0.5,
            bgSelected:"#ff0000"
        },
        {
            title:"模块",
            titleSelected:"APICloud",
            bg:"widget://image/navigationBar/item.png",
            alpha: 0.5,
            bgSelected:"#ff0000"
        },
        {
            title:"云编译",
            titleSelected:"APICloud",
            bg:"widget://image/navigationBar/item.png",
            alpha: 0.5,
            bgSelected:"#ff0000"
        },
        {
            title:"云设置",
            titleSelected:"APICloud",
            bg:"widget://image/navigationBar/item.png",
            alpha: 0.5,
            bgSelected:"#ff0000"
        },{
            title:"API分析",
            titleSelected:"APICloud",
            bg:"widget://image/navigationBar/item.png",
            alpha: 0.5,
            bgSelected:"#ff0000"
        },
        {
            title:"Database",
            titleSelected:"APICloud",
            bg:"widget://image/navigationBar/item.png",
            alpha: 0.5,
            bgSelected:"#ff0000"
        },
        {
            title:"API调试",
            titleSelected:"APICloud",
            bg:"widget://image/navigationBar/item.png",
            alpha: 0.5,
            bgSelected:"#ff0000"
        },
        {
            title:"版本",
            titleSelected:"APICloud",
            bg:"widget://image/navigationBar/item.png",
            alpha: 0.5,
            bgSelected:"#ff0000"
        },
        {
            title:"云修复",
            titleSelected:"APICloud",
            bg:"widget://image/navigationBar/item.png",
            alpha: 0.5,
            bgSelected:"#ff0000"
        },
        {
            title:"推送",
            titleSelected:"APICloud",
            bg:"widget://image/navigationBar/item.png",
            alpha: 0.5,
            bgSelected:"#ff0000"
        },
        {
            title:"统计",
            titleSelected:"APICloud",
            bg:"widget://image/navigationBar/item.png",
            alpha: 0.5,
            bgSelected:"#ff0000"
        }
    ],
    selectedIndex: 2,
    font: {
        size: 20,
        sizeSelected: 15,
        color: "#ff00ff",
        colorSelected: "#ffff00"
    },
    bg: "widget://image/navigationBar/bg.png",
    alpha: 0.8,
    fixedOn: api.frameName
};

function callback(ret, err){
    if(! ret){
        api.alert({title: "出错了", msg: err["msg"]});
        return;
    }

    var idx = ret["index"];
    var content = params["items"][ret["index"]]["title"] + "/" + params["items"][ret["index"]]["titleSelected"];

    api.alert({title: "提示", msg: "您选择了: " + content});

    if((params["items"].length - 1) == ret["index"]){
        api.alert({title: "提示", msg: "导航即将关闭!"},function(){
            navigationBar.close({id: ret["id"]});
        });
    }

}

navigationBar.open(params, callback);
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
        <h3 class="panel-title">动态获取与设置模块参数</h3>
    </div>
    <div class="panel-body">
		<pre class="pre-scrollable">
/* 打开一个默认配置的导航条;
 3秒后,将导航条移动到frame底部;
 导航条移动到frame底部时,改变背景图;
 背景图返回后,设置最后一个按钮为选中状态,此时会提示关闭导航条;
 自动获取当前模块高度,并弹窗提示;
 */
var navigationBar = api.require("navigationBar");

var params = {
    h: 44,
    items: [
        {
            title:"开发讨论"
        },
        {
            title:"模板分享"
        },
        {
            title:"BUG提交"
        },
        {
            title:"建议反馈"
        }
    ],
    font:{
         color: "#FF0000",
         alpha: 0.5
    },
    fixedOn: api.frameName
};

function callback(ret, err){
    if(! ret){ // 出错了.
        api.alert({title: "出错了", msg: err["msg"]});
        return;
    }

    if((params["items"].length - 1) == ret["index"]){
        api.alert({title: "提示", msg: "导航即将关闭!"},function(){
            navigationBar.close({id: ret["id"]});
        });
        return;
    }

    setTimeout(function(){
        navigationBar.config({
                id: ret["id"],
                key: "y",
                value: api.frameHeight - 44},
            function(configRet, configErr){
                if(configRet){
                    navigationBar.config({
                        id: ret["id"],
                        key: "bg",
                        value: "widget://image/navigationBar/bg.png"
                    }, function(configBgRet,configBgErr){
                        if(configBgRet){
                            navigationBar.config({
                                id: ret["id"],
                                key: "selectedIndex",
                                value: params["items"].length - 1
                            }, function(configSelRet, configSelErr){
                                if(configSelRet){
                                    navigationBar.config({
                                        id: ret["id"],
                                        key: "h"
                                    }, function(configWRet, configWErr){
                                        if(configWRet){
                                            var msg = "导航条当前高度为: " + configWRet["oldValue"];
                                            api.alert({"title": "提示", "msg": msg});
                                        }
                                    });
                                }
                            });
                        }
                    });
                }
            });
    }, 3000);
}

navigationBar.open(params, callback);
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