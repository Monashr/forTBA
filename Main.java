import java.util.ArrayList;
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
        List<nodeBasket> listOfBasket = new ArrayList<nodeBasket>();
        reader.readFile();

        if(reader.getRegex() != "null") {
            System.out.println("Regex " + reader.getRegex() + " Loaded");
            RegularEx ex = new RegularEx(reader.getRegex());

            if(reader.hasCheck()) {
                regexChecker(reader, ex, "");
                return;
            }

        } else if(reader.getStateCounts() != -1) {
            System.out.println(reader.getStateCounts() + " Finite Automata Detected");
            
            for(int i = 0 ; i < reader.getStateCounts() ; i++) {
                listOfBasket.add(new nodeBasket());
            }
            
            for(int k = 0 ; k < reader.getStateCounts() ; k++) {
                for(Map.Entry<String, String> entry : reader.getEntrySet(k)) {
                    listOfBasket.get(k).nodeInsert(entry.getKey(), statePicker(entry.getValue()));
                }

                for(int j = 0; j < reader.getStateCounts() ; j++) {
                    for(int i = 0 ; i < reader.getLinksSize(j) ; i++) {
                        List<String> linkList = reader.getLinks(k);
                        String input[] = linkList.get(i).split(" ", 3);
                        listOfBasket.get(k).linkNode(input[0], input[1], input[2].charAt(0));
                    }
                }
    
                if(reader.isDFAconvert()) {
                    System.out.println("Converting...");
                    listOfBasket.get(k).toNFA();
                }

                if(reader.hasCheck()) {
                    System.out.println("Checking Input " + reader.getCheck());
                    listOfBasket.get(k).transverse(reader.getCheck());
                    return;
                } 
                

            }
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
