package yushin.storage.storageApp.DAO;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import yushin.storage.storageApp.entities.Item;

/**
 *
 * @author Ilya
 */
public class ItemDAOTest {
    
    /**
     * Проверяем, что ItemDAO.create(null) выбросит исключение.
     */
    @Test
    public void testCreateNull() {

        try{
            ItemDAO.create(null);
            fail();
        }
        catch(Exception e){
            System.out.println("Ура, выкинул исключение!");
        }
        
    }
    
    /**
     * Проверяем, что StorageDAO.create(null) выбросит исключение.
     */
    @Test
    public void testUpdateUnexistItem(){
        String result = ItemDAO.update(new Item());
        assertNotEquals("OK", result);
    }

    /**
     * Проверяем, что создание, изменение и удаление товара пройдет успешно.
     */
    @Test
    public void testAll() {
        
        // Создадим новый товар
        Item item = new Item();
        item.setName("New item");
        
        // И сохраним его в БД
        String result = ItemDAO.create(item);
        assertEquals("OK", result);

        // Найдем этот товар
        int id = item.getId();
        item = ItemDAO.findById(id);
        assertEquals(item.getId(), id);
        
        // Изменим его имя
        item.setName("Renamed item");
        result = ItemDAO.update(item);
        assertEquals("OK", result);
        
        item = ItemDAO.findById(id);
        assertEquals(item.getId(), id);
        assertEquals("Renamed item", item.getName());
        
        // И удалим его
        result = ItemDAO.delete(item);
        assertEquals("OK", result);
        assertNull(ItemDAO.findById(id));
        
    }
    
    /**
     * Проверяем, что ItemDAO.findById(-1) вернет null.
     */
    @Test
    public void testFindUnexistendItem() {
        assertNull(ItemDAO.findById(-1));
    }

    /**
     * Проверяем, что создание выдача списка складов пройдет успешно.
     * Также проверяется, что вновь добавленный склад в этом списке окажется
     * Тест требует доработки!
     */
    @Test
    public void testFindAll() {
        
        // Построим новый склад
        Item item = new Item();
        item.setName("New item");
        String result = ItemDAO.create(item);
        assertEquals("OK", result);
        int id = item.getId();
        
        // Проверим, что добавленный документ окажется в списке 
        List<Item> items = ItemDAO.findAll();
        boolean hasItem = false;
        for(int i = 0; i < items.size(); i++){
            Item localItem = items.get(i);
            if(localItem.getId() == id){
                hasItem = true;
                break;
            }
        }
        assertTrue(hasItem);
        
        // Удалим товар
        result = ItemDAO.delete(item);
        assertEquals("OK", result);
        
    }
    
}
