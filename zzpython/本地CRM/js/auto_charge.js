//<![CDATA[
// code by sam 2007-02-14
var nn;
nn=1;
var stoprun = 0 ;
setTimeout('change_div()',3000);
function change_div()
{
	//alert("sam:"+stoprun);
 if(stoprun == 0){
 if(nn>6) nn=1
 setTimeout('setFocus('+nn+',0)',3000);
 
}else{setTimeout('setFocus('+nn+',0)',600000);}
	nn++;
 tt=setTimeout('change_div()',3000);
}
function setFocus(i,m)
{
	var m;
	//alert(m);
	if(m !=0)	stoprun = 1;
	//alert(stoprun);
  selectLayer1(i);
 }
function selectLayer1(i)
{
 switch(i)
 {
 case 1:
document.getElementById("auto_menu_1").style.display="block";
document.getElementById("auto_menu_2").style.display="none";
document.getElementById("auto_menu_3").style.display="none";
document.getElementById("auto_menu_4").style.display="none";
document.getElementById("auto_menu_5").style.display="none";
document.getElementById("box_baojia1_bottom").style.display="block";
document.getElementById("box_baojia2_bottom").style.display="none";
document.getElementById("box_baojia3_bottom").style.display="none";
document.getElementById("box_baojia4_bottom").style.display="none";
document.getElementById("box_baojia5_bottom").style.display="none";
document.getElementById("auto_menu_1_content").style.display="block";
document.getElementById("auto_menu_2_content").style.display="none";
document.getElementById("auto_menu_3_content").style.display="none";
document.getElementById("auto_menu_4_content").style.display="none";
document.getElementById("auto_menu_5_content").style.display="none";
//document.getElementById("auto_menu_1").onclick = new Function('stopscroll = true');
break;
case 2:
document.getElementById("auto_menu_1").style.display="none";
document.getElementById("auto_menu_2").style.display="block";
document.getElementById("auto_menu_3").style.display="none";
document.getElementById("auto_menu_4").style.display="none";
document.getElementById("auto_menu_5").style.display="none";
document.getElementById("box_baojia1_bottom").style.display="none";
document.getElementById("box_baojia2_bottom").style.display="block";
document.getElementById("box_baojia3_bottom").style.display="none";
document.getElementById("box_baojia4_bottom").style.display="none";
document.getElementById("box_baojia5_bottom").style.display="none";
document.getElementById("auto_menu_1_content").style.display="none";
document.getElementById("auto_menu_2_content").style.display="block";
document.getElementById("auto_menu_3_content").style.display="none";
document.getElementById("auto_menu_4_content").style.display="none";
document.getElementById("auto_menu_5_content").style.display="none";
//document.getElementById("auto_menu_2").onclick = new Function('stopscroll = true');
 break;

 case 3:
document.getElementById("auto_menu_1").style.display="none";
document.getElementById("auto_menu_2").style.display="none";
document.getElementById("auto_menu_3").style.display="block";
document.getElementById("auto_menu_4").style.display="none";
document.getElementById("auto_menu_5").style.display="none";
document.getElementById("box_baojia1_bottom").style.display="none";
document.getElementById("box_baojia2_bottom").style.display="none";
document.getElementById("box_baojia3_bottom").style.display="block";
document.getElementById("box_baojia4_bottom").style.display="none";
document.getElementById("box_baojia5_bottom").style.display="none";
document.getElementById("auto_menu_1_content").style.display="none";
document.getElementById("auto_menu_2_content").style.display="none";
document.getElementById("auto_menu_3_content").style.display="block";
document.getElementById("auto_menu_4_content").style.display="none";
document.getElementById("auto_menu_5_content").style.display="none";
//document.getElementById("auto_menu_3").onclick = new Function('stopscroll = true');
break;
 case 4:
document.getElementById("auto_menu_1").style.display="none";
document.getElementById("auto_menu_2").style.display="none";
document.getElementById("auto_menu_3").style.display="none";
document.getElementById("auto_menu_4").style.display="block";
document.getElementById("auto_menu_5").style.display="none";
document.getElementById("box_baojia1_bottom").style.display="none";
document.getElementById("box_baojia2_bottom").style.display="none";
document.getElementById("box_baojia3_bottom").style.display="none";
document.getElementById("box_baojia4_bottom").style.display="block";
document.getElementById("box_baojia5_bottom").style.display="none";
document.getElementById("auto_menu_1_content").style.display="none";
document.getElementById("auto_menu_2_content").style.display="none";
document.getElementById("auto_menu_3_content").style.display="none";
document.getElementById("auto_menu_4_content").style.display="block";
document.getElementById("auto_menu_5_content").style.display="none";
//document.getElementById("auto_menu_4").onclick = new Function('stopscroll = true');
break;
 case 5:
document.getElementById("auto_menu_1").style.display="none";
document.getElementById("auto_menu_2").style.display="none";
document.getElementById("auto_menu_3").style.display="none";
document.getElementById("auto_menu_4").style.display="none";
document.getElementById("auto_menu_5").style.display="block";
document.getElementById("box_baojia1_bottom").style.display="none";
document.getElementById("box_baojia2_bottom").style.display="none";
document.getElementById("box_baojia3_bottom").style.display="none";
document.getElementById("box_baojia4_bottom").style.display="none";
document.getElementById("box_baojia5_bottom").style.display="block";
document.getElementById("auto_menu_1_content").style.display="none";
document.getElementById("auto_menu_2_content").style.display="none";
document.getElementById("auto_menu_3_content").style.display="none";
document.getElementById("auto_menu_4_content").style.display="none";
document.getElementById("auto_menu_5_content").style.display="block";
//document.getElementById("auto_menu_4").onclick = new Function('stopscroll = true');
break;
 }
}

//]]>
