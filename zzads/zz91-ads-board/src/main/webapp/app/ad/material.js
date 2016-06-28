Ext.namespace("com.zz91.ads.board.ad.material")
/*
	定义素材相关编辑窗口
	hetao138@gmail.com 2011-6-13
*/

/*
	提交素材表单初始化
	param ads.board.material.FormUploadMaterial
*/



com.zz91.ads.board.ad.material.FormUploadMaterial = Ext.extend(Ext.form.FormPanel,{
	constructor:function(config){		
		config = config||{};
		Ext.apply(this,config);
		
		var c = {
			labelAlign:"right",
			labelWidth:80,
			layout:"form",
			frame:true,
			fileUpload:true,
			defaults:{
				xtype:"textfield",
				anchor:"99%"
			},
			items:[{
				xtype:"hidden",
				name:"id",
				id:"id"
			},{
				fieldLabel:"素材名称",
				name:"materialName",
				allowBlank:false,
				itemsCls:"required"
			},{
				xtype:"textarea",
				fieldLabel:"素材描述",
				name:"materialDiscript"
			},{
				xtype:"fileuploadfield",
				name:"materialPath",
				listeners:{
					"focus":function(c){
						// 依赖app/js/extux/UploadWin.js
						com.zz91.ads.board.UploadConfig.uploadURL = Context.ROOT + "/ad/check/upload.htm";
						var win = new com.zz91.ads.board.UploadWin({
							title:"上传素材"
						});
						com.zz91.ads.board.UploadConfig.uploadSuccess = function(f,o){
							if(o.result.success){
								win.close();
								Ext.get("materialPath").setValue(o.result.data); 
							}
						};
						win.show();
					}
				}
			}],
			buttons:[{
				text:"提交",
				handler:this.save,
				scope:this
			},{
				text:"关闭",
				handler:function(){
					Ext.getCmp(AdCheck.WinUploadMaterialId).close();
				}
			}]
		};
		
		com.zz91.ads.board.ad.material.FormUploadMaterial.superclass.constructor.call(this,c);
	},
	loadRecords:function(id){
		var _field = [
			{name:	"id",mapping:"id"}
		];
		
		var _form = this;
		var _store= new Ext.data.JsonStore({
			root:	"records",
			fields:	_field,
			url:	Context.ROOT + "/ad/check/queryById.htm",
			baseParams:{"id":id},
			autoLoad:true,
			listeners:{
				"datachanged":function(){
					var record = s.getAt(0);
					if (record == null) {
						Ext.MessageBox.alert(Context.MSG_TITLE,"数据加载失败...");
					} else {
						form.getForm().loadRecord(record);
					}
				}
			}
		}); 
	},
	//saveURL:Context.ROOT+"/ad/check/add.htm",
	save:function(){
		var saveURL = this.saveURL;
		if(this.getForm().isvalidate()){
			this.getForm().submit({
				url:saveURL,
				method:"post",
				type:"json",
				success:function(_form,_action){
					com.zz91.ads.board.utils.Msg("","保存成功！");
				},
				failure:function(_form,_action){
					com.zz91.ads.board.utils.Msg("","保存失败！");
				}
			});
		}
	}
});


/**
 * 素材form
 * @class com.zz91.ads.board.ad.ad.AdMaterialEditForm
 * @extends Ext.form.FormPanel
 */
com.zz91.ads.board.ad.material.AdMaterialEditForm=Ext.extend(Ext.form.FormPanel, {
	aid:0,
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var c={
			labelAlign : "right",
			labelWidth : 80,
			layout:"form",
			frame:true,
			defaults:{
				anchor:"99%",
				xtype:"textfield",
				labelSeparator:""
			},
			items:[{
				xtype:"hidden",
				id:"id",
				name:"id"
			},{
				name:"name",
				fieldLabel:"素材名称",
				itemCls :"required",
				allowBlank : false
			},{
				xtype:"textarea",
				name:"remark",
				fieldLabel:"素材描述"
			}],
			buttons:[{
				text:"保存",
				handler:this.save,
				scope:this
			}]
		};
		
		com.zz91.ads.board.ad.material.AdMaterialEditForm.superclass.constructor.call(this,c);
	},
	save:function(){
		var adId=this.aid; 
		if(this.getForm().isValid()){
			this.getForm().submit({
				url:Context.ROOT+"/ad/material/createMaterial.htm?adId="+adId,
				method:"post",
				type:"json",
				success:this.onSaveSuccess,
				failure:this.onSaveFailure,
				scope:this
			});
		}
	},
	onSaveSuccess:function (){
		
	},
	onSaveFailure:function (){
		com.zz91.ads.board.utils.Msg("","保存失败！");
	}
});

