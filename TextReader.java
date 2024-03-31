import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TextReader {
    private String fileName;

    public TextReader(String fileName) {
        this.fileName = fileName;
    }

    public void readFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        TextReader reader = new TextReader("example.txt");
        reader.readFile();
    }
}
