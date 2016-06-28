<!-- #include file="../../include/ad!$#5V.asp" -->
<%
telstr="1,9262|"
telstr=telstr&"2,9270|"
telstr=telstr&"3,9267|"
telstr=telstr&"4,9261|"
telstr=telstr&"5,9264|"
telstr=telstr&"6,9231|"
telstr=telstr&"7,9152|"
telstr=telstr&"8,9187|"
telstr=telstr&"9,9122|"
telstr=telstr&"10,9271|"
telstr=telstr&"11,9293|"
telstr=telstr&"12,9275|"
telstr=telstr&"13,9189|"
telstr=telstr&"14,9186|"
telstr=telstr&"15,9291|"
telstr=telstr&"16,9195|"
telstr=telstr&"17,9200|"
telstr=telstr&"18,9177|"
telstr=telstr&"19,9307|"
telstr=telstr&"20,9183|"
telstr=telstr&"21,9276|"
telstr=telstr&"22,9273|"
telstr=telstr&"23,9295|"
telstr=telstr&"24,9272|"
telstr=telstr&"25,9250|"
telstr=telstr&"26,9210|"
telstr=telstr&"27,9274|"
telstr=telstr&"28,9279|"
telstr=telstr&"29,9222|"
telstr=telstr&"30,9260|"
telstr=telstr&"31,9263|"
telstr=telstr&"32,9235|"
telstr=telstr&"33,9284|"
telstr=telstr&"34,9265|"
telstr=telstr&"35,9257|"
telstr=telstr&"36,9252|"
telstr=telstr&"37,9359|"
telstr=telstr&"38,9129|"
telstr=telstr&"39,9283|"
telstr=telstr&"40,9305|"
telstr=telstr&"41,9256|"
telstr=telstr&"42,9212|"
telstr=telstr&"43,9121|"
telstr=telstr&"44,9190|"
telstr=telstr&"45,9278|"
telstr=telstr&"46,9306|"
telstr=telstr&"47,9312|"
telstr=telstr&"48,9313|"
telstr=telstr&"49,9311|"
telstr=telstr&"50,9110|"
telstr=telstr&"51,9255|"
telstr=telstr&"52,9238|"
telstr=telstr&"53,9254|"
telstr=telstr&"54,9205|"
telstr=telstr&"55,9269|"
telstr=telstr&"56,9203|"
telstr=telstr&"57,9213|"
telstr=telstr&"58,9237|"
telstr=telstr&"59,9315|"
telstr=telstr&"60,9309|"
telstr=telstr&"61,9211|"
telstr=telstr&"62,9202|"
telstr=telstr&"63,9236|"
telstr=telstr&"64,9201"
arrtel=split(telstr,"|")

sql="select usertel from users where userid like '13__' and closeflag=1 and chatclose=1"
set rs=conn.execute(sql)
if not rs.eof or not rs.bof then
	while not rs.eof
		utel=rs(0)
		telflag=0
		if utel<>"" then
		for i=0 to ubound(arrtel)
			lytel=split(arrtel(i),",")(1)
			if utel=lytel then
				telflag=1
			end if
		next
		if telflag=0 then
			response.Write(utel&"<br>")
		end if
		end if
	rs.movenext
	wend
end if
rs.close
set rs=nothing
conn.close
set conn=nothing
%>
