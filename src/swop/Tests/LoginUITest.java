package swop.Tests;

public class LoginUITest {

    // TEST OUTDATED
    /**
    LoginUI login = new LoginUI();



    @Test
    void constructor() {
        assertEquals(login.getID(),"login");
    }

    @Test
    void loadUsersJSONTest() {
        // UserMap should be null
        assertEquals(login.getUserMap(), null);

        // create Test Map
        Map <String, String> testUserMap = new HashMap<>();
        testUserMap.put("a", "garage holder");
        testUserMap.put("b", "car mechanic");
        testUserMap.put("c", "manager");

        // Set input to a
        InputStream sysInBackup = System.in; // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream("a".getBytes());
        System.setIn(in);

        // Load LoginUI
        login.load();

        assertEquals(login.getUserMap(), testUserMap);
        assertEquals(login.getUserID(), "a");
        assertEquals(login.getKeyValue(), "garage holder");

        // reset System.in to its original
        System.setIn(sysInBackup);
    }
    */
}
