package orm.lib.Relationship;

import orm.ObjectRelationalMapping;
import orm.lib.Annotation.OneToMany;
import orm.lib.Common.Common;
import orm.lib.Condition.SimpleCondition;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class OneToManyRelationship<T> extends Relationship<T> {
    public OneToManyRelationship(Object target) {
        super(target);
    }

    public List<T> get(ObjectRelationalMapping orm, Class tableName) throws SQLException, IllegalAccessException, InstantiationException {
        Object value = Common.getPrimaryKeyValue(target);
        Field field = Common.getFieldRelationshipInfo(target, OneToMany.class);
        OneToMany oneToMany = field.getDeclaredAnnotation(OneToMany.class);
        List<T> ls = orm.select(Arrays.asList("*")).from(tableName).where(new SimpleCondition(oneToMany.foreignKey()).equal(value)).execute();
        boolean isAccessible = field.isAccessible();
        field.setAccessible(true);
        field.set(target, ls);
        field.setAccessible(isAccessible);
        return ls;
    }
}
