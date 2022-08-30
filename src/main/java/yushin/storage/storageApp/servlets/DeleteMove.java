/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package yushin.storage.storageApp.servlets;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import yushin.storage.storageApp.DAO.MoveDAO;
import yushin.storage.storageApp.entities.Move;

/**
 *
 * @author ilya
 */
@WebServlet(name = "DeleteMove", urlPatterns = {"/DeleteMove"})
public class DeleteMove extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String moveJSON = request.getParameter("move");
        
        String result;
        try{
            Move move = new Gson().fromJson(moveJSON, Move.class);
            result = MoveDAO.delete(move);
        }
        catch(Exception e){
            result = e.getMessage();
        }
        
        // Возврат сообщения
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println(result);
        }
        
    }

}
