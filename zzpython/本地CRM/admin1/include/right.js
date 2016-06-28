document.ns = navigator.appName == "Netscape"
window.screen.width>800 ? imgheight=345:imgheight=175
window.screen.width>800 ? imgright=6:imgright=6
function threenineload()
{
if (navigator.appName == "Netscape")
{document.threenine.pageY=pageYOffset+window.innerHeight-imgheight;
document.threenine.pageX=imgright;
threeninemove();
}
else
{
threenine.style.top=document.body.scrollTop+document.body.offsetHeight-imgheight;
threenine.style.right=imgright;
threeninemove();
}
}
function threeninemove()
{
if(document.ns)
{
document.threenine.top=pageYOffset+window.innerHeight-imgheight
document.threenine.right=imgright;
setTimeout("threeninemove();",80)
}
else
{
threenine.style.top=document.body.scrollTop+document.body.offsetHeight-imgheight;
threenine.style.right=imgright;
setTimeout("threeninemove();",80)
}
}

function MM_reloadPage(init) { //reloads the window if Nav4 resized
if (init==true) with (navigator) {if ((appName=="Netscape")&&(parseInt(appVersion)==4)) {
document.MM_pgW=innerWidth; document.MM_pgH=innerHeight; onresize=MM_reloadPage; }}
else if (innerWidth!=document.MM_pgW || innerHeight!=document.MM_pgH) location.reload();
}
MM_reloadPage(true)


if (navigator.appName == "Netscape")
{document.write("<layer id=threenine top=150 width=40 height=80><a href='##' target=_blank><img src=js/fload.gif width=150 height=300 border=0></a></layer>");
threenineload()}
else
{
document.write("<div id=threenine style='position: absolute;width:40;top:150;visibility: visible;z-index: 1'><img src=js/fload.gif  width=150 height=300 border=0 usemap='#Map33'>   <map name='Map33' id='Map33'>       <area shape='rect' coords='91,6,102,18' onClick=threenine.style.visibility='hidden' >  </map></div>");

threenineload()
}