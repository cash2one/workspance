/*
 * 认证信息
 */
Ext.namespace("ast.ast1949.admin.credit.creditbaseinfo")

//定义变量
var _C = new function(){
	this.RESULT_GRID = "resultgrid";
}

//认证信息列表
ast.ast1949.admin.credit.creditbaseinfo.ResultGrid = Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config || {};
		Ext.apply(this,config);
		
		var _fields =this.listRecord;
		var _url = this.listUrl;
		var _store = new Ext.data.JsonStore({
			root:"records",
			totalProperty:'totalRecords',
			remoteSort:true,
			fields:_fields,
			url:_url,
			autoLoad:true
		});
		var grid=this;
		
		var _sm = new Ext.grid.CheckboxSelectionModel({});

		var _cm=new Ext.grid.ColumnModel([_sm,
			{
				header:"编号",
				sortable:false,
				dataIndex:"id",
				hidden:true
			},{
				header:"审核状态",
				sortable:true,
				dataIndex:"checkStatus",
				width:100,
				renderer:function(value,metadata,record,rowindex,colindex,store){
					var str="";
					if(value!=null){
						if(value=="1"){
							str = '<img src ="'+Context.ROOT+'/css/admin/icons/Item.True.gif" />';
						}else if(value=="2"){
							str = '<img src ="'+Context.ROOT+'/css/admin/icons/Item.False.gif" />';
						}else{
							str = '<img src ="'+Context.ROOT+'/css/admin/icons/Item.Info.Small.gif" />';
						}
					}
					
					return str;
				}
			},{
				header:"认证公司",
				sortable:false,
				width:150,
				dataIndex:"companyName",
				renderer : function(value, metadata, record, rowIndex, colndex, store) {
					var webstr="<a href='"+Context.ROOT+Context.PATH+"/admin/credit/creditbaseinfo/goEsite.htm?companyId="+record.get("companyId")+"' target='_blank' >";
					webstr=webstr+"<img src='"+Context.ROOT+"/css/admin/icons/web16.png' /></a>";
					var title=" <a href='"+Context.ROOT+Context.PATH+"/admin/credit/creditbaseinfo/edit.htm?id="+record.get("id")+"' target='_blank'>"+value+"</a>";
					return webstr+title;
				}
			}, {
				header:"认证类型",
				sortable:true,
				width:100,
				dataIndex:"attestType",
				renderer:function(value,metadata,record,rowindex,colindex,store){
					var str="";
					if(value!=null){
						if(value=="1"){
							str = '工商管理注册';
						}else{
							str = '个人';
						}
					}
					return str;
				}
			},{
				header:"公司名称",
				sortable:false,
				width:190,
				dataIndex:"compName",
				renderer : function(value, metadata, record, rowIndex, colndex, store) {
					var val="";
					if(record.get("membershipCode")=="10051003"){
						val="<img src='"+Context.ROOT+"/images/ldblogo.jpg'width='20px'height='20px' />";
					}else if(record.get("membershipCode")!="10051000"&&record.get("membershipCode")!=""){
						val="<img src='"+Context.ROOT+"/images/recycle.gif' />";
					}
					var title=" <a href='"+Context.ROOT+Context.PATH+"/admin/credit/creditbaseinfo/edit.htm?activeFlg=1&id="+record.get("id")+"' target='_blank'>"+value+"</a>";
					return val + title;
				}
			}, {
				header:"发布时间",
				sortable:true,
				dataIndex:"gmtCreated",
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d');
					}
					else{
						return "";
					}
				}
			}, {
				header:"刷新时间",
				sortable:true,
				dataIndex:"gmtModified",
				renderer : function(value, metadata, record, rowIndex,colIndex, store) {
					if(value!=null){
						return Ext.util.Format.date(new Date(value.time), 'Y-m-d');
					}
					else{
						return "";
					}
				}
			}
		]);
		
		var con={
			iconCls:"icon-grid",
			loadMask:Context.LOADMASK,
			store:_store,
			sm:_sm,
			cm:_cm,
			tbar:this.mytoolbar,
			bbar:new Ext.PagingToolbar({
				pageSize : Context.PAGE_SIZE,
				store : _store,
				displayInfo: true,
				displayMsg: '显示第 {0} - {1} 条记录,共 {2} 条',
				emptyMsg : '没有可显示的记录',
				beforePageText : '第',
				afterPageText : '页,共{0}页',
				paramNames : {start:"startIndex",limit:"pageSize"}
			}),
		};
		ast.ast1949.admin.credit.creditbaseinfo.ResultGrid.superclass.constructor.call(this,con);
	},
	listRecord:Ext.data.Record.create([
   	    {name:"id",mapping:"companyAttest.id"},
   	    {name:"companyId",mapping:"companyAttest.companyId"},
   	    {name:"companyName",mapping:"companyAttest.companyName"},
   	    {name:"attestType",mapping:"companyAttest.attestType"},
   	    {name:"compName",mapping:"name"},
   	    {name:"gmtCreated",mapping:"companyAttest.gmtCreated"},
   	    {name:"gmtModified",mapping:"companyAttest.gmtModified"},
   	    {name:"checkStatus",mapping:"companyAttest.checkStatus"},
   	    {name:"membershipCode",mapping:"membershipCode"}
   	]),
   	mytoolbar:[
		{
			text:"编辑",
			iconCls:"edit",
			handler:function(btn){
				var grid = Ext.getCmp(_C.RESULT_GRID);
				var row = grid.getSelections();
				for(var i=0;i<row.length;i++){
					window.open(Context.ROOT+Context.PATH
						+"/admin/credit/creditbaseinfo/edit.htm?id="+row[i].get("id"));
				}
			}
		},"-",{
			text:"删除",
			handler:function(btn){
				delStatus();
			}
		},"-",
		{
			iconCls:"item-true",
			id:"checkButton",
			text:"通过审核",
			handler:function(btn){
				checkButton(1);
			}
		},"-",{
			iconCls:"item-false",
			id:"unCheckButton",
			text:"审核不通过",
			handler:function(btn){
					checkButton(2)
			}
		},"-",
   		"认证公司",
	   	{
	   		xtype:"textfield",
	   		id:"companyName",
	   		name:"companyName",
	   		width:100
	   	},"公司名称",{
	   		xtype:"textfield",
	   		id:"compName",
	   		name:"compName",
	   		width:100
	   	},"-",{
	   		text:"查询",
	   		iconCls:"query",
	   		handler:function(){
		   		var grid = Ext.getCmp(_C.RESULT_GRID);
		   		var B=grid.store.baseParams;
		   		B=B||{};
		   		B["compName"] = Ext.get("compName").dom.value;
		   		B["companyName"] = Ext.get("companyName").dom.value;
		   		grid.store.baseParams = B;
	   			grid.store.reload({
	   				params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}
	   			})
	   		}
	   	},"->",{
			xtype:"checkbox",
			boxLabel:"高会",
			id:"isVip",
			listeners:{
				"check":function(field,newvalue,oldvalue){
					var grid = Ext.getCmp(_C.RESULT_GRID);
					grid.searchForMembership();
				}
			}
		},{
			xtype:"checkbox",
			boxLabel:"普会",
			id:"noVip",
			listeners:{
				"check":function(field,newvalue,oldvalue){
					var grid = Ext.getCmp(_C.RESULT_GRID);
					grid.searchForMembership();
				}
			}
		},{
			xtype:"checkbox",
			boxLabel:"未审核",
			id:"unChecked",
			checked:true,
			listeners:{
				"check":function(field,newvalue,oldvalue){
					var grid = Ext.getCmp(_C.RESULT_GRID);
					grid.searchByCheckStatus();
				}
			}
		},
		{
			xtype:"checkbox",
			boxLabel:"通过",
			id:"beChecked",
			listeners:{
				"check":function(field,newvalue,oldvalue){
					var grid = Ext.getCmp(_C.RESULT_GRID);
					grid.searchByCheckStatus();
				}
			}
		},
		
		{
			xtype:"checkbox",
			boxLabel:"不通过",
			id:"noChecked",
			/*checked:true,*/
			listeners:{
				"check":function(field,newvalue,oldvalue){
					var grid = Ext.getCmp(_C.RESULT_GRID);
					grid.searchByCheckStatus();
				}
			}
		}
	],
	searchForMembership:function(){
		var B=this.getStore().baseParams||{};
		var ary = new Array();
		if(Ext.getCmp("isVip").getValue()){
			B["isVip"] = 1;
		}else{
			B["isVip"] = undefined;
		}
		if(Ext.getCmp("noVip").getValue()){
			B["isNoVip"] = 1;
		}else{
			B["isNoVip"] = undefined;
		}
		this.getStore().baseParams = B;
		this.getStore().reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
	},
	searchByCheckStatus:function(){
		var B=this.getStore().baseParams||{};
		var ary = new Array();
		if(Ext.getCmp("beChecked").getValue()){
			ary.push(1);
		}
		if(Ext.getCmp("unChecked").getValue()){
			ary.push(0);
		}
		if(Ext.getCmp("noChecked").getValue()){
			ary.push(2);
		}

		B["checkStatus"] = ary.join(",");
		this.getStore().baseParams = B;
		this.getStore().reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
	}
});

