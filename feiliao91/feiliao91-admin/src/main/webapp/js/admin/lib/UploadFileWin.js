Ext.namespace("ast.ast1949");

ast.ast1949.Upload = function(_cfg,onSaveSuccess,onSaveFailure){
	if(_cfg == null){
		_cfg={};
	}
	var _model = _cfg["model"]||"upload";
	var _filetype = _cfg["filetype"] || "img";
	var _usetype = _cfg["usetype"]==false?false:true;
//	alert(_model);
	var uploadWin=new ast.ast1949.UploadFileWin({
			title:"文件上传",
			view:true,
			nView:false,
			listeners:{
				"saveSuccess" : onSaveSuccess,
				"saveFailure" : onSaveFailure,
				"submitFailure" : onSubmitFailure
			}
		});
		uploadWin.show();

	Ext.get("upload_save").on("click",function(){
		uploadWin.submit(_model,_filetype,_usetype);
	});

	Ext.get("upload_cancel").on("click",function(){
		uploadWin.close();
	});

//	function onSaveSuccess(form,_action){
//		var res= Ext.decode(_action.response.responseText);
//		if(res.success){
//			var uploaded="";
//			for(var i=0,len=res.data.length;i<len;i++){
//				if(uploaded==""){
//					uploaded=res.data[i];
//				}else{
//					uploaded=uploaded+","+res.data[i];
//				}
//			}
//			Ext.get("file").dom.value = uploaded
//			uploadWin.close();
//		}else{
//			Ext.MessageBox.show({
//				title:Context.MSG_TITLE,
//				msg : "发生错误,请联系管理员",
//				buttons:Ext.MessageBox.OK,
//				icon:Ext.MessageBox.ERROR
//			});
//		}
//	}

//	function onSaveFailure(){
//		Ext.MessageBox.show({
//			title:Context.MSG_TITLE,
//			msg : "发生错误,请联系管理员",
//			buttons:Ext.MessageBox.OK,
//			icon:Ext.MessageBox.ERROR
//		});
//	}

	function onSubmitFailure(){
		Ext.MessageBox.show({
			title:Context.MSG_TITLE,
			msg : "发生错误,请联系管理员",
			buttons:Ext.MessageBox.OK,
			icon:Ext.MessageBox.ERROR
		});
	}
}

ast.ast1949.UploadFileWin = Ext.extend(Ext.Window,{
	_form:null,
	constructor:function(_cfg){
		if(_cfg == null){
			_cfg = {};
		}

		Ext.apply(this, _cfg);

		var _title = this["title"] || "";
		var _isView = this["view"] || "";
		var _notView = this["nView"] || "";
		var _url=this["url"] ||"";

		this._form = new Ext.form.FormPanel({
			id:"upload_form",
			fileUpload: true,
			region:"center",
			frame:true,
			bodyStyle:"padding:5px 5px 0",
			labelAlign : "right",
			labelWidth : 80,
//			width:300,
			items:[{
				xtype:"fieldset",
				layout:"column",
				autoHeight:true,
				title:"选择文件",
				items:[{
					columnWidth: 1,
					layout: "form",
					defaults:{
						disabled:_notView
					},
					items:[{
						xtype:"fileuploadfield",
						fieldLabel:"上传文件",
						allowBlank:false,
						name:"file1",
						id:"file1",
						tabIndex:1,
						anchor:"95%",
						blankText : "请选择上传文件"
					}
					]
				}]
			}],
			buttons:[{
				id:"upload_save",
				text:"上传",
				hidden:_notView
			},{
				id:"upload_cancel",
				text:"取消",
				hidden:_notView
			},{
				id:"upload_close",
				text:"关闭",
				hidden:_isView
			}]
		});

		ast.ast1949.UploadFileWin.superclass.constructor.call(this,{
			id:"upload-file-window",
			title:_title,
			closeable:true,
			width:380,
			autoHeight:true,
			modal:true,
			border:false,
			plain:true,
			layout:"form",
			items:[this._form]
		});
	},
	submit:function(model,filetype,usetype){
		var _url=Context.ROOT + Context.PATH + "/admin/upload?tp="+timestamp()+"&filetype="+filetype+"&model="+model+"&usetype="+usetype;
		if(this._form.getForm().isValid()){
			this._form.getForm().submit({
				url:_url,
//				params:{model:model,filetype:filetype},
				method: "post",
				type:"json",
				success:this.onSaveSuccess,
				failure:this.onSaveFailure,
				scope:this
			});
		}else{
			return false;
		}
	},
	loadRecord:function(_record){
		this._form.getForm().loadRecord(_record);
	},
	onSaveSuccess:function(_form,_action){
		this.fireEvent("saveSuccess",_form,_action,_form.getValues());
	},
	onSaveFailure:function(_form,_action){
		this.fireEvent("saveFailure",_form,_action,_form.getValues());
	}
});