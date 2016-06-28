<%@ Control Language="C#" ClassName="NavHeader" %>
<%@ Import Namespace="BlogEngine.Core" %>

<li><a href="<%=Utils.RelativeWebRoot %>"><img src="<%=Utils.RelativeWebRoot %>themes/Qeeke/images/menu1.gif" alt=""/><br/>
网站首页</a></li>

<li><a href="<%=Utils.AbsoluteWebRoot %>tag.aspx"><img src="<%=Utils.RelativeWebRoot %>themes/Qeeke/images/menu3.gif" alt=""/><br/>所有标签</a></li>

<li><a href="<%=Utils.AbsoluteWebRoot %>archive.aspx" title="sitemaps"><img src="<%=Utils.RelativeWebRoot %>themes/Qeeke/images/menu4.gif" alt=""/><br/>文章归档</a></li>

<li ><a href="<%=Utils.AbsoluteWebRoot %>search.aspx"><img src="<%=Utils.RelativeWebRoot %>themes/Qeeke/images/menu2.gif" alt=""/><br/>内容搜索</a></li>

<li><a href="<%=Utils.AbsoluteWebRoot %>contact.aspx"><img src="<%=Utils.RelativeWebRoot %>themes/Qeeke/images/menu5.gif" alt=""/><br/>联系我们</a></li>

<li><a href="<%=Utils.FeedUrl %>"><img src="<%=Utils.RelativeWebRoot %>themes/Qeeke/images/menu6.gif" alt=""/><br/>内容订阅</a></li>
