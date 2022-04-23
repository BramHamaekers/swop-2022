package swop.UI.Generators;

import swop.UI.Builders.DisplayStatus;

import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class ManagerGenerator extends UserGenerator {
    public void generateCarModels(DisplayStatus builder, List<Map<String, String>> possibleBatch) {
        builder.appendTitle("Possible batchoptions");
        ListIterator<Map<String,String>> it = possibleBatch.listIterator();
        while(it.hasNext()) {
            builder.inputInfo(String.format("%s [%d]", it.next(), (it.nextIndex()-1)));
        }
        builder.endInfo();
        builder.addOption("Select the batch", possibleBatch.size());
    }
}
