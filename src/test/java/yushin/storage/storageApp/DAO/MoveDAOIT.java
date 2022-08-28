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
import yushin.storage.storageApp.entities.Item;
import yushin.storage.storageApp.entities.ItemInStorage;
import yushin.storage.storageApp.entities.Move;
import yushin.storage.storageApp.entities.Storage;

/**
 *
 * @author Ilya
 */
public class MoveDAOIT {
    
    /**
     * Проверяем, что StorageDAO.create(null) выбросит исключение.
     */
    @Test
    public void testCreateNull() {
        try{
            MoveDAO.create(null);
            fail();
        }
        catch(Exception e){
            System.out.println("Ура, выкинул исключение!");
        }
    }
    
    /**
     * 123.
     */
    @Test
    public void testCreateWhenStorageAndItemExist() {
        
        // Построим 2 новых склада
        Storage storage = new Storage();
        StorageDAO.create(storage);
        int storage_from = storage.getId();
        
        storage = new Storage();
        StorageDAO.create(storage);
        int storage_to = storage.getId();
        
        // Добавим товары на этот склад
        Buy buy = new Buy();
        buy.setStorage_id(storage_from);
        buy.setItems("[{id: 1, number: 1, price: 150}]");
        BuyDAO.create(buy);
        
        // Создадим новый документ
        Move move = new Move();
        move.setStorage_from(storage_from); 
        move.setStorage_to(storage_to);
        move.setItems("[{id: 1, number: 1}]");
        
        // И сохраним его в БД
        MoveDAO.create(move);
        assertFalse(move.getId() == 0);
        
    }
    
    /**
     * 123.
     */
    @Test
    public void testCreateWhenItemUnexist() {
        
        // Построим новый склад
        Storage storage = new Storage();
        StorageDAO.create(storage);
        int storage_id = storage.getId();
        
        // Создадим новый документ
        Move move = new Move();
        move.setStorage_from(storage_id);
        move.setStorage_to(2);
        move.setItems("[{id: 1, number: 1}]");
        
        // И сохраним его в БД
        MoveDAO.create(move);
        assertTrue(move.getId() == 0);
        
    }
    
    /**
     * 123.
     */
    @Test
    public void testCreateWhenStorageUnexist() {
        
        // Создадим новый документ
        Move move = new Move();
        move.setStorage_from(-1);
        move.setStorage_to(1); 
        move.setItems("[{id: 1, number: 3}]");
        
        // И сохраним его в БД
        MoveDAO.create(move);
        assertTrue(move.getId() == 0);
        
        move = new Move();
        move.setStorage_from(1);
        move.setStorage_to(-1); 
        move.setItems("[{id: 1, number: 1}]");
        
        // И сохраним его в БД
        MoveDAO.create(move);
        assertTrue(move.getId() == 0);
        
    }

    /**
     * Проверяем, что создание, изменение и удаление документа пройдет успешно.
     */
    @Test
    public void testAll() {
        
        // Купим товар и закинем его на 1-й склад
        Buy buy = new Buy();
        buy.setStorage_id(1);
        buy.setItems("[{id: 1, number: 3, price: 160}]");
        BuyDAO.create(buy);
        
        List<ItemInStorage> itemsInStorage = ItemInStorageDAO.findAllByItemIdStorage(1, 1);
        ItemInStorage itemInStorage;
        int number = 0;
        if(!itemsInStorage.isEmpty()){
            itemInStorage = itemsInStorage.get(0);
            number = itemInStorage.getNumber();
        }
        
        itemsInStorage = ItemInStorageDAO.findAllByItemIdStorage(1, 2);
        ItemInStorage itemInStorage2;
        int number2 = 0;
        if(!itemsInStorage.isEmpty()){
            itemInStorage2 = itemsInStorage.get(0);
            number2 = itemInStorage2.getNumber();
        } 
        
        // Создадим новый документ
        Move move = new Move();
        move.setStorage_from(1);
        move.setStorage_to(2);
        move.setItems("[{id: 1, number: 3}]");
        
        // И сохраним его в БД
        MoveDAO.create(move);

        // Найдем этот документ
        int id = move.getId();
        move = MoveDAO.findById(id);
        assertEquals(move.getId(), id);
        
        // Проверим, что убавилось 3 товара с артикулом 1 на склад 1 и добавилось стоько же на склад 2;
        itemInStorage = ItemInStorageDAO.findAllByItemIdStorage(1, 1).get(0);
        itemInStorage2 = ItemInStorageDAO.findAllByItemIdStorage(1, 2).get(0);
        assertEquals(number - 3, itemInStorage.getNumber());
        assertEquals(number2 + 3, itemInStorage2.getNumber());
        
    }
    
    /**
     * Проверяем, что Move.findById(-1) вернет null.
     */
    @Test
    public void testFindUnexistendMove() {
        Move move  = MoveDAO.findById(-1);
        assertNull(move);
    }

    /**
     * Проверяем, что создание выдача списка документов о покупке пройдет успешно.
     * Также проверяется, что вновь добавленный склад в этом списке окажется
     * Тест требует доработки!
     */
    @Test
    public void testFindAll() {
        
        // Построим новый склад
        Storage storage = new Storage();
        StorageDAO.create(storage);
        int storage_from = storage.getId();
        
        Storage storage2 = new Storage();
        StorageDAO.create(storage2);
        int storage_to = storage2.getId();
        
        Buy buy = new Buy();
        buy.setStorage_id(storage_from);
        buy.setItems("[{id: 1, number: 3, price: 160 }]");
        BuyDAO.create(buy);
        
        // Создадим новый документ
        Move move = new Move();
        move.setStorage_from(storage_from);
        move.setStorage_to(storage_to); 
        move.setItems("[{id: 1, number: 3}]");
        
        // И сохраним его в БД
        MoveDAO.create(move);
        int id = move.getId();
        
        // Проверим, что добавленный документ окажется в списке 
        // (пердполагается, что он будет в конце списка, что, вообще говоря, неверно)
        List<Move> moves = MoveDAO.findAll();
        move = moves.get(moves.size() - 1);
        assertEquals(id, move.getId());
        
    }
    
}
