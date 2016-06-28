Ext.namespace("com.zz91.zzmail");

com.zz91.zzmail.ThreadMonitor=Ext.extend(Ext.chart.LineChart,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
//		numTask = mainPool.getNumTask();
//			totalTime = mainPool.getTotalTime();
//			numQueue = mainPool.getQueue().size();
//			activeThread=mainPool.getActiveCount();
			
			
//		var store=new Ext.data.JsonStore({
////			root : "records",
//			fields : [{name:"numTask", type:"int"},
//				{name:"totalTime", type:"int"},
//				{name:"numQueue", type:"int"},
//				{name:"cacheQueue", type:"int"},
//				{name:"activeThread", type:"int"},
//				{name:"time", type:"string"}],
//			url : Context.ROOT+ "/monitor.htm",
//			autoLoad : true,
//			listeners : {
//				"datachanged" : function() {
//					//TODO 改变标题，用以显示
////					var record = _store.getAt(0);
////					if (record != null) {
////						form.getForm().loadRecord(record);
////					}
//				}
//			}
//		});
		
		var c={
//			store: store,
			frame:true,
			layout:"fit",
			xField: 'time',
			xAxis: new Ext.chart.CategoryAxis({  
				title: '时间'  
			}),
			yAxis: new Ext.chart.NumericAxis({  
				title: '数量'  
			}),
			series:[{
				type:"line",
				displayName: '队列中',
				yField:"numQueue",
				style: {
					color:0x111199
				}
			}, {
				type:"line",
				displayName: '缓存队列',
				yField:"cacheQueue",
				style: {
					color:0x991111
				}
			}, {
				type:"line",
				displayName: '工作线程',
				yField:"activeThread",
				style: {
					color:0x119911
				}
			}]
		};
		
		com.zz91.zzmail.ThreadMonitor.superclass.constructor.call(this,c);
	}
});