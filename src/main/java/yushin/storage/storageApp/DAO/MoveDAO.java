package yushin.storage.storageApp.DAO;

import java.util.List;
import org.hibernate.Session;
import yushin.storage.storageApp.entities.Move;
import yushin.storage.storageApp.util.HibernateSessionUtil;

public class MoveDAO {
    
    public void create(Move move){
        
        Session session = HibernateSessionUtil.instance.openSession();
        session.save(move);
        session.close();
        
    }
    
    public Move findById(int id){
        
        Session session = HibernateSessionUtil.instance.openSession();
        Move move = session.find(Move.class, id);
        session.close();
        return move;
        
    }
    
    public void update(Move move){
        
        Session session = HibernateSessionUtil.instance.openSession();
        session.update(move);
        session.close();
        
    }
    
    public void delete(Move move){
        
        Session session = HibernateSessionUtil.instance.openSession();
        session.delete(move);
        session.close();
        
    }
    
    public List<Move> findAll(){
        
        Session session = HibernateSessionUtil.instance.openSession();
        List<Move> moves = (List<Move>) session.createQuery("from move").list();
        return moves;
        
    }
      
}
