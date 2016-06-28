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
// 使用默认设置显示文件内容;点击,向上/向下翻页,会有提示;在最后一页往后翻页,在第一页向前翻页,会使模块关闭.
var bookReader = api.require('bookReader');

bookReader.close();

bookReader.open({
    filePath:"widget://res/bookReader/test.txt"
},function(ret, err){
    if( ! ret){
        api.alert({title: "出错了", msg: err["msg"]});
    }

    if("page_over" == ret["eventType"] ||
        "page_begin" == ret["eventType"]){
        bookReader.close();
        return;
    }

    api.alert({
        title: "提示",
        msg: "事件类型: " + ret["eventType"] + "\n阅读当前进度: " + ret["progress"]
    });
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
        <h3 class="panel-title">setValue 设置参数</h3>
    </div>
    <div class="panel-body">
		<pre class="pre-scrollable">
//打开阅读器; 3秒后自动 更改背景, 字体,进度,数据源等;设置成功或失败时,会有相关提示.
var bookReader = api.require('bookReader');

bookReader.close();

bookReader.open({
    filePath:"widget://res/bookReader/test.txt",
    fixedOn: api.frameName
});

setTimeout(function(){
    bookReader.setValue({
        bg: "widget://image/bookReader/bg.png",
        progress: "80",
        textStyle: {
            size: 30,
            color: "#0000FF"
        },
        filePath: "widget://res/bookReader/test2.txt"
    }, function(ret,err){
        if(ret && ret["status"]){
            api.alert({title: "提示", msg: "设置成功!"});
            return;
        }

        api.alert({title: "出错了", msg: err["msg"]});
    });
}, 3000);
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
        <h3 class="panel-title">打开 显示 隐藏 关闭</h3>
    </div>
    <div class="panel-body">
		<pre class="pre-scrollable">
// 使用自定义配置打开阅读器;3秒后,自动隐藏; 3秒后,自动显示;3秒后,自动关闭.
var bookReader = api.require('bookReader');

bookReader.close();

bookReader.open({
    x:0,
    y:100,
    w: api.frameWidth,
    h: api.frameHeight,
    bg: "widget://image/bookReader/bg.png",
    progress: "50",
    textStyle: {
        size: 20,
        color: "#00FF00"
    },
    filePath: "widget://res/bookReader/test.txt",
    fixedOn: api.frameName
});

setTimeout(function(){
    bookReader.hide();

    setTimeout(function(){
        bookReader.show();

        setTimeout(function(){
             bookReader.close();
        }, 3000);
    }, 3000);

}, 3000);
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
        <h3 class="panel-title">获取指定文件的进度  设置屏幕亮度</h3>
    </div>
    <div class="panel-body">
        <pre class="pre-scrollable">
/*
获取指定文件的阅读进度, toast 提示;
获取屏幕亮度, toast 提示;
* */
var bookReader = api.require('bookReader');

bookReader.getProgress({
    filePath: "widget://res/bookReader/test.txt"
},function(ret,err){
    if(ret.status){
        api.toast({
            msg: '当前阅读进度: ' + ret.progress,
            duration: 1000,
            location: 'top'
        });
    }else{
        api.toast({
            msg: '出错了: ' + err.msg,
            duration: 1000,
            location: 'top'
        });
    }

    var brightness = 50;
    bookReader.setBrightness({
        brightness: brightness
    },function(ret,err){
        if(ret.status){
            api.toast({
                msg: '设置成功!当前亮度为: ' + brightness,
                duration: 1000,
                location: 'top'
            });
            return;
        }

        api.toast({
            msg: "设置失败",
            duration: 1000,
            location: 'top'
        });
    });
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
        <h3 class="panel-title">获取当前屏幕亮度</h3>
    </div>
    <div class="panel-body">
        <pre class="pre-scrollable">
/*
设置、获取当前屏幕亮度;
* */
var bookReader = api.require('bookReader');

bookReader.getBrightness(function(ret,err){
    if(ret.status){
        api.toast({
            msg: '当前亮度为: ' + ret.brightness,
            duration: 1000,
            location: 'top'
        });
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
        <h3 class="panel-title">设置当前屏幕亮度</h3>
    </div>
    <div class="panel-body">
        <pre class="pre-scrollable">
            /*
            设置、获取当前屏幕亮度;
            * */
            var bookReader = api.require('bookReader');
            
            bookReader.brightness(function(ret,err){
            if(ret.status){
            api.toast({
            msg: '当前亮度为: ' + ret.brightness,
            duration: 1000,
            location: 'top'
            });
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
</body>
<script src="../script/jquery-1.11.1.min.js"></script>
<script src='../script/highlight.pack.js'></script>
<script src='../script/demo2.js'></script>
</html>