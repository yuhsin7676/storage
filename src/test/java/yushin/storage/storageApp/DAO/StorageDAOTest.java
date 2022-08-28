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
            
            // Попытаемся сохранить в БД несуществующий склад
            StorageDAO.create(null);
            
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
        
        // И сохраним его в БД
        StorageDAO.create(storage);

        // Найдем этот склад
        int id = storage.getId();
        storage = StorageDAO.findById(id);
        assertEquals(storage.getId(), id);
        
        // Изменим его имя
        storage.setName("Renamed storage");
        StorageDAO.update(storage);
        storage = StorageDAO.findById(id);
        assertEquals(storage.getId(), id);
        assertEquals("Renamed storage", storage.getName());
        
        // И удалим его
        StorageDAO.delete(storage);
        assertNull(StorageDAO.findById(id));
        
    }
    
    /**
     * Проверяем, что StorageDAO.findById(-1) вернет null.
     */
    @Test
    public void testFindUnexistendStorage() {

        // Попытаемся сохранить в БД несуществующий склад
        Storage storage = StorageDAO.findById(-1);
        assertNull(storage);
        
    }

    /**
     * Проверяем, что создание выдача списка складов пройдет успешно.
     * Также проверяется, что вновь добавленный склад в этом списке окажется
     * Тест требует доработки!
     */
    @Test
    public void testFindAll() {
        
        // Построим новый склад
        Storage storage = new Storage();
        storage.setName("New storage");
        
        // И сохраним его в БД
        StorageDAO.create(storage);
        int id = storage.getId();
        
        // Проверим, что добавленный склад окажется в списке 
        // (пердполагается, что он будет в конце списка, что, вообще говоря, неверно)
        List<Storage> storages = StorageDAO.findAll();
        storage = storages.get(storages.size() - 1);
        assertEquals(id, storage.getId());
        
    }
    
}
