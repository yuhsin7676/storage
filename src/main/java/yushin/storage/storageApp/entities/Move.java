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
    private int id;
    
    private int storage_from;
    private int storage_to;
    private String items;

    public int getId() {
        return id;
    }

    public int getStorage_from() {
        return storage_from;
    }

    public void setStorage_from(int storage_from) {
        this.storage_from = storage_from;
    }

    public int getStorage_to() {
        return storage_to;
    }

    public void setStorage_to(int storage_to) {
        this.storage_to = storage_to;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }
    
    
    
}
