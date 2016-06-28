Ext.namespace("com.zz91.sms.gateway");

com.zz91.sms.gateway.UploadConfig = new function(){
	this.uploadURL = Context.ROOT+"/upload";
	this.uploadSuccess = null;
}

com.zz91.sms.gateway.UploadWin = Ext.extend(Ext.Window, {
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		var c={
			width:380,
			autoHeight:true,
			modal:true,
			items:[
				new Ext.FormPanel({
					id:"fileuploadform",
					timeout:10000,
					fileUpload: true,
					labelAlign : "right",
					labelWidth : 60,
					layout:"form",
					bodyStyle:'padding:5px 0 0',
					frame:true,
					items:[{
						xtype: 'fileuploadfield',
						id: 'form-file',
						allowBlank:false,
						emptyText: '请选择一个文件',
						fieldLabel: '选择文件',
						itemCls:"required",
						name: 'uploadfile',
						anchor:"100%",
						buttonText: '',
						buttonCfg: {
						iconCls: 'upload-icon'
						}
					}],
					buttons:[{
						text:"上传",
						socpe:this,
						handler:function(btn){
						var fm = Ext.getCmp("fileuploadform");
							if(fm.getForm().isValid()){
//								fm.getForm().submit({
//									url: com.zz91.sms.gateway.UploadConfig.uploadURL,
//									waitMsg: "正在上传...",
//									timeout:10000,//10秒超时,
//									success: com.zz91.sms.gateway.UploadConfig.uploadSuccess
//								});
								fm.getForm().doAction('submit', {  
									url:com.zz91.sms.gateway.UploadConfig.uploadURL,  
					                method:'POST',
					                waitMsg:'正在上传...',  
					                timeout:60000,//60秒超时,
					                success: com.zz91.sms.gateway.UploadConfig.uploadSuccess
					            });  
							}
						}
					}]
				})
			]
		};

		com.zz91.sms.gateway.UploadWin.superclass.constructor.call(this,c);
		
	},
	callbackFn:function(uploadedFile){
		}
	

});