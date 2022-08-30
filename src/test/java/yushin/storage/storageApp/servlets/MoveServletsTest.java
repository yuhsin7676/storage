package yushin.storage.storageApp.servlets;

import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import yushin.storage.storageApp.DAO.MoveDAO;
import yushin.storage.storageApp.DAO.ItemInStorageDAO;
import yushin.storage.storageApp.DAO.StorageDAO;
import yushin.storage.storageApp.entities.Move;
import yushin.storage.storageApp.entities.ItemInStorage;
import yushin.storage.storageApp.entities.Storage;

public class MoveServletsTest {

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
     * Проверим, что при наличии 2 складов и товаров на 1-м AddMove пройдет успешно.
     */
    @Test
    public void testAddMove() throws Exception {
        
        // Добавим 1 склад
        Storage storage_from = new Storage();
        StorageDAO.create(storage_from);
        int storage_from_id = storage_from.getId();
        
        // Добавим 2 склад
        Storage storage_to = new Storage();
        StorageDAO.create(storage_to);
        int storage_to_id = storage_to.getId();
        
        // Добавим товары на 1-й
        ItemInStorage itemInStorage = new ItemInStorage();
        itemInStorage.setItem_id(-1);
        itemInStorage.setStorage_id(storage_from_id);
        itemInStorage.setNumber(3);
        ItemInStorageDAO.create(itemInStorage);
        
        when(request.getParameter("move")).thenReturn("{id: 0, storage_from: " + storage_from_id + ", storage_to: " + storage_to_id + ", items: \"[{id: -1, number: 3}]\"}");
        new AddMove().doPost(request, response);
        verify(printWriter).println("OK");
        
        // Удалим товар и склад
        itemInStorage = ItemInStorageDAO.findAllByItemStorage(-1, storage_to_id).get(0);
        ItemInStorageDAO.delete(itemInStorage);
        StorageDAO.delete(storage_from);
        StorageDAO.delete(storage_to);

    }
    
    /**
     * Проверим, что AddMove не рухнет при некорректных данных.
     */
    @Test
    public void testAddMoveIncorrect() throws Exception {
        when(request.getParameter("move")).thenReturn("Помойка");
        new AddMove().doPost(request, response);
        verify(printWriter).println(anyString());
        verify(printWriter, never()).println("OK");
    }
    
    /**
     * Проверим, что AddMove не рухнет при отсутствии входных данных.
     */
    @Test
    public void testAddMoveNull() throws Exception {
        when(request.getParameter("move")).thenReturn(null);
        new AddMove().doPost(request, response);
        verify(printWriter).println(anyString());
        verify(printWriter, never()).println("OK");
    }
    
    /**
     * Проверяем, что перенеос товаров по несуществующим складам пройдет неудачно.
     */
    @Test
    public void testAddMoveUnexistStorage() throws Exception {
        when(request.getParameter("move")).thenReturn("{id: 0, storage_from: -3, storage_to: -4, items: \"[{id: -1, number: 3}]\"}");
        new AddMove().doPost(request, response);
        verify(printWriter).println(anyString());
        verify(printWriter, never()).println("OK");
    }
    
    /**
     * Проверим, что FindAllMove не рухнет.
     */
    @Test
    public void testFindAllMove() throws Exception {
        new FindAllMove().doPost(request, response);
        verify(printWriter).println(anyString());
    }
    
    /**
     * Проверим, что DeleteMove пройдет успешно если документ существует.
     */
    @Test
    public void testDeleteMove() throws Exception {
        
        // Добавим 1 склад
        Storage storage_from = new Storage();
        StorageDAO.create(storage_from);
        int storage_from_id = storage_from.getId();
        
        // Добавим 2 склад
        Storage storage_to = new Storage();
        StorageDAO.create(storage_to);
        int storage_to_id = storage_to.getId();
        
        // Добавим товары на 1-й
        ItemInStorage itemInStorage = new ItemInStorage();
        itemInStorage.setItem_id(-1);
        itemInStorage.setStorage_id(storage_from_id);
        itemInStorage.setNumber(3);
        ItemInStorageDAO.create(itemInStorage);
        
        // Создадим документ о перемещении
        Move move = new Move();
        move.setStorage_from(storage_from_id);
        move.setStorage_to(storage_to_id);
        move.setItems("[{id: -1, number: 3}]");
        MoveDAO.create(move);
        int move_id = move.getId();
        
        when(request.getParameter("move")).thenReturn("{id: " + move_id + ", storage_from: " + storage_from_id + ", storage_to: " + storage_to_id + ", items: \"[{id: -1, number: 3}]\"}");
        new DeleteMove().doPost(request, response);
        verify(printWriter).println("OK");
        
        // Удалим товар и склад
        itemInStorage = ItemInStorageDAO.findAllByItemStorage(-1, storage_to_id).get(0);
        ItemInStorageDAO.delete(itemInStorage);
        StorageDAO.delete(storage_from);
        StorageDAO.delete(storage_to);

    }
    
    /**
     * Проверим, что DeleteMove пройдет неудачно если документ НЕсуществует.
     */
    @Test
    public void testDeleteMoveUnexist() throws Exception {
        when(request.getParameter("move")).thenReturn("{id: -3, storage_id: -1, items: \"[{id: -1, number: 3, price: 150 }]\"}");
        new DeleteMove().doPost(request, response);
        verify(printWriter).println(anyString());
        verify(printWriter, never()).println("OK");
    }
    
    /**
     * Проверим, что DeleteMove не рухнет при некорректных данных.
     */
    @Test
    public void testDeleteMoveIncorrect() throws Exception {
        when(request.getParameter("move")).thenReturn("Любая абракадабра");
        new DeleteMove().doPost(request, response);
        verify(printWriter).println(anyString());
        verify(printWriter, never()).println("OK");
    }
 
    /**
     * Проверим, что DeleteMove не рухнет при отсутствии входных данных.
     */
    @Test
    public void testDeleteMoveNull() throws Exception {
        when(request.getParameter("move")).thenReturn(null);
        new DeleteMove().doPost(request, response);
        verify(printWriter).println(anyString());
        verify(printWriter, never()).println("OK");
    }
}
