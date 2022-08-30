package yushin.storage.storageApp.servlets;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import yushin.storage.storageApp.DAO.StorageDAO;
import yushin.storage.storageApp.entities.Storage;

@WebServlet(name = "AddStorage", urlPatterns = {"/AddStorage"})
public class AddStorage extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String storageJSON = request.getParameter("storage");
        
        String result;
        try{
            Storage storage = new Gson().fromJson(storageJSON, Storage.class);
            result = StorageDAO.create(storage);
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
