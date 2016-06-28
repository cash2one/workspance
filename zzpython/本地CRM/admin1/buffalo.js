function BuffaloCall(methodname){
	this.method = methodname;
	this.params = [];
	return this;
}

BuffaloCall.prototype.setMethod = function(methodName){
  if (!methodName) return;
  this.method = methodName;
}

BuffaloCall.prototype.addParameter = function(data){
  if (arguments.length==0) return;
  this.params[this.params.length] = data;
}

BuffaloCall.prototype.xml = function(){
  var method = this.method;
  
  var xml = "";
  xml += "<burlap:call>\n";
  xml += "<method>" + method+ "</method>\n";
  
  for (var i = 0; i < this.params.length; i++){
    var data = this.params[i];
    xml += BuffaloCall.getParamXML(BuffaloCall.dataTypeOf(data),data) + "\n";
  }
  
  xml += "</burlap:call>";
  
  return xml; 
}

BuffaloCall.dataTypeOf = function (o){
  var type = typeof(o);
  type = type.toLowerCase();
  switch(type){
    case "number":
      if (Math.round(o) == o) type = "int";
      else type = "double";
      break;
    case "object":
      var con = o.constructor;
      if (con == Date) type = "date";
      else if (con == Array) type = "list";
      else type = "map";
      break;
  }
  return type;
}

BuffaloCall.doValueXML = function(type,data){
	
  var xml = "<" + type + ">" + xmlEncode(data) + "</" + type + ">";
  return xml;
}

function xmlEncode(data) {
	var str = "";
	if (typeof(data) == "string") {
		str = data;
	} else {
		return data;
	}
	
	str = str.replace("&","&amp;");
	str = str.replace("<","&lt;");
	str = str.replace(">","&gt;");
	
	return str;
}

BuffaloCall.doBooleanXML = function(data){
  var value = (data==true)?1:0;
  var xml = "<boolean>" + value + "</boolean>";
  return xml;
}

BuffaloCall.doDateXML = function(data){
  var xml = "<date>";
  xml += dateToISO8609(data);
  xml += "</date>";
  return xml;
}

BuffaloCall.doArrayXML = function(data){
  var xml = "<list>\n";
  xml += "<type>" +""+ "</type>\n";
  xml += "<length>" +data.length+ "</length>\n";
  for (var i = 0; i < data.length; i++){
	xml += BuffaloCall.getParamXML(BuffaloCall.dataTypeOf(data[i]),data[i]) + "\n";
  }
  xml += "</list>\n";
  return xml;
}

BuffaloCall.doStructXML = function(data){
  var boClass = data[Buffalo.BOCLASS];
  var boType = "java.util.HashMap";
  if (typeof(boClass) != 'undefined' || boClass != null) {
	boType = boClass;
  }
  var xml = "<map>\n";
  xml += "<type>" +boType+ "</type>\n";

  for (var i in data){
	  if (data[i] != boType) {
		xml += BuffaloCall.getParamXML(BuffaloCall.dataTypeOf(i),i)+"\n";
		xml += BuffaloCall.getParamXML(BuffaloCall.dataTypeOf(data[i]),data[i]) + "\n";
	  }
  }
  xml += "</map>\n";
  return xml;
}

BuffaloCall.getParamXML = function(type,data){
  var xml;
  switch (type){
    case "date":
      xml = BuffaloCall.doDateXML(data);
      break;
    case "list":
      xml = BuffaloCall.doArrayXML(data);
      break;
    case "map":
      xml = BuffaloCall.doStructXML(data);
      break;
	  case "boolean":
      xml = BuffaloCall.doBooleanXML(data);
      break;
    default:
      xml = BuffaloCall.doValueXML(type,data);
      break;
  }
  return xml;
}

function dateToISO8609(date){
  var year = new String(date.getYear());
  var month = leadingZero(new String(date.getMonth()+1));
  var day = leadingZero(new String(date.getDate()));
  var time = leadingZero(new String(date.getHours())) + leadingZero(new String(date.getMinutes())) + leadingZero(new String(date.getSeconds()));

  var converted = year+month+day+"T"+time+"Z";
  return converted;
} 

function leadingZero(n){
  if (n.length==1) n = "0" + n;
  return n;
}

