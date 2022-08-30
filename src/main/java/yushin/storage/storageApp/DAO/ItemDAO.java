package yushin.storage.storageApp.DAO;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import yushin.storage.storageApp.entities.Item;
import yushin.storage.storageApp.util.HibernateSessionUtil;

public class ItemDAO {
    
    public static String create(Item item){
        
        if(findById(item.getId()) == null){
            Session session = HibernateSessionUtil.instance.openSession();
            session.save(item);
            session.close();
            return "OK";
        }
        else
            return "Create failed: item " + item.getId() + " already exist";
        
    }
    
    public static Item findById(int id){
        
        Session session = HibernateSessionUtil.instance.openSession();
        Item item = session.find(Item.class, id);
        session.close();
        return item;
        
    }
    
    public static String update(Item item){
        
        if(findById(item.getId()) != null){
            Session session = HibernateSessionUtil.instance.openSession();
            Transaction tx = session.beginTransaction();
            session.update(item);
            tx.commit();
            session.close();
            return "OK";
        }
        else
            return "Update failed: item " + item.getId() + " does not exist";
        
    }
    
    public static String delete(Item item){
        
        try{
            Session session = HibernateSessionUtil.instance.openSession();
            Transaction tx = session.beginTransaction();
            session.delete(item);
            tx.commit();
            session.close();
            return "OK";
        }
        catch(Exception e){
            return e.getMessage();
        }
        
    }
    
    public static List<Item> findAll(){
        
        Session session = HibernateSessionUtil.instance.openSession();
        List<Item> items = (List<Item>) session.createQuery("from Item").list();
        session.close();
        return items;
        
    }
    
    public static List<Item> findAll(List<String> filters){
        
        Session session = HibernateSessionUtil.instance.openSession();
        
        String hql = "from Item where 1 = 2 ";
        for(int i = 0; i < filters.size(); i++)
            hql += "or name = :filter" + i + " ";
        Query query = session.createQuery(hql);
        for(int i = 0; i < filters.size(); i++)
            query.setParameter("filter" + i, filters.get(i));
        List<Item> items = (List<Item>) query.list();
        
        session.close();
        return items;
        
    }
      
}
