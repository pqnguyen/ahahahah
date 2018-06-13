package orm.lib.Relationship;

import orm.ObjectRelationalMapping;
import orm.lib.Annotation.Id;
import orm.lib.Annotation.OneToMany;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.List;

public abstract class Relationship<T> {
    protected Object target;
    protected Relationship(Object target) {
        this.target = target;
    }

    public abstract List<T> get(ObjectRelationalMapping orm, Class tableName) throws Exception;
}
