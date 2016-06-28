Ext.namespace("ast.ast1949.admin.productsPic");
ast.ast1949.admin.productsPic.imageView = Ext.extend(Ext.Panel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		var c={
			id:'images-view',
			autoScroll:true,
			items:[{
			       id:"excelName"
			}]
				,
			tbar:[{
				iconCls:"add",
				text:"上传文档",
				id:'fileUrl',
				name:'fileUrl',
				listeners:{
					"click":function(field,e){
						com.zz91.sms.gateway.UploadConfig.uploadURL=Context.ROOT+"/zz91/common/doJiageUpload.htm?offerId="+Ext.getCmp("id").value;
						var win = new com.zz91.sms.gateway.UploadWin({
							title:"上传文件",
						});
						com.zz91.sms.gateway.UploadConfig.uploadSuccess=function(form,action){
							Ext.Msg.alert(Context.MSG_TITLE,"上传成功");
							win.close();
							var a = action.result.data;
							Ext.get("excelName").dom.value = action.result.data;
						}
						win.show();
					}
				}
			},{
				iconCls:"add",
				text:"下载",
				listeners:{
					"click":function(field,e){
						Ext.Ajax.request({
							url: window.open(Context.ROOT+"/zz91/common/downloadFile.htm?offerId="+Ext.getCmp("id").value)
						});
					}
				}
			}]
		};
		
		ast.ast1949.admin.productsPic.imageView.superclass.constructor.call(this,c);


	},
});

