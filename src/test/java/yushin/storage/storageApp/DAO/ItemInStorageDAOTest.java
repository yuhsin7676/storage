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
import yushin.storage.storageApp.entities.Storage;

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
            ItemInStorageDAO.create(null);
            fail();
        }
        catch(Exception e){
            System.out.println("Ура, выкинул исключение!");
        }
    }
    
    /**
     * Проверяем, что ItemInStorage.update(new ItemInStorage()) выбросит исключение.
     */
    @Test
    public void testUpdateUnexistItemInStorage(){
        ItemInStorageDAO.update(new ItemInStorage());
    }

    /**
     * Проверяем, что создание, изменение и удаление товара на складе пройдет успешно.
     */
    @Test
    public void testAll() {
        
        // Построим 2 новых склада
        Storage storage1 = new Storage();
        storage1.setName("New storage");
        StorageDAO.create(storage1);
        int storage_id1 = storage1.getId();
        
        Storage storage2 = new Storage();
        storage2.setName("New storage");
        StorageDAO.create(storage2);
        int storage_id2 = storage1.getId();
        
        // Создадим новый товар
        ItemInStorage itemInStorage = new ItemInStorage();
        itemInStorage.setStorage_id(storage_id1);
        itemInStorage.setItem_id(1);
        itemInStorage.setNumber(1);
        ItemInStorageDAO.create(itemInStorage);

        // Найдем этот товар
        int id = itemInStorage.getId();
        itemInStorage = ItemInStorageDAO.findById(id);
        assertEquals(id, itemInStorage.getId());
        
        // Изменим его количество
        itemInStorage.setNumber(3);
        ItemInStorageDAO.update(itemInStorage);
        itemInStorage = ItemInStorageDAO.findById(id);
        assertEquals(itemInStorage.getId(), id);
        assertEquals(3, itemInStorage.getNumber());
        
        // Изменим его номер склада
        itemInStorage.setStorage_id(storage_id2);
        ItemInStorageDAO.update(itemInStorage);
        itemInStorage = ItemInStorageDAO.findById(id);
        assertEquals(itemInStorage.getId(), id);
        assertEquals(storage_id2, itemInStorage.getStorage_id());
        
        // И удалим его
        ItemInStorageDAO.delete(itemInStorage);
        assertNull(ItemInStorageDAO.findById(id));
        
        // Заодно удалим склады
        StorageDAO.delete(storage1);
        StorageDAO.delete(storage2);
        
    }
    
    /**
     * Проверяем, что создание товара на несуществующем складе завершится неудачей.
     */
    @Test
    public void testCreateItemInUnexistStorage() {
        
        // Создадим новый товар (не должен создасться)
        ItemInStorage itemInStorage = new ItemInStorage();
        itemInStorage.setStorage_id(-1);
        itemInStorage.setItem_id(1);
        itemInStorage.setNumber(1);
        ItemInStorageDAO.create(itemInStorage);
        assertEquals(0, itemInStorage.getId());
        
    }
    
    /**
     * Проверяем, что ItemInStorageDAO.findById(-1) вернет null.
     */
    @Test
    public void testFindUnexistendItemInStorage() {
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
        Storage storage = new Storage();
        StorageDAO.create(storage);
        int storage_id = storage.getId();
        
        // Создадим новый товар
        ItemInStorage itemInStorage = new ItemInStorage();
        itemInStorage.setItem_id(1);
        itemInStorage.setNumber(2);
        itemInStorage.setStorage_id(storage_id);
        ItemInStorageDAO.create(itemInStorage);
        int id = itemInStorage.getId();
        
        // Проверим, что добавленный товар окажется в списке 
        List<ItemInStorage> itemsInStorage = ItemInStorageDAO.findAll();
        boolean hasItemInStorage = false;
        for(int i = 0; i < itemsInStorage.size(); i++){
            ItemInStorage localItemInStorage = itemsInStorage.get(i);
            if(localItemInStorage.getId() == id){
                hasItemInStorage = true;
                break;
            }
        }
        assertTrue(hasItemInStorage);
        
        // Удалим данный товар и склад
        itemInStorage = ItemInStorageDAO.findById(id);
        ItemInStorageDAO.delete(itemInStorage);
        StorageDAO.delete(storage);
        
    }
    
    /**
     * Проверяем, что выдача списка существующих товаров на существующем складе пройдет успешно.
     * Также проверяется, что вновь добавленный товар в этом списке окажется
     */
    @Test
    public void testFindAllByItemIdStorage() {
        
        // Построим новый склад
        Storage storage = new Storage();
        StorageDAO.create(storage);
        int storage_id = storage.getId();
        
        // Создадим товар
        ItemInStorage itemInStorage = new ItemInStorage();
        itemInStorage.setItem_id(1);
        itemInStorage.setNumber(1);
        itemInStorage.setStorage_id(storage_id);
        ItemInStorageDAO.create(itemInStorage);

        // Найдем товар
        List<ItemInStorage> itemsInStorage = ItemInStorageDAO.findAllByItemStorage(1, storage_id);
        itemInStorage = itemsInStorage.get(0);
        int id = itemInStorage.getId();
        
        // Проверим, что добавленный товар окажется в списке 
        // (пердполагается, что он будет единственным)
        itemsInStorage = ItemInStorageDAO.findAllByItemStorage(1, storage_id);
        itemInStorage = itemsInStorage.get(0);
        assertEquals(id, itemInStorage.getId());
        
        // Удалим товар и склад
        ItemInStorageDAO.delete(itemInStorage);
        StorageDAO.delete(storage);
        
    }
    
    /**
     * Проверяем, что выдача списка НЕсуществующих товаров на существующем складе вернет пустой массив.
     */
    @Test
    public void testFindAllByUnexistItemExistStorage() {
        
        // Построим новый склад
        Storage storage = new Storage();
        StorageDAO.create(storage);
        int storage_id = storage.getId();
        
        List<ItemInStorage> itemsInStorage = ItemInStorageDAO.findAllByItemStorage(1, storage_id);
        assertEquals(0, itemsInStorage.size());
        
        // Удалим склад
        StorageDAO.delete(storage);
    }
    
    /**
     * Проверяем, что выдача списка НЕсуществующих товаров на НЕсуществующем складе вернет пустой массив.
     */
    @Test
    public void testFindAllByUnexistItemUnexistStorage() {
        List<ItemInStorage> itemsInStorage = ItemInStorageDAO.findAllByItemStorage(-1, -1);
        assertEquals(0, itemsInStorage.size());
    }
        
    
}
