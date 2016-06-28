<%

function cate(tb,a,b)
set rs_cate=server.CreateObject("adodb.recordset")
sql_cate="select * from "&tb
rs_cate.open sql_cate,conn,1,2
place="<select name='"& a &"'>"

if not rs_cate.eof or not rs_cate.bof then

	do while not rs_cate.eof
	if trim(b)<>"" then
		if cstr(trim(b))=cstr(trim(rs_cate("code"))) then
		place=place&"<option value=" & trim(rs_cate("code")) & " selected>"& trim(rs_cate(2)) &"</option>"
		else
		place=place&"<option value=" & trim(rs_cate("code")) & ">" & trim(rs_cate(2)) & "</option>"
		end if
	else
	    place=place&"<option value=" & trim(rs_cate("code")) & ">" & trim(rs_cate(2)) & "</option>"
	end if  
	rs_cate.movenext
	loop
	rs_cate.close

end if
place=place&"</select>"
response.Write(place)
end function
'***************************
'***************************
function catesdo(tb,a,b,c,sizes)
set rs_cate=server.CreateObject("adodb.recordset")
sql_cate="select * from "&tb
rs_cate.open sql_cate,conn,1,2
if c<>"" then
place="<input name='"& a &"' type='text' size='"&sizes&"' id='"& a &"' value='"&b&"' onclick=""window.plus('div"&c&"')"" onclick=""window.plus('div"&c&"')"">"
place=place&"<table border=""0"" cellpadding=""0"" cellspacing=""0"" name='div"&c&"' id='div"&c&"' style='display:none; POSITION: absolute; overflow: visible; visibility: visible;'><tr><td><select name='"& c &"' onChange=dochan('"&c&"','"&a&"') onmouseout=""window.plus('div"&c&"')"">"
else
place="<select name='"& a &"'>"
end if
place=place&"<option value=>请选择--</option>"
if not rs_cate.eof or not rs_cate.bof then

	do while not rs_cate.eof
	if trim(b)<>"" then	
		if cstr(trim(b))=cstr(trim(rs_cate("code"))) then
		place=place&"<option value='" & trim(rs_cate("code")) & "' selected>"& trim(rs_cate(2)) &"</option>"
		else
		place=place&"<option value='" & trim(rs_cate("code")) & "'>" & trim(rs_cate(2)) & "</option>"
		end if
	else
	    place=place&"<option value='" & trim(rs_cate("code")) & "'>" & trim(rs_cate(2)) & "</option>"
	end if
	rs_cate.movenext
	loop
	rs_cate.close

end if
if c<>"" then
place=place&"</select></td></tr></table>"
else
place=place&"</select>"
end if
response.Write(place)
end function
'*****************************
function cates(tb,a,b)
set rs_cate=server.CreateObject("adodb.recordset")
sql_cate="select * from "&tb
rs_cate.open sql_cate,conn,1,2
place="<select name='"& a &"'>"
place=place&"<option value=>请选择--</option>"
if not rs_cate.eof or not rs_cate.bof then

	do while not rs_cate.eof
	if trim(b)<>"" then	
		if cstr(trim(b))=cstr(trim(rs_cate("code"))) then
		place=place&"<option value=" & trim(rs_cate("code")) & " selected>"& trim(rs_cate(2)) &"</option>"
		else
		place=place&"<option value=" & trim(rs_cate("code")) & ">" & trim(rs_cate(2)) & "</option>"
		end if
	else
	    place=place&"<option value=" & trim(rs_cate("code")) & ">" & trim(rs_cate(2)) & "</option>"
	end if
	rs_cate.movenext
	loop
	rs_cate.close

end if
place=place&"</select>"
response.Write(place)
end function
'''''''''''''''''''''''''''''''''''''''''''''''''
function cates_two(tb,a,b)
set rs_cate=server.CreateObject("adodb.recordset")

