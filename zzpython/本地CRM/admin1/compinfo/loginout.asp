<%		Option Explicit
        session("userID")=""
		session("personid")=""
		session("admin_user")=""
		session("userClass")=""
		session("admin_user")=""
		session("littleuserID")=""
		session("lmcode")=""
		session("Partadmin")=""
		Response.Cookies("personid")=""
		Response.Cookies("userID")=""
		Response.Cookies("admin_user")=""
		Response.Cookies("littleuserID")=""
		Response.Cookies("userClass")=""
		Response.Cookies("Partadmin")=""
		response.Write("<script>parent.window.location='login.asp';</script>")
		response.End()
%>
