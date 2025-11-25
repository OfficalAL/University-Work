package bcu.AlexRush23140056.expressiontree;

import java.util.Map;
import java.util.Set;

public abstract class Expression {
    public abstract double evaluate(Map<String, Double> variables);
    public abstract Set<String> freeVariables();
    @Override
    public abstract String toString();
}
