Ext.namespace("com.zz91.util");

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
Ext.define("com.zz91.util.CategoryCombo",{
	extend:"Ext.form.ComboBox",
	initComponent:function(){
		
		var states=Ext.create("Ext.data.Store",{
			model:this.getCategoryModel(),
			proxy:{
				type:"ajax",
				url:this.getQueryUrl()+this.getRootCode(),
				reader: {
					type: 'json'
				}
			},
			autoLoad:this.getAl()
		});
		
		var c={
			store: states,
			triggerAction: 'all'
		}
		
		Ext.applyIf(this,c);

		this.callParent();
	},
	rootCode:"",
	queryUrl:Context.ROOT+"/path/to/query/url",
	al:true,
	categoryModel:"CategoryModel",
	config:{
		queryUrl:null,
		rootCode:null,
		al:true,
		categoryModel:null
	},
	displayField: 'label',
	valueField: 'code'
});

Ext.define("com.zz91.util.LocalCombo",{
	extend:"Ext.form.ComboBox",
	initComponent:function(){

		var _data=this.getLocalData();
		
		var states=Ext.create("Ext.data.Store",{
			fields:["name","value"],
			data:_data
		});
		
		var c={
			store: states,
			triggerAction: 'all'
		}
		
		Ext.applyIf(this,c);

		this.callParent();
	},
	localData:[],
	config:{
		localData:[]
	},
	displayField: 'name',
	valueField: 'value'
});

/**
 * 类别树选择器
 * */
Ext.define("com.zz91.util.Tree",{
	extend:"Ext.tree.Panel",
	initComponent:function(){
		
		var store=Ext.create("Ext.data.TreeStore",{
			nodeParam:this.getNodeParam(),
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
				text:'所有类别'
			}
		});
		
		var c={
			store: store
		}
		
		Ext.applyIf(this,c);

		this.callParent();
	},
	nodeParam:"parentCode",
	rootCode:"",
	treeModel:"CategoryTreeModel",
	queryUrl:Context.ROOT+"/zz91/common/categoryTreeNode.htm",
	config:{
		queryUrl:null,
		rootCode:null,
		treeModel:"CategoryTreeModel",
		nodeParam:null
	}
});

Ext.define("com.zz91.util.TreeSelectorWin",{
	extend:"Ext.Window",
	initComponent:function(){
		
		var tree=Ext.create("com.zz91.util.Tree",{
			rootCode:this.getRootCode(),
			treeModel:this.getTreeModel(),
			nodeParam:this.getNodeParam(),
			queryUrl:this.getQueryUrl()
		});
		
		var c={
			layout:"fit",
			items:[tree],
			buttons:[{
				text:"选择",
				iconCls:"accept16",
				scope:this,
				handler:function(btn,e){
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
		};
		
		Ext.applyIf(this,c);

		this.callParent();
	},
	rootCode:"",
	nodeParam:"parentCode",
	treeModel:"CategoryTreeModel",
	queryUrl:Context.ROOT+"/zz91/common/categoryTreeNode.htm",
	config:{
		queryUrl:null,
		rootCode:null,
		initCode:null,
		treeModel:"CategoryTreeModel",
		nodeParam:null
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
		}else{
			path=path+this.getRootCode();
		}

		this.child("treepanel").selectPath(path, "id");
	}
});

/**
 * 上传对话框
 * */
Ext.define("com.zz91.util.UploadWin",{
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
