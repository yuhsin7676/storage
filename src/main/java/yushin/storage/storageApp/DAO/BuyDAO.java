package yushin.storage.storageApp.DAO;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.hibernate.Transaction;
import yushin.storage.storageApp.entities.Buy;
import yushin.storage.storageApp.entities.Item;
import yushin.storage.storageApp.entities.ItemInStorage;
import yushin.storage.storageApp.entities.Storage;
import yushin.storage.storageApp.util.HibernateSessionUtil;

public class BuyDAO {
    
    public static String create(Buy buy){
        
        Session session = HibernateSessionUtil.instance.openSession();
        Storage storage = StorageDAO.findById(buy.getStorage_id());
        if(storage == null)
            return "Create failed: storage " + buy.getStorage_id() + " does not exist";
        
        try{
            // некорректный json вызовет Exception
            Type type = new TypeToken<ArrayList<Map<String, Integer>>>(){}.getType();
            ArrayList<Map<String, Integer>> items = new Gson().fromJson(buy.getItems(), type);

            Transaction tx = session.beginTransaction();
            for(int i = 0; i < items.size(); i++){
                List<ItemInStorage> itemsInStorage = ItemInStorageDAO.findAllByItemStorage(items.get(i).get("id"), buy.getStorage_id());
                if(items.get(i).get("number") <= 0){
                    tx.rollback();
                    return "Create failed: number of items in buy must be positive ";
                } 
                else if(!itemsInStorage.isEmpty()){
                    ItemInStorage itemInStorage = itemsInStorage.get(0);
                    itemInStorage.setNumber(itemInStorage.getNumber() + items.get(i).get("number"));
                    ItemInStorageDAO.update(itemInStorage);
                }
                else{
                    ItemInStorage itemInStorage = new ItemInStorage();
                    itemInStorage.setItem_id(items.get(i).get("id"));
                    itemInStorage.setNumber(items.get(i).get("number"));
                    itemInStorage.setStorage_id(buy.getStorage_id());
                    ItemInStorageDAO.create(itemInStorage);
                }

                Item item = ItemDAO.findById(items.get(i).get("id"));
                if(item != null){
                    item.setPrice_buy(items.get(i).get("price"));
                    ItemDAO.update(item);
                }

            }
            tx.commit();  
            session.save(buy);
            session.close();
            return "OK";
        }
        catch(Exception e){
            return e.getMessage();
        }
        
    }
    
    public static Buy findById(int id){
        
        Session session = HibernateSessionUtil.instance.openSession();
        Buy buy = session.find(Buy.class, id);
        session.close();
        return buy;
        
    }
    
    public static String delete(Buy buy){
        
        try{
            Session session = HibernateSessionUtil.instance.openSession();
            Transaction tx = session.beginTransaction();
            session.delete(buy);
            tx.commit();
            session.close();
            return "OK";
        }
        catch(Exception e){
            return e.getMessage();
        }
        
    }
    
    public static List<Buy> findAll(){
        
        Session session = HibernateSessionUtil.instance.openSession();
        List<Buy> buys = (List<Buy>) session.createQuery("from Buy").list();
        return buys;
        
    }
      
}
