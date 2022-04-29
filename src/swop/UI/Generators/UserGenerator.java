package swop.UI.Generators;

import swop.UI.Builders.FormBuilder;

import java.util.Iterator;
import java.util.List;

/**
 * class the represents a default generator for each type of user
 */
public class UserGenerator {

	/**
	 * Will at an action string block to the given builder consisting of a question and a list of actions
	 * @param builder the FormBuilder where the string block will be added to
	 * @param actions List<String> of actions
	 * @param question string with the question
	 */
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
