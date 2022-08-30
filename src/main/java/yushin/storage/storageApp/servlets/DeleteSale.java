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
import yushin.storage.storageApp.DAO.SaleDAO;
import yushin.storage.storageApp.entities.Sale;

/**
 *
 * @author ilya
 */
@WebServlet(name = "DeleteSale", urlPatterns = {"/DeleteSale"})
public class DeleteSale extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String saleJSON = request.getParameter("sale");
        
        String result;
        try{
            Sale sale = new Gson().fromJson(saleJSON, Sale.class);
            result = SaleDAO.delete(sale);
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
