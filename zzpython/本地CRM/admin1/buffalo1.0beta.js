/* 
 * Buffalo Web Remoting, An amowa infrastructure facility, a web remoting library.
 * 
 * Author: Michael Chen (mechiland AT gmail DoT com, http://michael.nona.name)
 * 
 * Version: 1.0
 *
 */

/** 
 * @fileoverview Buffalo.js is the core component of the Buffalo web remoting.
 * This file encapuslate the Buffalo, BuffaloCall, BuffaloReply object, and 
 * most of it's methods.
 *
 * @author Michael Chen mechiland@gmail.com http://michael.nona.name
 * @version 1.0
 */

/**
 * BuffaloCall object.
 * In most case, you don't need to construct a BuffaloCall yourself. just use
 * buffalo.remoteCall(...) to make common use.
 *
 * @constructor
 * @param {String} methodname the remote call method name. 
 *        note: this method name is ONLY the method name, not the service name. 
 *        Buffalo will take care of the service name and url.
 */
//window.onerror = function(){return false};

function BuffaloCall(methodname){
	
	/**
	 * The method name
	 * @type String
	 * @private
	 */
	this.method = methodname;

	/**
	 * The parameters.
	 * @type Array
	 * @private
	 */
	this.params = [];

	
	return this;
}

/**
 * Set the remote call method name. 
 * 
 * @param {String} methodName the method name to set.
 */
BuffaloCall.prototype.setMethod = function(methodName){
  if (!methodName) return;
  this.method = methodName;
}

/**
 * Add one paramter to this buffalo call. Buffalo will try to find type 
 * of the data.
 *
 * @param {varient} data the param to be added. 
 */
BuffaloCall.prototype.addParameter = function(data){
  if (arguments.length==0) return;
  this.params[this.params.length] = data;
}

/**
 * Construct the call informatiton to a XML format, 
 * prepare to be sent to the buffalo end point.
 * 
 * @type String
 * @returns the xml format of this call.
 */
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
  
  return xml; // for now
}

/**
 * Judge the type of the paramter. should never be invoked by outside.
 *
 * @private
 * @type String
 * @returns the type of the object. 
 */
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

/**
 * Process the common value to a xml.
 * @private
 * @returns the xml format of the data
 */
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

/**
 * Process the boolean paramter to xml.
 * @private
 */
BuffaloCall.doBooleanXML = function(data){
  var value = (data==true)?1:0;
  var xml = "<boolean>" + value + "</boolean>";
  return xml;
}

/**
 * @private
 */
BuffaloCall.doDateXML = function(data){
  var xml = "<date>";
  xml += dateToISO8609(data);
  xml += "</date>";
  return xml;
}

/**
 * @private
 */
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

/**
 * @private
 */
