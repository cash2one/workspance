function wuru(txt)
{
	//alert(Math.round(n*1000)/1000)
	var aeeid=Math.round(txt*1000)/1000
	var kkid=new Array()
	//alert(aeeid)	
		kkid=aeeid.toString().split(".")
	if (kkid.length>1)
	{   
	    var end1=kkid[1].substr(0,1)
	    var end2=kkid[1].substr(1,1)
		var end3=kkid[1].substr(2,1)
		if (end1=="")
		{
		 end1="0"
		}
		if (end2=="")
		{
		 end2="0"
		}
		if (end3=="")
		{
		 end3="0"
		}
		aeeid=kkid[0]+"."+end1.toString()+end2.toString()+end3.toString()
     }else
	 {
	 aeeid=kkid[0]+"."+"000"
	 }
	return aeeid;
}
function IsDigit(id)
{
    
	if (document.all(id).value.substr(0,1)=="-")
	{
		if (document.all(id).value.substr(1,1)==".")
		{
		alert("请输入数字")
		document.all(id).value="0.000"
		return false
		}
		num=document.all(id).value.substr(1)
		if (isNaN(num))
		{
		alert("请输入数字")
		//aa=document.all(id).value
		//n=aa.length-1
			//if (n==0)
			//{
			document.all(id).value="0.000"
			//}else
			//{
			//document.all(id).value="0.00"
			//}
		return false
		}
	}
	else
	{
	    num=document.all(id).value
		if (isNaN(num))
		{
		alert("请输入数字")
		aa=document.all(id).value
		n=aa.length-1
			if (n==0)
			{
			document.all(id).value="0.000"
			}else
			{
			document.all(id).value="0.000"
			}
		return false
		}
	}
	
}
function shuzi(put)
{
//put.style="text-align: right;"
//put.style.border=""
put.focus()
}
function ge(ge)
{
document.all(ge).select()
}