package yushin.storage.storageApp;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import yushin.storage.storageApp.entities.Buy;
import yushin.storage.storageApp.entities.Item;
import yushin.storage.storageApp.entities.Move;
import yushin.storage.storageApp.entities.Sale;
import yushin.storage.storageApp.entities.Storage;

public class StartApp {

    public static void main(String[] args) {
        int[] a = new int[]{0};
        
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        configuration.addAnnotatedClass(Buy.class);
        configuration.addAnnotatedClass(Item.class);
        configuration.addAnnotatedClass(Move.class);
        configuration.addAnnotatedClass(Sale.class);
        configuration.addAnnotatedClass(Storage.class);
        
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        
        // Покупка бананов
        Buy buy = new Buy();
        buy.storage_id = 2;
        buy.items = "{1: 140}";
        
        // Продажа бананов
        Sale sale = new Sale();
        sale.storage_id = 1;
        sale.items = "{1: 140}";
        
        // Перемещение бананов
        Move move = new Move();
        move.storage_from = 1;
        move.storage_to = 2;
        move.items = "{1: 140}";
        
        session.save(buy);
        session.save(sale);
        session.save(move);
        session.close();
        
    }
    
}
