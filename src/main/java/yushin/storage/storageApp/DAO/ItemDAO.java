package yushin.storage.storageApp.DAO;

import java.util.List;
import org.hibernate.Session;
import yushin.storage.storageApp.entities.Item;
import yushin.storage.storageApp.util.HibernateSessionUtil;

public class ItemDAO {
    
    public void create(Item item){
        
        Session session = HibernateSessionUtil.instance.openSession();
        session.save(item);
        session.close();
        
    }
    
    public Item findById(int id){
        
        Session session = HibernateSessionUtil.instance.openSession();
        Item item = session.find(Item.class, id);
        session.close();
        return item;
        
    }
    
    public void update(Item item){
        
        Session session = HibernateSessionUtil.instance.openSession();
        session.update(item);
        session.close();
        
    }
    
    public void delete(Item item){
        
        Session session = HibernateSessionUtil.instance.openSession();
        session.delete(item);
        session.close();
        
    }
    
    public List<Item> findAll(){
        
        Session session = HibernateSessionUtil.instance.openSession();
        List<Item> items = (List<Item>) session.createQuery("from item").list();
        return items;
        
    }
      
}