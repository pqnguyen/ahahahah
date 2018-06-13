package orm.lib.Common;

public class Sorting {
    private StringBuilder str = new StringBuilder();
    public Sorting ascend(String ... columns) {
        if (str.length() != 0) str.append(",");
        str.append(String.join(", ", columns)).append(" ASC ");
        return this;
    }

    public Sorting descend(String ... columns) {
        if (str.length() != 0) str.append(",");
        str.append(String.join(", ", columns)).append(" DESC ");
        return this;
    }

    @Override
    public String toString() {
        return str.toString();
    }
}
