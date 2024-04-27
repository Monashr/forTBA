import java.util.ArrayList;

public class Expression {
    ArrayList<String> expression;
    boolean closure;

    public Expression(String expression) {
        this.expression = new ArrayList<>();
        this.expression.add(expression);
        this.closure = false;
    }

    public void addExpression(String expression) {
        this.expression.add(expression);
    }

    public void isClosure() {
        this.closure = true;
    }

    public boolean isItClosure() {
        return this.closure;
    }

    public String getExpression(int index) {
        return this.expression.get(index);
    }
}
