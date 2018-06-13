package orm.lib;

import orm.lib.Condition.Condition;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.stream.Collectors;

public class UpdateStatement extends SqlStatement  {
    public UpdateStatement(Statement statement) {
        super(statement);
    }

    public UpdateStatement update(Class tableName) {
        stmtString.append("update ")
                  .append(tableName.getSimpleName());
        metaData.put("update", tableName.getSimpleName());
        return this;
    }

    public UpdateStatement set(Object object) throws IllegalAccessException {
        Map<String, Object> values = ObjectMapping.getValues(object);
        String str = values.keySet().stream().map((key) -> {
            Object value = values.get(key);
            if (value instanceof String) return String.format("%s = '%s'", key, value);
            return String.format("%s = %s", key, value);
        }).collect(Collectors.joining(", "));
        stmtString.append(" set ")
                  .append(str);
        metaData.put("set", str);
        return this;
    }

    public UpdateStatement where(Condition condition) {
        stmtString.append(" where ")
                .append(condition.toString());
        metaData.put("where", condition);
        return this;
    }

    public int execute() throws SQLException {
        System.out.println(stmtString.toString());
        return statement.executeUpdate(stmtString.toString());
    }
}
