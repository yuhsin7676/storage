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
     * Проверяем, что при создании документа по продаже существующих товаров 
     * с существующего склада добавится документ в бд.
     * Также проверяются изменения в Item и ItemInStorage.
     */
    @Test
    public void testCreateWhenStorageAndItemExist() {
        
        // Добавим новый товар в номенклатуру
        Item item = new Item();
        item.setName("New item");
        ItemDAO.create(item);
        int item_id = item.getId();
        
        // Построим новый склад
        Storage storage = new Storage();
        StorageDAO.create(storage);
        int storage_id = storage.getId();
        
        // Добавим товары на этот склад
        Buy buy = new Buy();
        buy.setStorage_id(storage_id);
        buy.setItems("[{id: " + item_id + ", number: 1, price: 150 }]");
        BuyDAO.create(buy);
        
        // Проверим, что на вновь созданном складе есть данные товары
        List<ItemInStorage> itemsInStorage1 = StorageDAO.getItems(storage);
        assertEquals(1, itemsInStorage1.get(0).getNumber());
        
        // Создадим новый документ
        Sale sale = new Sale();
        sale.setStorage_id(storage_id); 
        sale.setItems("[{id: " + item_id + ", number: 1, price: 160 }]");
        SaleDAO.create(sale);
        assertFalse(sale.getId() == 0);
        
        // Проверим, что на вновь созданном складе больше нет данных товаров,
        // А в номенклатуре установилась цена продажи в 160
        assertTrue(StorageDAO.getItems(storage).isEmpty());
        item = ItemDAO.findById(item_id);
        assertEquals(160, item.getPrice_sale());
        
        // Удалим наш товар и склад
        ItemDAO.delete(item);
        StorageDAO.delete(storage);   
        
    }
    
    /**
     * Проверяем, что при создании документа по продаже НЕсуществующих товаров 
     * с существующего склада НЕ добавится документ в бд.
     * Также проверяется отсутствие изменений в Item и ItemInStorage.
     */
    @Test
    public void testCreateWhenItemUnexist() {
        
        // Добавим новый товар в номенклатуру
        Item item = new Item();
        item.setName("New item");
        item.setPrice_sale(120);
        ItemDAO.create(item);
        int item_id = item.getId();
        
        // Построим новый склад
        Storage storage = new Storage();
        StorageDAO.create(storage);
        int storage_id = storage.getId();
        
        // Проверим, что на вновь созданном складе нет товаров
        assertTrue(StorageDAO.getItems(storage).isEmpty());
        
        // Создадим новый документ (сохранение не должно пройти)
        Sale sale = new Sale();
        sale.setStorage_id(storage_id); 
        sale.setItems("[{id: " + item_id + ", number: 1, price: 160 }]");
        SaleDAO.create(sale);
        assertTrue(sale.getId() == 0);
        
        // Проверим, что изменений нет
        assertTrue(StorageDAO.getItems(storage).isEmpty());
        assertEquals(120, ItemDAO.findById(item_id).getPrice_sale());
        
        // Удалим наш товар и склад
        ItemDAO.delete(item);
        StorageDAO.delete(storage);
        
    }
    
    /**
     * Проверяем, что при создании документа по продаже товаров 
     * с НЕсуществующего склада НЕ добавится документ в бд.
     */
    @Test
    public void testCreateWhenStorageUnexist() {
        
        // Добавим новый товар в номенклатуру
        Item item = new Item();
        item.setName("New item");
        item.setPrice_sale(120);
        ItemDAO.create(item);
        int item_id = item.getId();
        
        // Создадим новый документ (сохранение не должно пройти)
        Sale sale = new Sale();
        sale.setStorage_id(-1); 
        sale.setItems("[{id: " + item_id + ", number: 3, price: 160 }]");
        SaleDAO.create(sale);
        assertTrue(sale.getId() == 0);
        
        // Проверим, что изменений нет
        assertEquals(120, ItemDAO.findById(item_id).getPrice_sale());
        
        // Удалим наш товар и склад
        ItemDAO.delete(item);
        
    }

    /**
     * Проверяем, что удаление существующего документа пройдет успешно.
     * Одновременно проверим, что повторное удаление должно выбросить исключение
     */
    @Test
    public void testDeleteExistSale() {
        
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
        
        // Создадим новый документ
        Sale sale = new Sale();
        sale.setStorage_id(1);
        sale.setItems("[{id: 1, number: 3, price: 160 }]");
        SaleDAO.create(sale);

        // Удалим документ
        SaleDAO.delete(sale);
        
        // Еще раз удалим документ
        try{
            SaleDAO.delete(sale);
            fail("Тест не пройден: повторный SaleDAO.delete() не выбросил исключение");
        }
        catch(Exception e){
            System.out.println("Ура, выкинул исключение!");  
        }
        
        // Удалим наш склад
        StorageDAO.delete(storage);
        
        
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
     * Проверяем, что создание выдача списка документов о продаже пройдет успешно.
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
        SaleDAO.create(sale);
        int id = sale.getId();
        
        // Проверим, что добавленный документ окажется в списке 
        List<Sale> sales = SaleDAO.findAll();
        boolean hasSale = false;
        for(int i = 0; i < sales.size(); i++){
            sale = sales.get(i);
            if(sale.getId() == id){
                hasSale = true;
                break;
            }
        }
        assertTrue(hasSale);
        
    }
    
}
