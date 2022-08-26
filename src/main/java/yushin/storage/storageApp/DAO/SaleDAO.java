package yushin.storage.storageApp.DAO;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import yushin.storage.storageApp.entities.Sale;
import yushin.storage.storageApp.util.HibernateSessionUtil;

public class SaleDAO {
    
    public void create(Sale sale){
        
        Session session = HibernateSessionUtil.instance.openSession();
        session.save(sale);
        session.close();
        
    }
    
    public Sale findById(int id){
        
        Session session = HibernateSessionUtil.instance.openSession();
        Sale sale = session.find(Sale.class, id);
        session.close();
        return sale;
        
    }
    
    public void update(Sale sale){
        
        Session session = HibernateSessionUtil.instance.openSession();
        Transaction tx = session.beginTransaction();
        session.update(sale);
        tx.commit();
        session.close();
        
    }
    
    public void delete(Sale sale){
        
        Session session = HibernateSessionUtil.instance.openSession();
        Transaction tx = session.beginTransaction();
        session.delete(sale);
        tx.commit();
        session.close();
        
    }
    
    public List<Sale> findAll(){
        
        Session session = HibernateSessionUtil.instance.openSession();
        List<Sale> sales = (List<Sale>) session.createQuery("from sale").list();
        return sales;
        
    }
      
}
