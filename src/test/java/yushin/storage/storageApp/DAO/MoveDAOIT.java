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
     * Проверяем, что при создании документа по перемещению существующих товаров 
     * между существующими складами добавится документ в бд.
     * Также проверяются изменения в ItemInStorage.
     */
    @Test
    public void testCreateWhenStorageAndItemExist() {
        
        // Построим 2 новых склада
        Storage storage_1 = new Storage();
        StorageDAO.create(storage_1);
        int storage_from = storage_1.getId();
        
        Storage storage_2 = new Storage();
        StorageDAO.create(storage_2);
        int storage_to = storage_2.getId();
        
        // Добавим товары на первый склад
        Buy buy = new Buy();
        buy.setStorage_id(storage_from);
        buy.setItems("["
                + "{id: 1, number: 1, price: 150},"
                + "{id: 2, number: 1, price: 150}"
                + "]");
        BuyDAO.create(buy);
        
        // Проверим, что на 1-м вновь созданном складе есть данные товары, а на 2-м - нет
        List<ItemInStorage> itemsInStorage1 = StorageDAO.getItems(storage_1);
        List<ItemInStorage> itemsInStorage2 = StorageDAO.getItems(storage_2);
        assertEquals(1, itemsInStorage1.get(0).getNumber());
        assertEquals(1, itemsInStorage1.get(1).getNumber());
        assertTrue(itemsInStorage2.isEmpty());
        
        // Создадим новый документ
        Move move = new Move();
        move.setStorage_from(storage_from); 
        move.setStorage_to(storage_to);
        move.setItems("[{id: 1, number: 1}, "
                + "{id: 2, number: 1}"
                + "]");
        
        // И сохраним его в БД
        MoveDAO.create(move);
        assertFalse(move.getId() == 0);
        
        // Проверим, что на 1-м вновь созданном складе нет товаров, а на 2-м - есть
        itemsInStorage1 = StorageDAO.getItems(storage_1);
        itemsInStorage2 = StorageDAO.getItems(storage_2);
        assertEquals(1, itemsInStorage2.get(0).getNumber());
        assertEquals(1, itemsInStorage2.get(1).getNumber());
        assertTrue(itemsInStorage1.isEmpty());
        
        // Удалим наши товары и склады
        ItemInStorageDAO.delete(ItemInStorageDAO.findAllByItemStorage(1, storage_to).get(0));
        ItemInStorageDAO.delete(ItemInStorageDAO.findAllByItemStorage(2, storage_to).get(0));
        StorageDAO.delete(storage_1);
        StorageDAO.delete(storage_2);
        
    }
    
    /**
     * Проверяем, что при создании документа по перемещению товаров, 
     * один из которых НЕ существует, 
     * между существующими складами НЕ добавится документ в бд.
     * Также проверяется отсутствие изменений в ItemInStorage
     */
    @Test
    public void testCreateWhenItemUnexist() {
        
        // Построим 2 новых склада
        Storage storage_1 = new Storage();
        StorageDAO.create(storage_1);
        int storage_from = storage_1.getId();
        
        Storage storage_2 = new Storage();
        StorageDAO.create(storage_2);
        int storage_to = storage_2.getId();
        
        // Добавим товары на первый склад
        Buy buy = new Buy();
        buy.setStorage_id(storage_from);
        buy.setItems("[{id: 1, number: 1, price: 150}]");
        BuyDAO.create(buy);
        
        // Проверим, что на 1-м вновь созданном складе есть 1-й товар и нет 2-го, а на 2-м - нет
        List<ItemInStorage> itemsInStorage1 = StorageDAO.getItems(storage_1);
        List<ItemInStorage> itemsInStorage2 = StorageDAO.getItems(storage_2);
        assertEquals(1, itemsInStorage1.size());
        assertEquals(1, itemsInStorage1.get(0).getNumber());
        assertTrue(itemsInStorage2.isEmpty());
        
        // Создадим новый документ
        Move move = new Move();
        move.setStorage_from(storage_from);
        move.setStorage_to(storage_to);
        move.setItems("[{id: 1, number: 1}]");
        move.setItems("[{id: 2, number: 1}]");
        
        // И сохраним его в БД (сохранение не должно удасться)
        MoveDAO.create(move);
        assertTrue(move.getId() == 0);
        
        // Проверим, что в ItemInStorage нет изменений
        itemsInStorage1 = StorageDAO.getItems(storage_1);
        itemsInStorage2 = StorageDAO.getItems(storage_2);
        assertEquals(1, itemsInStorage1.size());
        assertEquals(1, itemsInStorage1.get(0).getNumber());
        assertTrue(itemsInStorage2.isEmpty());
        
        // Удалим наши склады
        StorageDAO.delete(storage_1);
        StorageDAO.delete(storage_2);
        
    }
    
    /**
     * Проверяем, что при создании документа по перемещению существующих товаров
     * между складами, один из которых НЕ существует, НЕ добавится документ в бд.
     * Также проверяется отсутствие изменений в ItemInStorage
     */
    @Test
    public void testCreateWhenStorageUnexist() {
        
        // Построим новый склад
        Storage storage_1 = new Storage();
        StorageDAO.create(storage_1);
        int storage_id = storage_1.getId();
        
        // Добавим товары на первый склад
        Buy buy = new Buy();
        buy.setStorage_id(storage_id);
        buy.setItems("[{id: 1, number: 1, price: 150}]");
        BuyDAO.create(buy);
        
        // Проверим, что на вновь созданном складе есть 1-й товар
        List<ItemInStorage> itemsInStorage1 = StorageDAO.getItems(storage_1);
        assertEquals(1, itemsInStorage1.size());
        assertEquals(1, itemsInStorage1.get(0).getNumber());
        
        // Создадим новый документ
        Move move = new Move();
        move.setStorage_from(-1);
        move.setStorage_to(storage_id); 
        move.setItems("[{id: 1, number: 1}]");
        MoveDAO.create(move);
        assertTrue(move.getId() == 0);
        
        // Проверим, что ничего не изменилось
        itemsInStorage1 = StorageDAO.getItems(storage_1);
        assertEquals(1, itemsInStorage1.size());
        assertEquals(1, itemsInStorage1.get(0).getNumber());
        
        move = new Move();
        move.setStorage_from(storage_id);
        move.setStorage_to(-1); 
        move.setItems("[{id: 1, number: 1}]");
        MoveDAO.create(move);
        assertTrue(move.getId() == 0);
        
        // Проверим, что ничего не изменилось
        itemsInStorage1 = StorageDAO.getItems(storage_1);
        assertEquals(1, itemsInStorage1.size());
        assertEquals(1, itemsInStorage1.get(0).getNumber());
        
    }

    /**
     * Проверяем, что удаление существующего документа пройдет успешно.
     * Одновременно проверим, что повторное удаление должно выбросить исключение
     */
    @Test
    public void testDeleteExistMove() {
        
        // Построим 2 новых склада
        Storage storage_1 = new Storage();
        StorageDAO.create(storage_1);
        int storage_from = storage_1.getId();
        
        Storage storage_2 = new Storage();
        StorageDAO.create(storage_2);
        int storage_to = storage_2.getId();
        
        // Закупим 3 товара на 1-й склад
        Buy buy = new Buy();
        buy.setStorage_id(storage_from);
        buy.setItems("[{id: 1, number: 3, price: 160}]");
        BuyDAO.create(buy);
        
        // Создадим новый документ
        Move move = new Move();
        move.setStorage_from(storage_from);
        move.setStorage_to(storage_to);
        move.setItems("[{id: 1, number: 3}]");
        MoveDAO.create(move);
        
        // Удалим документ
        MoveDAO.delete(move);
        
        // Еще раз удалим документ
        try{
            MoveDAO.delete(move);
            fail("Тест не пройден: повторный MoveDAO.delete() не выбросил исключение");
        }
        catch(Exception e){
            System.out.println("Ура, выкинул исключение!");  
        }
        
        // Удалим наши товары и склады
        ItemInStorageDAO.delete(ItemInStorageDAO.findAllByItemStorage(1, storage_to).get(0));
        StorageDAO.delete(storage_1);
        StorageDAO.delete(storage_2);
        
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
     * Проверяем, что выдача списка документов о перемещении пройдет успешно.
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
        List<Move> moves = MoveDAO.findAll();
        boolean hasMove = false;
        for(int i = 0; i < moves.size(); i++){
            move = moves.get(i);
            if(move.getId() == id){
                hasMove = true;
                break;
            }
        }
        assertTrue(hasMove);
        
    }
    
}
