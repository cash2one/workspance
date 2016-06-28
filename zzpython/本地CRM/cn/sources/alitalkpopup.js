function setCookieAlitalkpopup()
{
    if ( getCookieAlitalkpopup("alitalkpromote") == null )
    {
	var expires = new Date();
	expires.setTime(expires.getTime() + 4*60*60*1000);
	var the_cookie = "alitalkpromote=viewed;path=/;domain=.alibaba.com;expires=" + expires.toGMTString();
    	document.cookie = the_cookie;
    }
}

function getCookieAlitalkpopup(cookieName) {
  var cookieString = document.cookie;
  var start = cookieString.indexOf(cookieName + '=');
  if (start == -1)
    return null;
  start += cookieName.length + 1;
  var end = cookieString.indexOf(';', start);
  if (end == -1) return unescape(cookieString.substring(start));
  return unescape(cookieString.substring(start, end));
}


function newCheckAlitalkInstalled()
{
		var obj;
		try{
		   obj = new ActiveXObject("AlitalkSetup.Install");
		}
		catch(e){
		}
		if (null!=obj){
		  return true;
		} else {
		   return false;
		}
}

function checkAlitalkpopup()
{
   
			if (newCheckAlitalkInstalled())
				{ 
	  		  setCookieAlitalkpopup() ;
	  		  return false;
	     	 }
	  	if ( getCookieAlitalkpopup("alitalkpromote") != null )
	    	{
	    		return false;
	       }  	
   return true;	
}



