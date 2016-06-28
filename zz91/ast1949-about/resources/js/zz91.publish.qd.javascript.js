function  viewFlash(sid,sURL, sName, sFeatures){ 
	var FLASHCAB = "../../download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab"; 
	var FLASHCID = "CLSID:D27CDB6E-AE6D-11CF-96B8-444553540000"; 
	var FLASHVER = "8,0,0,0"; 
	var sFeature; 
	var sWidth  = "100%"; 
	var sHeight  = "100%"; 
	var pmBoolean = "false"; 
	var sTempArray; 
	var sParamTag = ""; 

	sFeature = sFeatures.split(/\s*,\s*/); 
	for (var i=0; i< sFeature.length ; i++) { 
		sTempArray = sFeature[i].split(/\s*=\s*/); 
		if (sTempArray[0].toLowerCase() == "width"){ 
			sWidth = sTempArray[1]; 
		} else if (sTempArray[0].toLowerCase() == "height"){ 
			sHeight = sTempArray[1]; 
		} else { 
			if (sTempArray[1].toLowerCase() == "yes" || sTempArray[1] == "1" || sTempArray[1].toLowerCase() == "true"){ 
				pmBoolean = "true"; 
			} else if (sTempArray[1].toLowerCase() == "no" || sTempArray[1] == "0" || sTempArray[1].toLowerCase() == "false"){ 
				pmBoolean = "false"; 
			} else { 
				pmBoolean = sTempArray[1]; 
			} 
			sParamTag = "<PARAM NAME='"+sTempArray[0]+"'VALUE='" + pmBoolean + "'>\n"+sParamTag; 
		} 
	} 
	document.write("<OBJECT ID='"+sName+"' NAME='"+sName+"' CLASSID='"+FLASHCID+"' CODEBASE='"+FLASHCAB+"#version="+FLASHVER+"' WIDTH='"+sWidth+"' HEIGHT='"+sHeight+"'>"); 
	document.write("<PARAM NAME='movie' VALUE='" + sURL + "?url=http://www.zz91.com/upimages/upload/openad.asp?id="+sid+"'>"); 
	document.write("<PARAM NAME='wmode' VALUE='transparent'>"); 
	document.write("<PARAM NAME='wmode' VALUE='wmode'>"); 
	document.write(sParamTag); 
	document.write("<EMBED SRC='"+sURL+"?url=http://www.zz91.com/upimages/upload/openad.asp?id="+sid+"' wmode='transparent' MENU='false' WIDTH='"+sWidth+"' HEIGHT='"+sHeight+"' ID='"+sName+"' NAME='"+sName+"' TYPE='application/x-shockwave-flash' PLUGINSPAGE='../../www.macromedia.com/go/getflashplayer' />") 
	document.write("</OBJECT>"); 
}
function  viewFlashurl(sLink,sURL, sName, sFeatures){ 
	var FLASHCAB = "../../download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab"; 
	var FLASHCID = "CLSID:D27CDB6E-AE6D-11CF-96B8-444553540000"; 
	var FLASHVER = "8,0,0,0"; 
	var sFeature; 
	var sWidth  = "100%"; 
	var sHeight  = "100%"; 
	var pmBoolean = "false"; 
	var sTempArray; 
	var sParamTag = ""; 

	sFeature = sFeatures.split(/\s*,\s*/); 
	for (var i=0; i< sFeature.length ; i++) { 
		sTempArray = sFeature[i].split(/\s*=\s*/); 
		if (sTempArray[0].toLowerCase() == "width"){ 
			sWidth = sTempArray[1]; 
		} else if (sTempArray[0].toLowerCase() == "height"){ 
			sHeight = sTempArray[1]; 
		} else { 
			if (sTempArray[1].toLowerCase() == "yes" || sTempArray[1] == "1" || sTempArray[1].toLowerCase() == "true"){ 
				pmBoolean = "true"; 
			} else if (sTempArray[1].toLowerCase() == "no" || sTempArray[1] == "0" || sTempArray[1].toLowerCase() == "false"){ 
				pmBoolean = "false"; 
			} else { 
				pmBoolean = sTempArray[1]; 
			} 
			sParamTag = "<PARAM NAME='"+sTempArray[0]+"'VALUE='" + pmBoolean + "'>\n"+sParamTag; 
		} 
	} 
	document.write("<OBJECT ID='"+sName+"' NAME='"+sName+"' CLASSID='"+FLASHCID+"' CODEBASE='"+FLASHCAB+"#version="+FLASHVER+"' WIDTH='"+sWidth+"' HEIGHT='"+sHeight+"'>"); 
	document.write("<PARAM NAME='movie' VALUE='" + sURL + "?url=http://www.zz91.com/upimages/upload/openad.asp?id="+sid+"'>"); 
	document.write("<PARAM NAME='wmode' VALUE='transparent'>"); 
	document.write("<PARAM NAME='wmode' VALUE='wmode'>"); 
	document.write(sParamTag); 
	document.write("<EMBED SRC='"+sURL+"?url="+sLink+"' wmode='transparent' MENU='false' WIDTH='"+sWidth+"' HEIGHT='"+sHeight+"' ID='"+sName+"' NAME='"+sName+"' TYPE='application/x-shockwave-flash' PLUGINSPAGE='../../www.macromedia.com/go/getflashplayer' />") 
	document.write("</OBJECT>"); 
}

