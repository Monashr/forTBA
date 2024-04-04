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
    private String check;
    private List<String> links;
    private Map<String, String> stateMap;

    public TextReader(String fileName) {
        this.fileName = fileName;
        this.Regex = "null";
        this.check = "null";
        this.links = new ArrayList<>();
        this.stateMap = new HashMap<>();
    }

    public void readFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            String mode = null;
            while ((line = br.readLine()) != null) {

                if(
                    line.equalsIgnoreCase("regex") || 
                    line.equalsIgnoreCase("state") || 
                    line.equalsIgnoreCase("links") || 
                    line.equalsIgnoreCase("check") ) {

                    mode = line;
                    continue;
                }

                if(mode.equalsIgnoreCase("regex")) {
                    this.Regex = line;
                    continue;
                }

                if (mode.equalsIgnoreCase("state")) {
                    String[] parts = line.split(" ");
                    if (parts.length >= 2) {
                        this.stateMap.put(parts[0], parts[1]);
                    }
                    continue;
                } 

                if(mode.equalsIgnoreCase("links")) {
                    this.links.add(line);
                    continue;
                }

                if(mode.equalsIgnoreCase("check")) {
                    this.check = line;
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

    public int getStateCount() {
        return this.stateMap.size();
    }

    public boolean hasCheck() {
        if(this.check.equals("null")) {
            return false;
        }

        return true;
    }

    public String getCheck() {
        return this.check;
    }

}