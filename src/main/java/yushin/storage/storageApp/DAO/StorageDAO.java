package yushin.storage.storageApp.DAO;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import yushin.storage.storageApp.entities.ItemInStorage;
import yushin.storage.storageApp.entities.Storage;
import yushin.storage.storageApp.util.HibernateSessionUtil;

public class StorageDAO {
    
    public static String create(Storage storage){
        
        if(findById(storage.getId()) == null){
            Session session = HibernateSessionUtil.instance.openSession();
            session.save(storage);
            session.close();
            return "OK";
        }
        else
            return "Create failed: item " + storage.getId() + " already exist";
        
    }
    
    public static Storage findById(int id){
        
        Session session = HibernateSessionUtil.instance.openSession();
        Storage storage = session.find(Storage.class, id);
        session.close();
        return storage;
        
    }
    
    public static String update(Storage storage){
        
        if(findById(storage.getId()) != null){
            Session session = HibernateSessionUtil.instance.openSession();
            Transaction tx = session.beginTransaction();
            session.update(storage);
            tx.commit();
            session.close();
            return "OK";
        }
        else
            return "Update failed: item " + storage.getId() + " does not exist";
        
    }
    
    public static String delete(Storage storage){
        
        if(getItems(storage).isEmpty()){
            try{
                Session session = HibernateSessionUtil.instance.openSession();
                Transaction tx = session.beginTransaction();
                session.delete(storage);
                tx.commit();
                session.close();
                return "OK";
            }
            catch(Exception e){
                return e.getMessage();
            }
        }
        else
            return "Delete failed: storage contain items";
        
    }
    
    public static List<ItemInStorage> getItems(Storage storage){
        
        Session session = HibernateSessionUtil.instance.openSession();
        List<ItemInStorage> storages = ItemInStorageDAO.findAllByStorage(storage.getId());
        session.close();
        return storages;
        
    }
    
    public static List<Storage> findAll(){
        
        Session session = HibernateSessionUtil.instance.openSession();
        List<Storage> storages = (List<Storage>) session.createQuery("From Storage").list();
        session.close();
        return storages;
        
    }
      
}
