package swop;

import java.io.FileReader;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

public class Login_UI extends UI{

	
	
	private Scanner input_scanner;
	private String user_name;
	private JSONArray UserList;
	
	@Override
	public void load() {	
		this.input_scanner = new Scanner(System.in);
		System.out.println("Please login with username 2 continue");
		user_name = input_scanner.nextLine();
		this.loadUserDatabase();
		System.out.println(UserList);
		
		
	}
	private void loadUserDatabase() {
		JSONParser jsonParser = new JSONParser();
        
        try (FileReader data = new FileReader("users.json"))
        {
            Object data_obj = jsonParser.parse(data);
            UserList = (JSONArray) data_obj; 
        } 
        catch (Exception e) {
        	e.printStackTrace();
            //System.out.println("ERROR COULDN'T CONNECT TO DATABASE");
        }
		
	}

}
