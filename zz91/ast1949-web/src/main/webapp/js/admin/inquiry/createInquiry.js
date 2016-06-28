Ext.namespace("ast.ast1949.admin.inquiry");

var CREATEINQUIRY = new function(){
	this.PRODUCT_GRID = "productgrid";
	this.INQUIRY_FORM = "inquiryform";
}

ast.ast1949.admin.inquiry.inquiryForm = Ext.extend(Ext.form.FormPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);

		var c = {
			labelAlign : "right",
			labelWidth : 80,
//			region:"center",
			layout:"form",
			bodyStyle:'padding:5px 0 0',
			frame:true,
			defaults:{
				anchor:"95%",
				xtype:"textfield",
				labelSeparator:""
			},
			items:[{
				xtype : "hidden",
				id:"id",
				name : "id"
//				dataIndex : "id"
			},{
				xtype : "hidden",
				id:"senderId",
				name : "senderId"
//				dataIndex : "senderId"
			},{
				id:"title",
				name : "title",
				fieldLabel : "询盘标题",
				itemCls:"required",
				allowBlank : false
			},{
				xtype : "textarea",
				id:"content",
				name : "content",
				fieldLabel : "询盘内容",
				itemCls:"required",
				allowBlank : false
			}]
		};

		ast.ast1949.admin.inquiry.inquiryForm.superclass.constructor.call(this,c);
	},
	initProducts:function(obj){
		obj = obj||{};
		this.findById("id").setValue(obj.id);
		this.findById("senderId").setValue(obj.senderId);
		this.findById("title").setValue(obj.title);
		this.findById("content").setValue(obj.content);
	}
});

