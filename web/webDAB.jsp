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
            DABEngine theDAB = (DABEngine) application.getAttribute("sharedDAB"); // fetch the permanent shared grid
            if (theDAB == null) { // if it doesn't exist yet...
                theDAB = new HW3HtmlDAB(new HW1_DAB()); // ... create a new one (for the JSP only!)
            }
        %>

        <%=theDAB%>
        
        <% String turnMsg = (String) session.getAttribute("turn");
            if (turnMsg == null) { // if no such message...
                turnMsg = "ONE"; // ...use a temporary default message
            }
            
            String scoreOne = (String) session.getAttribute("scoreOne");
            if (scoreOne == null) { // if no such message...
                scoreOne = "0"; // ...use a temporary default message
            }
            
            String scoreTwo = (String) session.getAttribute("scoreTwo");
            if (scoreTwo == null) { // if no such message...
                scoreTwo = "0"; // ...use a temporary default message
            }
        %>
        
        <h4>Turn: <%=turnMsg%> </h4>
        <h4>ONE: <%=scoreOne%>&nbsp; - &nbsp; TWO: <%=scoreTwo%></h4>
        <hr/>
        
        <% String message = (String) session.getAttribute("message");
            if (message == null) { // if no such message...
                message = "No message from servlet"; // ...use a temporary default message
            }
        %>
        <h3>Your Previous Action:</h3> 
        <h5> <%=message%></h5>
        <hr/>
    </body>
    <%@include file="WEB-INF/jspf/form.jspf"%>
</html>