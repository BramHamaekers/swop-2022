package swop.UI.Generators;

import swop.Records.AllStats;
import swop.UI.Builders.DisplayStatus;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * A generator for all the output of Manager
 */
public class ManagerGenerator extends UserGenerator {
    /**
     * Generate the output text for all the possible batches
     * @param builder the builder used in displaying the possible batches
     * @param possibleBatch a list of all possible batches to display
     */
    public void generateBatchSelection(DisplayStatus builder, List<Map<String, String>> possibleBatch) {
        builder.appendTitle("Possible batchoptions");
        ListIterator<Map<String,String>> it = possibleBatch.listIterator();
        while(it.hasNext()) {
            builder.inputInfo(String.format("%s [%d]", it.next(), (it.nextIndex()-1)));
        }
        builder.endInfo();
        builder.addOption("Select the batch", possibleBatch.size());
    }

    /**
     * Generate the output text for all the possible algorithms
     * @param builder the builder used in displaying the possible algorithms
     * @param algorithms a list of all available algorithms
     * @param activeAlgorithm the current algorithm
     */
    public void generateAlgorithmSelection(DisplayStatus builder,List<String> algorithms, String activeAlgorithm) {
        builder.initialQuestion("The available algorithms: ");
        builder.appendSubTitle("Current algorithm: " + activeAlgorithm);
        Iterator<String> it = algorithms.iterator();
        int i = 0;
        while(it.hasNext()) {
            builder.inputInfo(String.format("%s [%s]", it.next(), i));
            i++;
        }
        builder.addOption("Select option", algorithms.size());
    }

    /**
     * Generate the output text for all the statistics
     * @param builder the builder used in displaying the statistics
     * @param stats all relevant statistics of type {@code AllStats}
     */
    public void generateProductionStatistics(DisplayStatus builder, AllStats stats){
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
        i = 0;
        for(var delay: stats.delayLast2().entrySet()) {	
        	builder.inputInfo(String.format("Delay on day %s: %s",delay.getKey(), delay.getValue().toString()));
        	i++;}
        builder.endInfo();
        builder.inputInfo("Done viewing -> press enter");
    }
}
