package swop.UI.Builders;

/**
 * A class to implement the {@code FormBuilder} for our cli interface
 */
public class DisplayStatus implements FormBuilder {
    /**
     * The length that the title has when displaying a title
     */
    int titleLength = 0;
    /**
     * The StringBuilder used in displaying information.
     */
    StringBuilder builder = new StringBuilder();

    /**
     * adds the text to the builder
     * @param text a given string
     */
    void print(String text) {
        builder.append(text);
    }

    /**
     * adds the text to the builder and adds a newline
     * @param text a given string
     */
    void println(String text) {
    	print(text + String.format("%n"));
    }

    /**
     * converts builder to string
     * @return a string from builder
     */
    public String getDisplay() {
        return builder.toString();
    }

    /**
     * converts a number of options to a string with these numbers
     * @param opts a number of options
     * @return a string containing the numbers
     */
    String numberOptions(int opts) {
        StringBuilder res = new StringBuilder();
        for(int i = 0; i<opts; i++)
            res.append(String.format("[%d] ",i));
        return res.toString();
    }

    @Override
    public void appendTitle(String title){
        String str = String.format("============ %s ============",title);
        println(str);
        this.titleLength = str.length();
    }

    @Override
    public void inputInfo(String info) {
        println(info);
    }

    @Override
    public void endInfo() {
        for(int i = 0; i< (this.titleLength -1); i++){
            print("=");
        }
        println("=");
    }

    @Override
    public void addOption(String option, int options) {
        print(String.format("%s %s: ", option, numberOptions(options)));
    }

    @Override
    public void appendSubTitle(String subtitle) {
        println("---" + subtitle + "---");
    }

    @Override
    public void initialQuestion(String question) {
        println(question);
    }
}

