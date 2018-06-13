package orm.lib;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

abstract public class SqlStatement {
    protected Statement statement;
    protected StringBuilder stmtString;
    protected MetaData metaData;

    protected static class MetaData {
        private Map<String, Object> map = new HashMap<>();
        public void put(String key, Object value) {
            map.put(key, value);
        }
        public Object get(String key) {
            return map.get(key);
        }
    }

    public SqlStatement(Statement statement) {
        this.statement = statement;
        init();
    }

    protected void init() {
        stmtString = new StringBuilder();
        metaData = new MetaData();
    }
}
