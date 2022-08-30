package yushin.storage.storageApp.servlets;

import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import yushin.storage.storageApp.DAO.ItemDAO;
import yushin.storage.storageApp.entities.Item;

/**
 *
 * @author ilya
 */
public class ItemServletsTest {
    
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
     * Проверяем, что добавление нового товара пройдет успешно.
     */
    @Test
    public void testAddItem() throws Exception {
        when(request.getParameter("item")).thenReturn("{id: 0, name: \"New item\", price_buy: 140, price_sale: 160}");
        new AddItem().doPost(request, response);
        verify(printWriter).println("OK");
    }
    
    /**
     * Проверяем, что при некорректных данных программа не рухнет .
     */
    @Test
    public void testAddItemIncorrectData() throws Exception {
        when(request.getParameter("item")).thenReturn("Фигня");
        new AddItem().doPost(request, response);
        verify(printWriter).println(anyString());
        verify(printWriter, never()).println("OK");
    }
    
    /**
     * Проверяем, что при отсутствии параметров программа также не рухнет.
     */
    @Test
    public void testAddItemNullData() throws Exception {
        when(request.getParameter("item")).thenReturn(null);
        new AddItem().doPost(request, response);
        verify(printWriter).println(anyString());
        verify(printWriter, never()).println("OK");
    }
    
    /**
     * Проверяем, что обновление cуществующего товара пройдет успешно.
     */
    @Test
    public void testUpdateItem() throws Exception {
        
        // Добавим товар
        Item item = new Item();
        ItemDAO.create(item);
        int item_id = item.getId();
        
        when(request.getParameter("item")).thenReturn("{id: "+ item_id + ", name: \"Rename item\", price_buy: 140, price_sale: 160}");
        new UpdateItem().doPost(request, response);
        verify(printWriter).println("OK");
        
        // Удалим товар
        ItemDAO.delete(item);
        
    }
    
    /**
     * Проверяем, что обновление НЕcуществующего товара пройдет неудачно.
     */
    @Test
    public void testUpdateUnexistItem() throws Exception {
        when(request.getParameter("item")).thenReturn("{id: -5, name: \"Rename item\", price_buy: 140, price_sale: 160}");
        new UpdateItem().doPost(request, response);
        verify(printWriter).println(anyString());
        verify(printWriter, never()).println("OK");
    }
    
    /**
     * Проверяем, что при некорректных данных программа не рухнет .
     */
    @Test
    public void testUpdateItemIncorrectData() throws Exception {
        when(request.getParameter("item")).thenReturn("Что-то непонятное");
        new UpdateItem().doPost(request, response);
        verify(printWriter).println(anyString());
        verify(printWriter, never()).println("OK");
    }
    
    /**
     * Проверяем, что при отсутствии параметров программа также не рухнет.
     */
    @Test
    public void testUpdateItemNullData() throws Exception {
        when(request.getParameter("item")).thenReturn(null);
        new UpdateItem().doPost(request, response);
        verify(printWriter).println(anyString());
        verify(printWriter, never()).println("OK");
    }
    
    /**
     * Проверим, что FindAllItem не рухнет.
     */
    @Test
    public void testFindAllItem() throws Exception {
        new FindAllItem().doPost(request, response);
        verify(printWriter).println(anyString());
    }
    
    /**
     * Проверим, что FindAllItem c фильтрами не рухнет.
     */
    @Test
    public void testFindAllItemFilter() throws Exception {
        when(request.getParameter("filters")).thenReturn("['banana', 'orange']");
        new FindAllItem().doPost(request, response);
        verify(printWriter).println(anyString());
    }
    
    /**
     * Проверим, что DeleteItem пройдет успешно если товар существует.
     */
    @Test
    public void testDeleteItem() throws Exception {
        
        // Добавим товар
        Item item = new Item();
        ItemDAO.create(item);
        int item_id = item.getId();
        
        when(request.getParameter("item")).thenReturn("{id: " + item_id + ", name: \"Любое название\", price_buy: 666, price_sale: 777}");
        new DeleteItem().doPost(request, response);
        verify(printWriter).println("OK");
        
    }
    
    /**
     * Проверим, что DeleteItem пройдет неудачно если товар НЕсуществует.
     */
    @Test
    public void testDeleteItemUnexist() throws Exception {
        when(request.getParameter("item")).thenReturn("{id: -3, name: \"Любое название\", price_buy: 666, price_sale: 777}");
        new DeleteItem().doPost(request, response);
        verify(printWriter).println(anyString());
        verify(printWriter, never()).println("OK");
    }
    
    /**
     * Проверим, что DeleteItem не рухнет при некорректных данных.
     */
    @Test
    public void testDeleteItemIncorrect() throws Exception {
        when(request.getParameter("item")).thenReturn("Мусор");
        new DeleteItem().doPost(request, response);
        verify(printWriter).println(anyString());
        verify(printWriter, never()).println("OK");
    }
 
    /**
     * Проверим, что DeleteItem не рухнет при отсутствии входных данных.
     */
    @Test
    public void testDeleteItemNull() throws Exception {
        when(request.getParameter("item")).thenReturn(null);
        new DeleteItem().doPost(request, response);
        verify(printWriter).println(anyString());
        verify(printWriter, never()).println("OK");
    }
    
}