sql_cate="select * from "&tb&" where code like '__00'"
rs_cate.open sql_cate,conn,1,2
place="<select name='"& a &"'>"
place=place&"<option value=>请选择--</option>"
if not rs_cate.eof or not rs_cate.bof then

	do while not rs_cate.eof
	    place=place&"<option value=" & trim(rs_cate("code")) & ">====" & trim(rs_cate(2)) & "====</option>"
		sql_cate1="select * from "&tb&" where code like '"&left(rs_cate("code"),2)&"%' and code<>'"&rs_cate("code")&"'"
		set rs_cate1=server.CreateObject("adodb.recordset")
		rs_cate1.open sql_cate1,conn,1,2
			if not rs_cate1.eof or not rs_cate1.bof then
				do while not rs_cate1.eof
					if trim(b)<>"" then	
						if cstr(trim(b))=cstr(trim(rs_cate1("code"))) then
						place=place&"<option value=" & trim(rs_cate1("code")) & " selected>"& trim(rs_cate1(2)) &"</option>"
						else
						place=place&"<option value=" & trim(rs_cate1("code")) & ">" & trim(rs_cate1(2)) & "</option>"
						end if
					else
						place=place&"<option value=" & trim(rs_cate1("code")) & ">" & trim(rs_cate1(2)) & "</option>"
					end if				
	            rs_cate1.movenext
				loop
				rs_cate1.close
			end if
	rs_cate.movenext
	loop
	rs_cate.close
end if
place=place&"</select>"
response.Write(place)
end function
'''''''''''''''''''''''''''''''''''''''''''''''''
function selet(a,b,c)
set rs_selet=server.CreateObject("adodb.recordset")
sqlselet="select "&b&" from "&a&" where code='"&c&"'"
rs_selet.open sqlselet,conn,1,2
if not rs_selet.eof or not rs_selet.bof then
response.Write(rs_selet(b))
end if
rs_selet.close
end function
''''''''''''''''''''''''''''''''''''''''''
function shows(a,b)
set rs_selet=server.CreateObject("adodb.recordset")
sqlselet="select meno from "&a&" where code='"&b&"'"
rs_selet.open sqlselet,conn,1,2
if not rs_selet.eof or not rs_selet.bof then
response.Write(trim(rs_selet("meno")))
end if
rs_selet.close
end function
'''''''''''''''
function selet1(a,b,c,d)
set rs_selet=server.CreateObject("adodb.recordset")
sqlselet="select "&b&" from "&a&" where "&d&"='"&c&"'"
rs_selet.open sqlselet,conn,1,2
if not rs_selet.eof or not rs_selet.bof then
response.Write(rs_selet(b))
end if
rs_selet.close
end function
''''''''''''''''''''''''''''''''''''''''''
''''''''''''''''''''''''''''''''''''''''''
function selet2(a,b,c,d)
set rs_selet=server.CreateObject("adodb.recordset")
sqlselet="select "&b&" from "&a&" where "&d&"="&c
rs_selet.open sqlselet,conn,1,2
if not rs_selet.eof or not rs_selet.bof then
response.Write(rs_selet(b))
end if
rs_selet.close
end function
'''''''''''''''''''''''''''''''''''''''''
'************************************
function cate_radio(tb,a,b)
set rs_cate=server.CreateObject("adodb.recordset")
sql_cate="select * from "&tb
rs_cate.open sql_cate,conn,1,2
place=""
if not rs_cate.eof or not rs_cate.bof then

	do while not rs_cate.eof
	if b="" or isnull(b) then
	b="10"
	end if
	if cstr(trim(b))=cstr(trim(rs_cate("code"))) then
	place=place&"&nbsp;<input type='radio' name='"&a&"' value=" & trim(rs_cate("code")) & " checked='checked' />&nbsp;"& trim(rs_cate(2))
    else
    place=place&"&nbsp;<input type='radio' name='"&a&"' value=" & trim(rs_cate("code")) & " />&nbsp;"& trim(rs_cate(2))
    end if
	rs_cate.movenext
	loop
	rs_cate.close

end if

response.Write(place)
end function
'*************************************
function pagetop(page)
		  
dim total,rn
	rn=25  '设定每页显示数
	rs.pagesize=rn
	total=rs.recordcount
    totalpg=rs.pagecount	
    if totalpg-page<0 then
   	page=1                                                                                                         
    end if
dim flagaa
   flagaa=1
if not rs.eof  then
		rs.pagesize=rn
		rs.AbsolutePage =1
		if page<>"" then 
		rs.AbsolutePage =page
		end if
		RowCount=rn
		beginpage=rn*(page-1)
		if beginpage=0 then
		beginpage=1
		end if
		if total<rn then
		endpage=total
		else 
			if clng(page)>=totalpg then
			endpage=total
			else
			endpage=rn*(page)
			end if
		end if
