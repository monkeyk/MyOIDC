<%--
 * 
 * @author Shengzhao Li
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ include file="../commons/taglib-header.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8"/>
    <c:set var="contextPath" value="${pageContext.request.contextPath}" scope="application"/>
    <script>
        <%--JS gloable varilible--%>
        var contextPath = "${contextPath}";
    </script>


    <meta name="viewport" content="width=device-width,user-scalable=no"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta name="${_csrf.headerName}" content="${_csrf.token}"/>
    <link rel="shortcut icon" href="${contextPath}/static/favicon.ico"/>

    <title><decorator:title default=""/> . MyOIDC</title>

    <link href="${contextPath}/static/bootstrap/css/bootstrap.min.css" rel="stylesheet"/>
    <decorator:head/>

</head>
<body>
<div class="container">

    <decorator:body/>

    <div>
        <hr/>
        <p class="text-muted text-center">
            &copy; MyOIDC - Ver. 1.0.0
        </p>
    </div>
</div>

</body>
</html>