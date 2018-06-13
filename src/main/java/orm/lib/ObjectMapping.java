package orm.lib;

import orm.lib.Relationship.Relationship;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectMapping {

    public static boolean isExistConlume(String fieldName, ResultSetMetaData resultSetMetaData) throws SQLException {
        int numColumn = resultSetMetaData.getColumnCount();
        for (int i = 1; i <= numColumn; i++)
            if (fieldName.equals(resultSetMetaData.getColumnName(i))) return true;
        return false;
    }

    public static Object mapping(Class className, ResultSet rs) throws IllegalAccessException, InstantiationException, SQLException {
        Object target = className.newInstance();

        ResultSetMetaData resultSetMetaData =  rs.getMetaData();
        Field[] fields = className.getDeclaredFields();
        for (Field field : fields)
        if (isExistConlume(field.getName(), resultSetMetaData)) {
            boolean isAccessible = field.isAccessible();
            field.setAccessible(true);

            field.set(target, rs.getObject(field.getName()));

            field.setAccessible(isAccessible);
        }
        return target;
    }

    public static Map<String, Object> getValues(Object object) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<>();
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            boolean isAccessible = field.isAccessible();
            field.setAccessible(true);
            if (!(field.get(object) instanceof Relationship)) {
                Object value = field.get(object);
                if (value == null || value instanceof List) continue;
                map.put(field.getName(), field.get(object));
            }

            field.setAccessible(isAccessible);
        }
        return map;
    }
}
