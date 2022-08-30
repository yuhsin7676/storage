package yushin.storage.storageApp.servlets;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import yushin.storage.storageApp.DAO.SaleDAO;
import yushin.storage.storageApp.entities.Sale;

@WebServlet(name = "FindAllSale", urlPatterns = {"/FindAllSale"})
public class FindAllSale extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        List<Sale> sale = SaleDAO.findAll();
        String result = new Gson().toJson(sale);
        
        // Возврат сообщения
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println(result);
        }
        
    }

}
