package bcu.AlexRush23140056.expressiontree;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class BinaryOperation extends Expression {
    private final Expression left;
    private final Expression right;
    public BinaryOperation(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }
    @Override
    public double evaluate(Map<String, Double> variables) {
        double a = left.evaluate(variables);
        double b = right.evaluate(variables);
        return evaluate(a, b);
    }
    @Override
    public Set<String> freeVariables() {
        Set<String> vars = new HashSet<>(left.freeVariables());
        vars.addAll(right.freeVariables());
        return java.util.Collections.unmodifiableSet(vars);
    }
    protected abstract double evaluate(double a, double b);
    protected abstract String getSymbol();
    @Override
    public String toString() {
        return "(" + left.toString() + " " + getSymbol() + " " + right.toString() + ")";
    }
}
