package swop.UI;

interface DocumentBuilder {
    void appendTitle(String title);
    void inputInfo(String info);
    void endInfo();
    void addOption(String option, int number);
    void appendSubTitle(String subtitle);
}
