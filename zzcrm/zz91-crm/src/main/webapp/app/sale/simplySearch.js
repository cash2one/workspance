Ext.namespace("com.zz91.ep.crm");

/**
 * 高级搜索(只针对于公海和全部客户)
 */
com.zz91.ep.crm.simipSearchForm = Ext.extend(Ext.form.FormPanel, {
	targetGrid:null,
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		var _store = this.targetGrid.getStore();
		var B = _store.baseParams;
		B = B||{};
		
		var c = {
			bodyStyle : "padding:2px 2px 0",
			labelAlign : "right",
			labelWidth : 100,
			autoScroll:true,
			layout : "column",
			items:[{
				columnWidth:0.5,
				layout:"form",
				defaults:{
					anchor:"99%",
					xtype:"textfield",
					labelSeparator:""
				},
				items:[{
						fieldLabel : "公司名称",
						id : "cname",
						name:"cname",
						listeners:{
							"change":function(field,newvalue,oldvalue){
								if(newvalue==""){
									B["cname"] = undefined;
								}else{
									B["cname"] = newvalue;
								}
								_store.baseParams = B;
							}
						}
					},{
						fieldLabel : "地址",
						id : "address",
						name:"address",
						listeners:{
							"change":function(field,newvalue,oldvalue){
								if(newvalue==""){
									B["address"] = undefined;
								}else{
									B["address"] = newvalue;
								}
								_store.baseParams = B;
							}
						}
					},{
						fieldLabel : "电话/手机",
						id : "contact",
						name:"contact",
						listeners:{
							"change":function(field,newvalue,oldvalue){
								if(newvalue==""){
									B["contact"] = undefined;
								}else{
									B["contact"] = newvalue;
								}
								_store.baseParams = B;
							}
						}
					},{
						xtype: "combo",
						mode:"local",
						triggerAction:"all",
						fieldLabel:"公司业务类型",
						hiddenId:"businessCode",
						hiddenName: "businessCode",
						anchor: '99%',
						displayField: "name",
						valueField: "code",
						store: new Ext.data.Store({
							autoLoad: true,
							url: Context.ROOT+"/system/param/queryParamByTypes.htm?types="+COMBO.paramTypes["company_industry_code"],
							reader: new Ext.data.JsonReader({
								fields: [
									{name:"code",mapping:"value"},
									{name:"name",mapping:"name"}
								]
							}),
						}),
						listeners:{
							"change":function(field,newValue,oldValue){
								B["businessCode"]=newValue;
							}
						}
					},{
						xtype:"datefield",
						fieldLabel : "最近登陆时间(始)",
						id:"gmtLoginStart",
						name:"gmtLoginStart",
						format : 'Y-m-d',
						listeners:{
							"change":function(field,newvalue,oldvalue){
								if(newvalue==""){
									B["gmtLoginStart"] = null;
								}else{
									B["gmtLoginStart"] = newvalue;
								}
								_store.baseParams = B;
							}
						}
					},{
						xtype:"combo",
						mode:"local",
						triggerAction:"all",
						hiddenName:"sr",
						hiddenId:"sr",
						fieldLabel:"排序字段",
						emptyText:"",
						lazyRender:true,
						store:new Ext.data.ArrayStore({
							fields:["k","v"],
							data:[
								["gmt_register","注册时间"],
								["gmt_login","最近登录时间"],
								["gmt_contact","最近联系时间"],
								["gmt_next_contact","下次联系时间"],
								["star","星级"],
								["login_count","登录次数"],
								["contact_able_count","有效联系次数"]
							]
						}),
						valueField:"k",
						displayField:"v",
						listeners:{
							"change":function(field,newvalue,oldvalue){
								if(newvalue==""){
									B["sr"] = undefined;
								}else{
									B["sr"] = newvalue;
								}
								_store.baseParams = B;
							}
						}
					}]
					},{
						columnWidth:0.5,
						layout:"form",
						defaults:{
							anchor:"99%",
							xtype:"textfield",
							labelSeparator:""
						},
						items:[{
							xtype: "combo",
							mode:"local",
							triggerAction:"all",
							fieldLabel:"行业",
							hiddenId:"industryCode",
							hiddenName: "industryCode",
							anchor: '99%',
							displayField: "name",
							valueField: "code",
							store: new Ext.data.Store({
								autoLoad: true,
								url: Context.ROOT+"/system/param/queryParamByTypes.htm?types="+COMBO.paramTypes["comp_industry"],
								reader: new Ext.data.JsonReader({
									fields: [
										{name:"code",mapping:"value"},
										{name:"name",mapping:"name"}
									]
								}),
							}),
							listeners:{
								"change":function(field,newValue,oldValue){
									B["industryCode"]=newValue;
								}
							}
						},{
							fieldLabel : "联系人",
							id : "name",
							name:"name",
							listeners:{
								"change":function(field,newvalue,oldvalue){
									if(newvalue==""){
										B["name"] = undefined;
									}else{
										B["name"] = newvalue;
									}
									_store.baseParams = B;
								}
							}
						},{
							fieldLabel : "email",
							id : "email",
							name:"email",
							listeners:{
								"change":function(field,newvalue,oldvalue){
									if(newvalue==""){
										B["email"] = undefined;
									}else{
										B["email"] = newvalue;
									}
									_store.baseParams = B;
								}
							}
						},{
							xtype: "combo",
							mode:"local",
							triggerAction:"all",
							fieldLabel:"来源类型",
							hiddenId:"registerCode",
							hiddenName: "registerCode",
							anchor: '99%',
							displayField: "name",
							valueField: "code",
							store: new Ext.data.Store({
								autoLoad: true,
								url: Context.ROOT+"/system/param/queryParamByTypes.htm?types="+COMBO.paramTypes["register_type"],
								reader: new Ext.data.JsonReader({
									fields: [
										{name:"code",mapping:"value"},
										{name:"name",mapping:"name"}
									]
								}),
							}),
							listeners:{
								"change":function(field,newValue,oldValue){
									B["registerCode"]=newValue;
								}
							}
						},{
							xtype:"datefield",
							fieldLabel : "最近登陆时间(末)",
							id:"gmtLoginEnd",
							name:"gmtLoginEnd",
							format : 'Y-m-d',
							listeners:{
								"change":function(field,newvalue,oldvalue){
									if(newvalue==""){
										B["gmtLoginEnd"] = null;
									}else{
										B["gmtLoginEnd"] = newvalue;
									}
									_store.baseParams = B;
								}
							}
						},{
							xtype:"combo",
							mode:"local",
							triggerAction:"all",
							hiddenName:"sortMode",
							hiddenId:"sortMode",
							fieldLabel:"排序方式",
							emptyText:"",
							lazyRender:true,
							store:new Ext.data.ArrayStore({
								fields:["k","v"],
								data:[
									["0","升序"],
									["1","降序"]
								]
							}),
							valueField:"k",
							displayField:"v",
							listeners:{
								"change":function(field,newvalue,oldvalue){
									if(newvalue==""){
										B["sortMode"] = undefined;
									}else{
										B["sortMode"] = newvalue;
									}
									_store.baseParams = B;
								}
							}
						},{
							xtype:"hidden",
							fieldLabel : "公司所在库(个人库,公海库,高级客户库等)",
							id:"ctype",
							name:"ctype",
							listeners:{
								"change":function(field,newvalue,oldvalue){
									if(newvalue==""){
										B["ctype"] = undefined;
									}else{
										B["ctype"] = newvalue;
									}
									_store.baseParams = B;
								}
							}
						},{
							xtype:"hidden",
							fieldLabel : "登录次数",
							id:"loginCount",
							name:"loginCount",
							listeners:{
								"change":function(field,newvalue,oldvalue){
									if(newvalue==""){
										B["loginCount"] = undefined;
									}else{
										B["loginCount"] = newvalue;
									}
									_store.baseParams = B;
								}
							}
						},{
							xtype:"hidden",
							fieldLabel : "关系状态(0:已过期,1:未过期)",
							id:"status",
							name:"status",
							listeners:{
								"change":function(field,newvalue,oldvalue){
									if(newvalue==""){
										B["status"] = undefined;
									}else{
										B["status"] = newvalue;
									}
									_store.baseParams = B;
								}
							}
						},{
							xtype:"hidden",
							fieldLabel : "是否放入废品池(0:否,1:是)",
							id:"disableStatus",
							name:"disableStatus",
							listeners:{
								"change":function(field,newvalue,oldvalue){
									if(newvalue==""){
										B["disableStatus"] = undefined;
									}else{
										B["disableStatus"] = newvalue;
									}
									_store.baseParams = B;
								}
							}
						},{
							xtype:"hidden",
							fieldLabel : "销售状态 0:销售 1:非销售",
							id:"saleStatus",
							name:"saleStatus",
							listeners:{
								"change":function(field,newvalue,oldvalue){
									if(newvalue==""){
										B["saleStatus"] = undefined;
									}else{
										B["saleStatus"] = newvalue;
									}
									_store.baseParams = B;
								}
							}
						},{
							xtype:"hidden",
							fieldLabel : "重复公司ID(为0时,没有重复公司)",
							id:"repeatId",
							name:"repeatId",
							listeners:{
								"change":function(field,newvalue,oldvalue){
									if(newvalue==""){
										B["repeatId"] = undefined;
									}else{
										B["repeatId"] = newvalue;
									}
									_store.baseParams = B;
								}
							}
						},{
							xtype:"hidden",
							fieldLabel : "星级",
							id:"star",
							name:"star",
							listeners:{
								"change":function(field,newvalue,oldvalue){
									if(newvalue==""){
										B["star"] = undefined;
									}else{
										B["star"] = newvalue;
									}
									_store.baseParams = B;
								}
							}
						}]
					}],
			buttons:[{
				text:"搜索",
				handler:function(btn){
					_store.reload({params:{"start":0, "limit":Context.PAGE_SIZE}});
				}
			}]
		}
		com.zz91.ep.crm.simipSearchForm.superclass.constructor.call(this,c);
	},
	//公司所在库
	initSearchDto:function(ctype,loginCount,status,disableStatus,saleStatus,repeatId,star){
		if (ctype!=null){
			this.findById("ctype").setValue(ctype);
		}
		if (loginCount!=null){
			this.findById("loginCount").setValue(loginCount);
		}
		if (status!=null){
			this.findById("status").setValue(status);
		}
		if (disableStatus!=null){
			this.findById("disableStatus").setValue(disableStatus);
		}
		if (saleStatus!=null){
			this.findById("saleStatus").setValue(saleStatus);
		}
		if (repeatId!=null){
			this.findById("repeatId").setValue(repeatId);
		}
		if (star!=null){
			this.findById("star").setValue(star);
		}
	}
});