package yushin.storage.storageApp.servlets;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import yushin.storage.storageApp.DAO.ItemDAO;
import yushin.storage.storageApp.entities.Item;

@WebServlet(name = "DeleteItem", urlPatterns = {"/DeleteItem"})
public class DeleteItem extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String itemJSON = request.getParameter("item");
        
        String result;
        try{
            Item item = new Gson().fromJson(itemJSON, Item.class);
            result = ItemDAO.delete(item);
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
