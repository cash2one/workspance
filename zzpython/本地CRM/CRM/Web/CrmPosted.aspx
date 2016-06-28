<%@ Page Language="C#" AutoEventWireup="true" CodeFile="CrmPosted.aspx.cs" Inherits="CRM_CrmPosted" %>

<%@ Register Assembly="AspNetPager" Namespace="Wuqi.Webdiyer" TagPrefix="webdiyer" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" >
<head runat="server">
    <title>我提交过的文档</title>
    <link href="Crm.css" rel="stylesheet" type="text/css" />
</head>
<body>
    <form id="form1" runat="server">
        <div class="right">
            <div id="MyCrm">
                <strong>我提交过的文档</strong>
                <div id="MyCrm_List">
                    <div id="Crm_Search">
                    <div id="Crm_Action" style="line-height:24px; margin-bottom:4px">
                        <asp:DropDownList ID="DDL_Crm_State" runat="server" AutoPostBack="True" OnSelectedIndexChanged="DDL_Crm_State_SelectedIndexChanged">
                            <asp:ListItem Value="0">未验收的任务</asp:ListItem>
                            <asp:ListItem Value="1">已验收的任务</asp:ListItem>
                        </asp:DropDownList>
                        <asp:CheckBox ID="Chk_All" runat="server" Text="全 选" OnCheckedChanged="Chk_All_CheckedChanged" AutoPostBack="True" />&nbsp;
                        <asp:Button ID="But_State" runat="server" Text="验收选中项" OnClick="But_State_Click" />
                        <span id="State_Msg" runat="server"></span>
                    </div>
                    </div>
                    <asp:DataList ID="DL_MyCrm" runat="server">
                        <HeaderTemplate>
                            <div id="sMyCrmTitle" style=" text-align:center; width:640px; border-bottom:1px solid #333; background-color:#eee;line-height:24px">
                                <strong style="width:50px; float: left;">选择</strong>
                                <strong style="width:160px; float:left;">
                                    标 题
                                </strong>
                                <strong style="width: 100px; float: left;">
                                    接收部门 
                                </strong>
                                <strong style="width: 80px; float:left; ">
                                    接收人
                                </strong>
                                <strong style="width:120px; float:left; ">
                                    提交时间
                                </strong>
                                <strong style="width:60px; float:left; ">
                                    回复信息
                                </strong>
                                <strong style="width: 60px; float: left;">
                                    &nbsp; 
                                </strong>
                            </div>    
                        </HeaderTemplate>
                        <ItemTemplate>
                            <div id="sMyCrm" style=" width:640px; border-bottom:1px solid #333; text-align:center " class="crmlink">
                                <span style="width:10px;">&nbsp;</span>
                                <span style="width:40px; text-align:left;">
                                    <asp:CheckBox ID="CheckBox1" runat="server" enabled='<%# Eval("Crm_Finish")%>'/>
                                    <asp:Label ID="Label1" runat="server" Text='<%# Eval("Crm_id")%>' Visible="false"></asp:Label>
                                </span>
                                <span nowrap="nowrap" style="width: 160px; text-overflow: ellipsis; overflow: hidden;">
                                    <a href='CrmPosted.aspx?MyCrmId=<%# Eval("Crm_id")%>'>
                                        <%# Eval("Crm_Title")%>
                                    </a>
                                </span>
                                <span style="width: 100px;">
                                    <%# Eval("meno")%>
                                </span>
                                <span style="width: 80px;">
                                    <%# Eval("Crm_Request")%>
                                </span>
                                <span style="width:120px;">
                                    <%# Eval("Crm_Date")%>
                                </span>
                                <span style="width:60px;">
                                    <%# Eval("Crm_Reply")%>
                                </span>
                                <span style="width:60px;">
                                    <a href='CrmSending.aspx?backurl=CrmPosted.aspx&FbCrm_id=<%# Eval("Crm_id")%>'>
                                        反 馈
                                    </a>
                                </span>
                                <span style="width:10px;"> &nbsp; </span>
                            </div>
                        </ItemTemplate>
                    </asp:DataList>
                    <webdiyer:aspnetpager id="AspNetPager1" runat="server" firstpagetext="First"
                        lastpagetext="Last" nextpagetext="Next" pageindexboxtype="DropDownList" pagesize="5"
                        prevpagetext="Prev" showboxthreshold="10" showcustominfosection="Left" showpageindexbox="Auto"
                        submitbuttontext="Go" textafterpageindexbox="" textbeforepageindexbox="" urlpaging="True">
                    </webdiyer:aspnetpager>
                    <div id="NoRows" runat="server" style="color:Red; font-weight:bold;">
                        没有我提交过的文档</div>

                </div>
                <div id="Crm_Infor" style="padding:5px; width:640px;" runat="server">
                <hr color="#666">
                    <div style="font-size: 16pt; border-bottom: 1px solid #333; background-color: #eee;
                        line-height: 30px;"><strong id="sCrm_Title" runat="server">Crm_Title</strong></div>
                    <div style="border-bottom:1px solid #333; padding:5px">
                        <span id="sCrm_Aothur" runat="server" style="float:left; width:360px;">To_Meno To_Staff Date</span>
                        <span id="sCrm_Annex" runat="server" class="annex">没有附件
                            <img width="14px" height="14px" border="0" src="images/math.gif" />附件：报价.doc
                        </span>
                    </div>
                    <div style="height:150px; overflow:auto; padding:5px; border:1px solid #333">
                        <span id="sCrm_Content" runat="server">Crm_Content</span>
                    </div>
                <hr color="#666">
                </div>
                <div id="GetReply">
                <a name="Re"></a>
                <asp:DataList ID="DL_GetReply" runat="server" Width="620px">
                <HeaderTemplate>
                    <div style="background-color:#eee; line-height:24px; border-bottom:solid 1px #333">
                        &nbsp;&nbsp;回复及反馈--
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
                    <div id="sReply_Foot" style="width:160px; text-align:center">
                        <span>
                            <a href='CrmSending.aspx?backurl=CrmPosted.aspx&FbCrm_id=<%=ReCrm_id%>'>［回 复］</a>
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
