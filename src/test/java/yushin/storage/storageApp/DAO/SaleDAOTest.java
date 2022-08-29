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
public class SaleDAOTest {
    
    /**
     * Проверяем, что StorageDAO.create(null) выбросит исключение.
     */
    @Test
    public void testCreateNull() {

        try{
            SaleDAO.create(null);
            fail();
        }
        catch(Exception e){
            System.out.println("Ура, выкинул исключение!");
        }
        
    }
    
    /**
     * Проверяем, SaleDAO.create() не рухнет при некорректных данных.
     */
    @Test
    public void testCreateBuyWithIncorrectItems() {
        
        // Построим новый склад (чтобы исключить ошибку отсутствия такового)
        Storage storage = new Storage();
        StorageDAO.create(storage);
        int storage_id = storage.getId();
        
        // И закинем туда товары
        Buy buy = new Buy();
        buy.setStorage_id(storage_id);
        buy.setItems("[{id: -1, number: 3, price: 150}]");
        BuyDAO.create(buy);
        
        // Создадим новый документ с синтаксической ошибкой в items
        Sale sale = new Sale();
        sale.setStorage_id(storage_id); 
        sale.setItems("[{id: -1, number: 3, price: 160 }");
        String result = SaleDAO.create(sale);
        assertNotEquals("OK", result);
        assertTrue(sale.getId() == 0);
        
        // Создадим новый документ со строкой вместо числа в price 
        sale.setItems("[{id: -1, number: 3, price: str }]");
        result = SaleDAO.create(sale);
        assertNotEquals("OK", result);
        assertTrue(sale.getId() == 0);
        
        // Создадим новый документ с отрицательным числом покупаемых товаров
        sale.setItems("[{id: -1, number: -3, price: 160 }]");
        result = SaleDAO.create(sale);
        assertNotEquals("OK", result);
        assertTrue(sale.getId() == 0);
        
        // Создадим новый документ с абракадаброй
        sale.setItems("qwer3rfsdf");
        result = SaleDAO.create(sale);
        assertNotEquals("OK", result);
        assertTrue(sale.getId() == 0);
        
        // Удалим доки, товары и склад
        BuyDAO.delete(buy);
        ItemInStorageDAO.delete(ItemInStorageDAO.findAllByItemStorage(-1, storage_id).get(0));
        StorageDAO.delete(storage);
        
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
        String result = SaleDAO.create(sale);
        assertEquals("OK", result);
        assertFalse(sale.getId() == 0);
        
        // Проверим, что на вновь созданном складе больше нет данных товаров,
        // А в номенклатуре установилась цена продажи в 160
        assertTrue(StorageDAO.getItems(storage).isEmpty());
        item = ItemDAO.findById(item_id);
        assertEquals(160, item.getPrice_sale());
        
        // Удалим наш документы, товар и склад
        BuyDAO.delete(buy);
        result = SaleDAO.delete(sale);
        assertEquals("OK", result);
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
        String result = SaleDAO.create(sale);
        assertNotEquals("OK", result);
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
        String result = SaleDAO.create(sale);
        assertNotEquals("OK", result);
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
        buy.setItems("[{id: -1, number: 3, price: 150 }]");
        BuyDAO.create(buy);
        
        // Создадим новый документ
        Sale sale = new Sale();
        sale.setStorage_id(storage_id);
        sale.setItems("[{id: -1, number: 3, price: 160 }]");
        String result = SaleDAO.create(sale);
        assertEquals("OK", result);

        // Удалим документ
        result = SaleDAO.delete(sale);
        assertEquals("OK", result);
        
        // Еще раз удалим документ
        result = SaleDAO.delete(sale);
        assertNotEquals("OK", result);
        
        // Удалим документ о покупке и склад
        BuyDAO.delete(buy);
        StorageDAO.delete(storage);
        
        
    }
    
    /**
     * Проверяем, что Sale.findById(-1) вернет null.
     */
    @Test
    public void testFindUnexistendSale() {
        assertNull(SaleDAO.findById(-1));
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
        buy.setItems("[{id: -1, number: 3, price: 150 }]");
        BuyDAO.create(buy);
        
        // Создадим новый документ
        Sale sale = new Sale();
        sale.setStorage_id(storage_id); 
        sale.setItems("[{id: -1, number: 3, price: 160 }]");
        String result = SaleDAO.create(sale);
        assertEquals("OK", result);
        int id = sale.getId();
        
        // Проверим, что добавленный документ окажется в списке 
        List<Sale> sales = SaleDAO.findAll();
        boolean hasSale = false;
        for(int i = 0; i < sales.size(); i++){
            Sale localSale = sales.get(i);
            if(localSale.getId() == id){
                hasSale = true;
                break;
            }
        }
        assertTrue(hasSale);
        
        // Удалим доки
        result = SaleDAO.delete(sale);
        assertEquals("OK", result);
        BuyDAO.delete(buy);
        StorageDAO.delete(storage);
        
    }
    
}
