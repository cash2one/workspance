Ext.namespace("ast.ast1949.util");

///**
// * 地区选择器
// * */
//Ext.define("ast.ast1949.util.Area",{
//	
//});

Ext.define("CategoryModel",{
	extend: 'Ext.data.Model',
	fields:["id",{name:"code",mapping:"code"},{name:"label",mapping:"label"}]
});

Ext.define("CategoryTreeModel",{
	extend: 'Ext.data.Model',
	fields:[{name:"id",mapping:"data"},{name:"text",mapping:"text"},{name:"leaf",mapping:"leaf"}]
});

/**
 * 类别下拉选择器
 * */
Ext.define("ast.ast1949.util.CategoryCombo",{
	extend:"Ext.form.ComboBox",
	initComponent:function(){
		var _autoLoad=this.getAl();
		var _url=this.getQueryUrl()+this.getRootCode();
		
		var states=Ext.create("Ext.data.Store",{
			model:"CategoryModel",
			proxy:{
				type:"ajax",
				url:_url,
				reader: {
					type: 'json'
//					root: 'records'
		        }
			},
			autoLoad:_autoLoad
		});
		
		var c={
			store: states,
			triggerAction: 'all'
		}
		
		Ext.applyIf(this,c);

		this.callParent();
	},
	rootCode:"",
	queryUrl:Context.ROOT+"/zz91/common/categoryCombo.htm?preCode=",
	al:true,
	config:{
		queryUrl:null,
		rootCode:null,
		al:true
	},
	displayField: 'label',
	valueField: 'code'
});

/**
 * 类别树选择器
 * */
Ext.define("ast.ast1949.util.Tree",{
	extend:"Ext.tree.Panel",
	initComponent:function(){
		var store=Ext.create("Ext.data.TreeStore",{
			nodeParam:"parentCode",
			defaultRootId:this.getRootCode(),
			model:this.getTreeModel(),
			proxy:{
				type:"ajax",
				url:this.getQueryUrl(),
				reader: {
					type: 'json',
					root: 'records'
				}
			},
			root:{
				expanded:this.getRootexpanded(),
				text:'所有类别'
			}
		});
		
		var c={
			store: store
		}
		
		Ext.applyIf(this,c);

		this.callParent();
	},
	rootCode:"",
	treeModel:"CategoryTreeModel",
	queryUrl:Context.ROOT+"/zz91/common/categoryTreeNode.htm",
	rootexpanded:false,
	config:{
		queryUrl:null,
		rootCode:null,
		treeModel:"CategoryTreeModel",
		rootexpanded:false
	}
});

Ext.define("ast.ast1949.util.TreeSelectorWin",{
	extend:"Ext.Window",
	initComponent:function(){
		
		var tree=Ext.create("ast.ast1949.util.Tree",{
			rootCode:this.getRootCode(),
			treeModel:this.getTreeModel(),
			queryUrl:this.getQueryUrl(),
			rootexpanded:false
		});
		
		var c={
			layout:"fit",
			items:[tree],
			buttons:[{
				text:"选择",
				iconCls:"accept16",
				scope:this,
				handler:function(btn,e){
					//tree选中项
					var model=tree.getSelectionModel().getLastSelected();
					this.callbackFn(model);
				}
			},{
				text:"关闭",
				iconCls:"close16",
				scope:this,
				handler:function(btn,e){
					this.close();
				}
			}]
//			listeners:{
//				"activate":function(_this, e){
//					tree.expandPath("/10011000/100110001002", "id");
//				}
//			}
		};
		
		Ext.applyIf(this,c);

		this.callParent();
	},
	rootCode:"",
	treeModel:"CategoryTreeModel",
	queryUrl:Context.ROOT+"/zz91/common/categoryTreeNode.htm",
	config:{
		queryUrl:null,
		rootCode:null,
		initCode:null,
		treeModel:"CategoryTreeModel"
	},
	callbackFn:function(nodeInterface){
	},
	initTree:function(codelength){
		var path="/";
		codelength=codelength||4;
		var pathArr=new Array();
		if(this.getInitCode()!=null && this.getInitCode().length>0){
			for(var i=this.getRootCode().length;i<=this.getInitCode().length;i=i+codelength){
				pathArr.push(this.getInitCode().substring(0,i));
			}
		}
		if(pathArr.length>0){
			path=path+pathArr.join("/");
			this.child("treepanel").selectPath(path, "id");
		}else{
			path=path+this.getRootCode();
//			this.child("treepanel").expandPath(path, "id");
			this.child("treepanel").getRootNode().expand();
		}

	}
});

/**
 * 上传对话框
 * */
Ext.define("ast.ast1949.util.UploadWin",{
	extend:"Ext.Window",
	modal:true,
	width:400,
	autoHeight:true,
	uploadUrl:Context.ROOT+"/upload",
	initComponent:function(){
		
		var form=Ext.create("Ext.form.Panel",{
			fileUpload: true,
			bodyPadding:10,
			fieldDefaults:{
				labelAlign : "right",
				labelWidth : 60,
				labelSeparator:"",
				msgTarget:"under"
			},
			items:[{
				xtype: 'filefield',
				name:"uploadfile",
				anchor:"100%",
				emptyText: '请选择一个文件',
				fieldLabel:"选择文件",
				allowBlank:false,
				formItemCls:"x-form-item required"
			}]
		});
		
		var c={
			title:"文件上传",
			items:[form],
			buttons:[{
				text:"上传",
				iconCls:"addfile16",
				scope:this,
				handler:function(btn,e){
	        		if(form.getForm().isValid()){
	        			var _url=this.uploadUrl;
	        			var _success=this.callbackFn;
		                form.getForm().submit({
		                    url: _url,
		                    waitMsg: "正在上传...",
		                    success: _success
		                });
	                }
				}
			}]
		};
		
		Ext.applyIf(this,c);
		this.callParent();
	},
	callbackFn:function(uploadedFile){
	}
});

//Ext.define("ast.ast1949.util.Upload",{
//	resources:"http://img1.zz91.com",
//	buildImgUrl:function(filepath){
//		if(filepath.indexOf("/")>0){
//			return this.getResources()+"/"+filepath;
//		}
//		return this.getResources()+filepath;
//	},
//	showImg:function(filepath){
//		window.open(this.buildImgUrl());
//	}
//});