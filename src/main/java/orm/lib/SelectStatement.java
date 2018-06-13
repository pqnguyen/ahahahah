package orm.lib;

import orm.lib.Common.Sorting;
import orm.lib.Condition.Condition;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.stream.Collectors;

public class SelectStatement extends SqlStatement {

    public SelectStatement(Statement statement) {
        super(statement);
    }

    public SelectStatement select(List<Object> columns) {
        stmtString.append(" select ")
                   .append(
                           columns.stream().map(column -> column.toString())
                                   .collect(Collectors.joining(", "))
                   );
        metaData.put("select", columns);
        return this;
    }

    public SelectStatement from(Class className) {
        stmtString.append(" from ")
                  .append(className.getSimpleName());
        metaData.put("from", className);
        return this;
    }

    public SelectStatement from(SelectStatement selectStatement, String aliasName) {
        String str = String.format("(%s) as %s", selectStatement.toString(), aliasName);
        stmtString.append(" from ")
                  .append(str);
        metaData.put("from", selectStatement);
        return this;
    }

    public SelectStatement join(String tableName, String left, String right) {
        stmtString.append(" inner join ")
                    .append(String.format("%s on %s = %s", tableName, left, right));
        return this;
    }

    public SelectStatement where(Condition condition) {
        stmtString.append(" where ")
                  .append(condition.toString());
        metaData.put("where", condition);
        return this;
    }

    public SelectStatement groupBy(List<String> columns) {
        stmtString.append(" group by ")
                  .append(String.join(", ", columns));
        metaData.put("groupby", columns);
        return this;
    }

    public SelectStatement having(Condition condition) {
        stmtString.append(" having ")
                  .append(condition.toString());
        metaData.put("having", condition);
        return this;
    }

    public SelectStatement limit(Integer number) {
        stmtString.append(" limit ")
                   .append(number);
        metaData.put("limit", number);
        return this;
    }

    public SelectStatement orderBy(Sorting sorting) {
        stmtString.append(" order by ")
                  .append(sorting.toString());
        return this;
    }

    public List execute() throws SQLException, InstantiationException, IllegalAccessException {
        ResultSet rs = statement.executeQuery(getStmtString().toString());
        List<Object> ls = new ArrayList<>();
        while (rs.next()) {
            Object target = ObjectMapping.mapping(((Class) metaData.get("from")), rs);
            ls.add(target);
        }
        return ls;
    }

    public StringBuilder getStmtString() {
        return stmtString;
    }

    @Override
    public String toString() {
        return stmtString.toString();
    }
}