ast.ast1949.admin.inquiry.productsGrid=Ext.extend(Ext.grid.GridPanel,{
	queryUrl : Context.ROOT + Context.PATH + "/admin/inquiry/listProducts.htm",
	constructor:function(config){
		config = config || {};
		Ext.apply(this,config);

		var _fields = this.fieldsRecord;
		var _url = this.queryUrl;
		var _store = new Ext.data.JsonStore({
			root:"records",
			totalProperty: 'totalRecords',
			remoteSort:true,
			fields:_fields,
			url:_url,
			autoLoad:false
		});

		var sm = new Ext.grid.CheckboxSelectionModel();
		var cm = new Ext.grid.ColumnModel([sm, {
			header : "编号",
			width : 60,
			sortable : false,
			dataIndex : "id"
		},{
			header : "公司ID",
			width : 20,
			sortable : false,
			hidden:true,
			dataIndex : "companyId"
		},{
			header:"公司",
			width:150,
			dataIndex:"companyName"
		},{
			header : "供求信息",
			width:500,
			sortable : false,
			dataIndex : "title"
		},{
			header : "刷新时间",
			sortable : false,
			dataIndex : "refreshTime",
			renderer : function(value, metadata, record, rowIndex, colIndex, store) {
				if (value != null) {
					return Ext.util.Format.date(new Date(value.time), 'Y-m-d');
				}
			}
		}]);

		var c={
			iconCls:"icon-grid",
			store:_store,
			sm:sm,
			cm:cm,
			frame:true,
			tbar:["供求类型 ",{
				xtype:"combo",
				id:"search-productsTypeCode_combo",
				name:"search-productsTypeCode",
				hiddenId:"search-products-type-code",
				triggerAction : "all",
				forceSelection : true,
				displayField : "label",
				valueField : "code",
				width:80,
				store:new Ext.data.JsonStore( {
					root : "records",
					fields : [ "label", "code" ],
					autoLoad:false,
					url : Context.ROOT + Context.PATH+ "/admin/category/selectCategoiesByPreCode.htm?preCode="+Context.CATEGORY["productsTypeCode"],
					listeners :{
					  load:function(){
					  }
					}
				})
//				,
//				listeners:{
//					"change":function(obj,newvalue,oldvalue){
//
//					}
//				}
			},"&nbsp;地区 ",{
				xtype:"combotree",
				id : "combo-area",
				name : "combo-area",
				width:120,
				hiddenName : "search-areaCode",
				hiddenId : "search-areaCode",
				editable:true,
				tree:new Ext.tree.TreePanel({
					loader: new Ext.tree.TreeLoader({
						root : "records",
						fields : [ "label", "code" ],
						autoLoad: false,
						url:Context.ROOT + Context.PATH+ "/admin/category/child.htm",
						listeners:{
							beforeload:function(treeload,node){
								this.baseParams["parentCode"] = node.attributes["data"];
							}
						}
					}),
			   	 	root : new Ext.tree.AsyncTreeNode({text:'全部地区',data:Context.CATEGORY.areaCode})
				})
			},"-"," 公司名称 ",{
				xtype:"textfield",
				id:"search-company-name"
			},"->",{
				text : "查询",
				iconCls : "query",
				scope:this,
				handler:function(btn){
					//TODO 这里查询供求信息
//					grid = Ext.getCmp(CREATEINQUIRY.PRODUCT_GRID);
					var B=this.getStore().baseParams||{};

					B["name"]=Ext.getCmp("search-company-name").getValue();
					if(Ext.get("search-productsTypeCode_combo").dom.value==""){
						B["productsTypeCode"]="";
					}else{
						B["productsTypeCode"]=Ext.getCmp("search-productsTypeCode_combo").getValue();
					}

					if(Ext.get("combo-area").dom.value==""){
						B["areaCode"]="";
					}else{
						B["areaCode"]=Ext.get("search-areaCode").dom.value;
					}
					this.baseParams = B;
					this.getStore().reload({params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}});
				}
			},"-",{
				text:"导出询盘给选中供求",
				iconCls:"item-add",
				handler:function(btn){
					var grid=Ext.getCmp(CREATEINQUIRY.PRODUCT_GRID);
					var form=Ext.getCmp(CREATEINQUIRY.INQUIRY_FORM);

					if(form.getForm().isValid()){
						var row = grid.getSelections();
						var _ids = new Array();
						for (var i=0,len = row.length;i<len;i++){
							var _id=row[i].get("id");
							var _companyId=row[i].get("companyId");
							_ids.push(_id+"|"+_companyId);
						}
						if(_ids.length>0) {
							var _url=Context.ROOT + Context.PATH + "/admin/inquiry/createInquiryForProductsByInquiry.htm?productsArray="+_ids;
							form.getForm().submit({
								url:_url,
								method:"post",
								success:function(){
									Ext.MessageBox.show({
										title:Context.MSG_TITLE,
										msg : "询盘信息已成功导出给指定的供求信息",
										buttons:Ext.MessageBox.OK,
										icon:Ext.MessageBox.INFO
									});
								},
								failure:function(){
									Ext.MessageBox.show({
										title:Context.MSG_TITLE,
										msg : "询盘信息导出发生错误",
										buttons:Ext.MessageBox.OK,
										icon:Ext.MessageBox.ERROR
									});
								}
							});
						} else {
							Ext.MessageBox.show({
								title:Context.MSG_TITLE,
								msg : "请至少选择一条记录！",
								buttons:Ext.MessageBox.OK,
								icon:Ext.MessageBox.ERROR
							});
						}
					} else {
						Ext.MessageBox.show({
								title:Context.MSG_TITLE,
								msg : "验证未通过！",
								buttons:Ext.MessageBox.OK,
								icon:Ext.MessageBox.ERROR
							});
					}
				}
			}],
			bbar: new Ext.PagingToolbar({
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
		ast.ast1949.admin.inquiry.productsGrid.superclass.constructor.call(this,c);

	},
	fieldsRecord:[{
		name:"id",
		mapping:"products.id"
	},{
		name:"companyId",
		mapping:"products.companyId"
	},{
		name:"companyName",
		mapping:"company.name"
	},{
		name:"title",
		mapping:"products.title"
	},{
		name:"details",
		mapping:"products.details"
	},{
		name:"refreshTime",
		mapping:"products.refreshTime"
	}]
});
