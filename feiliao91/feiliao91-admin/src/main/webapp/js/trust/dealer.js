Ext.namespace("ast.ast1949.trust.dealer");

var DEALER=new function(){
	this.GRID="dealergrid";
}

ast.ast1949.trust.dealer.CHECKSTATUS=["未审核","通过","","退回"];

ast.ast1949.trust.dealer.FIELDS=[
	{name:"id",mapping:"id"},
    {name:"name",mapping:"name"},
    {name:"tel",mapping:"tel"},
    {name:"qq",mapping:"qq"},
    {name:"gmtCreated",mapping:"gmtCreated"},
    {name:"gmtModified",mapping:"gmtModified"}
];

ast.ast1949.trust.dealer.Grid=Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config || {};
		Ext.apply(this,config);
		
		var _url = this.listUrl;
		
		var _store = new Ext.data.JsonStore({
			root:"records",
			totalProperty:'totalRecords',
			remoteSort:true,
			fields: ast.ast1949.trust.dealer.FIELDS,
			url:_url,
			autoLoad:true
		});
		
		var _sm = new Ext.grid.CheckboxSelectionModel({});
		
		var _cm=new Ext.grid.ColumnModel([_sm,{
			 	header:"编号",
	            hidden:true,
	            dataIndex:"id"
	        },{
	            header:"交易员姓名",
	            sortable:true,
	            width:150,
	            dataIndex:"name",
	        },{
	            header:"联系方式",
	            sortable:true,
	            width:150,
	            dataIndex:"tel"
	        },{
	            header:"qq",
	            sortable:true,
	            width:150,
	            dataIndex:"qq"
	        },{
	            header:"发布时间",
	            sortable:true,
	            width:150,
	            dataIndex:"gmtCreated",
	            renderer : function(value, metadata, record, rowIndex,colIndex, store) {
	                if(value!=null){
	                    return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
	                }else{
	                    return "";
	                }
	            }
	        },{
	            header:"更新时间",
	            sortable:true,
	            width:150,
	            dataIndex:"gmtModified",
	            renderer : function(value, metadata, record, rowIndex,colIndex, store) {
	                if(value!=null){
	                    return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
	                }else{
	                    return "";
	                }
	            }
	        }
	        ]);
		
		var c={
			iconCls:"icon-grid",
			loadMask:Context.LOADMASK,
			store:_store,
			sm:_sm,
			cm:_cm,
			autoExpandColumn:4,
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
			})
		};
		
		ast.ast1949.trust.dealer.Grid.superclass.constructor.call(this,c);
		
	},
	listUrl:Context.ROOT+Context.PATH+"/trust/queryAllDealers.htm",
	
	mytoolbar:[{
			text:"添加",
			iconCls:"add",
			handler:function(){
				ast.ast1949.trust.dealer.updateDealerInfo();
			}
		},"-",{
			iconCls:"delete",
			text:"删除",
			handler:function(){
		    	   var sm=Ext.getCmp(DEALER.GRID).getSelectionModel();
		    	   var submitIds=sm.getCount();
		    	   if ( submitIds== 0){
		    		   ast.ast1949.utils.Msg('',"请选择您要删除的标签，至少一项");
		    	   } else{
		    		   Ext.MessageBox.confirm(Context.MSG_TITLE,"确定要删除?",function(btn){
		    			   if(btn!="yes"){
		    				   return ;
		    			   }else{
		    				   var row = sm.getSelections();
	                           var _ids = new Array();
	                           for (var i=0,len = row.length;i<len;i++){
	                        	   var id=row[i].get("id");
	                        	   Ext.Ajax.request({
			    					   url:Context.ROOT+Context.PATH+ "/trust/delDealer.htm",
			    					   params:{
			    						   "id":id
			    					   },
			    					   method:"post",
			    					   type:"json",
			    					   success:function(response,opt){
			    				            var obj = Ext.decode(response.responseText);
			    				            if(obj.success){
			    				            	Ext.getCmp(DEALER.GRID).getStore().reload();
			    				            }
			    				        },
			    				        failure:function(response,opt){
			    				        }
			    				   });
			    			   } 
		    			   }
				       });
		         }
		       }
		}]
});
ast.ast1949.trust.dealer.updateDealerInfo=function(){
	var form=new ast.ast1949.trust.dealer.updateDealerInfoForm({
	});
	var win = new Ext.Window({
		id:"addDealerWin",
		title:"添加交易员",
		width:330,
		modal:true,
		autoHeight:true,
		items:[form]
	});
	
	win.show();
}

ast.ast1949.trust.dealer.updateDealerInfoForm = Ext.extend(Ext.form.FormPanel,{
	targetGrid:null,
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var form=this;
		
		var c={
			labelAlign : "right",
			layout : "form",
			frame:true,
			defaults:{
				anchor:"95%",
				xtype:"textfield",
				labelSeparator:""
			},
			items:[{
				fieldLabel:"交易员姓名:",
				name:"name"
			},{
				fieldLabel:"联系方式:",
				name:"tel",
				
			},{
				fieldLabel:"qq:",
				name:"qq",
			}],
			buttons:[{
				text:"保存",
				handler:function(btn){
					if(form.getForm().isValid()){
						var grid = Ext.getCmp(DEALER.GRID);
						var url=Context.ROOT+Context.PATH+"/trust/addDealer.htm";	
						form.getForm().submit({
							url: url,
							method:"post",
							type:"json",
							success:function(response,opt){              
								ast.ast1949.utils.Msg("","交易员信息保存成功");
								Ext.getCmp("addDealerWin").close();
								grid.getStore().reload();  
							},
							failure:function(response,opt){
								ast.ast1949.utils.Msg(obj.data,"交易员信息保存失败");
							}
						});
					}else{
						Ext.MessageBox.show({
							title:Context.MSG_TITLE,
							msg : "验证未通过",
							buttons:Ext.MessageBox.OK,
							icon:Ext.MessageBox.ERROR
						});
					}
				}
			}]
		};
		
		ast.ast1949.trust.dealer.updateDealerInfoForm.superclass.constructor.call(this,c);
	}
});

