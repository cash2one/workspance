<%@ Control Language="C#" AutoEventWireup="true" CodeFile="fckeditor.ascx.cs" Inherits="admin_fckeditor" %>
<%@ Import Namespace="BlogEngine.Core" %>
<%@ Import Namespace="BlogEngine.Core.Web.Controls" %>
<%@ Register TagPrefix="FCKeditorV2" Namespace="FredCK.FCKeditorV2" Assembly="FredCK.FCKeditorV2" %>

<FCKeditorV2:FCKeditor id="FCKeditor" runat="server" value="" HtmlEncodeOutput="false" Width="100%" Height="400px"></FCKeditorV2:FCKeditor>
