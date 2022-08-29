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
import yushin.storage.storageApp.entities.Buy;
import yushin.storage.storageApp.entities.ItemInStorage;
import yushin.storage.storageApp.entities.Storage;

/**
 *
 * @author ilya
 */
public class StorageDAOTest {
    
    /**
     * Проверяем, что StorageDAO.create(null) выбросит исключение.
     */
    @Test
    public void testCreateNull() {

        try{
            StorageDAO.create(null);
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
    public void testUpdateUnexistStorage(){
        
        StorageDAO.update(new Storage());
        
    }

    /**
     * Проверяем, что создание, изменение и удаление склада пройдет успешно.
     */
    @Test
    public void testAll() {
        
        // Построим новый склад
        Storage storage = new Storage();
        storage.setName("New storage");
        String result = StorageDAO.create(storage);
        assertEquals("OK", result);

        // Найдем этот склад
        int id = storage.getId();
        storage = StorageDAO.findById(id);
        assertEquals(storage.getId(), id);
        
        // Изменим его имя
        storage.setName("Renamed storage");
        result = StorageDAO.update(storage);
        assertEquals("OK", result);
        storage = StorageDAO.findById(id);
        assertEquals(storage.getId(), id);
        assertEquals("Renamed storage", storage.getName());
        
        // И удалим его
        result = StorageDAO.delete(storage);
        assertEquals("OK", result);
        assertNull(StorageDAO.findById(id));
        
    }
    
    /**
     * Проверяем, что удаление склада, на котором есть товары завершится неудачей.
     */
    @Test
    public void testStorageWithItems() {
        
        // Построим новый склад
        Storage storage = new Storage();
        storage.setName("New storage");
        String result = StorageDAO.create(storage);
        assertEquals("OK", result);
        int id = storage.getId();
        
        // Купим на этот склад товары
        Buy buy = new Buy();
        buy.setStorage_id(id);
        buy.setItems("[{id: 1, number: 3, price: 160 }]");
        BuyDAO.create(buy);
        
        // И удалим его (не должен удалиться)
        result = StorageDAO.delete(storage);
        assertNotEquals("OK", result);
        assertNotNull(StorageDAO.findById(id));
        
        // Удалим наши доки, товары и склады
        BuyDAO.delete(buy);
        ItemInStorage itemInStorage = ItemInStorageDAO.findAllByItemStorage(1, id).get(0);
        ItemInStorageDAO.delete(itemInStorage);
        result = StorageDAO.delete(storage);
        assertEquals("OK", result);
        
    }
    
    /**
     * Проверяем, что StorageDAO.findById(-1) вернет null.
     */
    @Test
    public void testFindUnexistendStorage() {
        assertNull(StorageDAO.findById(-1));
    }

    /**
     * Проверяем, что выдача списка складов пройдет успешно.
     * Также проверяется, что вновь добавленный склад в этом списке окажется
     */
    @Test
    public void testFindAll() {
        
        // Построим новый склад
        Storage storage = new Storage();
        storage.setName("New storage");
        
        // И сохраним его в БД
        String result = StorageDAO.create(storage);
        assertEquals("OK", result);
        int id = storage.getId();
        
        // Проверим, что добавленный документ окажется в списке 
        List<Storage> storages = StorageDAO.findAll();
        boolean hasStorage = false;
        for(int i = 0; i < storages.size(); i++){
            Storage localStorage = storages.get(i);
            if(localStorage.getId() == id){
                hasStorage = true;
                break;
            }
        }
        assertTrue(hasStorage);
        
        // Удалим данный склад
        result = StorageDAO.delete(storage);
        assertEquals("OK", result);
        
    }
    
}
