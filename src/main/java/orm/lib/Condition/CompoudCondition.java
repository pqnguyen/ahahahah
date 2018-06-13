package orm.lib.Condition;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

enum CompoundOperator {
    And ("and"),
    Or ("or");

    String operator;
    CompoundOperator(String operator) {
        this.operator = operator;
    }
}

public class CompoudCondition extends Condition {

    static class RightCondition {
        public Condition right;
        public CompoundOperator operator;
        public RightCondition(Condition right, CompoundOperator operator) {
            this.right = right;
            this.operator = operator;
        }

        public String getOperator() {
            return operator.operator;
        }

        @Override
        public String toString() {
            return String.format("%s %s", getOperator(), right.toString());
        }
    }

    Condition left;
    List<RightCondition> right;

    public CompoudCondition(Condition left) {
        this.left = left;
        right = new ArrayList<>();
    }

    public CompoudCondition and(Condition right) {
        RightCondition rightCondition = new RightCondition(right, CompoundOperator.And);
        this.right.add(rightCondition);
        return this;
    }

    public CompoudCondition or(Condition right) {
        RightCondition rightCondition = new RightCondition(right, CompoundOperator.Or);
        this.right.add(rightCondition);
        return this;
    }

    @Override
    public String toString() {
        String str = right.stream().map((value) -> value.toString()).collect(Collectors.joining(" "));
        return String.format("(%s %s)", left.toString(), str);
    }
}
