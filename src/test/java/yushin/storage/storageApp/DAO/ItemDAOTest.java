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
            
            // Попытаемся сохранить в БД несуществующий товар
            ItemDAO.create(null);
            
            // Если прожует null, то тест не пройден
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
        
        ItemDAO.update(new Item());
        
    }

    /**
     * Проверяем, что создание, изменение и удаление склада пройдет успешно.
     */
    @Test
    public void testAll() {
        
        // Создадим новый товар
        Item item = new Item();
        item.setName("New item");
        
        // И сохраним его в БД
        ItemDAO.create(item);

        // Найдем этот товар
        int id = item.getId();
        item = ItemDAO.findById(id);
        assertEquals(item.getId(), id);
        
        // Изменим его имя
        item.setName("Renamed item");
        ItemDAO.update(item);
        item = ItemDAO.findById(id);
        assertEquals(item.getId(), id);
        assertEquals("Renamed item", item.getName());
        
        // И удалим его
        ItemDAO.delete(item);
        assertNull(ItemDAO.findById(id));
        
    }
    
    /**
     * Проверяем, что ItemDAO.findById(-1) вернет null.
     */
    @Test
    public void testFindUnexistendItem() {

        // Попытаемся сохранить в БД несуществующий склад
        Item item = ItemDAO.findById(-1);
        assertNull(item);
        
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
        
        // И сохраним его в БД
        ItemDAO.create(item);
        int id = item.getId();
        
        // Проверим, что добавленный товар окажется в списке 
        // (пердполагается, что он будет в конце списка, что, вообще говоря, неверно)
        List<Item> items = ItemDAO.findAll();
        item = items.get(items.size() - 1);
        assertEquals(id, item.getId());
        
    }
    
}
