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

/**
 *
 * @author bethanyfahey
 */
public class SimpleGame extends HttpServlet {

    private ServletContext application = null;

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
        
        switch(dabCmd){
            
            case "init": {
                //TODO: validate params
                try{
                    theDAB.init(Integer.parseInt(dabSize));
                    out.print("HELLO");
                }catch(NumberFormatException nfe){
                    nfe.printStackTrace();
                }
            }
                
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
