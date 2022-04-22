package swop.UI.Generators;

import swop.UI.Builders.FormBuilder;

import java.util.Iterator;
import java.util.List;

public class UserGenerator {

    public void selectAction(FormBuilder builder, List<String> actions, String question) {
        builder.initialQuestion(question);
        Iterator<String> it = actions.iterator();
        int i = 0;
        while(it.hasNext()) {
            builder.inputInfo(String.format("%s [%s]", it.next(), i));
            i++;
        }
        builder.addOption("Select option", actions.size());
    }
}
