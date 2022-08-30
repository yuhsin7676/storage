package yushin.storage.storageApp.servlets;

import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import yushin.storage.storageApp.DAO.BuyDAO;
import yushin.storage.storageApp.DAO.ItemInStorageDAO;
import yushin.storage.storageApp.DAO.StorageDAO;
import yushin.storage.storageApp.entities.Buy;
import yushin.storage.storageApp.entities.ItemInStorage;
import yushin.storage.storageApp.entities.Storage;

public class BuyServletsTest {

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
     * Проверим, что при наличии склада AddBuy пройдет успешно.
     */
    @Test
    public void testAddBuy() throws Exception {
        
        // Добавим склад
        Storage storage = new Storage();
        StorageDAO.create(storage);
        int storage_id = storage.getId();
        
        when(request.getParameter("buy")).thenReturn("{id: 0, storage_id: " + storage_id + ", items: \"[{id: -1, number: 3, price: 150 }]\"}");
        new AddBuy().doPost(request, response);
        verify(printWriter).println("OK");
        
        // Удалим товар и склад
        ItemInStorage itemInStorage = ItemInStorageDAO.findAllByItemStorage(-1, storage_id).get(0);
        ItemInStorageDAO.delete(itemInStorage);
        StorageDAO.delete(storage);

    }
    
    /**
     * Проверим, что AddBuy не рухнет при некорректных данных.
     */
    @Test
    public void testAddBuyIncorrect() throws Exception {
        when(request.getParameter("buy")).thenReturn("erwerwer");
        new AddBuy().doPost(request, response);
        verify(printWriter).println(anyString());
        verify(printWriter, never()).println("OK");
    }
    
    /**
     * Проверим, что AddBuy не рухнет при отсутствии входных данных.
     */
    @Test
    public void testAddBuyNull() throws Exception {
        when(request.getParameter("buy")).thenReturn(null);
        new AddBuy().doPost(request, response);
        verify(printWriter).println(anyString());
        verify(printWriter, never()).println("OK");
    }
    
    /**
     * Проверяем, что покупка товаров на несуществующий склад пройдет неудачно.
     */
    @Test
    public void testAddBuyUnexistStorage() throws Exception {
        when(request.getParameter("buy")).thenReturn("{id: 0, storage_id: -1, items: \"[{id: -1, number: 3, price: 150 }]\"}");
        new AddBuy().doPost(request, response);
        verify(printWriter).println(anyString());
        verify(printWriter, never()).println("OK");

    }
    
    /**
     * Проверим, что FindAllBuy не рухнет.
     */
    @Test
    public void testFindAllBuy() throws Exception {
        new FindAllBuy().doPost(request, response);
        verify(printWriter).println(anyString());
    }
    
    /**
     * Проверим, что DeleteBuy пройдет успешно если документ существует.
     */
    @Test
    public void testDeleteBuy() throws Exception {
        
        // Добавим склад
        Storage storage = new Storage();
        StorageDAO.create(storage);
        int storage_id = storage.getId();
        
        // Создадим документ о покупке
        Buy buy = new Buy();
        buy.setStorage_id(storage_id);
        buy.setItems("[{id: -1, number: 3, price: 150 }]");
        BuyDAO.create(buy);
        int buy_id = buy.getId();
        
        when(request.getParameter("buy")).thenReturn("{id: " + buy_id + ", storage_id: " + storage_id + ", items: \"Любая абракадабра\"}");
        new DeleteBuy().doPost(request, response);
        verify(printWriter).println("OK");
        
        // Удалим товар и склад
        ItemInStorage itemInStorage = ItemInStorageDAO.findAllByItemStorage(-1, storage_id).get(0);
        ItemInStorageDAO.delete(itemInStorage);
        StorageDAO.delete(storage);

    }
    
    /**
     * Проверим, что DeleteBuy пройдет неудачно если документ НЕсуществует.
     */
    @Test
    public void testDeleteBuyUnexist() throws Exception {
        when(request.getParameter("buy")).thenReturn("{id: -3, storage_id: -1, items: \"[{id: -1, number: 3, price: 150 }]\"}");
        new DeleteBuy().doPost(request, response);
        verify(printWriter).println(anyString());
        verify(printWriter, never()).println("OK");
    }
    
    /**
     * Проверим, что DeleteBuy не рухнет при некорректных данных.
     */
    @Test
    public void testDeleteBuyIncorrect() throws Exception {
        when(request.getParameter("buy")).thenReturn("Любая абракадабра");
        new DeleteBuy().doPost(request, response);
        verify(printWriter).println(anyString());
        verify(printWriter, never()).println("OK");
    }
 
    /**
     * Проверим, что DeleteBuy не рухнет при отсутствии входных данных.
     */
    @Test
    public void testDeleteBuyNull() throws Exception {
        when(request.getParameter("buy")).thenReturn(null);
        new DeleteBuy().doPost(request, response);
        verify(printWriter).println(anyString());
        verify(printWriter, never()).println("OK");
    }
}
