/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vt.cs5244;

import edu.vt.cs5244.util.HW3HtmlDAB;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author bethanyfahey
 */
public class SimpleGame extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        //get the Session object
        HttpSession session = request.getSession();

        //get the context
        ServletContext application = getServletConfig().getServletContext();

        DABEngine theDAB = (DABEngine) application.getAttribute("sharedDAB"); // fetch the permanent shared grid
        if (theDAB == null) { // if it doesn't exist yet...
            theDAB = new HW3HtmlDAB(new HW1_DAB()); // ... create a new one ...
            application.setAttribute("sharedDAB", theDAB); // ...and store it permanently
            // the line immediately above changes the state of the system!
        }

        String dabCmd = request.getParameter("command");
        String dabCol = request.getParameter("draw_col");
        String dabRow = request.getParameter("draw_row");
        String dabEdge = request.getParameter("draw_edge");
        String dabSize = request.getParameter("init_size");
        String dabConfirm = request.getParameter("init_confirm");
        boolean isDrawn = false;

        try {
            if (dabCmd.equalsIgnoreCase("init")) {
                //TODO: validate params

                if (!"on".equals(dabConfirm)) {
                    session.setAttribute("message", "INIT: Requires a checkmark to confirm.");
                    response.sendRedirect("../webDAB.jsp");
                    return;
                } else {
                    application.setAttribute("init_size", dabSize);

                    try {

                        synchronized (theDAB) {
                            theDAB.init(Integer.parseInt(dabSize));
                        }

                    } catch (DABException dabe) {
                        session.setAttribute("message", "INIT: Board Size of " + dabSize + " not Supported.");
                    } catch (NumberFormatException nfe) {
                        session.setAttribute("message", "INIT: Size value must be numeric");
                    }
                    response.sendRedirect("../webDAB.jsp");
                    return;
                }
            } else if (dabCmd.equalsIgnoreCase("draw")) {

                Player turn = null;
                
                try {

                    synchronized (theDAB) {

                        turn = theDAB.getTurn();
                        isDrawn = theDAB.drawEdge(Integer.parseInt(dabRow), Integer.parseInt(dabCol), Util.parseEdge(dabEdge));
                    }

                    if (!isDrawn) {
                        session.setAttribute("message", "DRAW(" + dabRow + ", " + dabCol + ", " + dabEdge + ") Line Already Drawn.");
                    } else {
                        session.setAttribute("message", "DRAW(" + dabRow + ", " + dabCol + ", " + dabEdge + ") Success!");
                    }
                } catch (DABException dabe) {
                    if (turn == null) {
                        session.setAttribute("message", "DRAW(" + dabRow + ", " + dabCol + ", " + dabEdge + ") Game is Over");
                    } else if ( dabEdge == null){
                        session.setAttribute("message", "DRAW: Edge value must be selected from the drop-down box.");
                    }else if( dabEdge.equalsIgnoreCase("none")){
                        session.setAttribute("message", "DRAW: Edge value must be selected from the drop-down box.");                        
                    }else{
                        session.setAttribute("message", "DRAW: Location is Out of Bounds.");                    
                    }
                } catch (NumberFormatException nfe) {
                    session.setAttribute("message", "DRAW: Row and Col values must both be numeric.");
                }

                response.sendRedirect("../webDAB.jsp");
                return;
            } else {
                session.setAttribute("message", "Please select a Valid Command.");
                response.sendRedirect("../webDAB.jsp");
                return;

            }

        } catch (NullPointerException npe) {
            session.setAttribute("message", "Please select a Valid Command.");
            response.sendRedirect("../webDAB.jsp");
            return;
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
