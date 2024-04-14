import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class TextReader {
    private String fileName;
    private String Regex;
    private String check;
    private boolean DFAconvert;
    private List<List<String>> links;
    private List<Map<String, String>> stateMap;

    public TextReader(String fileName) {
        this.fileName = fileName;
        this.Regex = "null";
        this.check = "null";
        this.DFAconvert = false;
        this.links = new ArrayList<>();
        this.stateMap = new ArrayList<>();
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
                    line.equalsIgnoreCase("check") ||
                    line.equalsIgnoreCase("dfaconvert")) {

                    mode = line;
                    if(mode.equalsIgnoreCase("state")) {
                        this.stateMap.add(new HashMap<>());
                        this.links.add(new ArrayList<>());
                    } else if(mode.equalsIgnoreCase("dfaconvert")) {
                        this.DFAconvert = true;
                    }
                    continue;
                }

                if(mode.equalsIgnoreCase("regex")) {
                    this.Regex = line;
                    continue;
                }

                if (mode.equalsIgnoreCase("state")) { 
                    String[] parts = line.split(" ");
                    if (parts.length >= 2) {
                        this.stateMap.get(getStateCounts() - 1).put(parts[0], parts[1].toLowerCase());
                    }
                    continue;
                } 

                if(mode.equalsIgnoreCase("links")) {
                    this.links.get(getStateCounts() - 1).add(line);
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

    public List<String> getLinks(int num) {
        return this.links.get(num);
    }

    public int getLinksSize(int i) {
        return this.links.get(i).size();
    }

    public String getRegex() {
        return this.Regex;
    }

    public Map<String, String> getStateMap(int num) {
        return this.stateMap.get(num);
    }

    public int getStateCounts() {
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

    public Set<Entry<String, String>> getEntrySet(int num) {
        return this.stateMap.get(num).entrySet();
    }

    public boolean isDFAconvert() {
        return this.DFAconvert;
    }

}