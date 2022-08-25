package yushin.storage.storage.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "buy")
public class Buy {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    
    public int storage_id;
    public String items;
    
}
