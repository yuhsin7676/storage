package yushin.storage.storage.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "move")
public class Move {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    
    public int storage_from;
    public int storage_to;
    public String items;
    
}
