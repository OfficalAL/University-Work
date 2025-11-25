package bcu.AlexRush23140056.expressiontree;

import java.util.Map;
import java.util.Set;

public class SquareRoot extends Expression {
    private final Expression subExpression;
    public SquareRoot(Expression subExpression) {
        this.subExpression = subExpression;
    }
    @Override
    public double evaluate(Map<String, Double> variables) {
        double value = subExpression.evaluate(variables);
        if (value < 0) {
            throw new ArithmeticException("Cannot take square root of negative value");
        }
        return Math.sqrt(value);
    }
    @Override
    public Set<String> freeVariables() {
        return subExpression.freeVariables();
    }
    @Override
    public String toString() {
        return "sqrt(" + subExpression.toString() + ")";
    }
}