//审核
function checkButton(status){
	var grid = Ext.getCmp(_C.RESULT_GRID);
	
	var row = grid.getSelections();
	
	var success=0;
	var step=0;
	for (var i=0,len = row.length;i<len;i++){
		step++;
		var _id=row[i].get("id");
		var _cid=row[i].get("companyId");
		var _status=row[i].get("checkStatus");
		/*提交*/
		var conn = new Ext.data.Connection();
		conn.request({
			url: Context.ROOT+Context.PATH+ "/admin/credit/creditbaseinfo/updateCheckStatus.htm?st="+ Math.random() +
			"&ids=" + _id +
			"&currents=" + _status +
			"&checkStatus=" + status,
			method : "get",
			scope : this,
			callback : function(options,success,response){
				var res= Ext.decode(response.responseText);
				if(res.success){
					success++;
				}
				if(step==row.length&&success==row.length) {
					ast.ast1949.utils.Msg("","所选记录已成功更新！");
					grid.getStore().reload();
				} else {
					grid.getStore().reload();
				}
			}
		});
	}
};

//删除
function delStatus(){
	 Ext.Msg.confirm('提示信息','你确定要执行删除操作吗?',function(btn){ 
         if(btn == 'yes') {
        	 	var grid = Ext.getCmp(_C.RESULT_GRID);
        		var rows=grid.getSelectionModel().getSelections();
        		for(var i=0;i<rows.length;i++){
        			
        			Ext.Ajax.request({
        				url:Context.ROOT+Context.PATH+ "/admin/credit/creditbaseinfo/delete.htm",
        				params:{"id":rows[i].get("id")},
        				success:function(response,opt){
        					var obj = Ext.decode(response.responseText);
        					if(obj.success){
        						grid.getStore().reload();
        					}else{
        						com.zz91.utils.Msg("",MESSAGE.deleteSuccess);
        					}
        				},
        				failure:function(response,opt){
        					Ext.MessageBox.show({
        						title:MESSAGE.title,
        						msg : MESSAGE.submitFailure,
        						buttons:Ext.MessageBox.OK,
        						icon:Ext.MessageBox.ERROR
        					});
        				}
        			});
        		}
        	 }
         })
}