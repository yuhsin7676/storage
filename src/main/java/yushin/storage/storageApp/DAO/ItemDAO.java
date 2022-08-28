package yushin.storage.storageApp.DAO;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import yushin.storage.storageApp.entities.Item;
import yushin.storage.storageApp.util.HibernateSessionUtil;

public class ItemDAO {
    
    public static void create(Item item){
        
        Session session = HibernateSessionUtil.instance.openSession();
        session.save(item);
        session.close();
        
    }
    
    public static Item findById(int id){
        
        Session session = HibernateSessionUtil.instance.openSession();
        Item item = session.find(Item.class, id);
        session.close();
        return item;
        
    }
    
    public static void update(Item item){
        
        if(findById(item.getId()) != null){
            Session session = HibernateSessionUtil.instance.openSession();
            Transaction tx = session.beginTransaction();
            session.update(item);
            tx.commit();
            session.close();
        }
        
    }
    
    public static void delete(Item item){
        
        Session session = HibernateSessionUtil.instance.openSession();
        Transaction tx = session.beginTransaction();
        session.delete(item);
        tx.commit();
        session.close();
        
    }
    
    public static List<Item> findAll(){
        
        Session session = HibernateSessionUtil.instance.openSession();
        List<Item> items = (List<Item>) session.createQuery("from Item").list();
        return items;
        
    }
      
}
