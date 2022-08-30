package yushin.storage.storageApp.servlets;

import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import yushin.storage.storageApp.DAO.SaleDAO;
import yushin.storage.storageApp.DAO.ItemInStorageDAO;
import yushin.storage.storageApp.DAO.StorageDAO;
import yushin.storage.storageApp.entities.Sale;
import yushin.storage.storageApp.entities.ItemInStorage;
import yushin.storage.storageApp.entities.Storage;

public class SaleServletsTest {

    HttpServletRequest request;
    HttpServletResponse response;
    PrintWriter printWriter;
    
    @BeforeEach
    public void init() throws Exception{
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        printWriter = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(printWriter);
    }
    
    /**
     * Проверим, что при наличии 2 складов и товаров на 1-м AddSale пройдет успешно.
     */
    @Test
    public void testAddSale() throws Exception {
        
        // Добавим склад
        Storage storage = new Storage();
        StorageDAO.create(storage);
        int storage_id = storage.getId();
        
        // Добавим товары
        ItemInStorage itemInStorage = new ItemInStorage();
        itemInStorage.setItem_id(-1);
        itemInStorage.setStorage_id(storage_id);
        itemInStorage.setNumber(3);
        ItemInStorageDAO.create(itemInStorage);
        
        when(request.getParameter("sale")).thenReturn("{id: 0, storage_id: " + storage_id + ", items: \"[{id: -1, number: 3, price: 160}]\"}");
        new AddSale().doPost(request, response);
        verify(printWriter).println("OK");
        
        // Удалим склад
        StorageDAO.delete(storage);

    }
    
    /**
     * Проверим, что AddSale не рухнет при некорректных данных.
     */
    @Test
    public void testAddSaleIncorrect() throws Exception {
        when(request.getParameter("sale")).thenReturn("Помойка");
        new AddSale().doPost(request, response);
        verify(printWriter).println(anyString());
        verify(printWriter, never()).println("OK");
    }
    
    /**
     * Проверим, что AddSale не рухнет при отсутствии входных данных.
     */
    @Test
    public void testAddSaleNull() throws Exception {
        when(request.getParameter("sale")).thenReturn(null);
        new AddSale().doPost(request, response);
        verify(printWriter).println(anyString());
        verify(printWriter, never()).println("OK");
    }
    
    /**
     * Проверяем, что перенеос товаров по несуществующим складам пройдет неудачно.
     */
    @Test
    public void testAddSaleUnexistStorage() throws Exception {
        when(request.getParameter("sale")).thenReturn("{id: 0, storage_id: -3, items: \"[{id: -1, number: 3, price: 160}]\"}");
        new AddSale().doPost(request, response);
        verify(printWriter).println(anyString());
        verify(printWriter, never()).println("OK");
    }
    
    /**
     * Проверим, что FindAllSale не рухнет.
     */
    @Test
    public void testFindAllSale() throws Exception {
        new FindAllSale().doPost(request, response);
        verify(printWriter).println(anyString());
    }
    
    /**
     * Проверим, что DeleteSale пройдет успешно если документ существует.
     */
    @Test
    public void testDeleteSale() throws Exception {
        
        // Добавим склад
        Storage storage = new Storage();
        StorageDAO.create(storage);
        int storage_id = storage.getId();
        
        // Добавим товары
        ItemInStorage itemInStorage = new ItemInStorage();
        itemInStorage.setItem_id(-1);
        itemInStorage.setStorage_id(storage_id);
        itemInStorage.setNumber(3);
        ItemInStorageDAO.create(itemInStorage);
        
        // Создадим документ о продаже
        Sale sale = new Sale();
        sale.setStorage_id(storage_id);
        sale.setItems("[{id: -1, number: 3}]");
        SaleDAO.create(sale);
        int sale_id = sale.getId();
        
        when(request.getParameter("sale")).thenReturn("{id: " + sale_id + ", storage_id: " + storage_id + ", items: \"[{id: -1, number: 3, price: 160}]\"}");
        new DeleteSale().doPost(request, response);
        verify(printWriter).println("OK");
        
        // Удалим склад
        StorageDAO.delete(storage);

    }
    
    /**
     * Проверим, что DeleteSale пройдет неудачно если документ НЕсуществует.
     */
    @Test
    public void testDeleteSaleUnexist() throws Exception {
        when(request.getParameter("sale")).thenReturn("{id: -3, storage_id: -1, items: \"[{id: -1, number: 3, price: 150 }]\"}");
        new DeleteSale().doPost(request, response);
        verify(printWriter).println(anyString());
        verify(printWriter, never()).println("OK");
    }
    
    /**
     * Проверим, что DeleteSale не рухнет при некорректных данных.
     */
    @Test
    public void testDeleteSaleIncorrect() throws Exception {
        when(request.getParameter("sale")).thenReturn("Любая абракадабра");
        new DeleteSale().doPost(request, response);
        verify(printWriter).println(anyString());
        verify(printWriter, never()).println("OK");
    }
 
    /**
     * Проверим, что DeleteSale не рухнет при отсутствии входных данных.
     */
    @Test
    public void testDeleteSaleNull() throws Exception {
        when(request.getParameter("sale")).thenReturn(null);
        new DeleteSale().doPost(request, response);
        verify(printWriter).println(anyString());
        verify(printWriter, never()).println("OK");
    }
}
