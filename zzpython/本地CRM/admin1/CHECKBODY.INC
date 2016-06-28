<%
   sub showBody(Str)
	dim dist
	dim i 
	for i = 1 to len(str)
	  if mid(str,i,1)<>"%" and ucase(mid(str,i,6))<>"SCRIPT" then
	    if mid(str,i,1)<>chr(13) then
               dist=dist+mid(str,i,1)
	    else
		response.write dist
     	        response.write "<BR>"+chr(13)+chr(10)
		dist=""
	    end if
          end if
        next 
	response.write dist
   end sub

   function checktxt(Str)
	dim dist
	dim i 
	dist=""
	for i = 1 to len(str)
	  if mid(str,i,1)<>"%" and mid(str,i,1)<>chr(13) and ucase(mid(str,i,6))<>"SCRIPT" then
	       dist=dist+mid(str,i,1)
	  end if
        next 
	checktxt=dist
   end function

%>