<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<%
lyUrl=request.QueryString("lyUrl")
lyUrl=replace(lyUrl,"|","\")
lyUrl=replace(lyUrl,"!","-")
lyUrl=replace(lyUrl,"E:\","")
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>录音</title>
</head>

<body>
<object id="player" width="356" height="285" classid="CLSID:6BF52A52-394A-11d3-B153-00C04F79FAA6">
      <param name="AutoStart" value="-1" />
      <!--是否自动播放-->
      <param name="Balance" value="0" />
      <!--调整左右声道平衡,同上面旧播放器代码-->
      <param name="enabled" value="-1" />
      <!--播放器是否可人为控制-->
      <param name="EnableContextMenu" value="-1" />
      <!--是否启用上下文菜单-->
      <param name="url" value="http://192.168.2.14/<%=lyUrl%>" />
      <!--播放的文件地址-->
      <param name="PlayCount" value="1" />
      <!--播放次数控制,为整数-->
      <param name="rate" value="1" />
      <!--播放速率控制,1为正常,允许小数,1.0-2.0-->
      <param name="currentPosition" value="1" />
      <!--控件设置:当前位置-->
      <param name="currentMarker" value="1" />
      <!--控件设置:当前标记-->
      <param name="defaultFrame" value="1" />
      <!--显示默认框架-->
      <param name="invokeURLs" value="0" />
      <!--脚本命令设置:是否调用URL-->
      <param name="baseURL" value="" />
      <!--脚本命令设置:被调用的URL-->
      <param name="stretchToFit" value="1" />
      <!--是否按比例伸展-->
      <param name="volume" value="80" />
      <!--默认声音大小0%-100%,50则为50%-->
      <param name="mute" value="0" />
      <!--是否静音-->
      <param name="uiMode" value="full" />
      <!--播放器显示模式:Full显示全部;mini最简化;None不显示播放控制,只显示视频窗口;invisible全部不显示-->
      <param name="windowlessVideo" value="0" />
      <!--如果是0可以允许全屏,否则只能在窗口中查看-->
      <param name="fullScreen" value="0" />
      <!--开始播放是否自动全屏-->
      <param name="enableErrorDialogs" value="-1" />
      <!--是否启用错误提示报告-->
    </object>
</body>
</html>
