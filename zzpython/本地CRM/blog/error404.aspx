<%@ Page Language="C#" AutoEventWireup="true" CodeFile="error404.aspx.cs" Inherits="error404" %>

<asp:Content ID="Content1" ContentPlaceHolderID="cphBody" Runat="Server">
  <div class="post error404">
    <h1>�ޣ�������ͼ�򿪵���ҳû�ҵ���</h1>
    <div id="divSearchEngine" runat="server" visible="False" class="search">
      <p>
        ���ո��� <strong><a href="<%=Request.UrlReferrer %>"><%=Request.UrlReferrer.Host %></a></strong>
        ���� <strong><%=SearchTerm %></strong>���������ƺ����ڡ�
      </p>
      <h2>��Ŀ��δʧЧ</h2>
      <p>����ҳ����ܶ������ã�</p>
      <div id="divSearchResult" runat="server" />
    </div>
    
    <div id="divExternalReferrer" runat="server" visible="False">
      <p>
        �����˴��󲢱��ض�λ����ҳ�棺 
        <a href="javascript:history.go(-1)"><%=Request.UrlReferrer.Host %></a> 
      </p>
      
      <p>�����������������ӣ�</p>
      <ul>
        <li><a href="archive.aspx"><%=Resources.labels.archive %></a></li>
        <li><a href="<%=BlogEngine.Core.Utils.RelativeWebRoot %>">��ҳ</a></li>
      </ul>
      
      <p>�������Գ���<strong>�������ո�Ѱ�ҵ�ҳ��</strong>��</p>
      <blog:SearchBox runat="server" />
      
      <p>���ڸ�����ɵĲ������Ǹ�⣡</p>
    </div>
    
    <div id="divInternalReferrer" runat="server" visible="False">
      <p>
        �����Ѿ������ˣ���������ǵĵ�Ǹ�����ڿ����������ޣ��� 20 ��������ض�λ�����ҳ�档
        �����ǽ����Ժ�Ĺ������޸���Щ���⣩��
      </p>
      
      <p>�������Գ���<strong>�������ո�Ѱ�ҵ�ҳ��</strong>��</p>
      <blog:SearchBox ID="SearchBox2" runat="server" /><br /><br />
    </div>
    
    <div id="divDirectHit" runat="server" visible="False">
      <p>����ҳ����ܶ������ã�</p>
      <ul>
        <asp:placeholder runat="server" id="phSearchResult" />
        <li><a href="archive.aspx"><%=Resources.labels.archive %></a></li>
        <li><a href="<%=BlogEngine.Core.Utils.RelativeWebRoot %>">��ҳ</a></li>
      </ul>
      
      <p>�������Գ���<strong>�������ո�Ѱ�ҵ�ҳ��</strong>��</p>
      <blog:SearchBox ID="SearchBox1" runat="server" />
      
      <hr />
      
      <p><strong>��������������������δ������Ҫ��ҳ�棺</strong></p>
      <ol type="a">
        <li>��ǩ���ղؼ���<strong>��Ŀ�Ѿ�����</strong></li>
        <li>��������������ǵ�<strong>��Ŀ�Ѿ�����</strong></li>
        <li><strong>δ֪�ĵ�ַ����</strong></li>
      </ol>
    </div>
  </div>
</asp:Content>