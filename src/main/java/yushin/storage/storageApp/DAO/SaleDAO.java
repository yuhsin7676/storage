package yushin.storage.storageApp.DAO;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.hibernate.Transaction;
import yushin.storage.storageApp.entities.Item;
import yushin.storage.storageApp.entities.ItemInStorage;
import yushin.storage.storageApp.entities.Sale;
import yushin.storage.storageApp.entities.Storage;
import yushin.storage.storageApp.util.HibernateSessionUtil;

public class SaleDAO {
    
    public static void create(Sale sale){
        
        Session session = HibernateSessionUtil.instance.openSession();
        
        Storage storage = StorageDAO.findById(sale.getStorage_id());
        
        // Если склада, указанного в документе нет, то выходим из функции
        if(storage == null)
            return;
        
        Type type = new TypeToken<ArrayList<Map<String, Integer>>>(){}.getType();
        ArrayList<Map<String, Integer>> items = new Gson().fromJson(sale.getItems(), type);
        
        Transaction tx = session.beginTransaction();
        for(int i = 0; i < items.size(); i++){
            List<ItemInStorage> itemsInStorage = ItemInStorageDAO.findAllByItemIdStorage(items.get(i).get("id"), sale.getStorage_id());
            if(!itemsInStorage.isEmpty()){
                ItemInStorage itemInStorage = itemsInStorage.get(0);
                itemInStorage.setNumber(itemInStorage.getNumber() - items.get(i).get("number"));
                if(itemInStorage.getNumber() < 0){
                    tx.rollback();
                    return;
                }
                session.update(itemInStorage);
            }
            else{
                tx.rollback();
                return;
            }
            
            Item item = ItemDAO.findById(items.get(i).get("id"));
            if(item != null){
                item.setPrice_sale(items.get(i).get("price"));
                session.update(item);
            }

        }
        tx.commit();
        
        session.save(sale);
        session.close();
        
    }
    
    public static Sale findById(int id){
        
        Session session = HibernateSessionUtil.instance.openSession();
        Sale sale = session.find(Sale.class, id);
        session.close();
        return sale;
        
    }
    
    public static List<Sale> findAll(){
        
        Session session = HibernateSessionUtil.instance.openSession();
        List<Sale> sales = (List<Sale>) session.createQuery("from Sale").list();
        return sales;
        
    }
      
}
