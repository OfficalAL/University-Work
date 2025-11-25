package bcu.AlexRush23140056.expressiontree;

public class Addition extends BinaryOperation {
    public Addition(Expression left, Expression right) {
        super(left, right);
    }
    @Override
    protected double evaluate(double a, double b) {
        return a + b;
    }
    @Override
    protected String getSymbol() {
        return "+";
    }
}
