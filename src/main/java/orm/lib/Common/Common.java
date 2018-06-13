package orm.lib.Common;

import orm.lib.Annotation.Id;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class Common {
    public static String getSqlValueString(List values) {
        String str = (String)values.stream().map((value) -> {
            if (value instanceof String) return String.format("'%S'", value);
            return value.toString();
        }).collect(Collectors.joining(", "));
        return String.format("(%s)", str);
    }

    public static Object getPrimaryKeyValue(Object target) throws IllegalAccessException {
        Field[] fields = target.getClass().getDeclaredFields();
        for (Field field : fields)
            if (field.getAnnotation(Id.class) != null) {
                boolean isAccessible = field.isAccessible();
                field.setAccessible(true);
                Object value = field.get(target);
                field.setAccessible(isAccessible);
                return value;
            }
        return null;
    }

    public static Field getFieldRelationshipInfo(Object target, Class annotationClass) {
        Field[] fields = target.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getDeclaredAnnotation(annotationClass) != null) return field;
        }
        return null;
    }
}