end if
dim temp
temp="<font color='#666666'> 共" & totalpg & "页"& total &"条记录  本页显示第" & beginpage &"-"& endpage &"条 </font>"
response.write temp
end function
'********************************************************
function dselect(a,dsort)
response.write "<table width='100%' border='0' cellpadding='0' cellspacing='0'>"& chr(13)
response.write"        <tr>"& chr(13)
response.write"          <td valign='top'>"& chr(13)
            
response.write"           <table width='100%' border=0 align='center' cellpadding=0 cellspacing=0>"& chr(13)
response.write"                    <tr>"& chr(13)
'response.write"                      <form name='company_sec' method='get'  action='product_admin.asp'>"& chr(13)
                       
response.write"                        <input type='hidden' name='startRow' value='0'>"& chr(13)
 response.write "                      <input TYPE='hidden' NAME='maxPage' VALUE='10'>"& chr(13)

 response.write "                      <td align=center>"& chr(13)
 response.write"                        <table border=0 cellpadding=0 cellspacing=0 width='100%'>"& chr(13)
 response.write "                         <tr>"& chr(13)
 response.write "                            <td>"& chr(13)
 response.write "                              <input type='hidden' name='trade_code'>"& chr(13)
 response.write "                              <select name='secRootCatId' onchange='changeCategory(this)' id='secRootCatId'>"& chr(13)
 if a="cate_province" then
 response.write "                                <option selected value=''>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>"& chr(13)
 else
 response.write "                                <option selected value=''>选择主类</option>"& chr(13) 
 end if
 response.write "                              </select>"& chr(13)
 response.write "                              <select name='secSubCatId' id='secSubCatId'>"& chr(13)
 if a="cate_province" then
 response.write  "                               <option selected value=''>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>"& chr(13)
 else
 response.write  "                               <option selected value=''>选择细类</option>"& chr(13)
 end if
 response.write  "                             </select>"& chr(13)
 response.write  "                           </td>"& chr(13)
 response.write  "                            </tr>"& chr(13)
 response.write  "                          </table>"& chr(13)
  response.write  "                       </td>"& chr(13)
 ' response.write  "                     </form>"& chr(13)
  response.write  "                   </tr>"& chr(13)
  response.write  "                 </table>"& chr(13)
  response.write  "         </td>"& chr(13)
  response.write  "       </tr>"& chr(13)
  response.write  "             <tr>"& chr(13)
  response.write  "               <td>"& chr(13)
	
response.write "<script>" & chr(13)   
response.write "   var catArr = new Array();"  & chr(13)
response.write "var cur;"  & chr(13)
sql1="select * from "&a&" where code like '__00' "
		  set rs1=server.createobject("adodb.recordset")
                                 rs1.open sql1,conn,1,1
								 if not rs1.eof then
								 
								do while not rs1.eof

response.write " cur = new Category('" & trim(rs1("code")) & "','" & trim(rs1("meno")) & "');"  & chr(13)
response.write " catArr = catArr.concat(cur);"   & chr(13)
set rs2=server.createobject("adodb.recordset")

      sql2="select * from "&a&" where code like '"& left(rs1("code"),2) &"__' and code<>'"&rs1("code")&"'"
		  
                                 rs2.open sql2,conn,1,1
								 if not rs2.eof then
								 do while not rs2.eof 
response.write "cur.addBoard(new Board('"& trim(rs1("code")) &"','" & trim(rs2("code")) & "','" & trim(rs2("meno")) & "'));"  & chr(13)								 
								             
                     rs2.movenext
								loop
								end if
								           
                     rs1.movenext
								loop
								end if
								

        //init catform
 response.write "       var catForm = document.company_sec.secRootCatId;"  & chr(13)
  response.write "      var boardForm = document.company_sec.secSubCatId; "      & chr(13)   

  response.write "      for (var i=0;i<catArr.length;i++) {"  & chr(13)
response.write "                catForm.options[i+1]=new Option(catArr[i].title,catArr[i].id);"  & chr(13)
 response.write "       }"  & chr(13)
response.write "        changeCategory(catForm);"  & chr(13)
        //init             
		if dsort<>"" then
		  response.write "        selectBoard('"&left(dsort,2)&"00','"&trim(dsort)&"');"  & chr(13)
		  end if   
        response.write "          </script>"  & chr(13)
		
