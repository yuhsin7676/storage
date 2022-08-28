package yushin.storage.storageApp.DAO;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import yushin.storage.storageApp.entities.ItemInStorage;
import yushin.storage.storageApp.util.HibernateSessionUtil;

public class ItemInStorageDAO {
    
    public static void create(ItemInStorage itemInStorage){
        
        List<ItemInStorage> itemsInStorage = findAllByItemIdStorage(itemInStorage.getItem_id(), itemInStorage.getStorage_id());
        if (!(itemsInStorage.size() > 0)){
            Session session = HibernateSessionUtil.instance.openSession();
            session.save(itemInStorage);
            session.close();
        }
        
    }
    
    public static ItemInStorage findById(int id){
        
        Session session = HibernateSessionUtil.instance.openSession();
        ItemInStorage item = session.find(ItemInStorage.class, id);
        session.close();
        return item;
        
    }
    
    public static void update(ItemInStorage itemInStorage){
        
        if(findById(itemInStorage.getId()) != null){
            Session session = HibernateSessionUtil.instance.openSession();
            Transaction tx = session.beginTransaction();
            session.update(itemInStorage);
            tx.commit();
            session.close();
        }
        
    }
    
    public static void delete(ItemInStorage itemInStorage){
        
        Session session = HibernateSessionUtil.instance.openSession();
        Transaction tx = session.beginTransaction();
        session.delete(itemInStorage);
        tx.commit();
        session.close();
        
    }
    
    public static List<ItemInStorage> findAll(){
        
        Session session = HibernateSessionUtil.instance.openSession();
        List<ItemInStorage> itemsInStorage = (List<ItemInStorage>) session.createQuery("from ItemInStorage").list();
        return itemsInStorage;
        
    }
    
    public static List<ItemInStorage> findAllByItemIdStorage(int item_id, int storage_id){
        
        Session session = HibernateSessionUtil.instance.openSession();
        List<ItemInStorage> itemsInStorage = (List<ItemInStorage>) session.createQuery("from ItemInStorage where item_id = " + item_id + " and storage_id = " + storage_id).list();
        session.close();
        return itemsInStorage;
        
    }
}
