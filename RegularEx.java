import java.util.ArrayList;

public class RegularEx {

    String regex;
    char symbol;
    int pointer;

    char mode;
    ArrayList<Expression> list;

    public RegularEx(String input) {
        this.regex = input;
        this.pointer = 0;
        this.symbol = this.regex.charAt(pointer);
    }

    public void addExpression(String input) {
        list.add(new Expression())
    }

    public void readRegex(String regex) {

        if(this.mode == '(') {

        }

        else if(regex.charAt(pointer) == '(' ) {
            readRegex(regex.substring(1));
            this.mode = '(';
            this.pointer++;

        } else if(regex.charAt(pointer) == '*') {

        }
    }
}