function BuffaloReply(sourceXML) {
	
	this._source = sourceXML;
	this._isFault = false;
	this._type = "null";
	this._objects = [];
	this._objectNodes = [];
	
	var xmldoc = XmlDocument.create();
	xmldoc.async=false;
	xmldoc.loadXML(sourceXML);
	var root = xmldoc.documentElement;
	this._root = root;

	var dataNode = root.firstChild;

	this._type = BuffaloReply._getType(dataNode);
	
	this.getType = function() {
		return this._type;
	}
	
	this.getResult = function() {
		return this.deserialize(dataNode);
	}
	
	this.isFault = function() {
		return (this._type == "fault");
	}
	
	this.isNull = function() {
		return (this._type == "null");
	}
	
	this.getSource = function() {
		return this._source;
	}
}	

BuffaloReply.prototype.deserialize = function(dataNode) {
	var ret;
	
	type = BuffaloReply._getType(dataNode);

	switch (type) {
		case "boolean": 
			ret = this.doBoolean(dataNode);
			break;
		case "date": 
			ret = this.doDate(dataNode);
			break;
		case "double": 
			ret = this.doDouble(dataNode);
			break;
		case "int": 
		case "long": 
			ret = this.doInt(dataNode);
			break;
		case "list": 
			ret = this.doList(dataNode);
			break;
		case "map": 
			ret = this.doMap(dataNode);
			break;
		case "null": 
			ret = this.doNull(dataNode);
			break;
		case "ref": 
			ret = this.doRef(dataNode);
			break;
		case "string": 
			ret = this.doString(dataNode);
			break;
		case "xml": 
			ret = this.doXML(dataNode);
			break;
		case "fault": 
			ret = this.doFault(dataNode);
			break;
		default:
			;
	}

	return ret;
}

BuffaloReply._getType = function(dataNode) {
	return dataNode.tagName;
}

BuffaloReply.getNodeText = function(dataNode) {
	
	if (dataNode.childNodes.length == 0) {
		return null;
	} else 
	return dataNode.firstChild.nodeValue;
}

BuffaloReply.prototype.doBoolean = function (dataNode) {
	var value = BuffaloReply.getNodeText(dataNode);
	return (value == "1");
}
BuffaloReply.prototype.doDate = function (dataNode) {

	var dateStr = BuffaloReply.getNodeText(dataNode);
	var year = parseInt(dateStr.substring(0,4),"10");
	var month = parseInt(dateStr.substring(4,6),"10") - 1;
	var day = parseInt(dateStr.substring(6,8),"10");
	var hour = parseInt(dateStr.substring(9,11),"10");
	var minute = parseInt(dateStr.substring(11,13),"10");
	var second = parseInt(dateStr.substring(13,15),"10");
	
	var d = new Date(year, month, day, hour, minute, second);
	return d;
}

BuffaloReply.prototype.doDouble = function (dataNode) {
	var value = BuffaloReply.getNodeText(dataNode);
	return parseFloat(value);
}
BuffaloReply.prototype.doInt = function (dataNode) {
	var value = BuffaloReply.getNodeText(dataNode);
	return parseInt(value);
}
BuffaloReply.prototype.doList = function (dataNode) {
	var arr = new Array();
	this._objects[this._objects.length] = arr;
	var children = dataNode.childNodes;
	for (var i=2; i < children.length; i++) {
		arr[arr.length] = this.deserialize(children[i]);
	}

	return arr;
}
BuffaloReply.prototype.doMap = function (dataNode) {
	
	var obj = new Object();
	this._objects[this._objects.length] = obj;

	var attrs = dataNode.childNodes;
	for (var i = 1; i < attrs.length; i+=2) {
		if (attrs[i+1].hasChildNodes() ) {
			obj[BuffaloReply.getNodeText(attrs[i])] = this.deserialize(attrs[i+1]);
		} else {
			obj[BuffaloReply.getNodeText(attrs[i])] = attrs[i+1].text;
		}
	}
	
	return obj;
}
BuffaloReply.prototype.doNull = function (dataNode) {
	return null;
}
BuffaloReply.prototype.doRef = function (dataNode) {
	var value = BuffaloReply.getNodeText(dataNode);
	var idx = parseInt(value);
	
	return this._objects[idx];
}
BuffaloReply.prototype.doString = function (dataNode) {
	var value = BuffaloReply.getNodeText(dataNode);
	if (value == null) {
		return "";
	}
	return (value);
}
BuffaloReply.prototype.doXML = function (dataNode) {
	var value = BuffaloReply.getNodeText(dataNode);
	return unescape(value);
}
BuffaloReply.prototype.doFault = function (dataNode) {
	var code = BuffaloReply.getNodeText(dataNode.childNodes[1]);
	var msg = BuffaloReply.getNodeText(dataNode.childNodes[3]);
	var detail = this.deserialize(dataNode.childNodes[5]);
	return new BuffaloFault(code, msg, detail);
}

