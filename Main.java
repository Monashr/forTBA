import java.util.Scanner;

public class Main {

    public static void loader(String fileName) {
        TextReader reader = new TextReader(fileName);
        reader.readFile();

        if(reader.getRegex() != "null") {
            System.out.print("Regex Detected");
            RegularEx ex = new RegularEx(reader.getRegex());

            if(reader.hasCheck()) {
                if(ex.compare(reader.getCheck())) {
                    System.out.println("Benar");
                } else {
                    System.out.println("Salah");
                };
            }


        } else if(reader.getStateCount() != 0) {
            System.out.print("Finite Automata Detected");
        }
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        nodeBasket basket = new nodeBasket();

        System.out.print("Input Filename : example.txt");
        String filename = "example.txt";//scanner.nextLine();
        loader(filename);

        scanner.close();
    }
}
