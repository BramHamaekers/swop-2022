package swop.UI.Generators;

import swop.UI.Builders.DisplayStatus;
import swop.UI.Builders.FormBuilder;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;

public class ManagerGenerator extends UserGenerator {
    public void generateBatchSelection(DisplayStatus builder, List<Map<String, String>> possibleBatch) {
        builder.appendTitle("Possible batchoptions");
        ListIterator<Map<String,String>> it = possibleBatch.listIterator();
        while(it.hasNext()) {
            builder.inputInfo(String.format("%s [%d]", it.next(), (it.nextIndex()-1)));
        }
        builder.endInfo();
        builder.addOption("Select the batch", possibleBatch.size());
    }

    public void generateAlgorithmSelection(DisplayStatus builder,List<String> actions, String activeAlgorithm) {
        builder.initialQuestion("The available algorithms: ");
        builder.appendSubTitle("Current algorithm: " + activeAlgorithm);
        Iterator<String> it = actions.iterator();
        int i = 0;
        while(it.hasNext()) {
            builder.inputInfo(String.format("%s [%s]", it.next(), i));
            i++;
        }
        builder.addOption("Select option", actions.size());
    }
    
    public void generateProductionStatistics(DisplayStatus builder, Map<String, Double> productionStatistics){
        builder.appendTitle("Production Statistics");
        builder.appendSubTitle("Cars Produced info");
        for (Entry<String, Double> s : productionStatistics.entrySet()) {
        	if(s.getValue() != null)
        		builder.inputInfo(s.getKey()+ ": " + s.getValue());
        	else
        		builder.inputInfo(s.getKey()+ ": " + "No info");
        }
        builder.endInfo();
        builder.inputInfo("Done viewing -> press enter");
    }
}