function BuffaloFault(code, message, detail) {
	this.code = code;
	this.message = message;
	this.detail = detail;

	this.toString = function() {
		return "code:" + this.code + ", message" + this.message + ", detail: " + this.detail;
	}
}

function Buffalo(gateway, async) {
	this.gateway = gateway;
	this.transport = null;
	this.async = (async != null) ? async : true;

	this.onLoading = Buffalo.showLoading;

	this.onFinish = new Function();
	
	this.onException = new Function();

	this.onError = Buffalo.showError;
}

Buffalo.prototype.setGateway = function(gateway) {
	this.gateway = gateway;
}

Buffalo.prototype.getGateway = function() {
	return this.gateway;
}

Buffalo.BOCLASS = "_BUFFALO_OBJECT_CLASS_";

Buffalo.loadingPane = null;

Buffalo.exceptionPane = null;

Buffalo.errorPane = null;

Buffalo.showLoading = function(state) {
	Buffalo.loadingPane = document.getElementById("buffalo_loading");
	if (Buffalo.loadingPane == null) {
		var el = document.createElement('DIV');
		el.setAttribute("id","buffalo_loading");
		el.style.cssText="display:none;font-family:Verdana;font-size:11px;border:1px solid #00CC00;background-color:#A4FFA4;padding:1px;position:absolute; right:1px; top:1px; width:110px; height:14px; z-index:10000";
		el.innerHTML="Buffalo loading... ";
		document.body.appendChild(el);
		Buffalo.loadingPane = el;
	}
	if (state) {
		Buffalo.loadingPane.style.display="block";
		Buffalo.loadingPane.style.top = document.body.scrollTop+1;
	} else {
		Buffalo.loadingPane.style.display="none";
	}
	
}

Buffalo.showError = function(errorStr) {
	Buffalo.errorPane = document.getElementById("buffalo_error");
	if (Buffalo.errorPane == null) {
		var el = document.createElement('DIV');
		el.setAttribute("id","buffalo_error");
		el.style.cssText="font-family:Verdana;font-size:11px;border:1px solid #00CC00;background-color:#ffdb9c;padding:1px;position:absolute;overflow:auto; right:1px; top:1px; width:500px; height:300px; z-index:1";
		el.innerHTML=errorStr;
		document.body.appendChild(el);
		Buffalo.errorPane = el;
	}
	
}

Buffalo.showException = function(ex) {
	
}


Buffalo.prototype.onStateChange = function(){
	
	if (this.transport.readyState == 4) {
		if (this.transport.status == '200') {
			
			var data = this.transport.responseText;
			if (data.indexOf("xmlns:burlap") == -1) {
				data.replace("<burlap:reply>", "<burlap:reply xmlns:burlap=\"http://www.amowa.net/buffalo/\">")
			}
			
			this.onLoading(false);
			
			this.onFinish(new BuffaloReply(data));

		} else {
			this.onError(this.transport.responseText);
		}
	}
}

Function.prototype.bind = function(object) {
	var method = this;
	return function() {
		method.apply(object, arguments);
	}
}

Buffalo.prototype._remoteCall = function(url, burlapCall, callback) {
	this.transport = XmlHttp.create();
	this.transport.open("POST", url, this.async);
	this.transport.send(burlapCall.xml());
	this.onFinish = callback;

	if (this.async) {
		this.transport.onreadystatechange = this.onStateChange.bind(this);
		this.onLoading(true);
	} else {
		if (this.transport.status == '200') {
			var data = this.transport.responseText;
			if (data.indexOf("xmlns:burlap") == -1) {
				data.replace("<burlap:reply>", "<burlap:reply xmlns:burlap=\"http://www.amowa.net/buffalo/\">")
			}
			this.onFinish(new BuffaloReply(data));	
		} else {
			this.onError(this.transport.responseText);
		}	
	}
}

Buffalo.prototype.remoteCall = function(service, params, callback) {	
	var idx = service.indexOf(".");
	
	var serviceId = service.substring(0,idx);
	var method = service.substring(idx+1,service.length);
	var newUrl = this.gateway+"?sid="+serviceId;

	var call = new BuffaloCall(method);
	for (var i = 0; i < params.length; i++) {
		call.addParameter(params[i]);
	}

	this._remoteCall(newUrl, call, callback);
}

Buffalo.getElementById = function(elementId) {
	return document.getElementById(elementId);
}


var _endPoint="/service/burlap_skeleton.bo";	

