<%		Option Explicit
        session("userID")=""
		session("personid")=""
		session("admin_user")=""
		session("userClass")=""
		session("admin_user")=""
		session("littleuserID")=""
		session("lmcode")=""
		Response.Cookies("personid")=""
		Response.Cookies("userID")=""
		Response.Cookies("admin_user")=""
		Response.Cookies("littleuserID")=""
		Response.Cookies("userClass")=""
		response.Write("<script>parent.window.location='login.asp';</script>")
		response.End()
%>