Ext.namespace("com.zz91.ep.crm");

/**
 * 高级搜索
 */
com.zz91.ep.crm.searchForm = Ext.extend(Ext.form.FormPanel, {
	targetGrid:null,
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var _store = this.targetGrid.getStore();
		var B = _store.baseParams;
		B = B||{};
		var form=this;
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
							fieldLabel : "主营业务",
							id : "mainProduct",
							name:"mainProduct",
							listeners:{
								"change":function(field,newvalue,oldvalue){
									if(newvalue==""){
										B["mainProduct"] = undefined;
									}else{
										B["mainProduct"] = newvalue;
									}
									_store.baseParams = B;
								}
							}
						},{
							xtype:"datefield",
							fieldLabel : "下次联系时间(始)",
							id:"gmtNextContactStart",
							name:"gmtNextContactStart",
							format : 'Y-m-d',
							listeners:{
								"change":function(field,newvalue,oldvalue){
									if(newvalue==""){
										B["gmtNextContactStart"] = null;
									}else{
										B["gmtNextContactStart"] = newvalue;
									}
									_store.baseParams = B;
								}
							}
						},{
							xtype:"datefield",
							fieldLabel : "最后联系时间(始)",
							id:"gmtLastContactStart",
							name:"gmtLastContactStart",
							format : 'Y-m-d',
							listeners:{
								"change":function(field,newvalue,oldvalue){
									if(newvalue==""){
										B["gmtLastContactStart"] = null;
									}else{
										B["gmtLastContactStart"] = newvalue;
									}
									_store.baseParams = B;
								}
							}
						},{
							xtype:"datefield",
							fieldLabel : "注册时间(始)",
							id:"gmtRegisterStart",
							name:"gmtRegisterStart",
							format : 'Y-m-d',
							listeners:{
								"change":function(field,newvalue,oldvalue){
									if(newvalue==""){
										B["gmtRegisterStart"] = null;
									}else{
										B["gmtRegisterStart"] = newvalue;
									}
									_store.baseParams = B;
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
						},{
							xtype:"combo",
							mode:"local",
							triggerAction:"all",
							hiddenName:"source",
							hiddenId:"source",
							fieldLabel:"来源途径",
							lazyRender:true,
							store:new Ext.data.ArrayStore({
								fields:["k","v"],
								data:[["1","网络来源为主"],
								      ["2","线下老客户为主"],["3","网络线下两者都有"]]
							}),
							valueField:"k",
							displayField:"v",
							listeners:{
								"change":function(field,newvalue,oldvalue){
									if(newvalue==""){
										B["source"] = undefined;
									}else{
										B["source"] = newvalue;
									}
									_store.baseParams = B;
								}
							}
						},{
							xtype:"combo",
							mode:"local",
							triggerAction:"all",
							hiddenName:"maturity",
							hiddenId:"maturity",
							fieldLabel:"网络成熟度",
							lazyRender:true,
							store:new Ext.data.ArrayStore({
								fields:["k","v"],
								data:[["1","网络不太懂，未做过推广"],["2","了解网络但未推广"],
								      ["3","了解网络已在其它付费推广"],["4","有受过伤，未在推广"]]
							}),
							valueField:"k",
							displayField:"v",
							listeners:{
								"change":function(field,newvalue,oldvalue){
									if(newvalue==""){
										B["maturity"] = undefined;
									}else{
										B["maturity"] = newvalue;
									}
									_store.baseParams = B;
								}
							}
						},{
							xtype:"combo",
							mode:"local",
							triggerAction:"all",
							hiddenName:"match",
							hiddenId:"match",
							fieldLabel:"求购商",
							lazyRender:true,
							store:new Ext.data.ArrayStore({
								fields:["k","v"],
								data:[["0","不是"],["1","是"]]
							}),
							valueField:"k",
							displayField:"v",
							listeners:{
								"change":function(field,newvalue,oldvalue){
									if(newvalue==""){
										B["mainBuy"] = undefined;
									}else{
										B["mainBuy"] = newvalue;
									}
									_store.baseParams = B;
								}
							}
						},{
							xtype:"combo",
							mode:"local",
							triggerAction:"all",
							fieldLabel:"最近联系后有登录",
							store:new Ext.data.ArrayStore({
								fields:["k","v"],
								data:[["0","否"],["1","是"]]
							}),
							valueField:"k",
							displayField:"v",
							listeners:{
								"change":function(field,newvalue,oldvalue){
									if(newvalue==""){
										B["afterLogin"] = undefined;
									}else{
										B["afterLogin"] = newvalue;
									}
									_store.baseParams = B;
								}
							}
						},{
							xtype:"combo",
							mode:"local",
							triggerAction:"all",
							fieldLabel:"决策人",
							store:new Ext.data.ArrayStore({
								fields:["k","v"],
								data:[["1","KP"],["2","KP推动者"],["0","不是KP"]]
							}),
							valueField:"k",
							displayField:"v",
							listeners:{
								"change":function(field,newvalue,oldvalue){
									if(newvalue==""){
										B["kp"] = undefined;
									}else{
										B["kp"] = newvalue;
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
		                    mode: "local",
		                    triggerAction: "all",
		                    forceSelection: true,
		                    editable: true,
		                    fieldLabel: "客户星级",
		                    id: "comboStar",
		                    hiddenId: "star",
		                    hiddenName: "star",
		                    displayField: "name",
		                    valueField: "value",
		                    autoSelect:true,
							store:new Ext.data.JsonStore({
								fields:["name","value"],
								 data :[
								    {name:"未划分星级",value:"0"},{name:"一星",value:"1"},
									{name:"二星",value:"2"},{name:"三星",value:"3"},
									{name:"四星",value:"4"},{name:"五星",value:"5"}]
							}),
							listeners:{
								"change":function(field,newValue,oldValue){
									B["star"]=newValue;
								}
							}
						},{
							xtype: "combo",
							mode:"local",
							triggerAction:"all",
							fieldLabel:"来源类型",
							id:"comboReg",
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
							fieldLabel : "下次联系时间(末)",
							id:"gmtNextContactEnd",
							name:"gmtNextContactEnd",
							format : 'Y-m-d',
							listeners:{
								"change":function(field,newvalue,oldvalue){
									if(newvalue==""){
										B["gmtNextContactEnd"] = null;
									}else{
										B["gmtNextContactEnd"] = newvalue;
									}
									_store.baseParams = B;
								}
							}
						},{
							xtype:"datefield",
							fieldLabel : "最后联系时间(末)",
							id:"gmtLastContactEnd",
							name:"gmtLastContactEnd",
							format : 'Y-m-d',
							listeners:{
								"change":function(field,newvalue,oldvalue){
									if(newvalue==""){
										B["gmtLastContactEnd"] = null;
									}else{
										B["gmtLastContactEnd"] = newvalue;
									}
									_store.baseParams = B;
								}
							}
						},{
							xtype:"datefield",
							fieldLabel : "注册时间(末)",
							id:"gmtRegisterEnd",
							name:"gmtRegisterEnd",
							format : 'Y-m-d',
							listeners:{
								"change":function(field,newvalue,oldvalue){
									if(newvalue==""){
										B["gmtRegisterEnd"] = null;
									}else{
										B["gmtRegisterEnd"] = newvalue;
									}
									_store.baseParams = B;
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
								data:[["0","升序"],["1","降序"]]
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
							xtype:"combo",
							mode:"local",
							triggerAction:"all",
							hiddenName:"promote",
							hiddenId:"promote",
							fieldLabel:"网络推广时间",
							lazyRender:true,
							store:new Ext.data.ArrayStore({
								fields:["k","v"],
								data:[["1","短期3个月内"],["2","长期（3个月以上or有计划未定时间）"],
								      ["3","无（直接拒绝）"],["4","有效果就合作"]]
							}),
							valueField:"k",
							displayField:"v",
							listeners:{
								"change":function(field,newvalue,oldvalue){
									if(newvalue==""){
										B["promote"] = undefined;
									}else{
										B["promote"] = newvalue;
									}
									_store.baseParams = B;
								}
							}
						},{
							xtype:"combo",
							mode:"local",
							triggerAction:"all",
							hiddenName:"match",
							hiddenId:"match",
							fieldLabel:"是否匹配",
							lazyRender:true,
							store:new Ext.data.ArrayStore({
								fields:["k","v"],
								data:[["0","未匹配"],["1","已匹配"]]
							}),
							valueField:"k",
							displayField:"v",
							listeners:{
								"change":function(field,newvalue,oldvalue){
									if(newvalue==""){
										B["match"] = undefined;
									}else{
										B["match"] = newvalue;
									}
									_store.baseParams = B;
								}
							}
						},{
							xtype:"combo",
							mode:"local",
							triggerAction:"all",
							hiddenName:"match",
							hiddenId:"match",
							fieldLabel:"供应商",
							lazyRender:true,
							store:new Ext.data.ArrayStore({
								fields:["k","v"],
								data:[["0","不是"],["1","是"]]
							}),
							valueField:"k",
							displayField:"v",
							listeners:{
								"change":function(field,newvalue,oldvalue){
									if(newvalue==""){
										B["mainSupply"] = undefined;
									}else{
										B["mainSupply"] = newvalue;
									}
									_store.baseParams = B;
								}
							}
						},{
							xtype:"combo",
							mode:"local",
							triggerAction:"all",
							fieldLabel:"询盘信息查询",
							lazyRender:true,
							store:new Ext.data.ArrayStore({
								fields:["k","v"],
								data:[
								      ["0","接收询盘信息"],
								      ["1","发送询盘信息"]]
							}),
							valueField:"k",
							displayField:"v",
							listeners:{
								"change":function(field,newvalue,oldvalue){
									_store.baseParams["messageTime"] = newvalue;
								}
							}
						},{
							xtype:"combo",
							mode:"local",
							triggerAction:"all",
							fieldLabel:"匹配程度",
							lazyRender:true,
							store:new Ext.data.ArrayStore({
								fields:["k","v"],
								data:[
								      ["1","紧急匹配"],
								      ["2","一般匹配"],
								      ["0","取消匹配"]
								]
							}),
							valueField:"k",
							displayField:"v",
							listeners:{
								"change":function(field,newvalue,oldvalue){
									_store.baseParams["matchDegree"] = newvalue;
								}
							}
						},{
							xtype:"hidden",
							fieldLabel : "总联系次数",
							id:"contactCount",
							name:"contactCount",
							listeners:{
								"change":function(field,newvalue,oldvalue){
									if(newvalue==""){
										B["contactCount"] = undefined;
									}else{
										B["contactCount"] = newvalue;
									}
									_store.baseParams = B;
								}
							}
						},{
							xtype:"hidden",
							fieldLabel : "次数查询条件(0:等于=,1:大于>,2:小于<)",
							id:"contactFlag",
							name:"contactFlag",
							listeners:{
								"change":function(field,newvalue,oldvalue){
									if(newvalue==""){
										B["contactFlag"] = undefined;
									}else{
										B["contactFlag"] = newvalue;
									}
									_store.baseParams = B;
								}
							}
						},{
							xtype:"hidden",
							fieldLabel : "查询条件(0不筛选联系次数,1:总联系次数,2:有效联系次数,3:无效联系次数)",
							id:"contactStatus",
							name:"contactStatus",
							listeners:{
								"change":function(field,newvalue,oldvalue){
									if(newvalue==""){
										B["contactStatus"] = undefined;
									}else{
										B["contactStatus"] = newvalue;
									}
									_store.baseParams = B;
								}
							}
						},{
							xtype:"hidden",
							fieldLabel : "(0:普通客户,1:重点客户)",
							id:"companyType",
							name:"companyType",
							listeners:{
								"change":function(field,newvalue,oldvalue){
									if(newvalue==""){
										B["companyType"] = undefined;
									}else{
										B["companyType"] = newvalue;
									}
									_store.baseParams = B;
								}
							}
						},{
							xtype:"hidden",
							fieldLabel : "(0:自动公海,1:手动公海)",
							id:"autoBlock",
							name:"autoBlock",
							listeners:{
								"change":function(field,newvalue,oldvalue){
									if(newvalue==""){
										B["autoBlock"] = undefined;
									}else{
										B["autoBlock"] = newvalue;
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
							fieldLabel : "公司所在库(0:刚注册客户,1:个人库,2:公海库,3高级客户库等)",
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
							fieldLabel : "销售成功标识(0:未成功,1:成功(待开通),2:成功(已开通))",
							id:"successStatus",
							name:"successStatus",
							listeners:{
								"change":function(field,newvalue,oldvalue){
									if(newvalue==""){
										B["successStatus"] = undefined;
									}else{
										B["successStatus"] = newvalue;
									}
									_store.baseParams = B;
								}
							}
						},{
							xtype:"hidden",
							fieldLabel : "手动数据是否审核(0:未审核 1:已审核)",
							id:"registStatus",
							name:"registStatus",
							listeners:{
								"change":function(field,newvalue,oldvalue){
									if(newvalue==""){
										B["registStatus"] = undefined;
									}else{
										B["registStatus"] = newvalue;
									}
									_store.baseParams = B;
								}
							}
						},{
							xtype:"hidden",
							fieldLabel : "是否是拖单/毁单 0:拖单 1:毁单 2:拖单和毁单",
							id:"dragDestryStatus",
							name:"dragDestryStatus",
							listeners:{
								"change":function(field,newvalue,oldvalue){
									if(newvalue==""){
										B["dragDestryStatus"] = undefined;
									}else{
										B["dragDestryStatus"] = newvalue;
									}
									_store.baseParams = B;
								}
							}
						},{
							xtype:"hidden",
							fieldLabel : "公海次数比较值",
							id:"blockCount",
							name:"blockCount",
							listeners:{
								"change":function(field,newvalue,oldvalue){
									if(newvalue==""){
										B["blockCount"] = null;
									}else{
										B["blockCount"] = newvalue;
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
										B["status"] = null;
									}else{
										B["status"] = newvalue;
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
								}
							}
						},{
							xtype:"hidden",
							fieldLabel : "销售类型",
							id:"saleType",
							name:"saleType",
							listeners:{
								"change":function(field,newvalue,oldvalue){
									if(newvalue==""){
										B["saleType"] = null;
									}else{
										B["saleType"] = newvalue;
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
		com.zz91.ep.crm.searchForm.superclass.constructor.call(this,c);
	},
	//下次联系开始时间,下次联系结束时间,公司类型(是否重点),拖单/毁单,联系次数,
	//次数查询状态,查询条件(总联系次数),公海次数比较值,公司所在库,废品池状态,最后联系时间(结束),
	//星级,公司来源,手动数据审核状态,是否自动掉公海,是否有效数据,销售状态,重复公司ID
	initSearchDto:function(nextStart, nextEnd,companyType,dragDestryStatus,contactCount,
			contactStatus,contactFlag,blockCount,ctype,disableStatus,gmtLastContactEnd,
			star,registerCode,registStatus,autoBlock,status,saleStatus,repeatId){
		if (nextStart!=null){
			this.findById("gmtNextContactStart").setValue(nextStart);
		}
		if (nextEnd!=null){
			this.findById("gmtNextContactEnd").setValue(nextEnd);
		}
		if (companyType!=null){
			this.findById("companyType").setValue(companyType);
		}
		if (dragDestryStatus!=null){
			this.findById("dragDestryStatus").setValue(dragDestryStatus);
		}
		if (contactCount!=null){
			this.findById("contactCount").setValue(contactCount);
		}
		if (contactStatus!=null){
			this.findById("contactStatus").setValue(contactStatus);
		}
		if (contactFlag!=null){
			this.findById("contactFlag").setValue(contactFlag);
		}
		if (blockCount!=null){
			this.findById("blockCount").setValue(blockCount);
		}
		if (ctype!=null){
			this.findById("ctype").setValue(ctype);
		}
		if (disableStatus!=null){
			this.findById("disableStatus").setValue(disableStatus);
		}
		if (gmtLastContactEnd!=null){
			this.findById("gmtLastContactEnd").setValue(gmtLastContactEnd);
		}
		if (star!=null){
			this.findById("comboStar").setValue(star);
		}
		if (registerCode!=null){
			this.findById("comboReg").setValue(registerCode);
		}
		if (registStatus!=null){
			this.findById("registStatus").setValue(registStatus);
		}
		if (autoBlock!=null){
			this.findById("autoBlock").setValue(autoBlock);
		}
		if (status!=null){
			this.findById("status").setValue(status);
		}
		if (saleStatus!=null){
			this.findById("saleStatus").setValue(saleStatus);
		}
		if (repeatId!=null){
			this.findById("repeatId").setValue(repeatId);
		}
	}
});