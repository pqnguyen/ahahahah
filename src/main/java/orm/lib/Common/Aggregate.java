package orm.lib.Common;

public class Aggregate {
    private String str;
    public Aggregate count(String columnName, String asName) {
        str = String.format("count(%s) as %s", columnName, asName);
        return this;
    }
    public Aggregate count(String columnName) {
        str = String.format("count(%s)", columnName);
        return this;
    }
    public Aggregate sum(String columnName, String asName) {
        str = String.format("sum(%s) as %s", columnName, asName);
        return this;
    }

    @Override
    public String toString() {
        return str;
    }
}
