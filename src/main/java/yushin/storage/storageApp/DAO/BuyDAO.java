package yushin.storage.storageApp.DAO;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import yushin.storage.storageApp.entities.Buy;
import yushin.storage.storageApp.entities.Item;
import yushin.storage.storageApp.entities.ItemInStorage;
import yushin.storage.storageApp.entities.Storage;
import yushin.storage.storageApp.util.HibernateSessionUtil;

public class BuyDAO {
    
    public static void create(Buy buy){
        
        Session session = HibernateSessionUtil.instance.openSession();
        
        Storage storage = StorageDAO.findById(buy.getStorage_id());
        
        // Если склада, указанного в документе нет, то выходим из функции
        if(storage == null)
            return;
        
        Type type = new TypeToken<ArrayList<Map<String, Integer>>>(){}.getType();
        ArrayList<Map<String, Integer>> items = new Gson().fromJson(buy.getItems(), type);
        for(int i = 0; i < items.size(); i++){
            List<ItemInStorage> itemsInStorage = ItemInStorageDAO.findAllByItemIdStorage(items.get(i).get("id"), buy.getStorage_id());
            if(!itemsInStorage.isEmpty()){
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
            
        session.save(buy);
        session.close();
        
    }
    
    public static Buy findById(int id){
        
        Session session = HibernateSessionUtil.instance.openSession();
        Buy buy = session.find(Buy.class, id);
        session.close();
        return buy;
        
    }
    
    public static List<Buy> findAll(){
        
        Session session = HibernateSessionUtil.instance.openSession();
        List<Buy> buys = (List<Buy>) session.createQuery("from Buy").list();
        return buys;
        
    }
      
}