response.write  "                 </td>"& chr(13)
 response.write  "              </tr>"& chr(13)
 response.write  "            </table>"& chr(13)
end function
'****************************************************
function dselect1(a,dsort,formname)
response.write "<table width='100%' border='0' cellpadding='0' cellspacing='0'>"& chr(13)
response.write"        <tr>"& chr(13)
response.write"          <td valign='top'>"& chr(13)
            
response.write"           <table width='100%' border=0 align='center' cellpadding=0 cellspacing=0 >"& chr(13)
response.write"                    <tr>"& chr(13)
'response.write"                      <form name='company_sec' method='get'  action='product_admin.asp'>"& chr(13)
                       
response.write"                        <input type='hidden' name='startRow' value='0'>"& chr(13)
 response.write "                      <input TYPE='hidden' NAME='maxPage' VALUE='10'>"& chr(13)

 response.write "                      <td align=center>"& chr(13)
 response.write"                        <table border=0 cellpadding=0 cellspacing=0 width='100%'>"& chr(13)
 response.write "                         <tr>"& chr(13)
 response.write "                            <td>"& chr(13)
 response.write "                              <input type='hidden' name='trade_code'>"& chr(13)
 response.write "                              <select name='secRootCatId1' onchange='changeCategory1(this)' id='secRootCatId1'>"& chr(13)
 response.write "                                <option selected value=''>请选择省份</option>"& chr(13)
 response.write "                              </select>"& chr(13)
 response.write "                              <select name='secSubCatId1' id='secSubCatId1'>"& chr(13)
 response.write  "                               <option selected value=''>请选择城市</option>"& chr(13)
 response.write  "                             </select>"& chr(13)
 response.write  "                           </td>"& chr(13)
 response.write  "                            </tr>"& chr(13)
 response.write  "                          </table>"& chr(13)
  response.write  "                       </td>"& chr(13)
 ' response.write  "                     </form>"& chr(13)
  response.write  "                   </tr>"& chr(13)
  response.write  "                 </table>"& chr(13)
  response.write  "         </td>"& chr(13)
  response.write  "       </tr>"& chr(13)
  response.write  "             <tr>"& chr(13)
  response.write  "               <td>"& chr(13)
	
response.write "<script>" & chr(13)   
response.write "   var catArr1 = new Array();"  & chr(13)
response.write "var cur1;"  & chr(13)
sql1="select * from "&a&" where code like '__00' "
		  set rs1=server.createobject("adodb.recordset")
                                 rs1.open sql1,conn,1,1
								 if not rs1.eof then
								 
								do while not rs1.eof

response.write " cur1 = new Category('" & trim(rs1("code")) & "','" & trim(rs1("meno")) & "');"  & chr(13)
response.write " catArr1 = catArr1.concat(cur1);"   & chr(13)
set rs2=server.createobject("adodb.recordset")

      sql2="select * from "&a&" where code like '"& left(trim(rs1("code")),2) &"__' and code<>'"&rs1("code")&"' order by code desc"
		  
                                 rs2.open sql2,conn,1,1
								 if not rs2.eof then
								 do while not rs2.eof 
response.write "cur1.addBoard(new Board('"& trim(rs1("code")) &"','" & trim(rs2("code")) & "','" & trim(rs2("meno")) & "'));"  & chr(13)								 
								             
                     rs2.movenext
								loop
								end if
								           
                     rs1.movenext
								loop
								end if
								

        //init catform
 response.write "       var catForm1 = document."&formname&".secRootCatId1;"  & chr(13)
  response.write "      var boardForm1 = document."&formname&".secSubCatId1; "      & chr(13)   

  response.write "      for (var i=0;i<catArr1.length;i++) {"  & chr(13)
response.write "                catForm1.options[i+1]=new Option(catArr1[i].title,catArr1[i].id);"  & chr(13)
 response.write "       }"  & chr(13)
response.write "        changeCategory1(catForm1);"  & chr(13)
        //init  
		if dsort<>"" then
		  response.write "        selectBoard1('"&left(dsort,2)&"00','"&trim(dsort)&"');"  & chr(13)
		  end if            
        response.write "          </script>"  & chr(13)
		
response.write  "                 </td>"& chr(13)
 response.write  "              </tr>"& chr(13)
 response.write  "            </table>"& chr(13)
