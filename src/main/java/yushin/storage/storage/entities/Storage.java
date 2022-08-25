package yushin.storage.storage.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "storage")
public class Storage {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    
    public String name;
    
}
