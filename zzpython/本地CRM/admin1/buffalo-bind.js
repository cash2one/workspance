/* 
 * Buffalo Web Remoting, An amowa infrastructure facility, a web remoting library.
 * 
 * Author: Michael Chen (mechiland AT gmail DoT com, http://michael.nona.name)
 * 
 * Version: 1.0
 *
 */

/** 
 * @fileoverview buffalo-bind.js is the bind library for binding the remote call
 * reply to the html elements.
 *
 * @author Michael Chen mechiland@gmail.com http://michael.nona.name
 * @version 1.0
 */

/**
 * Invoke the remote service directly, and bind the reply to 
 * the specified element. 
 * 
 * @param {String} service the remote service, such as serviceName.methodName
 * @param {Array} params The parameters. if none, use <code>[]</code> and don't use <code>null</code>
 * @param {String} bindElemId The element id in the page.
 */
Buffalo.prototype.bindReply = function(service, params, bindElemId) {
	this.remoteCall(service, params, function(reply) {
		Buffalo.bind(bindElemId, reply.getResult());
	});	
}

/**
 * Bind the value to the specified elementId.
 * 
 * @param {String} elementId The element id
 * @param {varient} bindValue The binded value. 
 */
Buffalo.bind = function(elementId, bindValue) {
	var elem = Buffalo.getElementById(elementId);
	switch(elem.tagName) {
		case "INPUT": 
			switch (elem.type.toLowerCase()) {
				
				case "text": ;
				case "hidden": ;
				case "password": BindFactory.bindText(elem, bindValue); break;

				case "checkbox": ;
				case "radio": BindFactory.bindRadioOrCheckbox(elem, bindValue); break;
			}
			break;
		case "TEXTAREA":
			BindFactory.bindText(elem, bindValue);
			break; 
		case "TABLE": 
			BindFactory.bindTable(elem, bindValue);
			break; 
		case "SELECT": 
			BindFactory.bindSelect(elem, bindValue);
			break; 
		case "DIV":
		case "SPAN":
			alert(bindValue);
			elem.innerHTML = bindValue;
			break;
		//TODO: add more bindings here for 
	}
}

/**
 * Factory method. Do not call it directly.
 */
function BindFactory(){}

/**
 * Report the error.
 * @param {HTMLDOMElement} elem the html element
 * @param {varient} the value to be binded
 * @param {String} msg the error message
 */
BindFactory.reportError = function(elem, value, msg) { 
	throw "Data bind failed: "+msg;
}

/**
 * Bind value to the text-like element, such as input, textarea
 * @param {HTMLDOMElement} elem the html element
 * @param {varient} value the value to bind
 */
BindFactory.bindText = function(elem, value) { 
	elem.value = value;
}

/**
 * Bind value to the radiobox oo checkbox.
 * @param {HTMLDOMElement} elem the html element
 * @param {varient} value the value to bind
 */
BindFactory.bindRadioOrCheckbox = function(elem, value) {
	var ret = false;
	switch (typeof(value)) {
		case 'boolean': ret = value; break;
		case 'string': ret = (value == "1" || value == "true" || value == "yes"); break;
		case 'number': ret = (parseInt(value) == 1); break;
		default: ret = false;
	}
	elem.checked = ret;
}

/**
 * Bind value to select/list element.
 *
 * @param {HTMLDOMElement} elem the html element
 * @param {varient} value the value to bind
 */
BindFactory.bindSelect = function(elem, value) {
	//TODO: Check the data type
	if (typeof(value) != "object" || value.constructor != Array) {
		BindFactory.reportError(elem,value,"绑定到Select控件需要数组类型数据!");
	}
	// delete all the nodes.
	while (elem.childNodes.length > 0) {
		elem.removeChild(elem.childNodes[0]);
	}
	
	// bind data
	for (var i = 0; i < value.length; i++) {
		
		var option = document.createElement("OPTION");
		
		var data = value[i];

		if (data == null || typeof(data) == "undefined") {
			option.value = "";
			option.text = "";
		}
		if (typeof(data) != 'object') {
			option.value = data;
			option.text = data;
		} else {
			option.value = data[elem.getAttribute("jvalue")];
			option.text = data[elem.getAttribute("jtext")];	
		}
		elem.options.add(option);
	}
}

/**
 * Bind value to resources select/list element.
 *
 * @param {HTMLDOMElement} elem the html element
 * @param {varient} value the value to bind
 */
BindFactory.bindResourcesSelect = function(elem, value,dv,tip) {
	if (typeof(value) != "object" || value.constructor != Array) {
		BindFactory.reportError(elem,value,"绑定到Select控件需要数组类型数据!");
	}
	// delete all the nodes.
	while (elem.childNodes.length > 0) {
		elem.removeChild(elem.childNodes[0]);
	}
	//设置提示选项,为空则无此选项
	if(!(tip==""||tip == null || typeof(tip) == "undefined")){
	var option = document.createElement("OPTION");
		option.value="";
		option.text =tip;
	elem.options.add(option);
}
	// bind data
	for (var i = 0; i < value.length; i++) {
		
		var option = document.createElement("OPTION");
		
		var data = value[i];

		if (data == null || typeof(data) == "undefined") {
			option.value = "";
			option.text = "";
		}
		if (typeof(data) == 'object') {
			option.value = data.value;
			option.text = data.name;

			//if(data.value == dv){
				//alert("1");
				//option.selected =true;
			//	}
		} 
		elem.options.add(option);
	}
	//设置默认属性
   for(i=0;i<elem.length;i++){
   if(elem[i].value==dv){
      elem[i].selected = true;
   }
  }

}

