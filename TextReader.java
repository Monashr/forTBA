import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TextReader {
    private String fileName;
    private String Regex;
    private List<String> links;
    private Map<String, String> stateMap;

    public TextReader(String fileName) {
        this.fileName = fileName;
        this.Regex = "null";
        this.links = new ArrayList<>();
        this.stateMap = new HashMap<>();
    }

    public void readFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean readingStates = true;
            boolean isItRegex = false;
            while ((line = br.readLine()) != null) {

                if(line.toLowerCase().equals("regex")) {
                    isItRegex = true;
                    continue;
                }

                if(isItRegex) {
                    this.Regex = line;
                    break;
                }

                if (line.toLowerCase().equals("link")) {
                    readingStates = false;
                    continue;
                }

                if (readingStates) {
                    String[] parts = line.split(" ");
                    if (parts.length >= 2) {
                        this.stateMap.put(parts[0], parts[1]);
                    }
                } 
                
                else {
                    this.links.add(line);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getLinks() {
        return this.links;
    }

    public String getRegex() {
        return this.Regex;
    }

    public Map<String, String> getStateMap() {
        return this.stateMap;
    }
}
