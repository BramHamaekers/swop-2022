package swop.UI.Builders;

public interface FormBuilder {
    void appendTitle(String title);
    void inputInfo(String info);
    void endInfo();
    void addOption(String option, int number);
    void appendSubTitle(String subtitle);
}
