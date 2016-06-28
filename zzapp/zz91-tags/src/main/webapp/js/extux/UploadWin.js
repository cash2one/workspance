// 依赖app/js/extux/FileUploadField.js
Ext.namespace("com.zz91.ads.board");

com.zz91.ads.board.UploadConfig = new function(){
	this.uploadURL = Context.ROOT+"/upload";
	this.uploadSuccess = null;
}

com.zz91.ads.board.UploadWin = Ext.extend(Ext.Window, {
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
			            buttonText: '浏览',
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
				                fm.getForm().submit({
				                    url: com.zz91.ads.board.UploadConfig.uploadURL,
				                    waitMsg: "正在上传...",
				                    success: com.zz91.ads.board.UploadConfig.uploadSuccess
				                });
			                }
			        	}
			        }]
			    })
			]
		};

		com.zz91.ads.board.UploadWin.superclass.constructor.call(this,c);
	}

});