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
import yushin.storage.storageApp.entities.Sale;
import yushin.storage.storageApp.entities.Storage;

/**
 *
 * @author Ilya
 */
public class BuyDAOTest {

    /**
     * Проверяем, что BuyDAO.create(null) выбросит исключение.
     */
    @Test
    public void testCreateNull() {
        try{
            BuyDAO.create(null);
            fail();
        }
        catch(Exception e){
            System.out.println("Ура, выкинул исключение!");
        }
    }
    
    /**
     * Проверяем, что при создании документа по покупке товаров 
     * на существующий склад добавится документ в бд.
     * Также проверяются изменения в Item и ItemInStorage.
     */
    @Test
    public void testCreateWhenStorageExist() {
        
        // Добавим 2 новых товара в номенклатуру
        Item item1 = new Item();
        item1.setName("New item");
        ItemDAO.create(item1);
        int item_id1 = item1.getId();
        
        Item item2 = new Item();
        item2.setName("New item");
        ItemDAO.create(item2);
        int item_id2 = item2.getId();
        
        // Построим новый склад
        Storage storage = new Storage();
        StorageDAO.create(storage);
        int storage_id = storage.getId();
        
        // Создадим новый документ
        Buy buy = new Buy();
        buy.setStorage_id(storage_id); 
        buy.setItems("["
                + "{id: " + item_id1 + ", number: 3, price: 160 },"
                + "{id: " + item_id2 + ", number: 3, price: 170 }"
                + "]");
        BuyDAO.create(buy);
        assertFalse(buy.getId() == 0);
        
        // Проверим, что на вновь созданном складе есть данные товары, а в номенклатуре изменились цены
        ItemInStorage itemInStorage1 = ItemInStorageDAO.findAllByItemStorage(item_id1, storage_id).get(0);
        ItemInStorage itemInStorage2 = ItemInStorageDAO.findAllByItemStorage(item_id2, storage_id).get(0);
        assertEquals(3, itemInStorage1.getNumber());
        assertEquals(3, itemInStorage2.getNumber());
        assertEquals(160, ItemDAO.findById(item_id1).getPrice_buy());
        assertEquals(170, ItemDAO.findById(item_id2).getPrice_buy());
        
        // Удалим наши товары со склада (иначе склад не удалится)
        ItemInStorageDAO.delete(itemInStorage1);
        ItemInStorageDAO.delete(itemInStorage2);
        
        // Удалим наши товары и склад
        ItemDAO.delete(item1);
        ItemDAO.delete(item2);
        StorageDAO.delete(storage);
        
        // Удалим документ
        BuyDAO.delete(buy);
        
    }
    
    /**
     * Проверяем, что при создании документа по покупке товаров 
     * на НЕсуществующий склад НЕ добавится документ в бд.
     * Также проверяются изменения в Item и ItemInStorage.
     */
    @Test
    public void testCreateWhenStorageUnexist() {
        
        // Добавим новый товар в номенклатуру
        Item item = new Item();
        item.setName("New item");
        item.setPrice_buy(120);
        ItemDAO.create(item);
        int item_id = item.getId();
        
        // Создадим новый документ (не должен создаться!)
        Buy buy = new Buy();
        buy.setStorage_id(-1); 
        buy.setItems("[{id: " + item_id + ", number: 3, price: 160 }]");
        BuyDAO.create(buy);
        assertTrue(buy.getId() == 0);
        
        // Проверим отсутствие изменений в Item и ItemInStorage
        assertTrue(ItemInStorageDAO.findAllByItemStorage(1, -1).isEmpty());
        assertEquals(120, ItemDAO.findById(item_id).getPrice_buy());
        
        // Удалим товар из номенклатуры
        ItemDAO.delete(item);
        
    }

    /**
     * Проверяем, что создание и удаление документа пройдет успешно.
     * Одновременно проверим, что повторное удаление должно выбросить исключение
     */
    @Test
    public void testDoubleDelete() {
        
        // Построим новый склад
        Storage storage = new Storage();
        StorageDAO.create(storage);
        int storage_id = storage.getId();
        
        // Купим товар и закинем его на новый склад 
        // На самом деле нам все равно, имеется ли товар в номенклатуре или нет,
        // в тестах выше мы создавали товар в номенклатуре исключительно для проверки изменений цены
        Buy buy = new Buy();
        buy.setStorage_id(storage_id);
        buy.setItems("[{id: 1, number: 3, price: 150 }]");
        BuyDAO.create(buy);
        
        // Удалим документ
        BuyDAO.delete(buy);
        
        // Еще раз удалим документ
        try{
            BuyDAO.delete(buy);
            fail("Тест не пройден: повторный BuyDAO.delete() не выбросил исключение");
        }
        catch(Exception e){
            System.out.println("Ура, выкинул исключение!");  
        }
        
        // Удалим наши товары и склад
        ItemInStorage itemInStorage = ItemInStorageDAO.findAllByItemStorage(1, storage_id).get(0);
        ItemInStorageDAO.delete(itemInStorage);
        StorageDAO.delete(storage);
        
    }
    
    /**
     * Проверяем, что Buy.findById(-1) вернет null.
     */
    @Test
    public void testFindUnexistendBuy() {
        Buy buy = BuyDAO.findById(-1);
        assertNull(buy);
    }

    /**
     * Проверяем, что выдача списка документов о покупке пройдет успешно.
     * Также проверяется наличие вновь добавленного документ в этом списке
     */
    @Test
    public void testFindAll() {
        
        // Построим новый склад
        Storage storage = new Storage();
        StorageDAO.create(storage);
        int storage_id = storage.getId();
        
        // Создадим новый документ
        Buy buy = new Buy();
        buy.setStorage_id(storage_id); 
        buy.setItems("[{id: 1, number: 3, price: 160 }]");
        BuyDAO.create(buy);
        int id = buy.getId();
        
        // Проверим, что добавленный документ окажется в списке 
        List<Buy> buys = BuyDAO.findAll();
        boolean hasBuy = false;
        for(int i = 0; i < buys.size(); i++){
            Buy localBuy = buys.get(i);
            if(localBuy.getId() == id){
                hasBuy = true;
                break;
            }
        }
        assertTrue(hasBuy);
        
        // Удалим наши товары и склад
        ItemInStorage itemInStorage = ItemInStorageDAO.findAllByItemStorage(1, storage_id).get(0);
        ItemInStorageDAO.delete(itemInStorage);
        StorageDAO.delete(storage);
        
        // Удалим документ
        BuyDAO.delete(buy);
        
    }
    
}
