Ext.namespace("com.zz91.ads.board.analysis.adhit");


com.zz91.ads.board.analysis.adhit.Grid = Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		this.config = config || {};
		Ext.apply(this, config);
		
		var _fields=Ext.data.Record.create([
			{name:"id",mapping:"id"},
			{name:"ad_id",mapping:"adId"},
			{name:"ad_position_id",mapping:"adPositionId"},
			{name:"ad_title",mapping:"adTitle"},
			{name:"num_show",mapping:"numShow"},
			{name:"num_hit",mapping:"numHit"},
			{name:"num_hit_first",mapping:"numHitFirst"},
			{name:"num_hit_per_hour",mapping:"numHitPerHour"},
			{name:"gmt_target",mapping:"gmtTarget"}
		]);
		
		var _store = new Ext.data.JsonStore({
			root:"records",
			totalProperty:"totals",
			fields:_fields,
			url:Context.ROOT+"/analysis/adhit/query.htm",
			remoteSort:true,
			autoLoad:true
		});
		
		var _sm			= new Ext.grid.CheckboxSelectionModel();
		
		var _cm			= new Ext.grid.ColumnModel([_sm,{
				header:"编号",
				hidden:true,
				dataIndex:"id"
			},{
				header:"广告",
				sortable:false,
				dataIndex:"ad_title",
				renderer:function(value, metaData, record, rowIndex, colIndex, store){
					return value;
				}
			},{
				header:"时间",
				sortable:true,
				dataIndex:"gmt_target",
				renderer:function(value, metaData, record, rowIndex, colIndex, store){
					return Ext.util.Format.date(new Date(value*1000), 'Y-m-d');
				}
			},{
				header:"展示次数",
				sortable:true,
				dataIndex:"num_show"
			},{
				header:"总点击次数",
				sortable:true,
				dataIndex:"num_hit"
			},{
				header:"有效点击",
				sortable:true,
				dataIndex:"num_hit_first"
			}
		]);
		
		var c = {
			loadMask:Context.LOADMASK,
			store:_store,
			cm:_cm,
			sm:_sm,
			tbar:["->","日期：",{
            	id:"basetime",
            	xtype:"datefield",
            	format:"Y-m-d",
            	listeners:{
					//失去焦点
					"blur":function(c){
						var val = Ext.getCmp("basetime").getValue();  //Ext.getCmp("basetime")
						var B	= _store.baseParams;
						B	= B ||{};
						if(val!=""){
							B["gmtTarget"]= Date.parse(val);
						}else{
							B["gmtTarget"]=null;
						}
						_store.baseParams = B;
						_store.reload({"params":{start:0,"limit":Context.PAGE_SIZE}});
					},
					//回车事件
					"specialkey":function(field, e){
						if(e.getKey()= Ext.EventObjet.ENTER){
							var val =  Ext.getCmp("basetime").getTime();
							var B	= _store.baseParams;
							B	= B||{};
							if(val!=""){
								B["gmtTarget"] = Date.parse(val);
							}else{
								B["gmtTarget"] = null;
							}
							_store.baseParams = B;
							_store.reload({"params":{"start":0,"limit":Context.PAGE_SIZE}});
						}
					}
				}
            }],
			bbar:new Ext.PagingToolbar({
				pageSize:Context.PAGE_SIZE,
				store:_store,
				displayInfo:true,
				displayMsg:"显示第{0}--{1}跳记录, 共{2}条记录",
				emptyMsg:"没有记录",
				beforePageText:"第",
				afterPageText:"页，共{0}页",
				pramaNames:{start:"start",limit:"limit"}
			})
		};
		com.zz91.ads.board.analysis.adhit.Grid.superclass.constructor.call(this, c);
	}
});
