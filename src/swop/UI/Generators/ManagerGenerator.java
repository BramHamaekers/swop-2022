package swop.UI.Generators;

import swop.Records.allStats;
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
    
    public void generateProductionStatistics(DisplayStatus builder, allStats stats){
        builder.appendTitle("Production Statistics");
        builder.appendSubTitle("Cars Produced info");
        builder.inputInfo(String.format("Avg cars produced daily: %2f",stats.avgOrders()));
        builder.inputInfo(String.format("Median cars produced daily: %2f",stats.mdnOrders()));
        String[] day = {"today","yesterday"};
        int i = 0;
        for(var cars: stats.ordersLast2().entrySet()) {	
        	builder.inputInfo(String.format("Cars produced %s: %d",day[i], cars.getValue()));
        	i++;}
        builder.appendSubTitle("Cars Delay info");
        builder.inputInfo(String.format("Avg delay daily: %2f",stats.avgDelay()));
        builder.inputInfo(String.format("Median delay daily: %2f",stats.mdnDelay()));
        String[] d = {"Most recent delay","Delay before"};
        i = 0;
        for(int delay: stats.delayLast2()) {	
        	builder.inputInfo(String.format("%s: %d",d[i], delay));
        	i++;}
        builder.endInfo();
        builder.inputInfo("Done viewing -> press enter");
    }
}
