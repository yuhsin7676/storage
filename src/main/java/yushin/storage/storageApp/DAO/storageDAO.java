package yushin.storage.storageApp.DAO;

import java.util.List;
import org.hibernate.Session;
import yushin.storage.storageApp.entities.Storage;
import yushin.storage.storageApp.util.HibernateSessionUtil;

public class storageDAO {
    
    public void create(Storage storage){
        
        Session session = HibernateSessionUtil.instance.openSession();
        session.save(storage);
        session.close();
        
    }
    
    public Storage findById(int id){
        
        Session session = HibernateSessionUtil.instance.openSession();
        Storage storage = session.find(Storage.class, id);
        session.close();
        return storage;
        
    }
    
    public void update(Storage storage){
        
        Session session = HibernateSessionUtil.instance.openSession();
        session.update(storage);
        session.close();
        
    }
    
    public void delete(Storage storage){
        
        Session session = HibernateSessionUtil.instance.openSession();
        session.delete(storage);
        session.close();
        
    }
    
    public List<Storage> findAll(){
        
        Session session = HibernateSessionUtil.instance.openSession();
        List<Storage> storages = (List<Storage>) session.createQuery("from storage").list();
        return storages;
        
    }
      
}