/**
 * Bind value to city select/list element.
 *
 * @param {HTMLDOMElement} elem the html element
 * @param {varient} value the value to bind
 */
BindFactory.bindCitySelect = function(elem, value,dv,tip) {
	if (typeof(value) != "object" || value.constructor != Array) {
		BindFactory.reportError(elem,value,"绑定到Select控件需要数组类型数据!");
	}
	// delete all the nodes.
	while (elem.childNodes.length > 0) {
		elem.removeChild(elem.childNodes[0]);
	}
	//设置提示选项,为空则无此选项
	if(!(tip==""||tip == null || typeof(tip) == "undefined")){
	    if(value.length!=1){
        	var option = document.createElement("OPTION");
    		option.value="";
    		option.text =tip;
        	elem.options.add(option);
    	}
    }
	// bind data
	for (var i = 0; i < value.length; i++) {
		
		var option = document.createElement("OPTION");
		
		var data = value[i];

		if (data == null || typeof(data) == "undefined") {
			option.value = "";
			option.text = "";
		}
		if (typeof(data) == 'object') {
			option.value = data.value;
			option.text = data.name;

			//if(data.value == dv){
				//alert("1");
				//option.selected =true;
			//	}
		} 
		elem.options.add(option);
	}
	//设置默认属性
   for(i=0;i<elem.length;i++){
   if(elem[i].value==dv){
      elem[i].selected = true;
   }
  }

}

	function setDefaultValue(elem,dv){
	//设置默认属性
   for(i=0;i<elem.length;i++){
   if(elem[i].value==dv){
      elem[i].selected = true;
   }
  }
}
/**
 * Bind value to a html table.
 * @param {HTMLDOMElement} elem the html element
 * @param {varient} value the value to bind
 */
BindFactory.bindTable = function(elem, value) {
	var jHeight = parseInt(elem.getAttribute("jheight"));
	var dataHeader = [];
	var tBody = elem.getElementsByTagName("TBODY")[0];
	
	// clear the generated rows
	if (elem.getElementsByTagName("TBODY").length > 0) {
		while (tBody.rows.length > jHeight) {
				tBody.deleteRow(jHeight);
		}
	}

	if (jHeight == 0) { // if table is null, push the data to the tables.

		for (x in value[0] ) {
			dataHeader[dataHeader.length] = x;
		}

		var hTr = elem.insertRow(elem.rows.length);
		for (var i = 0; i < dataHeader.length; i++) {
			var td = hTr.insertCell(hTr.cells.length);
			td.innerHTML = dataHeader[i];
		}
		
		for (var i = 0; i < value.length; i++) {
			var tr = elem.insertRow(elem.rows.length);
			var data = value[i];
			for (x in data ) {
				var td = tr.insertCell(tr.cells.length);
				td.innerHTML = data[x];
			}
		}	
	}
	
	if (jHeight == 1) { // 只有一行，那么第一行为header(其中每一个td指定jtext属性)
		var headerTR = tBody.rows[0];

		for (var i = 0; i < headerTR.cells.length ; i++ ) {
			dataHeader[dataHeader.length] = headerTR.cells[i].getAttribute("jtext");
		}
		
		for (var i = 0; i < value.length; i++) {
			var tr = tBody.insertRow(tBody.rows.length);
			var data = value[i];
			for (var j = 0; j < dataHeader.length; j++ ) {
				var td = tr.insertCell(tr.cells.length);
				td.innerHTML = data[dataHeader[j]];
			}
		}	
	}

	if (jHeight == 2) { // 两行，第一行为header, 第二行为后面循环的样式

		var headerTR = tBody.rows[0];

		for (var i = 0; i < headerTR.cells.length ; i++ ) {
			dataHeader[dataHeader.length] = headerTR.cells[i].getAttribute("jtext");
		}

		for (var i = 0; i < value.length; i++) {
			
			var tr;
			
			if (i == 0) { // 如果是第一行，那么直接使用
				tr = elem.rows[1];
			} else { // 否则，复制第一行
				tr = elem.rows[1].cloneNode(true);
			}

			if (i > 0) 	{
				tBody.appendChild(tr);
			}

			var data = value[i];
			for (var j = 0; j < tr.cells.length; j++ ) {
				var td = tr.cells[j];
				
				td.innerHTML = data[dataHeader[j]];
			}
			
		}	
	}

	if (jHeight >= 3) { // 三行及以上，第一行为header, 二、三行为交换样式，后面的行将会被忽略。
		var headerTR = tBody.rows[0];
		for (var i = 0; i < headerTR.cells.length ; i++ ) {
			dataHeader[dataHeader.length] = headerTR.cells[i].getAttribute("jtext");
		}
		for (var i = 0; i < value.length; i++) {
			var tr;
			
			if (i == 0) { // 如果是第一行，那么直接使用
				tr = tBody.rows[1];
			} else if (i == 1) 	{ // 第二行，也直接使用
				tr = tBody.rows[2];
			} else if ( i % 2 == 0) { // 取第一个行
				tr = tBody.rows[1].cloneNode(true);
			} else if (i % 2 == 1) { // 取第二个行
				tr = tBody.rows[2].cloneNode(true);
			}

			
			if (i > 1) 	{
				tBody.appendChild(tr);
			}

			var data = value[i];
			
			for (var j = 0; j < tr.cells.length; j++ ) {
				var td = tr.cells[j];	
				td.innerHTML = data[dataHeader[j]];
			}
		}	
	}
	
}

/**
 * Bind value to a repeater(under considering.)
 * @param {HTMLDOMElement} elem the html element
 * @param {varient} value the value to bind
 */
BindFactory.bindRepeater = function(elem, value) {
	//TODO: implementation will be added.
}
