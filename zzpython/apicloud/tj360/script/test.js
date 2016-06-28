function getData(){
    //show loading
    api.showProgress({
        title: '加载中...',
        modal: false
    });

    var model = api.require('model');
    var query = api.require('query');
    //mcm config
    model.config({
        appKey: '727724FF-FD26-C97B-B317-366723CA78A2',
        host: 'https://d.apicloud.com'
    });
    query.createQuery(function(ret, err){
        if(ret && ret.qid){
            var queryId = ret.qid;
            model.findAll({
            class:"sms",
            qid:queryId
            }, function(ret, err){
                if(ret){
                    //alert(JSON.stringify(ret))这里是所有数据
                    var content = $api.byId('content');//这里是获取页面的id-content
                    var tpl = $api.byId('template').text;//这里是吧template里的内容复制给tpl
                    var tempFn = doT.template(tpl);//这里是用doT。min。js这个模板来处理数据，不用管
                    content.innerHTML = tempFn(ret[0]);//这里是插入数据到页面，如果有多条数据把【0】去掉即可

                    //hide loading
                    api.hideProgress();
                }else{
                     alert(JSON.stringify(err));
                }
            });
        }
    });
}

apiready = function(){
	getData();
};