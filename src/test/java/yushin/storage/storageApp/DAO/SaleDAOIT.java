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
public class SaleDAOIT {
    
    /**
     * Проверяем, что StorageDAO.create(null) выбросит исключение.
     */
    @Test
    public void testCreateNull() {

        try{
            
            // Попытаемся сохранить в БД несуществующий документ
            SaleDAO.create(null);
            
            // Если прожует null, то тест не пройден
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
        
        // Построим новый склад
        Storage storage = new Storage();
        StorageDAO.create(storage);
        int storage_id = storage.getId();
        
        // Добавим товары на этот склад
        Buy buy = new Buy();
        buy.setStorage_id(storage_id);
        buy.setItems("[{id: 1, number: 1, price: 150 }]");
        BuyDAO.create(buy);
        
        // Создадим новый документ
        Sale sale = new Sale();
        sale.setStorage_id(storage_id); 
        sale.setItems("[{id: 1, number: 1, price: 160 }]");
        
        // И сохраним его в БД
        SaleDAO.create(sale);
        assertFalse(sale.getId() == 0);
        
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
        Sale sale = new Sale();
        sale.setStorage_id(storage_id); 
        sale.setItems("[{id: 1, number: 1, price: 160 }]");
        
        // И сохраним его в БД
        SaleDAO.create(sale);
        assertTrue(sale.getId() == 0);
        
    }
    
    /**
     * 123.
     */
    @Test
    public void testCreateWhenStorageUnexist() {
        
        // Создадим новый документ
        Sale sale = new Sale();
        sale.setStorage_id(-1); 
        sale.setItems("[{id: 1, number: 3, price: 160 }]");
        
        // И сохраним его в БД
        SaleDAO.create(sale);
        assertTrue(sale.getId() == 0);
        
    }

    /**
     * Проверяем, что создание, изменение и удаление документа пройдет успешно.
     */
    @Test
    public void testAll() {
        
        // Купим товар и закинем его на 1-й склад
        Buy buy = new Buy();
        buy.setStorage_id(1);
        buy.setItems("[{id: 1, number: 3, price: 150 }]");
        BuyDAO.create(buy);
        
        List<ItemInStorage> itemsInStorage = ItemInStorageDAO.findAllByItemIdStorage(1, 1);
        ItemInStorage itemInStorage;
        int number = 0;
        if(!itemsInStorage.isEmpty()){
            itemInStorage = itemsInStorage.get(0);
            number = itemInStorage.getNumber();
        } 
        
        // Создадим новый документ
        Sale sale = new Sale();
        sale.setStorage_id(1);
        sale.setItems("[{id: 1, number: 3, price: 160 }]");
        
        // И сохраним его в БД
        SaleDAO.create(sale);

        // Найдем этот документ
        int id = sale.getId();
        sale = SaleDAO.findById(id);
        assertEquals(sale.getId(), id);
        
        // Проверим, что убавилось 3 товара с артикулом 1 на склад 1;
        itemInStorage = ItemInStorageDAO.findAllByItemIdStorage(1, 1).get(0);
        assertEquals(number - 3, itemInStorage.getNumber());
        
        // Найдем товар с артикулом 1
        Item item = ItemDAO.findById(1);
        assertEquals(160, item.getPrice_sale());
        
    }
    
    /**
     * Проверяем, что Sale.findById(-1) вернет null.
     */
    @Test
    public void testFindUnexistendSale() {
        Sale sale  = SaleDAO.findById(-1);
        assertNull(sale);
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
        int storage_id = storage.getId();
        
        // Купим товар и закинем его на storage_id-й склад
        Buy buy = new Buy();
        buy.setStorage_id(storage_id);
        buy.setItems("[{id: 1, number: 3, price: 150 }]");
        BuyDAO.create(buy);
        
        // Создадим новый документ
        Sale sale = new Sale();
        sale.setStorage_id(storage_id); 
        sale.setItems("[{id: 1, number: 3, price: 160 }]");
        
        // И сохраним его в БД
        SaleDAO.create(sale);
        int id = sale.getId();
        
        // Проверим, что добавленный документ окажется в списке 
        // (пердполагается, что он будет в конце списка, что, вообще говоря, неверно)
        List<Sale> sales = SaleDAO.findAll();
        sale = sales.get(sales.size() - 1);
        assertEquals(id, sale.getId());
        
    }
    
}
