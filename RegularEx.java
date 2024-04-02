import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularEx {

    private String text;
    private Pattern pattern;
    private Matcher m;

    public RegularEx(String input) {
        this.text = input;
        this.pattern = Pattern.compile(insert(input));
    }

    public String insert(String input) {
        for(int i = 0 ; i < input.length(); i++) {

            if(input.charAt(i) ==  'e') {
                input = new StringBuilder(input).insert(i+1, '?').toString();
                i++;
            }
            
            if(input.charAt(i) ==  '+') {
                input = input.replace('+', '|');
            }
        }
        return input;
    }

    public boolean compare(String stringToCompare) {
        m = pattern.matcher(stringToCompare);
        return m.matches();
    }

    public String initialPattern() {
        return this.text;
    }

}
