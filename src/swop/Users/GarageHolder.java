package swop.Users;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import swop.CarManufactoring.Order;
import swop.UI.GarageHolderUI;

import java.io.FileReader;
import java.util.*;

public class GarageHolder extends User{
    private LinkedHashMap<String, List<String>> optionsMap;
    private Set<Order> orders;

    public GarageHolder(String id) {
        super(id);
        this.setOptionsMap(loadOptionsDatabase());
        // TODO: move to different location? -> everytime new Garageholder loads optionsMap. Should not be the case
        // -> create static database somehow
        this.orders = new HashSet<>();

        /////////// TEST ///////////
        List<String> parts = new ArrayList<>();
        parts.add("wheeeeel");
        Order a = new Order(parts);
        this.addOrder(a);

        Order b = new Order(parts);
        b.upBuildState();
        b.upBuildState();
        b.upBuildState();
        this.addOrder(b);
    }

    /**
     * Called when logging in as GarageHolder
     */
    public void load() {
        GarageHolderUI.init(this.getId());
        GarageHolderUI.displayOrders(this.orders);

        String action = GarageHolderUI.confirmAction();
        while (!isValidAction(action)) {
            action = GarageHolderUI.confirmAction();
        }
        if (Objects.equals(action, "n")) return;

        String model = GarageHolderUI.indicateCarModels();
        while (!isValidModel(model)) {
            model = GarageHolderUI.indicateCarModels();
        }

        GarageHolderUI.displayOrderingForm(this.getOptionsMap());
        //TODO alternate flow: cancel placing order
        List<Integer> order = GarageHolderUI.fillOrderingForm(this.getOptionsMap());

        //TODO: update production schedule
        //TODO: present an estimated completion date

        this.logout();
    }

    /************************ Checkers *************************/

    private boolean isValidAction(String action) {
        return Objects.equals(action, "y") || Objects.equals(action, "n");
    }

    private boolean isValidModel(String model) {
        return Objects.equals(model, "0");
    }

    /************************ Orders *************************/
    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public void addOrder(Order order) {
        this.orders.add(order);
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
    private boolean isValidOptionsMap(LinkedHashMap<String, List<String>> optionsMap) {
        return optionsMap != null;
    }

    public LinkedHashMap<String, List<String>> getOptionsMap() {
        return optionsMap;
    }

    public void setOptionsMap(LinkedHashMap<String, List<String>> optionsMap) {
        if (!isValidOptionsMap(optionsMap)) {
            System.out.println("Fail! optionsMap not valid"); //TODO: should throw error
        }
        this.optionsMap = optionsMap;
    }
}
