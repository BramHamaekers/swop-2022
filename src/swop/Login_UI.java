package swop;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Login_UI extends UI{

	
	
	private Scanner input_scanner;
	private String user_name;
	private Map usermap;
	@Override
	public void load() {	
		
		this.input_scanner = new Scanner(System.in);
		System.out.println("Please login with username 2 continue");
		user_name = input_scanner.nextLine();
		usermap = this.loadUserDatabase();
		if(usermap.containsKey(user_name)) System.out.println("Succes! change State/UI");
		else System.out.println("Fail! User not found");
		
	}
	
	private Map<String, String> loadUserDatabase() {
		JSONParser jsonParser = new JSONParser();
        
        try (FileReader data = new FileReader("users.json"))
        {
            Object data_obj = jsonParser.parse(data);
            return parseJSONArrayToMap ((JSONArray) data_obj);
        } 
        catch (Exception e) {
        	e.printStackTrace();
            //System.out.println("ERROR COULDN'T CONNECT TO DATABASE");
        }
		return null;
		
	}
	
	private Map<String, String> parseJSONArrayToMap(JSONArray userlist) {
		Map <String, String> map = new HashMap<>();
		String name;
		String job;
		Iterator<JSONObject> it = userlist.iterator();
		while(it.hasNext()) {
			JSONObject user = it.next();
			name = (String) user.get("name");
			job = (String) user.get("job");
			map.put(name, job);
		}
		return map;
	}

}
