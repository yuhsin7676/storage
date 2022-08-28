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
    
    public List<Move> findAll(){
        
        Session session = HibernateSessionUtil.instance.openSession();
        List<Move> moves = (List<Move>) session.createQuery("from Move").list();
        return moves;
        
    }
      
}
