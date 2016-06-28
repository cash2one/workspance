<%@ Page Language="C#" AutoEventWireup="true" CodeFile="CrmManage.aspx.cs" Inherits="CRM_CrmManage" %>

<%@ Register Assembly="AspNetPager" Namespace="Wuqi.Webdiyer" TagPrefix="webdiyer" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" >
<head runat="server">
    <title>文档信息管理</title>
    <link href="Crm.css" rel="stylesheet" type="text/css" />
    <style type="text/css">
        #Crm_Infor th{ text-align:center; width:80px;}
        #Crm_Infor td{ text-align:left;}
    </style>
</head>
<body>
    <form id="form1" runat="server">
        <div class="right">
            <div id="MyCrm">
                <strong>文档信息管理</strong>
                <div id="Manage_Search">
                    <span style=" margin-left:10px;">
                    <asp:DropDownList ID="DDL_Meno" runat="server" AutoPostBack="True" OnSelectedIndexChanged="DDL_Meno_SelectedIndexChanged">
                    </asp:DropDownList>
                    <asp:DropDownList ID="DDL_UserId" runat="server">
                    </asp:DropDownList>
                    <asp:RadioButtonList ID="RBL_Staff" runat="server" RepeatDirection="Horizontal" RepeatLayout="Flow">
                        <asp:ListItem Selected="True" Value="0">发送的信息</asp:ListItem>
                        <asp:ListItem Value="1">接收的信息</asp:ListItem>
                    </asp:RadioButtonList>
                    <asp:Button ID="But_Search" runat="server" OnClick="But_Search_Click" Text="查 看"  Width="60px" />
                    <span class="crmlink"><a href="CrmManage.aspx">查看全部信息</a></span>
                    <span class="crmlink"><a href="CrmPosted.aspx">任务验收</a></span>
                    </span>
                    <div id="NoRows" runat="server" style="font-weight:bold; color:Red; padding-left:20px ">没有您要的信息</div>
                </div>
                <div id="Manage_CrmList">
                    <asp:DataList ID="DL_ManageCrm" runat="server">
                        <HeaderTemplate>
                            <div id="mCrmHead" style="width:640px; border-bottom:1px solid #333; background-color:#eee; line-height:24px; text-align:center;">
                                <strong style="width:140px; float:left; margin-left:20px ">
                                    标 题
                                </strong>
                                <strong style="width:120px; float:left; ">
                                    发件人
                                </strong>
                                <strong style="width: 80px; float:left; ">
                                    收件部门
                                </strong>
                                <strong style="width: 80px; float: left;">
                                    任务分配
                                </strong>
                                <strong style="width:120px; float:left; ">
                                    提交时间
                                </strong>
                            </div>
                        </HeaderTemplate>
                        <ItemTemplate>
                            <div id="mCrmBody" style=" width:640px; border-bottom:1px solid #333; text-align:center;" class="crmlink">
                                <span style="width:150px; margin-left:10px; overflow: hidden; text-overflow:ellipsis; white-space:nowrap;">
                                    <a href='<%#postUrl%><%# Eval("Crm_id")%>' title='<%# Eval("Crm_Title")%>'>
                                            <%# Eval("Crm_Title")%>
                                    </a>
                                </span>
                                <span style="width:120px;">
                                    <%# Eval("F_Staff")%>[<%# Eval("F_Meno")%>]
                                </span>
                                <span style="width: 80px;">
                                    <%# Eval("T_Meno")%>
                                </span>
                                <span style="width: 80px;">
                                    <%# Eval("Crm_Request")%>
                                </span>
                                <span style="width:120px;">
                                    <%# Eval("Crm_Date")%>
                                </span>
                                <span style="width:60px;">
                                    <a href='CrmManage.aspx?MyCrmid=<%# Eval("Crm_id")%>#Re'>
                                        回复和反馈
                                    </a>
                                </span>
                            </div>
                        </ItemTemplate>
                    </asp:DataList>
                    <asp:HiddenField ID="Hid_strWhere" runat="server" Value="Crm_state=0" />
                    <asp:HiddenField ID="Hid_CrmId" runat="server" />
                    <webdiyer:aspnetpager id="AspNetPager1" runat="server" firstpagetext="First"
                        lastpagetext="Last" nextpagetext="Next" pageindexboxtype="DropDownList" pagesize="5"
                        prevpagetext="Prev" showboxthreshold="10" showcustominfosection="Left" showpageindexbox="Auto"
                        submitbuttontext="Go" urlpaging="True">
                    </webdiyer:aspnetpager>
                </div>  
                <div id="Crm_Infor" style="padding:5px; width :640px;" runat="server">
                <hr color="#666">
                    <div style="border-bottom:1px solid #333; background-color:#eee; line-height:30px;">
                        <strong id="mCrm_Title" runat="server" style="font-size:16pt; ">Crm_Title</strong>
                        <strong id="mCrm_state" runat="server" style="color:red; font-size:12px; float:right;">未分配</strong>
                    </div>
                    <div style="padding-left:5px;">
                        <div>
                            <span id="mCrm_AothurF" runat="server" style=" width:300px">
                                "陈聪" <font color="#3f62ba">chenc501@163.com</font>
                            </span>                    
                        </div>
                        <div>
                            <span　id="mCrm_Request" runat="server">
                                <span id="mCrm_ToMeno" runat="server"></span><asp:DropDownList ID="DDL_isRequest" runat="server"></asp:DropDownList><asp:Button ID="But_isRequest" runat="server" Text="分配任务" OnClick="But_isRequest_Click" Width="60px" />
                            </span>
                            <span id="mCrm_AothurT" runat="server">
                                "陈聪" <font color="#3f62ba">chenc501@163.com</font>
                            </span>                    
                        </div>
                        <div>
                            <span id="mCrm_date" runat="server" style="float:left; width:200px">
                            </span>
                            <span id="mCrm_Annex" runat="server" class="annex">
                                <img width="14px" height="14px" border="0" src="images/math.gif" />
                                附件：报价.doc
                            </span>                    
                        </div>
                    </div>
                    <div style="height:200px; overflow:auto; padding:5px; border:1px solid #333">
                        <span id="mCrm_Content" runat="server">Crm_Content</span>
                    </div>
                <hr color="#666">
                </div>
                <a name="Re"></a>
                <div id="GetReply" runat="server">
                    <asp:DataList ID="DL_GetReply" runat="server" Width="620px">
                    <HeaderTemplate>
                        <div style="background-color:#eee; line-height:24px; border-bottom:solid 1px #333">
                            &nbsp;&nbsp;回复及反馈>>
                            <strong id="sReply_Crm_Title" runat="server"><%=ReCrm_Title%></strong>
                        </div>
                    </HeaderTemplate>
                    <ItemTemplate>
                        <div id="sReply_Main" style="border-top:solid 1px #000; color:<%#Eval("ReplyColor") %>">
                            <div id="sReply_Top">
                                <strong style="float:right; margin-right:20px;"><a href="#Re">Top</a></strong>
                                <span><%#Eval("RealName") %>[<%#Eval("Meno")%>]&nbsp;&nbsp;<%#Eval("Reply_Date")%></span>   
                            </div>
                            <div style="height:70px; width:620px; padding:5px; overflow:auto; border:1px solid #333">
                                <%#Eval("Reply_Contents")%></div>
                        </div>
                    </ItemTemplate>
                    <FooterTemplate>
                        <div id="sReply_Foot" style="width:200px; text-align:center">
                            <span>
                                <a href='CrmSending.aspx?backurl=CrmManage.aspx&FbCrm_id=<%=ReCrm_id%>'>
                                    ［我的意见］
                                </a>
                            </span>
                         </div>
                    </FooterTemplate>
                    </asp:DataList>
                </div>
            </div>
        </div>
    </form>
</body>
</html>
