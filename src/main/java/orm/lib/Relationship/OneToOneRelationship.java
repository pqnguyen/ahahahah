package orm.lib.Relationship;

import orm.ObjectRelationalMapping;
import orm.lib.Annotation.OneToOne;
import orm.lib.Common.Common;
import orm.lib.Condition.SimpleCondition;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class OneToOneRelationship<T> extends Relationship<T> {

    public OneToOneRelationship(Object target) {
        super(target);
    }

    @Override
    public List<T> get(ObjectRelationalMapping orm, Class tableName) throws Exception {
        Object value = Common.getPrimaryKeyValue(target);
        Field field = Common.getFieldRelationshipInfo(target, OneToOne.class);
        OneToOne oneToMany = field.getDeclaredAnnotation(OneToOne.class);
        List<T> ls = orm.select(Arrays.asList("*")).from(tableName).where(new SimpleCondition(oneToMany.foreignKey()).equal(value)).limit(1).execute();
        boolean isAccessible = field.isAccessible();
        field.setAccessible(true);
        field.set(target, ls.get(0));
        field.setAccessible(isAccessible);
        return ls;
    }
}
