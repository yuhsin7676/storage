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
     * Test of update method, of class StorageDAO.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        Storage storage = null;
        StorageDAO.update(storage);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of delete method, of class StorageDAO.
     */
    @Test
    public void testDelete() {
        System.out.println("delete");
        Storage storage = null;
        StorageDAO.delete(storage);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findAll method, of class StorageDAO.
     */
    @Test
    public void testFindAll() {
        System.out.println("findAll");
        List<Storage> expResult = null;
        List<Storage> result = StorageDAO.findAll();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
