package orm.lib.Relationship;

import com.company.pojo.Project;
import orm.ObjectRelationalMapping;
import orm.lib.Annotation.Id;
import orm.lib.Annotation.ManyToMany;
import orm.lib.Annotation.OneToMany;
import orm.lib.Common.Common;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class ManyToManyRelationship<T> extends Relationship<T> {
    public ManyToManyRelationship(Object target) {
        super(target);
    }

    public final List<T> get(ObjectRelationalMapping orm, Class tableName) throws SQLException, IllegalAccessException, InstantiationException {
        Object value = Common.getPrimaryKeyValue(target);
        Field field = Common.getFieldRelationshipInfo(target, ManyToMany.class);
        ManyToMany annotation = field.getDeclaredAnnotation(ManyToMany.class);
        String tableSimpleName = tableName.getSimpleName();

        List<T> ls = orm.select(Arrays.asList(tableSimpleName + ".*"))
                .from(tableName)
                .join(annotation.throughTable(), String.format("%s.%s", tableSimpleName, annotation.left()), String.format("%s.%s", annotation.throughTable(), annotation.left()))
                .limit(10)
                .execute();

        boolean isAccessible = field.isAccessible();
        field.setAccessible(true);
        field.set(target, ls);
        field.setAccessible(isAccessible);
        return ls;
    }
}
