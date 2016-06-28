/*
 *加载地图
 *container:容器id
 *width:
 *height:
 *src:Flash源文件
 *province:省份id 
 *注：provincen不为空或大于0时，则加载省份区域图，否则，加载全国区域图。
*/
function LoadMap(container, width, height, src, province) 
{
    var cont = document.getElementById(container);
    if (province <=0) 
    {
        //cont.innerHTML = "<embed id='ply' width='" + width + "' height='" + height + "' name='ply' style='z-index: 100;' src='" + src + "' type='application/x-shockwave-flash' wmode='transparent'/>";
        //防止Flash遮挡弹出的div  wmode='transparent'
        cont.innerHTML ="<object width='" + width + "' height='" + height + "' data='" + src + "' type='application/x-shockwave-flash'><param name='wmode' value='transparent' /><param name='align' value='middle' /><param name='src' value='" + src + "' /><param name='quality' value='high' /></object>"
    
    }
    else 
    {
        //cont.innerHTML = "<img src='Maps/P" + province + ".jpg' style=' vertical-align:middle;'/>"
        cont.innerHTML = "<div style='position:relative;'><iframe src='P" + province + ".html' scrolling='no' frameborder='0' height='379px' width='600px' ></iframe></div><div style='position: absolute; top: 15px; left:2px; margin-left: 12px; width: 100px;' ><p style='color:Red;'>点击下图返回</p><a href='index.htm' target='_top'><img src='http://img0.zz91.com/front/images/map/maps/ditu.jpg'  width='100' height='88'style='border:0px' /></a></div>"; //<div style='position:absolute; bottom:2px; margin-left:475px; width:125px;' ><a href='Default.aspx' target='_top'>返回全国</a> <a href='Default.aspx?province=" + province + "' target='_top'>返回本地区</a></div>
    }
}
