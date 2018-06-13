package orm.lib.Condition;

import orm.lib.Common.Common;
import orm.lib.SelectStatement;

import java.util.List;

enum SimpleOperator {
    Equal ("="),
    NotEqual("<>"),
    Greater (">"),
    Lesser ("<"),
    GreaterOrEqual (">="),
    LesserOrEqual ("<="),
    In ("in"),
    NotIn("not in"),
    Exists("exists");

    public String operator;
    SimpleOperator(String operator) {
        this.operator = operator;
    }
}

public class SimpleCondition extends Condition {
    private Object left;
    private Object right;
    private SimpleOperator operator;
    public SimpleCondition() { this.left = null; }
    public SimpleCondition(Object left) {
        this.left = left;
    }

    public String getOperator() {
        return operator.operator;
    }

    public SimpleCondition equal(Object right) {
        this.right = right;
        this.operator = SimpleOperator.Equal;
        return this;
    }

    public SimpleCondition notEqual(Object right) {
        this.right = right;
        this.operator = SimpleOperator.NotEqual;
        return this;
    }

    public SimpleCondition greater(Object right) {
        this.right = right;
        this.operator = SimpleOperator.Greater;
        return this;
    }

    public SimpleCondition lesser(Object right) {
        this.right = right;
        this.operator = SimpleOperator.Lesser;
        return this;
    }

    public SimpleCondition greaterEqual(Object right) {
        this.right = right;
        this.operator = SimpleOperator.GreaterOrEqual;
        return this;
    }

    public SimpleCondition lesserEqual(Object right) {
        this.right = right;
        this.operator = SimpleOperator.LesserOrEqual;
        return this;
    }

    public SimpleCondition in(List right) {
        this.right = Common.getSqlValueString(right);
        this.operator = SimpleOperator.In;
        return this;
    }

    public SimpleCondition notIn(List right) {
        this.right = Common.getSqlValueString(right);
        this.operator = SimpleOperator.NotIn;
        return this;
    }

    public SimpleCondition exists(SelectStatement selectStatement) {
        this.right = String.format("(%s)", selectStatement.toString());
        this.operator = SimpleOperator.Exists;
        return this;
    }

    @Override
    public String toString() {
        if (this.left == null)
            return String.format("(%s %s)", this.getOperator(), this.right.toString());
        return String.format("(%s %s %s)", this.left.toString(), this.getOperator(), this.right.toString());
    }
}
