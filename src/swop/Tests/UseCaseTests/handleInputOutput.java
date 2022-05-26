package swop.Tests.UseCaseTests;

import swop.Main.AssemAssist;
import swop.UI.LoginUI;
import swop.UI.UI;
import swop.Users.User;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.ListIterator;

public class handleInputOutput {
    private static AssemAssist assem;



    public static ListIterator<String> continueUITest(String inputString, int skips) {
        ByteArrayInputStream input = new ByteArrayInputStream(inputString.getBytes());
        System.setIn(input);
        UI.scanner.updateScanner();

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        new LoginUI(getAssem());

        ListIterator<String> output = Arrays.asList(outContent.toString().split(String.format("%n")))
                .listIterator();

        skip(output, skips);

        return output;
    }

    public static ListIterator<String> setupUITest(String inputString, int skips) {
        assem = new AssemAssist();
        return continueUITest(inputString, skips);
    }


    public static User getUser(String a) {
        if (getAssem() == null) throw new IllegalArgumentException("assem = null");
        return getAssem().getUserMap().get(a);
    }


    public static void skip(ListIterator<String> output, int skips) {
        for (int i = 0; i < skips; i++)
            output.next();

    }

    public static AssemAssist getAssem() {
        return assem;
    }
}
