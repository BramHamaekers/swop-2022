package swop.Users;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import swop.UI.GarageHolderUI;

import java.io.FileReader;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GarageHolder extends User{
    private Map<String, List<String>> optionsMap;

    public GarageHolder(String id) {
        super(id);
        this.setOptionsMap(loadOptionsDatabase());
        // TODO: move to different location? -> everytime new Garageholder loads optionsMap. Should not be the case
        // -> create static database somehow
    }

    /**
     * Called when logging in as GarageHolder
     */
    public void load() {
        GarageHolderUI.init(this.getId());
        GarageHolderUI.displayOrderingForm(this.getOptionsMap());
    }




    /************************ Options Database *************************/

    /**
     * Parses the carOptions.json file and returns it as a LinkedHashMap<String, List<String>> of all available options
     * @return LinkedHashMap<String, List<String>> || null
     */
    private LinkedHashMap<String, List<String>> loadOptionsDatabase() {
        JSONParser jsonParser = new JSONParser();
        try (FileReader data = new FileReader("carOptions.json")) {
            return this.parseOptionsJSONArrayToMap((JSONArray) jsonParser.parse(data));
        }
        catch (Exception e) {e.printStackTrace();}
        return null;
    }

    /**
     * Parses the JSONArray obtainded from carOptions.json and returns it as a linked hashmap
     * @param optionsList JSONArray containing the components and its available options
     * @return LinkedHashMap<String, List<String>> of all components and its available options
     */
    private LinkedHashMap<String, List<String>> parseOptionsJSONArrayToMap(JSONArray optionsList) {
        LinkedHashMap <String, List<String>> map = new LinkedHashMap<>();
        for (JSONObject user : (Iterable<JSONObject>) optionsList) {
            map.put((String) user.get("component"), (List<String>) user.get("options"));
        }
        return map;
    }

    /**
     * Check if given optionsMap is a valid userMap
     * @param optionsMap LinkedHashMap<String, List<String>> to check
     * @return optionsMap != null
     */
    private boolean isValidOptionsMap(Map<String, List<String>> optionsMap) {
        return optionsMap != null;
    }

    public Map<String, List<String>> getOptionsMap() {
        return optionsMap;
    }

    public void setOptionsMap(Map<String, List<String>> optionsMap) {
        if (!isValidOptionsMap(optionsMap)) {
            System.out.println("Fail! optionsMap not valid"); //TODO: should throw error
        }
        this.optionsMap = optionsMap;
    }
}
