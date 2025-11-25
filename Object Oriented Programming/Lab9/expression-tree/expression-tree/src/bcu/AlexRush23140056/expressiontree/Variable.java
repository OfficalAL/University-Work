package bcu.AlexRush23140056.expressiontree;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Variable extends Expression {
    private final String name;
    public Variable(String name) {
        this.name = name;
    }
    @Override
    public double evaluate(Map<String, Double> variables) {
        if (!variables.containsKey(name)) {
            throw new IllegalArgumentException("Variable " + name + " not found in map");
        }
        return variables.get(name);
    }
    @Override
    public Set<String> freeVariables() {
        return Collections.unmodifiableSet(new HashSet<>(java.util.Arrays.asList(name)));
    }
    @Override
    public String toString() {
        return name;
    }
}
