package yushin.storage.storageApp.servlets;

import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import yushin.storage.storageApp.DAO.ItemDAO;
import yushin.storage.storageApp.DAO.ItemInStorageDAO;
import yushin.storage.storageApp.DAO.StorageDAO;
import yushin.storage.storageApp.entities.Item;
import yushin.storage.storageApp.entities.ItemInStorage;
import yushin.storage.storageApp.entities.Storage;

/**
 *
 * @author ilya
 */
public class StorageServletsTest {
    
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
     * Проверяем, что добавление нового склада пройдет успешно.
     */
    @Test
    public void testAddStorage() throws Exception {
        when(request.getParameter("storage")).thenReturn("{id: 0, name: \"New storage\"}");
        new AddStorage().doPost(request, response);
        verify(printWriter).println("OK");
    }
    
    /**
     * Проверяем, что при некорректных данных программа не рухнет .
     */
    @Test
    public void testAddStorageIncorrectData() throws Exception {
        when(request.getParameter("storage")).thenReturn("Фигня");
        new AddStorage().doPost(request, response);
        verify(printWriter).println(anyString());
        verify(printWriter, never()).println("OK");
    }
    
    /**
     * Проверяем, что при отсутствии параметров программа также не рухнет.
     */
    @Test
    public void testAddStorageNullData() throws Exception {
        when(request.getParameter("storage")).thenReturn(null);
        new AddStorage().doPost(request, response);
        verify(printWriter).println(anyString());
        verify(printWriter, never()).println("OK");
    }
    
    /**
     * Проверяем, что обновление cуществующего склада пройдет успешно.
     */
    @Test
    public void testUpdateStorage() throws Exception {
        
        // Добавим склад
        Storage storage = new Storage();
        StorageDAO.create(storage);
        int storage_id = storage.getId();
        
        when(request.getParameter("storage")).thenReturn("{id: "+ storage_id + ", name: \"Rename storage\"}");
        new UpdateStorage().doPost(request, response);
        verify(printWriter).println("OK");
        
        // Удалим склад
        StorageDAO.delete(storage);
        
    }
    
    /**
     * Проверяем, что обновление НЕcуществующего склада пройдет неудачно.
     */
    @Test
    public void testUpdateUnexistStorage() throws Exception {
        when(request.getParameter("storage")).thenReturn("{id: -5, name: \"Rename storage\"}");
        new UpdateStorage().doPost(request, response);
        verify(printWriter).println(anyString());
        verify(printWriter, never()).println("OK");
    }
    
    /**
     * Проверяем, что при некорректных данных программа не рухнет .
     */
    @Test
    public void testUpdateStorageIncorrectData() throws Exception {
        when(request.getParameter("storage")).thenReturn("Что-то непонятное");
        new UpdateStorage().doPost(request, response);
        verify(printWriter).println(anyString());
        verify(printWriter, never()).println("OK");
    }
    
    /**
     * Проверяем, что при отсутствии параметров программа также не рухнет.
     */
    @Test
    public void testUpdateStorageNullData() throws Exception {
        when(request.getParameter("storage")).thenReturn(null);
        new UpdateStorage().doPost(request, response);
        verify(printWriter).println(anyString());
        verify(printWriter, never()).println("OK");
    }
    
    /**
     * Проверим, что FindAllStorage не рухнет.
     */
    @Test
    public void testFindAllStorage() throws Exception {
        new FindAllStorage().doPost(request, response);
        verify(printWriter).println(anyString());
    }
    
    /**
     * Проверим, что DeleteStorage пройдет успешно если склад существует.
     */
    @Test
    public void testDeleteStorage() throws Exception {
        
        // Добавим склад
        Storage storage = new Storage();
        StorageDAO.create(storage);
        int storage_id = storage.getId();

        when(request.getParameter("storage")).thenReturn("{id: " + storage_id + ", name: \"Любая абракадабра\"}");
        new DeleteStorage().doPost(request, response);
        verify(printWriter).println("OK");

    }
    
    /**
     * Проверим, что DeleteStorage пройдет неудачно если склад существует, но там остались товары.
     */
    @Test
    public void testDeleteStorageExistItems() throws Exception {
        
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
        
        when(request.getParameter("storage")).thenReturn("{id: " + storage_id + ", name: \"Любая абракадабра\"}");
        new DeleteStorage().doPost(request, response);
        verify(printWriter).println(anyString());
        verify(printWriter, never()).println("OK");
        
        // Удалим товар и склад
        ItemInStorageDAO.delete(itemInStorage);
        StorageDAO.delete(storage);
        
    }
    
    /**
     * Проверим, что DeleteStorage пройдет неудачно если склад НЕсуществует.
     */
    @Test
    public void testDeleteStorageUnexist() throws Exception {
        when(request.getParameter("storage")).thenReturn("{id: -3, name: \"Любая абракадабра\"}");
        new DeleteStorage().doPost(request, response);
        verify(printWriter).println(anyString());
        verify(printWriter, never()).println("OK");
    }
    
    /**
     * Проверим, что DeleteStorage не рухнет при некорректных данных.
     */
    @Test
    public void testDeleteStorageIncorrect() throws Exception {
        when(request.getParameter("storage")).thenReturn("Мусор");
        new DeleteStorage().doPost(request, response);
        verify(printWriter).println(anyString());
        verify(printWriter, never()).println("OK");
    }
 
    /**
     * Проверим, что DeleteStorage не рухнет при отсутствии входных данных.
     */
    @Test
    public void testDeleteStorageNull() throws Exception {
        when(request.getParameter("storage")).thenReturn(null);
        new DeleteStorage().doPost(request, response);
        verify(printWriter).println(anyString());
        verify(printWriter, never()).println("OK");
    }
    
}