/**
 * 素材列表
 * @class com.zz91.ads.board.ad.ad.adMaterialGrid
 * @extends Ext.grid.GridPanel
 */
com.zz91.ads.board.ad.material.AdMaterialGrid = Ext.extend(Ext.grid.GridPanel, {
	aid:0,
	getAid:function(){
		return this.aid;
	},
	constructor:function(config){
		config = config || {};
		Ext.apply(this,config);

		var grid=this;
		
		var _aid=this.getAid();
		
		var _store=new Ext.data.JsonStore({
			remoteSort:true,
			fields:["id","adId","name","materialType","filePath","remark"],
			url:Context.ROOT+"/ad/material/queryMaterialOfAd.htm",
			params:{aid:grid.getAid()},
			autoLoad:false
		});
		
//		var _store = new Ext.data.JsonStore({
//			remoteSort:true,
//			fields:["id","adId","name","materialType","filePath","remark"],
//			url:Context.ROOT+"/ad/material/queryMaterialOfAd.htm?aid="+_aid,
////			params:{aid:_aid},
//			autoLoad:grid.autoload
//		});
		
		var _sm=new Ext.grid.CheckboxSelectionModel({});
		
		var _cm=new Ext.grid.ColumnModel([_sm, 
			{
				header:"编号",
				dataIndex:"id",
				hidden:true,
				sortable:false
			}, 
			{
				header:"素材",
				dataIndex:"name",
				sortable:false,
				renderer:function(value, metaData, record, rowIndex, colIndex, store){
					if(record.get("filePath")!=null && record.get("filePath")!=""){
						return "<a href='"+record.get("filePath")+"' target='_blank' >"+value+"</a>";
					}else{
						return value;
					}
				}
			},{
				header:"描述",
				dataIndex:"remark",
				sortable:false
			}
		]);
		
		var c = {
			iconCls:"icon-grid",
			viewConfig:{
				autoFill:true
			},
			loadMask:Context.LOADMASK,
			store:_store,
			sm:_sm,
			cm:_cm,
			tbar:[{
				iconCls:"add16",
				text:"添加素材",
				handler:function(btn){
					
					var form = new com.zz91.ads.board.ad.material.AdMaterialEditForm({
						aid:grid.getAid(),
						height:150,
						onSaveSuccess:function(_f,_a){
							_store.baseParams["aid"]=grid.getAid();
							_store.reload();
							this.ownerCt.close();
						}
					});
					
					var win = new Ext.Window({
						title:"添加广告素材",
						width:450,
						autoHeight:true,
						modal:true,
						items:[form]
					});
					win.show();
				}
			},{
				iconCls:"delete16",
				text:"删除素材",
				handler:function(btn){
					var row=grid.getSelectionModel().getSelected();
					Ext.Ajax.request({
				        url:Context.ROOT+"/ad/material/deleteMaterial.htm",
				        params:{"id":row.get("id")},
				        success:function(response,opt){
				            var obj = Ext.decode(response.responseText);
				            if(obj.success){
				            	_store.reload();
				            }else{
				                Ext.MessageBox.show({
				                    title:MESSAGE.title,
				                    msg : MESSAGE.saveFailure,
				                    buttons:Ext.MessageBox.OK,
				                    icon:Ext.MessageBox.ERROR
				                });
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
			}]
		};
		com.zz91.ads.board.ad.material.AdMaterialGrid.superclass.constructor.call(this,c);
	}
})

