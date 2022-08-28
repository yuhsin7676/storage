package yushin.storage.storageApp;

import org.hibernate.LockOptions;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import yushin.storage.storageApp.entities.Buy;
import yushin.storage.storageApp.entities.Item;
import yushin.storage.storageApp.entities.Move;
import yushin.storage.storageApp.entities.Sale;
import yushin.storage.storageApp.entities.Storage;
import yushin.storage.storageApp.util.HibernateSessionUtil;

public class StartApp {

    public static void main(String[] args) {
        
        //Session session = HibernateSessionUtil.instance.openSession();
        
        //Storage storage = new Storage();
        //storage.setName("234");
        //session.save(storage);
        //session.close();
        
        Session session = HibernateSessionUtil.instance.openSession();
        Storage storage = session.find(Storage.class, 9);
        storage.setName("456");
        session.update(storage);
        Storage storage2 = session.find(Storage.class, 9);
        
        Transaction tx = session.beginTransaction();
        tx.commit();
        
        session.clear();
        
        session.close();
        
        
        
        
        
        
        
        
        
        
        
        
        
        // Покупка бананов
        /*Buy buy = new Buy();
        buy.storage_id = 1;
        buy.items = "{1: 160}";
        
        // Продажа бананов
        Sale sale = new Sale();
        sale.storage_id = 2;
        sale.items = "{1: 160}";
        
        // Перемещение бананов
        Move move = new Move();
        move.storage_from = 1;
        move.storage_to = 2;
        move.items = "{1: 160}";*/
        
        //session.save(buy);
        //session.save(sale);
        //session.save(move);
        
        
    }
    
}

class A{
    public int a = 0;
    public int b = 0;
}