Ext.namespace("ast.ast1949.statesdata");
/**
 * k表示key,保存在combox的hiddenField
 * v表示value,combox显示的值
 */
ast.ast1949.statesdata.inquiry={
	//询盘对象类型
	beInquiredType:[
		{k:"0",v:"针对供求信息"},
		{k:"1",v:"针对公司"}
//		{k:"2",v:"针对询盘"}
	],
	//询盘来源类型
	inquiredType:[
		{k:"0",v:"网站客户发布的"},
		{k:"1",v:"从询盘信息导出来的"},
		{k:"2",v:"从供求信息导出来的"}
	],
	//导出处理状态
	exportStatus:[
		{k:"",v:"不限制"},
		{k:"0",v:"未处理"},
		{k:"1",v:"处理中"},
		{k:"2",v:"已处理"}
	],
	//群发标记
	batchSendType:[
		{k:"0",v:"客户自己发布的"},
		{k:"1",v:"客户询盘转供求询盘"},
		{k:"2",v:"供求信息转供求询盘"},
		{k:"3",v:"客户批量询盘"}
	],
	//稀缺信息模块,匹配信息是否查看
	isViewed:[
	    {k:"0",v:"否"},
	    {k:"1",v:"是"}
	]
};

ast.ast1949.statesdata.common={
	//关键字排名，审核是否通过	
	isChecked:[{k:"1",v:"审核"},{k:"0",v:"未审核"}],
	//留言管理，审核是否通过
	checked:[{k:"1",v:"审核"},{k:"0",v:"未审核"}]
};

/**
 * 供求信息管理
 * */
ast.ast1949.statesdata.products={
	checkStatus:[
		{k:"0",v:"未审核"},
		{k:"1",v:"审核通过"},
		{k:"2",v:"审核未通过退回"}
	]
};
/**
 *   企业报价有效期
 *   
 * */
ast.ast1949.statesdata.companyPrice={
	validTime:[
		{k:"0",v:"请选择"},
		{k:"10",v:"10天"},
		{k:"20",v:"20天"},
		{k:"30",v:"1个月"},
		{k:"90",v:"3个月"},
		{k:"180",v:"6个月"},
		{k:"-1",v:"长期有效"}	
	]
};