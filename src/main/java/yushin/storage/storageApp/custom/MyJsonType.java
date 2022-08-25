package yushin.storage.storageApp.custom;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

public class MyJsonType implements UserType {
    
    @Override
    public int[] sqlTypes(){
        return new int[]{Types.JAVA_OBJECT};
    }
    
    @Override
    public Class<MyJsonType> returnedClass() {
        return MyJsonType.class;
    }
    
    @Override
    public boolean equals(Object o, Object o1) throws HibernateException{
        if(o == null)
            return o1 == null;
        return o.equals(o1);
    }

    @Override
    public int hashCode(Object o) throws HibernateException{
        return o.hashCode();
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] strings, SharedSessionContractImplementor ssci, Object o) throws HibernateException, SQLException{
        if(rs.getString(strings[0]) == null)
            return null;
        return rs.getString(strings[0]);
    }

    @Override
    public void nullSafeSet(PreparedStatement ps, Object o, int i, SharedSessionContractImplementor ssci) throws HibernateException, SQLException{
        if(o == null)
            ps.setNull(i, Types.OTHER);
        else
            ps.setObject(i, o, Types.OTHER);
        
    }

    @Override
    public Object deepCopy(Object o) throws HibernateException{
        return o;
    }

    @Override
    public boolean isMutable(){
        return true;
    }

    @Override
    public Serializable disassemble(Object o) throws HibernateException{
        return (String) this.deepCopy(o);
    }

    @Override
    public Object assemble(Serializable srlzbl, Object o) throws HibernateException{
        return this.deepCopy(srlzbl);
    }

    @Override
    public Object replace(Object o, Object o1, Object o2) throws HibernateException{
        return o;
    }
    
}
