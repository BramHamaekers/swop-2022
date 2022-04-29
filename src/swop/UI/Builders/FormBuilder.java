package swop.UI.Builders;

public interface FormBuilder {
	/**
	 * Will create a line formatted as title and add it to the builder
	 * @param title is string that should be the title
	 */
    void appendTitle(String title);
    
    /**
     * Will add an extra line with info to the builder
     * @param info is string with the info
     */
    void inputInfo(String info);
    
    /**
     * If you want to have a clear end line 
     */
    void endInfo();
    
    /**
     * Will add a string line with option info to the builder + will store this as a possible option the user can select later
     * @param option string with option
     * @param number
     */
    void addOption(String option, int number);
    
    /**
     * Will create a line formatted as subtitle and add it to the builder
     * @param subtitle string that should be the subtitle
     */
    void appendSubTitle(String subtitle);
    
    /**
     * Will ad a string line with the question and ask you later a yes or no answer
     * @param question string with the question
     */
    void initialQuestion(String question);
}
