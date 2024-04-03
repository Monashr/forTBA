public class Main {

    public void mainMenu() {
        System.out.println("Load text");
    }

    public static void main(String[] args) {

        TextReader reader = new TextReader("example.txt");
        reader.readFile();

    }
}
