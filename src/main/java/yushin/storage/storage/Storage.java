package yushin.storage.storage;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import yushin.storage.storage.entities.Buy;
import yushin.storage.storage.entities.Item;
import yushin.storage.storage.entities.Move;
import yushin.storage.storage.entities.Sale;

public class Storage {

    public static void main(String[] args) {
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        configuration.addAnnotatedClass(Buy.class);
        configuration.addAnnotatedClass(Item.class);
        configuration.addAnnotatedClass(Move.class);
        configuration.addAnnotatedClass(Sale.class);
        configuration.addAnnotatedClass(Storage.class);
        
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        
        Item item = new Item();
        item.article = 1;
        item.name = "Banana";
        item.price_buy = 100;
        item.price_sale = 120;
        session.save(item);
        session.close();
        
    }
    
}
