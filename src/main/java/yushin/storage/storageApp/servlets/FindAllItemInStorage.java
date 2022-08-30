package yushin.storage.storageApp.servlets;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import yushin.storage.storageApp.DAO.ItemDAO;
import yushin.storage.storageApp.DAO.ItemInStorageDAO;
import yushin.storage.storageApp.entities.Item;
import yushin.storage.storageApp.entities.ItemInStorage;

@WebServlet(name = "FindAllItemInStorage", urlPatterns = {"/FindAllItemInStorage"})
public class FindAllItemInStorage extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String filtersJSON = request.getParameter("filters");
        
        List<ItemInStorage> itemsInStorage;
        if(filtersJSON != null){
            try{ 
                Type type = new TypeToken<List<Integer>>(){}.getType();
                List<Integer> filters = new Gson().fromJson(filtersJSON, type);
                itemsInStorage = ItemInStorageDAO.findAll(filters);
            }
            catch(Exception e){
                itemsInStorage = ItemInStorageDAO.findAll();
            }
        }
        else
            itemsInStorage = ItemInStorageDAO.findAll();
        
        String result = new Gson().toJson(itemsInStorage);
        
        // Возврат сообщения
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println(result);
        }
        
    }
}
