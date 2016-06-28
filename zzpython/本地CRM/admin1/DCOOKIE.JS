	var expireDate;
	
	function getCookie(name){
		var cname = name + "=";               
		var dc = document.cookie;
		if (dc.length > 0) {              
			begin = dc.indexOf(cname);
			if (begin != -1) {           
				begin += cname.length;
				end = dc.indexOf(";", begin);
				if (end == -1) end = dc.length;
				return unescape(dc.substring(begin, end));        }
		}
		return "";
	}

	function saveStorageCookie(NameStorage){
		expireDate = new Date;
		if (expireDate.getYear() < 100) {
			expireDate.setYear(expireDate.getYear() + 1900 + 1);
		} else {
			expireDate.setYear(expireDate.getYear() + 1);
		} 
		document.cookie = "NameStorage=" + NameStorage + ";expires=" + expireDate.toGMTString() + ";";
	}
	
	function getAnnounceCookie(){
		var dc = document.cookie;
		var name = getCookie("name");
		var pwd = getCookie("passwd");		var email=getCookie("email")		var signature=getCookie("signature")
		if (document.cookie) {
			if (name == null) {  
				//document.frmAnnounce.name.focus();
			} else {
				document.frmAnnounce.name.value = getCookie("name");
				document.frmAnnounce.passwd.value = getCookie("passwd")+"";				document.frmAnnounce.email.value = getCookie("email");								if (signature == "use") {
					document.frmAnnounce.chkSignature.checked = true;
				}
				//document.frmAnnounce.subject.focus();
			}
		}  else {
			//document.frmAnnounce.name.focus();
		}		}
