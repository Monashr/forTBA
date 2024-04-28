import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    private static void regexChecker(String input, RegularEx ex) {
        System.out.print("Checking " + input);

        if (ex.compare(input)) {
            System.out.println(" : True");
        }

        else {
            System.out.println(" : False");
        }
    }

    public static State statePicker(String input) {
        switch (input) {
            case "starting":
                return State.S;

            case "accepting":
                return State.A;

            case "startingaccepting":
                return State.AS;

            default:
                return State.N;
        }
    }

    public static void loader(String fileName, Scanner scanner) {
        TextReader reader = new TextReader(fileName);
        List<nodeBasket> listOfBasket = new ArrayList<nodeBasket>();
        reader.readFile();

        if (reader.getRegex() != "null") {
            System.out.println("Regex " + reader.getRegex() + " Loaded");
            RegularEx ex = new RegularEx(reader.getRegex());

            if (reader.hasCheck()) {
                regexChecker(reader.getCheck(), ex);
                return;
            }

        }

        else if (reader.getStateCounts() != -1) {
            System.out.println(reader.getStateCounts() + " Finite Automata Detected");

            for (int k = 0; k < reader.getStateCounts(); k++) {
                listOfBasket.add(new nodeBasket());

                for (Map.Entry<String, String> entry : reader.getEntrySet(k)) {
                    listOfBasket.get(k).nodeInsert(entry.getKey(), statePicker(entry.getValue()));
                }

                for (int i = 0; i < reader.getLinksSize(k); i++) {
                    List<String> linkList = reader.getLinks(k);
                    String input[] = linkList.get(i).split(" ", 3);
                    listOfBasket.get(k).linkNode(input[0], input[1], input[2].charAt(0));
                }
            }

            if (reader.isDFAconvert()) {
                System.out.println("Converting...");
                listOfBasket.get(0).toNFA();
                listOfBasket.get(0).toDFA();
                listOfBasket.get(0).print();
            }

            if (reader.hasCheck()) {
                System.out.println("Checking Input " + reader.getCheck());
                listOfBasket.get(0).transverse(reader.getCheck());
                return;
            }

            if (reader.isCompare()) {
                DFAcomparator comparator = new DFAcomparator(listOfBasket);

                if (comparator.engine()) {
                    System.out.println("DFA 1 and DFA 2 are Equivalent");
                }

                else {
                    System.out.println("DFA 1 and DFA 2 are not Equivalent");
                }
            }

            if (reader.minimize()) {
                DFAminimizer minimizer = new DFAminimizer(listOfBasket.get(0).getBasket());
                listOfBasket.get(0).basket = minimizer.engine();
                listOfBasket.get(0).print();
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
