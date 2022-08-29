package yushin.storage.storageApp.DAO;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import yushin.storage.storageApp.entities.ItemInStorage;
import yushin.storage.storageApp.util.HibernateSessionUtil;

public class ItemInStorageDAO {
    
    public static String create(ItemInStorage itemInStorage){
        
        if(StorageDAO.findById(itemInStorage.getStorage_id()) == null)
            return "Create failed: storage " + itemInStorage.getStorage_id() + " does not exist";
        if(itemInStorage.getNumber() <= 0)
            return "Create failed: number in itemInStorage must be positive";
        
        List<ItemInStorage> itemsInStorage = findAllByItemStorage(itemInStorage.getItem_id(), itemInStorage.getStorage_id());
        if (itemsInStorage.isEmpty()){
            Session session = HibernateSessionUtil.instance.openSession();
            session.save(itemInStorage);
            session.close();
            return "OK";
        }
        else
            return "Create failed: itemStorage with item " + itemInStorage.getItem_id() + " and storage " + itemInStorage.getStorage_id() + " does not exist";
        
    }
    
    public static ItemInStorage findById(int id){
        
        Session session = HibernateSessionUtil.instance.openSession();
        ItemInStorage item = session.find(ItemInStorage.class, id);
        session.close();
        return item;
        
    }
    
    public static String update(ItemInStorage itemInStorage){
        
        if(StorageDAO.findById(itemInStorage.getStorage_id()) == null)
            return "Update failed: storage " + itemInStorage.getStorage_id() + " does not exist ";
        if(itemInStorage.getNumber() <= 0)
            return "Update failed: number in itemInStorage must be positive";
        
        if(findById(itemInStorage.getId()) != null){
            Session session = HibernateSessionUtil.instance.openSession();
            Transaction tx = session.beginTransaction();
            session.update(itemInStorage);
            tx.commit();
            session.close();
            return "OK";
        }
        else
            return "Update failed: itemInStorage " + itemInStorage.getId() + " does not exist ";
        
    }
    
    public static String delete(ItemInStorage itemInStorage){
        
        try{
            Session session = HibernateSessionUtil.instance.openSession();
            Transaction tx = session.beginTransaction();
            session.delete(itemInStorage);
            tx.commit();
            session.close();
            return "OK";
        }
        catch(Exception e){
            return e.getMessage();
        }
        
    }
    
    public static List<ItemInStorage> findAll(){
        
        Session session = HibernateSessionUtil.instance.openSession();
        List<ItemInStorage> itemsInStorage = (List<ItemInStorage>) session.createQuery("from ItemInStorage").list();
        return itemsInStorage;
        
    }
    
    public static List<ItemInStorage> findAllByItemStorage(int item_id, int storage_id){
        
        Session session = HibernateSessionUtil.instance.openSession();
        List<ItemInStorage> itemsInStorage = (List<ItemInStorage>) session.createQuery("from ItemInStorage where item_id = " + item_id + " and storage_id = " + storage_id).list();
        session.close();
        return itemsInStorage;
        
    }
    
    public static List<ItemInStorage> findAllByStorage(int storage_id){
        
        Session session = HibernateSessionUtil.instance.openSession();
        List<ItemInStorage> itemsInStorage = (List<ItemInStorage>) session.createQuery("from ItemInStorage where storage_id = " + storage_id).list();
        session.close();
        return itemsInStorage;
        
    }
}
