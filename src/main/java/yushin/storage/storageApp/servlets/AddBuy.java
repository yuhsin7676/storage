package yushin.storage.storageApp.servlets;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import yushin.storage.storageApp.DAO.BuyDAO;
import yushin.storage.storageApp.entities.Buy;

@WebServlet(name = "AddBuy", urlPatterns = {"/AddBuy"})
public class AddBuy extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String buyJSON = request.getParameter("buy");
        
        String result;
        try{
            Buy buy = new Gson().fromJson(buyJSON, Buy.class);
            result = BuyDAO.create(buy);
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
