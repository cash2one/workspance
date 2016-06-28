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
				id:"account",
				name : "senderAccount",
				fieldLabel : "发件人",
				itemCls:"required",
				allowBlank : false
			},{
				id:"yuanliaoTypeCode",
				name : "yuanliaoTypeCode",
				fieldLabel : "供求类型"
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
		this.findById("account").setValue(obj.account);
		this.findById("yuanliaoTypeCode").setValue(obj.yuanliaoTypeCode);
	}
});

ast.ast1949.admin.inquiry.productsGrid=Ext.extend(Ext.grid.GridPanel,{
	queryUrl : Context.ROOT + Context.PATH + "/yuanliao/listYuanLiaoExportInquiry.htm",
	constructor:function(config){
		config = config || {};
		Ext.apply(this,config);
		var _this=this;
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
			hidden:true,
			sortable : false,
			dataIndex : "id"
		},{
			header : "公司ID",
			width : 20,
			sortable : false,
			hidden:true,
			dataIndex : "companyId"
		},{
			header:"供/求",
			dataIndex : "yuanliaoTypeLabel",
			width:50,
			renderer:function(value,metadata,record,rowindex,colindex,store){
				if(value=="10331000"){
					return "供应";
				}else{
					return "求购";
				}
			}
		},{
			header : "供求信息",
			width:400,
			sortable : false,
			dataIndex : "title",
			renderer : function(value, metadata, record, rowIndex, colndex, store) {
				var webstr="<a href='http://slyl.zz91.com/detail/"+record.get("id")+".html' target='_blank' >";
				webstr=webstr+"<img src='"+Context.ROOT+"/css/admin/icons/web16.png' /></a>";
				var title=value;
				if(!_this.readOnly){
					title=" <a href='"+
						Context.ROOT+Context.PATH+
						"/yuanliao/edit.htm?id="+record.get("id")+
						"&companyId="+record.get("companyId")+
						"&account="+record.get("account")+
						"' target='_blank'>"+value+"</a>";
				}
				return webstr+title;
			}
		},{
			header:"公司",
			width:150,
			dataIndex:"companyName",
			renderer : function(value, metadata, record, rowIndex, colndex, store) {
				var val="";
				if(record.get("membershipCode")!="10051000"){
					val="<img src='"+Context.ROOT+"/images/recycle"+record.get("membershipCode")+".gif' />";
				}
				if(_this.readOnly){
					val= val + value;
				}else{
					val= val + "<a href='" + Context.ROOT + Context.PATH + 
						"/crm/company/detail.htm?companyId=" + 
						record.get("companyId") + "' target='_blank'>" + 
						value + "</a>";
				}
				return val;
			}
		}, {
			header : "发布时间",
			sortable : true,
			width:80,
			dataIndex : "post_time",
			renderer : function(value, metadata, record, rowIndex, colIndex, store) {
				if (value != null) {
					return Ext.util.Format.date(new Date(value.time), 'Y-m-d');
				}
			}
		},{
			header : "刷新时间",
			sortable : false,
			dataIndex : "refresh_time",
			renderer : function(value, metadata, record, rowIndex, colIndex, store) {
				if (value != null) {
					return Ext.util.Format.date(new Date(value.time), 'Y-m-d');
				}
			}
		},{
			header : "审核时间",
			width:100,
			sortable : true,
			dataIndex : "check_time",
			renderer : function(value, metadata, record, rowIndex,colIndex, store) {
				if(value!=null){
					return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
				}
				else{
					return "";
				}
			}
		}]);

		var c={
			iconCls:"icon-grid",
			store:_store,
			sm:sm,
			cm:cm,
			frame:true,
			tbar:[" 关键字 ",{
				xtype:"textfield",
				id:"search-products-title"
			},"供求类型 ",{
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
			},{
				xtype:"textfield",
				hidden:true,
				id:"search-products-category"
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
						B["yuanliaoTypeCode"]="";
					}else{
						B["yuanliaoTypeCode"]=Ext.getCmp("search-productsTypeCode_combo").getValue();
					}

					if(Ext.get("combo-area").dom.value==""){
						B["areaCode"]="";
					}else{
						B["areaCode"]=Ext.get("search-areaCode").dom.value;
					}
					
					if(Ext.get("search-products-title").dom.value==""){
						B["keyword"]="";
					}else{
						B["keyword"]=Ext.get("search-products-title").dom.value;
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
							var _url=Context.ROOT + Context.PATH + "/admin/inquiry/createInquiryForYuanLiaoByInquiry.htm?productId="+Ext.getCmp("id").getValue()+"&productsArray="+_ids;
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
			},{
				text : "自动群发",
				iconCls : "item-add",
				scope:this,
				handler:function(btn){
					var B=this.getStore().baseParams||{};
					var name = "";
					if(Ext.getCmp("search-company-name").getValue()!=""){
						name = Ext.getCmp("search-company-name").getValue();
					}
					var productsTypeCode = "";
					if(Ext.get("search-productsTypeCode_combo").dom.value==""){
						productsTypeCode="";
					}else{
						productsTypeCode=Ext.getCmp("search-productsTypeCode_combo").getValue();
					}
					var areaCode = ""
					if(Ext.get("combo-area").dom.value==""){
						areaCode="";
					}else{
						areaCode=Ext.get("search-areaCode").dom.value;
					}
					var title = "";
					if(Ext.get("search-products-title").dom.value==""){
						title="";
					}else{
						title=Ext.get("search-products-title").dom.value;
					}
					var category="";
					if(Ext.get("search-products-category").dom.value!=""){
						category = Ext.get("search-products-category").dom.value;
					}
					var productId = "";
					if(Ext.get("id").dom.value!=""){
						productId = Ext.get("id").dom.value;
					}
					Ext.Ajax.request({
						url: Context.ROOT + Context.PATH +"/admin/products/batchExportInquiry.htm",
						async: false,   //ASYNC 是否异步( TRUE 异步 , FALSE 同步)
						dataType:"json",
						params: {//将真正的页面（服务）url参数传递到代理页面
							"name": name,
							"productsTypeCode": productsTypeCode,
							"title": title,
							"categoryProductsMainCode":category,
							"areaCode":areaCode,
							"productId":productId
						},
						success: function(data) {
							var jsonResult = Ext.util.JSON.decode(data.responseText); 
							if(jsonResult.success){
								Ext.MessageBox.show({
									title:Context.MSG_TITLE,
									msg : "询盘已经发送成功！成功导出给"+jsonResult.data+"个公司",
									buttons:Ext.MessageBox.OK,
									icon:Ext.MessageBox.INFO
								});
							}
						}, //请求成功的回调函数 
						failure: function() { 
							alert("获取目录请求失败！"); 
						}  // 请求失败的回调函数
			        });
					
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
		mapping:"yuanliao.id"
	},{
		name:"companyId",
		mapping:"yuanliao.companyId"
	},{
		name:"companyName",
		mapping:"company.name"
	},{
		name:"membershipCode",
		mapping:"company.membershipCode"
	},{
		name:"title",
		mapping:"yuanliao.title"
	},{
		name:"description",
		mapping:"yuanliao.description"
	},{
		name:"refresh_time",
		mapping:"yuanliao.refreshTime"
	},{
		name:"yuanliaoTypeLabel",
		mapping:"yuanliao.yuanliaoTypeCode"
	},{
		name:"check_time",
		mapping:"yuanliao.checkTime"
	},{
		name:"post_time",
		mapping:"yuanliao.postTime"
	}]
});
