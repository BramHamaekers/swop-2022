package swop.UI.Builders;

public class DisplayStatus implements FormBuilder {
    int titlelength = 0;
    StringBuilder builder = new StringBuilder();

    void print(String text) {
        builder.append(text);
    }
    void println(String text) {
        print(text);
        print("\n");
    }

    public String getDisplay() {
        return builder.toString();
    }

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
        this.titlelength = str.length();
    }

    @Override
    public void inputInfo(String info) {
        println(info);
    }

    @Override
    public void endInfo() {
        for(int i = 0; i< (this.titlelength-1); i++){
            print("=");
        }
        println("=");
    }

    @Override
    public void addOption(String option, int options) {
        if (options == 2) {
            print(String.format("%s [y] Yes [n] No: ", option));
        } else {
            print(String.format("%s %s: ", option, numberOptions(options)));
        }
    }

    @Override
    public void appendSubTitle(String subtitle) {
        println("---" + subtitle + "---");
    }
}

