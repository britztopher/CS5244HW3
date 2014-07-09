<%@page import="java.util.Map"%>
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
        <h1>Grid Game!</h1>

        <%
            String turn = "ONE";
            String scoreOne = "0";
            String scoreTwo = "0";
            String gridDisplay;
            Map<Player, Integer> scoreMap;

            DABEngine theDAB = (DABEngine) application.getAttribute("sharedDAB"); // fetch the permanent shared grid
            if (theDAB == null) { // if it doesn't exist yet...
                theDAB = new HW3HtmlDAB(new HW1_DAB()); // ... create a new one (for the JSP only!)
            }

            synchronized (theDAB) {
                gridDisplay = theDAB.toString();

                turn = Util.parsePlayer(theDAB.getTurn());
                scoreMap = theDAB.getScores();
            }
            
            scoreMap = theDAB.getScores();
            scoreOne = String.valueOf(scoreMap.get(Player.ONE));
            scoreTwo = String.valueOf(scoreMap.get(Player.TWO));

            if (turn == null) { // if no such message...
                turn = "GAME IS OVER!!";
            }

        %>

        <%=gridDisplay%>

        <h4>Turn: <%=turn%> </h4>
        <h4>ONE: <%=scoreOne%>&nbsp; - &nbsp; TWO: <%=scoreTwo%></h4>
        <hr/>

        <% String message = (String) session.getAttribute("message");
            if (message == null) { // if no such message...
                message = "No Moves Made Yet."; // ...use a temporary default message
            }
        %>
        <h3>Your Previous Action:</h3> 
        <h5> <%=message%></h5>
        <hr/>
    </body>
    <%@include file="WEB-INF/jspf/form.jspf"%>
</html>