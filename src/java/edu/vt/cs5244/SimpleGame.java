/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vt.cs5244;

import edu.vt.cs5244.util.HW3HtmlDAB;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author bethanyfahey
 */
public class SimpleGame extends HttpServlet {

    private ServletContext application = null;
    HttpSession session = null;

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
        PrintWriter out = response.getWriter();

        //get the Session object
        session = request.getSession();
        //get the context
        application = getServletConfig().getServletContext();

        //instantiate new DABEngine
        DABEngine myBEM = new HW1_DAB();

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

        try {

            if (dabCmd.equalsIgnoreCase("init")) {
                //TODO: validate params
                if (!"on".equals(dabConfirm)) {
                    session.setAttribute("message", "INIT: Requires a checkmark to confirm.");
                    response.sendRedirect("../webDAB.jsp");
                } else {
                    application.setAttribute("init_size", dabSize);

                    synchronized(theDAB){
                        theDAB.init(Integer.parseInt(dabSize));
                    }
                    
                    session.setAttribute("message", "INIT(" + dabSize + ") Success!");
                    session.setAttribute("turn", Util.parsePlayer(theDAB.getTurn()));

                    response.sendRedirect("../webDAB.jsp");
                }
            } else if (dabCmd.equalsIgnoreCase("draw")) {
                //TODO: validate params
                synchronized(theDAB){
                    theDAB.drawEdge(Integer.parseInt(dabRow), Integer.parseInt(dabCol), Util.parseEdge(dabEdge));
                }
                session.setAttribute("message", "DRAW(" + dabRow + ", " + dabCol + ", " + dabEdge + ") Success!");
                session.setAttribute("turn", Util.parsePlayer(theDAB.getTurn()));
                 
                Map<Player, Integer> scoreMap = theDAB.getScores();
                session.setAttribute("scoreOne", String.valueOf(scoreMap.get(Player.ONE)));
                session.setAttribute("scoreTwo", String.valueOf(scoreMap.get(Player.TWO)));

                response.sendRedirect("../webDAB.jsp");
            } else {

            }

            return;

        } catch (NumberFormatException nfe) {
            //TODO: replace stacktraces
            nfe.printStackTrace();
        } catch (IllegalArgumentException iae) {
            iae.printStackTrace();
        } catch (DABException dabe) {
            throw new DABException();
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
