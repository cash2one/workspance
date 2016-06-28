<%@ Page Language="C#" AutoEventWireup="true" CodeFile="CrmReceived.aspx.cs" Inherits="CRM_CrmReceived" %>

<%@ Register Assembly="AspNetPager" Namespace="Wuqi.Webdiyer" TagPrefix="webdiyer" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" >
<head runat="server">
    <title>提交给我的文档</title>
    <link href="Crm.css" rel="stylesheet" type="text/css" />
</head>
<body>
    <form id="form1" runat="server">
        <div class="right">
            <div id="GetCrm">
                <strong>提交给我的文档</strong>
                <div id="GetCrm_List">
                    <div id="Crm_Search">
                    <div id="Crm_Action" style="line-height:24px; margin-bottom:4px">
                        <asp:DropDownList ID="DDL_Crm_State" runat="server" AutoPostBack="True" OnSelectedIndexChanged="DDL_Crm_State_SelectedIndexChanged">
                            <asp:ListItem Value="0">未验收的任务</asp:ListItem>
                            <asp:ListItem Value="1">已验收的任务</asp:ListItem>
                        </asp:DropDownList>
                        <span id="State_Msg" runat="server"></span>
                    </div>
                    </div>
                    <asp:DataList ID="DL_GetCrm" runat="server">
                        <HeaderTemplate>
                            <div id="sMyCrmTitle" style="width:640px; border-bottom:1px solid #333; background-color:#eee; line-height:24px">
                                <strong style="width:140px; float:left; margin-left:20px ">
                                    标 题
                                </strong>
                                <strong style="width:80px; float:left; ">
                                    发件人
                                </strong>
                                <strong style="width: 100px; float:left; ">
                                    收件部门
                                </strong>
                                <strong style="width: 80px; float: left;">
                                    执行人
                                </strong>
                                <strong style="width:100px; float:left;">
                                    发送时间
                                </strong>
                                <strong style="width:60px; float: left;">
                                    反馈意见 
                                </strong>
                            </div>    
                        </HeaderTemplate>
                        <ItemTemplate>
                            <div id="gCrmList" style=" float:left; width:640px; border-bottom:1px solid #333; " class="crmlink">
                                <span style="width:150px; margin-left:10px; overflow: hidden; text-overflow:ellipsis; white-space:nowrap;">
                                    <a href='CrmReceived.aspx?MyCrmid=<%# Eval("Crm_id")%>'>
                                        <%# Eval("Crm_Title")%>
                                    </a>
                                </span>
                                <span style="width:80px;">
                                    <%# Eval("realname")%>
                                </span>
                                <span style="width:100px;">
                                    <%# Eval("meno")%>
                                </span>
                                <span style="width: 80px;">
                                    <%# Eval("Crm_Request")%>
                                </span>
                                <span style="width:100px;">
                                    <%# Eval("Crm_Date")%>
                                </span>
                                <span style="width:60px; text-align:center">
                                    <%# Eval("Crm_Reply")%>
                                </span>
                                <span style="width: 60px; text-align:center">
                                    <a href='CrmSending.aspx?backurl=CrmReceived.aspx&FbCrm_id=<%# Eval("Crm_id")%>'>
                                        回 复
                                    </a>
                                </span>
                            </div>
                        </ItemTemplate>
                    </asp:DataList>
                    <webdiyer:aspnetpager id="AspNetPager1" runat="server" firstpagetext="First" lastpagetext="Last"
                     nextpagetext="Next" pageindexboxtype="DropDownList" pagesize="5" prevpagetext="Prev"
                      showboxthreshold="10" showcustominfosection="Left" showpageindexbox="Auto" submitbuttontext="Go"
                       textafterpageindexbox="" textbeforepageindexbox="" UrlPaging="True">
                    </webdiyer:AspNetPager>
                    <div id="NoRows" runat="server" style="color:Red; font-weight:bold;">
                        没有提交给我的文档</div>
                </div>
                <div id="GetCrm_Infor" style="padding:5px; width:640px;" runat="server">
                <hr color="#666">
                    <div style="line-height:30px; background-color:#eee; border-bottom:solid 1px #333">
                        <strong id="sCrm_Title" runat="server" style="font-size:16pt;">Crm_Title</strong>
                    </div>
                    <div style="border-bottom:1px solid #333; padding:5px">
                        <span id="sCrm_Aothur" runat="server" style="float:left; width:360px;">From_Meno From_Staff Date</span>
                        <span id="sCrm_Annex" runat="server" class="annex">
                            <img width="14px" height="14px" border="0" src="images/math.gif" />附件：报价.doc
                        </span>
                    </div>
                    <div style="height:180px; width:620px; overflow:auto; padding:5px; border:1px solid #333">
                        <span id="sCrm_Content" runat="server">Crm_Content</span>
                    </div>
                <hr color="#666">
                </div>
                <div id="GetReply">
                    <a name="Re"></a>
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
                                <a href='CrmSending.aspx?backurl=CrmReceived.aspx&FbCrm_id=<%=ReCrm_id%>'>
                                    ［回 复］
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
