
<%@ page import="com.nala.csd.LogisticsProblem" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'logisticsProblem.label', default: 'LogisticsProblem')}" />
        <title><g:if test="${tp}"><g:message code="default.list.label" args="['TP店异常单记录列表']" /></g:if><g:else><g:message code="default.list.label" args="['异常单记录列表']" /></g:else></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <g:if test="${tp}">
                <g:link action="create" params="[storeType:'true']"> <g:message code="default.new.label" args="['TP店异常单记录列表']" /></span> </g:link>
            </g:if>
            <g:else>
                <g:link action="create" params="[storeType:'false']"> <g:message code="default.new.label" args="['异常单记录列表']" /></span></g:link>
            </g:else>
        %{--<span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="['异常单']" /></g:link></span>--}%
        <span class="menuButton">
            <g:if test="${tp}">
                <g:link class="create" action="importIndex" params="[storeType:'true']">导入TP店异常单</g:link></span>
            </g:if >
            <g:else>
                <g:link class="create" action="importIndex"  params="[storeType:'false']">导入异常单</g:link></span>
            </g:else>
        </div>
        <div class="body">
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
	        <g:form action="importExcel" method="post" enctype="multipart/form-data">	        	
	            <h1>请选择文件（要求Excel格式的异常单数据）</h1><br />
	            <h1>Excel单元格数据A~N排列如下：</h1><br />
	            <h1>店铺__顾客ID__订单编号__异常原因__商品名称__商品SKU__商家编码__规格编码__买家备注__卖家备注__购买数量__收货人__收货地址__电话</h1><br /><br />
                ${tp}
                        <input type="hidden" name="storeType" value="${tp}">
						<input type="file" name="excel" />
						<input type="submit" />
	            </div>
            </g:form>
        </div>
    </body>
</html>
