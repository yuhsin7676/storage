package yushin.storage.storageApp.DAO;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import yushin.storage.storageApp.entities.Storage;
import yushin.storage.storageApp.util.HibernateSessionUtil;

public class StorageDAO {
    
    public static void create(Storage storage){
        
        Session session = HibernateSessionUtil.instance.openSession();
        session.save(storage);
        session.close();
        
    }
    
    public static Storage findById(int id){
        
        Session session = HibernateSessionUtil.instance.openSession();
        Storage storage = session.find(Storage.class, id);
        session.close();
        return storage;
        
    }
    
    public static void update(Storage storage){
        
        Session session = HibernateSessionUtil.instance.openSession();
        Transaction tx = session.beginTransaction();
        session.update(storage);
        tx.commit();
        session.close();
        
    }
    
    public static void delete(Storage storage){
        
        Session session = HibernateSessionUtil.instance.openSession();
        Transaction tx = session.beginTransaction();
        session.delete(storage);
        tx.commit();
        session.close();
        
    }
    
    public static List<Storage> findAll(){
        
        Session session = HibernateSessionUtil.instance.openSession();
        List<Storage> storages = (List<Storage>) session.createQuery("from storage").list();
        session.close();
        return storages;
        
    }
      
}
