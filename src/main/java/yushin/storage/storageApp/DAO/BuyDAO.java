package yushin.storage.storageApp.DAO;

import java.util.List;
import org.hibernate.Session;
import yushin.storage.storageApp.entities.Buy;
import yushin.storage.storageApp.entities.Storage;
import yushin.storage.storageApp.util.HibernateSessionUtil;

public class BuyDAO {
    
    public static void create(Buy buy){
        
        Session session = HibernateSessionUtil.instance.openSession();
        
        Storage storage = StorageDAO.findById(buy.storage_id);
        
        
        session.find(Storage.class, buy);
        
        session.save(buy);
        //session.
        session.close();
        
    }
    
    public static Buy findById(int id){
        
        Session session = HibernateSessionUtil.instance.openSession();
        Buy buy = session.find(Buy.class, id);
        session.close();
        return buy;
        
    }
    
    public static void update(Buy buy){
        
        Session session = HibernateSessionUtil.instance.openSession();
        session.update(buy);
        session.close();
        
    }
    
    public static void delete(Buy buy){
        
        Session session = HibernateSessionUtil.instance.openSession();
        session.delete(buy);
        session.close();
        
    }
    
    public List<Buy> findAll(){
        
        Session session = HibernateSessionUtil.instance.openSession();
        List<Buy> buys = (List<Buy>) session.createQuery("from buy").list();
        return buys;
        
    }
      
}
