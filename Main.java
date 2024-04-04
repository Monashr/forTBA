import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    private static void regexChecker(TextReader reader, RegularEx ex, String check) {
        if(check.length() == 0) {
            System.out.print("Checking " + reader.getCheck());
            if(ex.compare(reader.getCheck())) {
                System.out.println(" : True");
            } else {
                System.out.println(" : False");
            };
        } else {
            System.out.print("Checking " + check);
            if(ex.compare(check)) {
                System.out.println(" : True");
            } else {
                System.out.println(" : False");
            }; 
        }
    }

    public static State statePicker(String input) {
        switch(input) {
            case "starting" :
                return State.S;
            case "accepting" :
                return State.A;
            case "startingaccepting" :
                return State.AS;
            default :
                return State.N;
        }
    }

    public static void loader(String fileName, Scanner scanner) {
        TextReader reader = new TextReader(fileName);
        reader.readFile();

        if(reader.getRegex() != "null") {
            System.out.println("Regex " + reader.getRegex() + " Loaded");
            RegularEx ex = new RegularEx(reader.getRegex());

            if(reader.hasCheck()) {
                regexChecker(reader, ex, "");
                return;
            }

            System.out.print("Enter String to Check : ");
            String input = scanner.nextLine();
            regexChecker(reader, ex, input);

        } else if(reader.getStateCount() != 0) {
            System.out.println("Finite Automata Detected");
            nodeBasket basket = new nodeBasket();
            
            for(Map.Entry<String, String> entry : reader.getEntrySet()) {
                basket.nodeInsert(entry.getKey(), statePicker(entry.getValue()));
            }
            for(int i = 0 ; i < reader.getLinksSize() ; i++) {
                List<String> linkList = reader.getLinks();
                String input[] = linkList.get(i).split(" ", 3);
                basket.linkNode(input[0], input[1], input[2].charAt(0));
            }

            if(reader.hasCheck()) {
                basket.cari(reader.getCheck());
                return;
            }

            System.out.print("Enter String to Check : ");
            String input = scanner.nextLine();
            basket.cari(input);
        }
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Input Filename : ");
        String filename = scanner.nextLine();
        loader(filename, scanner);

        scanner.close();
    }
}
