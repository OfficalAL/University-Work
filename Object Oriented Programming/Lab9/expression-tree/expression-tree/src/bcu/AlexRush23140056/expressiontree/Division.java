package bcu.AlexRush23140056.expressiontree;

public class Division extends BinaryOperation {
    public Division(Expression left, Expression right) {
        super(left, right);
    }
    @Override
    protected double evaluate(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return a / b;
    }
    @Override
    protected String getSymbol() {
        return "/";
    }
}
