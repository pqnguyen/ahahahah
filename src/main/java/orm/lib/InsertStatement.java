package orm.lib;

import orm.lib.Annotation.GeneratedValue;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InsertStatement extends SqlStatement {

    public InsertStatement(Statement statement) {
        super(statement);
    }

    public InsertStatement insertInto(Class className) {
        metaData.put("insert into", className.getSimpleName());
        return this;
    }

    public InsertStatement values(Object object) throws IllegalAccessException, NoSuchFieldException {
        Map<String, Object> columns = ObjectMapping.getValues(object);
        List<String> fields = columns.keySet().stream().filter((field) -> {
            try {
                return object.getClass().getDeclaredField(field).getAnnotation(GeneratedValue.class) == null;
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
                return false;
            }
        }).collect(Collectors.toList());

        String columnNames = String.join(", ", fields);
        String values = fields.stream().map((key) -> {
            Object value = columns.get(key);
            if (value instanceof String) return String.format("'%s'", value);
            return value.toString();
        }).collect(Collectors.joining(", "));

        stmtString.append(String.format("insert into %s (%s) values (%s)", metaData.get("insert into"), columnNames, values));
        System.out.println(stmtString.toString());
        return this;
    }

    public int InsertStatement() throws SQLException {
        return statement.executeUpdate(stmtString.toString());
    }
}
