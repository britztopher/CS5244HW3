<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="edu.vt.cs5244.*"%>
<%@page import="edu.vt.cs5244.util.*"%>
<%response.addHeader("Cache-control", "no-cache");%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Web DAB Home</title>
    </head> 
    <body>
        <h1>Hello World!</h1>
 
        <%
    DABEngine theDAB = (DABEngine)application.getAttribute("sharedDAB"); // fetch the permanent shared grid
    if (theDAB == null) { // if it doesn't exist yet...
        theDAB = new HW3HtmlDAB(new HW1_DAB()); // ... create a new one (for the JSP only!)
    }
%>

        <%=theDAB%>
    </body>
<%@include file="WEB-INF/jspf/form.jspf"%>
</html>