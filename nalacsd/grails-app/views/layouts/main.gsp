<!DOCTYPE html>
<html>
    <head>
        <title><g:layoutTitle default="Grails" /></title>
        <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
        <link rel="shortcut icon" href="${resource(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
        <g:javascript library="application" />
        <g:javascript library="jquery-1.6.1.min" />
        <g:render template="/common/ga"/>
        <g:layoutHead />
    </head>
    <body>
        <div id="spinner" class="spinner" style="display:none;">
            <img src="${resource(dir:'images',file:'spinner.gif')}" alt="${message(code:'spinner.alt',default:'Loading...')}" />
        </div>
        <div id="grailsLogo">
            <a href="/"><img src="${resource(dir:'images',file:'logo_with_beta.png')}" alt="Grails" border="0" /></a>
            <g:if test="${!controllerName.equals('login')}">
                <span>你好，<g:username />。&nbsp;&nbsp;|&nbsp;&nbsp;</span>
                <span><a href="/hero/show/<g:userid />">个人设置</a>&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;</span>
                <span><a href="/j_spring_security_logout">退出</a></span>
            </g:if>
        </div>
        <g:layoutBody />        
        <g:javascript library="My97DatePicker/WdatePicker" />
    </body>
</html>