BuffaloCall.doStructXML = function(data){
  var boClass = data[Buffalo.BOCLASS];
  // Default to hashMap
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

/**
 * @private
 */
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

/**
 * Convenient function to convert a Date object to a ISO8609 string format.
 * @private
 */
function dateToISO8609(date){
  var year = new String(date.getYear());
  var month = leadingZero(new String(date.getMonth()+1));
  var day = leadingZero(new String(date.getDate()));
  var time = leadingZero(new String(date.getHours())) + leadingZero(new String(date.getMinutes())) + leadingZero(new String(date.getSeconds()));

  var converted = year+month+day+"T"+time+"Z";
  return converted;
} 

/**
 * leading zero. 
 * @private
 */
function leadingZero(n){
  // pads a single number with a leading zero. Heh.
  if (n.length==1) n = "0" + n;
  return n;
}

/**
 * A burlap reply object hold the remote call's result, and convert the 
 * result xml to a object. This is one of the core object in the buffalo
 * web remoting. You should know the methods and properties very clearly.
 *
 * @constructor
 * @param {String} sourceXML the reply sourceXML
 */
function BuffaloReply(sourceXML) {
	
	// define the primary properties
	/**@private*/
	this._source = sourceXML;
	/**@private*/
	this._isFault = false;
	/**@private*/
	this._type = "null";
	/**@private*/
	this._objects = [];
	/**@private*/
	this._objectNodes = [];
	
	var xmldoc = XmlDocument.create();
	xmldoc.async=false;
	xmldoc.loadXML(sourceXML);
	var root = xmldoc.documentElement;
	/**@private*/
	this._root = root;

	var dataNode = root.firstChild;

	/**@private*/
	this._type = BuffaloReply._getType(dataNode);
	
	/**
	 * get the reply data type. The reply type will be:
	 * boolean, date, double, int, long, list, map, null, ref, string, xml or fault. 
	 * base64 is not supported. (do you want to use base64 at the client side?)
	 * 
	 * @type String
	 * @returns the reply's data type
	 * 
	 */
	this.getType = function() {
		return this._type;
	}
	
	/** 
	 * Get the result object. the returned type is differed by the type, 
	 * such as: if type=="boolean", then getResult() is a boolean value.
	 * the types are the same as burlap protocal. 
	 * here is the convert table:
	 * <ul>
	 * <li>boolean: a java script boolean value
	 * <li>date: a javascript Date value
	 * <li>double: a javascript float value, use parseFloat to convert
	 * <li>int,long: a javascript int value, use parseInt to convert
	 * <li>list: a javascript Array object
	 * <li>map: a javascript Object
	 * <li>null: javascript null
	 * <li>ref: a reference(pointer) to the specified javascript obejct.
	 * <li>string, xml: a javascript string
	 * <li>fault: a Fault object(NOT IMPLEMENTATED YET)
	 * </ul>
	 * @type varient
	 * @returns the result object
	 */
	this.getResult = function() {
		return this.deserialize(dataNode);
	}
	
	/**
	 * if this is a fault reply
	 * @type boolean
	 * @return if this is a fault reply
	 */
	this.isFault = function() {
		return (this._type == "fault");
	}
	
	/**
	 * indicate if this a null reply
	 * @type boolean
	 * @returns if this is a null reply
	 */
	this.isNull = function() {
		return (this._type == "null");
	}
	
	/**
	 * get the source xml, the source xml is capable with the burlap reply.
	 * @type String
	 * @returns the reply source xml
	 */
	this.getSource = function() {
		return this._source;
	}
}	

/**
 * Deserialize the xml data node to a javascript object
 * 
 * @private
 * @param {DOMElement} dataNode the dataNode
 * @returns the value of the dataNode
 */
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

/**
 * get the data type of the dataNode
 * @private
 */
BuffaloReply._getType = function(dataNode) {
	return dataNode.tagName;
}

/**
 * Get the dataNode value. for cross browser use.
 * @private
 */
BuffaloReply.getNodeText = function(dataNode) {
	
	if (dataNode.childNodes.length == 0) {
		return null;
	} else 
	return dataNode.firstChild.nodeValue;
}

/**
 * @private
 */
BuffaloReply.prototype.doBoolean = function (dataNode) {
	var value = BuffaloReply.getNodeText(dataNode);
	return (value == "1");
}
/**
 * @private
 */
BuffaloReply.prototype.doDate = function (dataNode) {

	var dateStr = BuffaloReply.getNodeText(dataNode);
	//parseInt will be strange if the first char is '0', so set the radix.
	var year = parseInt(dateStr.substring(0,4),"10");
	var month = parseInt(dateStr.substring(4,6),"10") - 1;
	var day = parseInt(dateStr.substring(6,8),"10");
	var hour = parseInt(dateStr.substring(9,11),"10");
	var minute = parseInt(dateStr.substring(11,13),"10");
	var second = parseInt(dateStr.substring(13,15),"10");
	
	var d = new Date(year, month, day, hour, minute, second);
	return d;
}
/**
 * @private
 */
BuffaloReply.prototype.doDouble = function (dataNode) {
	var value = BuffaloReply.getNodeText(dataNode);
	return parseFloat(value);
}
/**
 * @private
 */
BuffaloReply.prototype.doInt = function (dataNode) {
	var value = BuffaloReply.getNodeText(dataNode);
	return parseInt(value);
}
/**
 * @private
 */
BuffaloReply.prototype.doList = function (dataNode) {
	var arr = new Array();
	this._objects[this._objects.length] = arr;
	var children = dataNode.childNodes;
	for (var i=2; i < children.length; i++) {
		arr[arr.length] = this.deserialize(children[i]);
	}

	return arr;
}
/**
 * @private
 */
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
/**
 * @private
 */
BuffaloReply.prototype.doNull = function (dataNode) {
	return null;
}
/**
 * @private
 */
BuffaloReply.prototype.doRef = function (dataNode) {
	var value = BuffaloReply.getNodeText(dataNode);
	var idx = parseInt(value);
	
	return this._objects[idx];
}
/**
 * @private
 */
BuffaloReply.prototype.doString = function (dataNode) {
	var value = BuffaloReply.getNodeText(dataNode);
	if (value == null) {
		return "";
	}
	return (value);
}
/**
 * @private
 */
BuffaloReply.prototype.doXML = function (dataNode) {
	var value = BuffaloReply.getNodeText(dataNode);
	return unescape(value);
}
/**
 * TODO: add Fault object.
 * @private
 */
BuffaloReply.prototype.doFault = function (dataNode) {
	/*
	var value = BuffaloReply.getNodeText(dataNode);*/
	//TODO: need more attention.
	//return BuffaloReply.getNodeText(dataNode);
	var code = BuffaloReply.getNodeText(dataNode.childNodes[1]);
	var msg = BuffaloReply.getNodeText(dataNode.childNodes[3]);
	var detail = this.deserialize(dataNode.childNodes[5]);
	return new BuffaloFault(code, msg, detail);
	//return this.doMap(dataNode);
}

function BuffaloFault(code, message, detail) {
	this.code = code;
	this.message = message;
	this.detail = detail;

	this.toString = function() {
		return "code:" + this.code + ", message" + this.message + ", detail: " + this.detail;
	}
}

/**
 * The main entry class. User use like this:
 * 
 * <pre class="code">
 * var buffalo = new Buffalo("/buffalo/BUFFALO");
 * buffalo.remoteCall("simpleService.divide", [1.0,2.0], function(reply) {
 *           alert(reply.getResult());
 *            }
 * );
 * </pre>
 * @param {String} gateway the gateway url.
 * @constructor
 */ 
function Buffalo(gateway, async) {
	/**
	 * The gate way.
	 * @type String
	 * @private
	 */
	this.gateway = gateway;

	this.async = (async != null) ? async : true;

	this.onWaiting = Buffalo.showLoading;

	this.onFault = new Function();

	this.onError = new Function();
}

/**
 * Set the gateway(end point url) of this buffalo object. 
 * The url can be absolute or relative, such as http://host:port/BUFFALO or /BUFFALO
 * 
 * @param {String} gateway the gateway (end point url)
 * 
 */
Buffalo.prototype.setGateway = function(gateway) {
	this.gateway = gateway;
}

/**
 * Get the gate way of this buffalo object.
 * @type String
 * @returns the gateway of this buffalo object.
 */
Buffalo.prototype.getGateway = function() {
	return this.gateway;
}

Buffalo.BOCLASS = "_BUFFALO_OBJECT_CLASS_";

/**
 * The loading pane.
 * @type HTMLDOMElement
 * @private
 */
Buffalo.loadingPane = null;

/**
 * The exception pane
 * @type HTMLDOMElement
 * @private
 */
Buffalo.exceptionPane = null;

/**
 * the error pane
 * @type HTMLDOMElement
 * @private
 */
Buffalo.errorPane = null;

/**
 * Display or hide the loading pane. 
 * You don't need to call this function directly. Buffalo will take care of it when 
 * invoking remote functions.
 * 
 * @param {boolean} state the state of the loading pane, if true, show it, otherwise hide it.
 */
Buffalo.showLoading = function(state) {
	Buffalo.loadingPane = document.getElementById("buffalo_loading");
	if (Buffalo.loadingPane == null) {
		var el = document.createElement('DIV');
		el.setAttribute("id","buffalo_loading");
		el.style.cssText="display:none;font-family:Verdana;font-size:11px;border:1px solid #00CC00;background-color:#A4FFA4;padding:1px;position:absolute; right:1px; top:1px; width:110px; height:14px; z-index:1";
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

/**
 * Display the exception pane. 
 * TODO: not implemented yet.
 * @param {String} ex the exception string
 */
Buffalo.showException = function(ex) {
	
}

/**
 * Hide the exception pane.
 * TODO: not implemented yet.
 *
 */
Buffalo.hideException = function() {
	
}

Buffalo.instance = null;

/**
 * Invoke the remote Call, innerMethod. 
 * 
 * @private
 * @param <String> url The end point of the remote call
 * @param <BurlapCall> burlapCall The burlap call object
 * @param <Function> callback if the call returns, invoke the callback function
 */
Buffalo.prototype._remoteCall = function(url, burlapCall, callback) {
	Buffalo.instance = this;
	var xmlhttp = XmlHttp.create();
	xmlhttp.open("POST", url, this.async);
	xmlhttp.send(burlapCall.xml());
	//xmlhttp.onWaiting = this.onWaiting;
	// 异步方式
	if (this.async) {
		Buffalo.showLoading(true);
		//xmlhttp.onWaiting(true);
		//Buffalo.instance.onWaiting(true);
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4) {
				if (xmlhttp.status == '200') {
					var data = xmlhttp.responseText;
					//alert(data);
					// 标准的burlap返回的不是正常xml, 为了兼容普通burlap调用，需要进行调整
					if (data.indexOf("xmlns:burlap") == -1) {
						data.replace("<burlap:reply>", "<burlap:reply xmlns:burlap=\"http://www.amowa.net/buffalo/\">")
					}
					var reply = new BuffaloReply(data);

					Buffalo.showLoading(false);
					//Buffalo.instance.onWaiting(false);
					//xmlhttp.onWaiting(false);
					callback(reply);
					
				} else {
					var win = window.open("about:blank");
					win.document.open();
					win.document.write(xmlhttp.responseText);
					win.document.close();
				}
			}
		}
	} else {  // 同步方式
		if (xmlhttp.status == '200') {
			var data = xmlhttp.responseText;
			// 标准的burlap返回的不是正常xml, 为了兼容普通burlap调用，需要进行调整
			if (data.indexOf("xmlns:burlap") == -1) {
				data.replace("<burlap:reply>", "<burlap:reply xmlns:burlap=\"http://www.amowa.net/buffalo/\">")
			}
			
			var reply = new BuffaloReply(data);
			
			callback(reply);
			
		} else {
			var win = window.open("about:blank");
			win.document.open();
			win.document.write(xmlhttp.responseText);
			win.document.close();
		}
		
	}
	
}


/**
 * Call like this: remoteCall("someService.someMethod", arrayOfParams, callbackFunction)
 *
 * @param <String> service string the service name, like someService.someMethod
 * @param <Array> params array the service call parameters
 * @param <Function> callback the call back function
 */
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

/**
 * Help method to get the element reference for the elementId. 
 * for cross browser use.
 * @param {String} elementId the element id
 * @type HTMLDOMElement
 * @returns the dom element of the specified element id.
 */
Buffalo.getElementById = function(elementId) {
	return document.getElementById(elementId);
}