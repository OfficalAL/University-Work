package bcu.AlexRush23140056.expressiontree;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class Constant extends Expression {
    private final double value;
    public Constant(double value) {
        this.value = value;
    }
    @Override
    public double evaluate(Map<String, Double> variables) {
        return value;
    }
    @Override
    public Set<String> freeVariables() {
        return Collections.emptySet();
    }
    @Override
    public String toString() {
        return Double.toString(value);
    }
}
