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
import yushin.storage.storageApp.entities.Move;
import yushin.storage.storageApp.entities.Storage;
import yushin.storage.storageApp.util.HibernateSessionUtil;

public class MoveDAO {
    
    public static String create(Move move){
        
        Session session = HibernateSessionUtil.instance.openSession();
        Storage storage_from = StorageDAO.findById(move.getStorage_from());
        Storage storage_to = StorageDAO.findById(move.getStorage_to());
        if(storage_from == null)
            return "Create failed: storage " + move.getStorage_from() + " does not exist";
        else if(storage_to == null)
            return "Create failed: storage " + move.getStorage_to() + " does not exist";
        
        try{
            // Некорректный json вызовет Exception
            Type type = new TypeToken<ArrayList<Map<String, Integer>>>(){}.getType();
            ArrayList<Map<String, Integer>> items = new Gson().fromJson(move.getItems(), type);

            Transaction tx = session.beginTransaction();
            for(int i = 0; i < items.size(); i++){
                List<ItemInStorage> itemsInStorage = ItemInStorageDAO.findAllByItemStorage(items.get(i).get("id"), move.getStorage_from());
                if(!itemsInStorage.isEmpty()){
                    ItemInStorage itemInStorage = itemsInStorage.get(0);
                    itemInStorage.setNumber(itemInStorage.getNumber() - items.get(i).get("number"));
                    if(items.get(i).get("number") <= 0){
                        tx.rollback();
                        return "Create failed: number of items in move must be positive ";
                    }  
                    else if(itemInStorage.getNumber() < 0){
                        tx.rollback();
                        return "Create failed: number of items " + items.get(i).get("id") + " in storage" + move.getStorage_from() + " could't be negative ";
                    }
                    else if(itemInStorage.getNumber() == 0)
                        session.delete(itemInStorage);
                    else
                        session.update(itemInStorage);
                }
                else{
                    tx.rollback();
                    return "Create failed: items " + items.get(i).get("id") + " in storage " + move.getStorage_from() + " does not exist ";
                }

                itemsInStorage = ItemInStorageDAO.findAllByItemStorage(items.get(i).get("id"), move.getStorage_to());
                if(!itemsInStorage.isEmpty()){
                    ItemInStorage itemInStorage = itemsInStorage.get(0);
                    itemInStorage.setNumber(itemInStorage.getNumber() + items.get(i).get("number"));
                    session.update(itemInStorage);
                }
                else{
                    ItemInStorage itemInStorage = new ItemInStorage();
                    itemInStorage.setItem_id(items.get(i).get("id"));
                    itemInStorage.setNumber(items.get(i).get("number"));
                    itemInStorage.setStorage_id(move.getStorage_to());
                    session.persist(itemInStorage);
                }

            }
            tx.commit();

            session.save(move);
            session.close();
            return "OK";
        }
        catch(Exception e){
            return e.getMessage();
        }
        
    }
    
    public static Move findById(int id){
        
        Session session = HibernateSessionUtil.instance.openSession();
        Move move = session.find(Move.class, id);
        session.close();
        return move;
        
    }
    
    public static String delete(Move move){
        
        try{
            Session session = HibernateSessionUtil.instance.openSession();
            Transaction tx = session.beginTransaction();
            session.delete(move);
            tx.commit();
            session.close();
            return "OK";
        }
        catch(Exception e){
            return e.getMessage();
        }
        
    }
    
    public static List<Move> findAll(){
        
        Session session = HibernateSessionUtil.instance.openSession();
        List<Move> moves = (List<Move>) session.createQuery("from Move").list();
        return moves;
        
    }
      
}
