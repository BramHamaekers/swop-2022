package swop.Database;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import swop.Users.CarMechanic;
import swop.Users.GarageHolder;
import swop.Users.Manager;
import swop.Users.User;

public class ConvertMapType {

	/************************ User Map *************************/

	public static Map<String, User> changeToUserMap(Map<String, List<String>> userDatabase) {
		Map<String, User> userMap = new HashMap<>();
		userDatabase.forEach((id, job) -> userMap.put(id, createUser(id, job)));
		return userMap;
	}

	private static User createUser(String id, List<String> job) {
		for(String j : job) {
			switch (j) {
			case "garage holder":
				return new GarageHolder(id);
			case "car mechanic":
				return new CarMechanic(id);
			case "manager":
				return new Manager(id);
			default:
				System.out.println("Given job is not a valid job");
				return null; //TODO: should just throw an error and not return anything
			}
		}
		return null;
	}
}
