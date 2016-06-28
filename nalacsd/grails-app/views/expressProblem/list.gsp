
<%@ page import="com.nala.csd.ExpressProblem" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'expressProblem.label', default: 'ExpressProblem')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'expressProblem.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="description" title="${message(code: 'expressProblem.description.label', default: 'Description')}" />
                        
                            <th><g:message code="expressProblem.express.label" default="Express" /></th>
                        
                            <g:sortableColumn property="logisticsID" title="${message(code: 'expressProblem.logisticsID.label', default: 'Logistics ID')}" />
                        
                            <g:sortableColumn property="problemType" title="${message(code: 'expressProblem.problemType.label', default: 'Problem Type')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${expressProblemInstanceList}" status="i" var="expressProblemInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${expressProblemInstance.id}">${fieldValue(bean: expressProblemInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: expressProblemInstance, field: "description")}</td>
                        
                            <td>${fieldValue(bean: expressProblemInstance, field: "express")}</td>
                        
                            <td>${fieldValue(bean: expressProblemInstance, field: "logisticsID")}</td>
                        
                            <td>${fieldValue(bean: expressProblemInstance, field: "problemType")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${expressProblemInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
