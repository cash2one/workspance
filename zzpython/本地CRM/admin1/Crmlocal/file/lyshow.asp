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
<title>¼��</title>
</head>

<body>
<object id="player" width="356" height="285" classid="CLSID:6BF52A52-394A-11d3-B153-00C04F79FAA6">
      <param name="AutoStart" value="-1" />
      <!--�Ƿ��Զ�����-->
      <param name="Balance" value="0" />
      <!--������������ƽ��,ͬ����ɲ���������-->
      <param name="enabled" value="-1" />
      <!--�������Ƿ����Ϊ����-->
      <param name="EnableContextMenu" value="-1" />
      <!--�Ƿ����������Ĳ˵�-->
      <param name="url" value="http://192.168.2.14/<%=lyUrl%>" />
      <!--���ŵ��ļ���ַ-->
      <param name="PlayCount" value="1" />
      <!--���Ŵ�������,Ϊ����-->
      <param name="rate" value="1" />
      <!--�������ʿ���,1Ϊ����,����С��,1.0-2.0-->
      <param name="currentPosition" value="1" />
      <!--�ؼ�����:��ǰλ��-->
      <param name="currentMarker" value="1" />
      <!--�ؼ�����:��ǰ���-->
      <param name="defaultFrame" value="1" />
      <!--��ʾĬ�Ͽ��-->
      <param name="invokeURLs" value="0" />
      <!--�ű���������:�Ƿ����URL-->
      <param name="baseURL" value="" />
      <!--�ű���������:�����õ�URL-->
      <param name="stretchToFit" value="1" />
      <!--�Ƿ񰴱�����չ-->
      <param name="volume" value="80" />
      <!--Ĭ��������С0%-100%,50��Ϊ50%-->
      <param name="mute" value="0" />
      <!--�Ƿ���-->
      <param name="uiMode" value="full" />
      <!--��������ʾģʽ:Full��ʾȫ��;mini���;None����ʾ���ſ���,ֻ��ʾ��Ƶ����;invisibleȫ������ʾ-->
      <param name="windowlessVideo" value="0" />
      <!--�����0��������ȫ��,����ֻ���ڴ����в鿴-->
      <param name="fullScreen" value="0" />
      <!--��ʼ�����Ƿ��Զ�ȫ��-->
      <param name="enableErrorDialogs" value="-1" />
      <!--�Ƿ����ô�����ʾ����-->
    </object>
</body>
</html>
