package orm;

import orm.lib.Annotation.Id;
import orm.lib.Common.Common;
import orm.lib.Condition.SimpleCondition;
import orm.lib.InsertStatement;
import orm.lib.SelectStatement;
import orm.lib.UpdateStatement;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class ObjectRelationalMapping {
    private String hostAddress;
    private String databaseName;
    private String username;
    private String password;
    private String connectionString;
    private Connection connection;

    public ObjectRelationalMapping(String hostAddress, String databaseName, String username, String password) {
        this.hostAddress = hostAddress;
        this.databaseName = databaseName;
        this.username = username;
        this.password = password;
        this.connectionString = String.format("jdbc:mysql://%s/%s", hostAddress, databaseName);
    }

    public SelectStatement select(List<Object> columns) throws SQLException {
        Statement statement = connection.createStatement();
        return new SelectStatement(statement).select(columns);
    }

    public InsertStatement save(Object object) throws SQLException, IllegalAccessException, NoSuchFieldException {
        Statement statement = connection.createStatement();
        return new InsertStatement(statement).insertInto(object.getClass()).values(object);
    }

    public UpdateStatement update(Object object) throws SQLException, IllegalAccessException {
        Statement statement = connection.createStatement();
        Field id = Common.getFieldRelationshipInfo(object, Id.class);
        boolean accessible = id.isAccessible();
        id.setAccessible(true);
        Object value = id.get(object);
        id.setAccessible(accessible);
        return new UpdateStatement(statement).update(object.getClass()).set(object)
                                    .where(new SimpleCondition(id.getName()).equal(value));
    }

    public void openConnection() throws SQLException {
        connection = DriverManager.getConnection(connectionString, username, password);
    }

    public void closeConnection() throws SQLException {
        connection.close();
    }
}
