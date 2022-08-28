package yushin.storage.storageApp.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import yushin.storage.storageApp.entities.Buy;
import yushin.storage.storageApp.entities.Item;
import yushin.storage.storageApp.entities.ItemInStorage;
import yushin.storage.storageApp.entities.Move;
import yushin.storage.storageApp.entities.Sale;
import yushin.storage.storageApp.entities.Storage;



public class HibernateSessionUtil {
    
    public static final HibernateSessionUtil instance = new HibernateSessionUtil();
    private SessionFactory sessionFactory;
    
    private HibernateSessionUtil(){
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        configuration.addAnnotatedClass(Buy.class);
        configuration.addAnnotatedClass(Item.class);
        configuration.addAnnotatedClass(ItemInStorage.class);
        configuration.addAnnotatedClass(Move.class);
        configuration.addAnnotatedClass(Sale.class);
        configuration.addAnnotatedClass(Storage.class);
        this.sessionFactory = configuration.buildSessionFactory();
    }
    
    public Session openSession(){
        return this.sessionFactory.openSession();
    }
    
}
