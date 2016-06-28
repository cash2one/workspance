
<%@ Page Language="C#" AutoEventWireup="true" CodeFile="Default.aspx.cs" Inherits="Rcu_En_News.Web.Default" %>

<!--<%@ Register Src="Controls/News_List.ascx" TagName="News_List" TagPrefix="uc1" %>-->

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" >
<head id="Head1" runat="server">
<title>新闻页</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="css/default.css" type="text/css" />
<link rel="stylesheet" href="css/index_top2008.css" type="text/css" />
<link rel="stylesheet" href="css/ZZ91_news.css" type="text/css" />
<link rel="stylesheet" href="css/ZZ91_foot.css" type="text/css" />
</head>
<body>
    <form id="form1" runat="server">
        <div class="top">
            <div class="top_l">
                <a href="#">Make BusyTrade Your Homepage</a> | <a href="#">Add to Favorites</a>
            </div>
            <div class="top_r">
                <a href="#">Login</a> | <a href="#">Join</a> | <a href="#">Join Free</a> | <span
                    class="cart"><span class="basket"><a href="#">My Basket</a></span></span> |
                <a href="#">Help</a> | <a href="#">Services</a> | <a href="#">Map</a>
            </div>
        </div>
        <div class="top2">
            <div class="logo">
            </div>
            <div class="daohang">
                <span class="dh"><a href="#">For Buyers</a></span> <span class="dh"><a href="#">For
                    Sellers</a></span> <span class="dh"><a href="#">Community</a></span> <span class="dh">
                        <a href="#">My RC</a></span> <span class="dh"><a href="#">中国站</a></span>
            </div>
            <div class="menu">
                <ul>
                    <li><a href="#">HOME</a> </li>
                    <li><a href="#">Metal Price</a> </li>
                    <li><a href="#">Scrap News</a> </li>
                    <li><a href="#">Events</a> </li>
                    <li><a href="#">Discussion</a> </li>
                </ul>
                <span class="time">July 2, 2008 Tuesday</span>
            </div>
            <div class="menu_bottom">
                <div class="search">
                    <form action="#" method="post">
                        <img src="images/fdj.gif" />
                        <input class="srk" name="search" type="text" />
                        <input class="buttom" type="submit" value="Search" />
                    </form>
                </div>
                <div class="bk_news">
                    <ul>
                        <li class="t">Breaking News: </li>
                        <li><a href="#">example1</a> </li>
                        <li><a href="#">example2</a> </li>
                        <li><a href="#">example3</a> </li>
                        <li><a href="#">example4</a> </li>
                        <li><a href="#">example4</a> </li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="contain">
            <div class="left">
                <h2>
                    Scrap News</h2>
                <div class="news1">
                    <p class="newspic">
                        <img src="images/news1.gif" /></p>
                    <p class="newstitle">
                        <a href="#">Online Bookstore Force Bertelsmann To Shut</a><span class="date">July 9,2008</span></p>
                    <p class="newscon" id="P1" runat="server">
                        (新闻样文)Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy
                        nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim
                        ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut
                        aliquip ex ea commodo consequat. Duis autem vel eum iriure dolor in hendrerit in
                        vulputate velit esse molestie consequat, vel illum dolore eu.Duis autem vel eum
                        iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum
                        dolore eu.
                    </p>
                </div>
            <span>
              <!--  <uc1:News_List ID="News_List1" runat="server" />-->
            </span>
            </div>
            <div class="right">
                <h2>
                    Popular Articles</h2>
                <div class="popular">
                    <ul>
                        <li>1.<a href="#">WuJiang Hongchang ChenLiuzhou Yongli Mechince HangZhou uiten</a> </li>
                        <li>2.<a href="#">DaeGoe Industrial,Yuyao double urplus Yixing city</a> </li>
                        <li>3.<a href="#">DaeGoe Industrial,Yuyao double urplus Yixing city</a> </li>
                    </ul>
                </div>
                <div class="gg">
                    <img class="size" src="images/gg1.gif" />
                    <img class="size" src="images/gg2.gif" />
                </div>
                <h2>
                    Latest Buying Leads</h2>
                <div class="latest_sell">
                    <ul>
                        <li class="white"><a href="#">Sliding-Window-Roll..</a> </li>
                        <li class="grey"><a href="#">14.4M MT Steel Bar</a> </li>
                        <li class="white"><a href="#">Water Pumps and acc..</a> </li>
                        <li class="grey"><a href="#">Mazut 100,Gost 10585..</a> </li>
                    </ul>
                </div>
                <h3>
                    Latest Selling Leads</h3>
                <div class="latest_buy">
                    <ul>
                        <li class="white"><a href="#">Sliding-Window-Roll..</a> </li>
                        <li class="grey"><a href="#">14.4M MT Steel Bar</a> </li>
                        <li class="white"><a href="#">Water Pumps and acc..</a> </li>
                        <li class="grey"><a href="#">Mazut 100,Gost 10585..</a> </li>
                    </ul>
                </div>
                <div class="gg">
                    <img class="size" src="images/gg3.gif" />
                    <img class="size" src="images/gg4.gif" />
                </div>
            </div>
        </div>
        <div class="content_foot">
            <a href="#">About us</a> | <a href="#">Our services</a> | <a href="#">How to pay</a>
            | <a href="#">Contact us</a> | <a href="#">Chinese site</a>
            <p class="copyright">
                Copyright 2008 RecycleChina All Rights Reserved</p>
            <p class="copyright">
                <a href="#">浙ICP备 05004848</a></p>
        </div>
    </form>
</body>
</html>