end function
function dw_pub(d,dwname,addr)
if addr<>"" then
parray=split(trim(addr),"/",-1,1)
  if parray(6)<>"" then
    sqlcomf="select dw_id from dw_public where dw_id="&num&" and dw_db='"&d&"'"
	set rscomf=server.createobject("adodb.recordset")
	rscomf.open sqlcomf,conn,1,2
	if rscomf.eof then
	  sql="insert into dw_public(dw_id,dw_db,dwname,paddr,fdate,userid) values("&num&",'"&d&"','"&dwname&"','"&addr&"',getdate(),"&session("uid")&")"
      conn.Execute(sql)
	rscomf.close()
	end if
  end if
end if
end function
%>
<script>
function plus(id){

if (document.all(id).style.display=="none")
	{
	document.all(id).style.display=""
	
	}
else
	{
	document.all(id).style.display="none"

	}
}
		function dochan(c,a)
		{
		document.all(a).value=document.all(c).value
		document.all("div"+c).style.display="none"
		}
        //category methods
        function Category(id,title) {
                this.id = id;
                this.title=title;
                this.boardlist=new Array();
                this.addBoard=addBoard;
                this.getOptions = getOptions;
        }        
        
        function addBoard(board) {
                this.boardlist = this.boardlist.concat(board);
        }
        
        function getOptions() {
                var tmp = new Array();
                for(var i=0; i < this.boardlist.length;i++) {
                        var b = this.boardlist[i];
                        tmp[i]= b.getOption();
                }
                return tmp;
        }
        
        //board methods
        function Board(catid,id,title,total) {                
                this.catid=catid;
                this.id=id;
                this.title=title;
                this.getOption=getOption;
        }
                
        function getOption() {
                return new Option(this.title,this.id);                
        }
                
        function changeCategory(list) {        
                if (list.selectedIndex<=0) {
                        catForm.options[0].selected=true;
                var len = boardForm.options.length;
                for (var i=len-1;i>0;i--){
                        boardForm.options[i]=null;
                }                                        
                }
        else {
                var boards = catArr[list.selectedIndex-1].getOptions();
                var len = boardForm.options.length;
                for (var i=len-1;i>0;i--){
                        boardForm.options[i]=null;
                }                
                for (var i=0;i<boards.length;i++) {
                        boardForm.options[i+1]=boards[i];
                }
                }
                boardForm.options[0].selected=true;
        }
                
        function selectBoard(catid,bid) {
                for(var i=0;i<catForm.length;i++) {
                        if (catForm.options[i].value==catid) {
                                //alert("get Category");
                                catForm.options[i].selected=true;
                                changeCategory(catForm);
                                break;                        
                        }                        
                }                                
                for(var i=0;i<boardForm.length;i++) {
                        if (boardForm.options[i].value==bid) {
                                //alert("get Board");
                                boardForm.options[i].selected=true;                                
                                break;
                        }                        
                }                
        }        
        function changeCategory1(list) {        
                if (list.selectedIndex<=0) {
                        catForm1.options[0].selected=true;
                var len1 = boardForm1.options.length;
                for (var i=len1-1;i>0;i--){
                        boardForm1.options[i]=null;
                }                                        
                }
        else {
                var boards1 = catArr1[list.selectedIndex-1].getOptions();
                var len1 = boardForm1.options.length;
                for (var i=len1-1;i>0;i--){
                        boardForm1.options[i]=null;
                }                
                for (var i=0;i<boards1.length;i++) {
                        boardForm1.options[i+1]=boards1[i];
                }
                }
                boardForm1.options[0].selected=true;
        }
		//////////////////////////
		function selectBoard1(catid,bid) {
                for(var i=0;i<catForm1.length;i++) {
                        if (catForm1.options[i].value==catid) {
                                //alert("get Category");
                                catForm1.options[i].selected=true;
                                changeCategory1(catForm1);
                                break;                        
                        }                        
                }                                
                for(var i=0;i<boardForm1.length;i++) {
				        
                        if (boardForm1.options[i].value==bid) {
                                //alert(boardForm1.options[i].value);
                                boardForm1.options[i].selected=true;   
								//changeCategory1(boardForm1);                             
                                //break;
                        }                        
                }                
        }   
        //static methods   
		        </script>


