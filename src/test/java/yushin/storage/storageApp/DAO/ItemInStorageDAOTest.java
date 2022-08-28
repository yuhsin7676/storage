/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package yushin.storage.storageApp.DAO;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import yushin.storage.storageApp.entities.Item;
import yushin.storage.storageApp.entities.ItemInStorage;

/**
 *
 * @author Ilya
 */
public class ItemInStorageDAOTest {
    
    /**
     * Проверяем, что ItemInStorageDAO.create(null) выбросит исключение.
     */
    @Test
    public void testCreateNull() {

        try{
            
            // Попытаемся сохранить в БД несуществующий склад
            ItemInStorageDAO.create(null);
            
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
    public void testUpdateUnexistItemInStorage(){
        
        ItemInStorageDAO.update(new ItemInStorage());
        
    }

    /**
     * Проверяем, что создание, изменение и удаление склада пройдет успешно.
     */
    @Test
    public void testAll() {
        
        // Создадим новый товар
        ItemInStorage itemInStorage = new ItemInStorage();
        itemInStorage.setItem_id(1);
        
        // И сохраним его в БД
        ItemInStorageDAO.create(itemInStorage);

        // Найдем этот товар
        int id = itemInStorage.getId();
        itemInStorage = ItemInStorageDAO.findById(id);
        assertEquals(itemInStorage.getId(), id);
        
        // Изменим его количество
        itemInStorage.setNumber(3);
        ItemInStorageDAO.update(itemInStorage);
        itemInStorage = ItemInStorageDAO.findById(id);
        assertEquals(itemInStorage.getId(), id);
        assertEquals(3, itemInStorage.getNumber());
        
        // Изменим его номер склада
        itemInStorage.setStorage_id(1);
        ItemInStorageDAO.update(itemInStorage);
        itemInStorage = ItemInStorageDAO.findById(id);
        assertEquals(itemInStorage.getId(), id);
        assertEquals(1, itemInStorage.getStorage_id());
        
        // И удалим его
        ItemInStorageDAO.delete(itemInStorage);
        assertNull(ItemInStorageDAO.findById(id));
        
    }
    
    /**
     * Проверяем, что ItemInStorageDAO.findById(-1) вернет null.
     */
    @Test
    public void testFindUnexistendItemInStorage() {

        // Попытаемся сохранить в БД несуществующий склад
        ItemInStorage itemInStorage = ItemInStorageDAO.findById(-1);
        assertNull(itemInStorage);
        
    }

    /**
     * Проверяем, что создание выдача списка товаров пройдет успешно.
     * Также проверяется, что вновь добавленный склад в этом списке окажется
     * Тест требует доработки!
     */
    @Test
    public void testFindAll() {
        
        // Построим новый склад
        ItemInStorage itemInStorage = new ItemInStorage();
        
        // И сохраним его в БД
        ItemInStorageDAO.create(itemInStorage);
        int id = itemInStorage.getId();
        
        if(id != 0){
            // Проверим, что добавленный товар окажется в списке 
            // (пердполагается, что он будет в конце списка, что, вообще говоря, неверно)
            List<ItemInStorage> itemsInStorag = ItemInStorageDAO.findAll();
            itemInStorage = itemsInStorag.get(itemsInStorag.size() - 1);
            assertEquals(id, itemInStorage.getId());
        }
        
        // Иначе товар не должен добавиться
        
    }
    
    /**
     * Проверяем, что создание выдача списка товаров пройдет успешно.
     * Также проверяется, что вновь добавленный склад в этом списке окажется
     * Тест требует доработки!
     */
    @Test
    public void testFindAllByItemIdStorage() {
        
        List<ItemInStorage> itemsInStorage = ItemInStorageDAO.findAllByItemIdStorage(-1, -1);
        assertEquals(0, itemsInStorage.size());
        
        // Найдем товар
        ItemInStorage itemInStorage;
        itemsInStorage = ItemInStorageDAO.findAllByItemIdStorage(3, 3);
        if(itemsInStorage.size() > 0)
            itemInStorage = itemsInStorage.get(0);
        else{
            itemInStorage = new ItemInStorage();
            itemInStorage.setItem_id(3);
            itemInStorage.setStorage_id(3);       
            ItemInStorageDAO.create(itemInStorage);
        }
        int id = itemInStorage.getId();
        
        // Проверим, что добавленный товар окажется в списке 
        // (пердполагается, что он будет единственным)
        itemsInStorage = ItemInStorageDAO.findAllByItemIdStorage(3, 3);
        itemInStorage = itemsInStorage.get(0);
        assertEquals(id, itemInStorage.getId());
        
    }
    
}
