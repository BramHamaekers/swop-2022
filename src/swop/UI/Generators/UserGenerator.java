package swop.UI.Generators;

import swop.UI.FormBuilder;

import java.util.Iterator;
import java.util.List;

public class UserGenerator {

    public void selectAction(FormBuilder builder, List<String> actions) {
        builder.appendTitle("What would you like to do?");
        Iterator<String> it = actions.iterator();
        int i = 0;
        while(it.hasNext()) {
            builder.inputInfo(String.format("%s [%s]", it.next(), i));
            i++;
        }
        builder.endInfo();
        builder.addOption("Select action", actions.size());
    }
}
