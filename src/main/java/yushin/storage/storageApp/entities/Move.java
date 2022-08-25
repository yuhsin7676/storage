package yushin.storage.storageApp.entities;

import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

@Entity
@Table(name = "move")
@TypeDef(name = "json", typeClass = JsonObject.class)
public class Move {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    
    public int storage_from;
    public int storage_to;
    
    public String items;
    
}
