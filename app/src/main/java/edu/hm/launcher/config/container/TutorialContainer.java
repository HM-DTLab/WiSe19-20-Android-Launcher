package edu.hm.launcher.config.container;

public class TutorialContainer {

    private final String tutorialAppTitle;

    private final String tutorialFolder;

    private final String[] tutorialTitle;

    private final String[] tutorialFiles;

    public TutorialContainer(String tutorialAppTitle, String tutorialFolder, String[] tutorialTitle, String[] tutorialFiles) {
        this.tutorialAppTitle = tutorialAppTitle;
        this.tutorialFolder = tutorialFolder;
        this.tutorialFiles = tutorialFiles;
        this.tutorialTitle = tutorialTitle;
    }

    public String getTutorialAppTitle() {
        return tutorialAppTitle;
    }

    public String getTutorialFolder()   {
        return tutorialFolder;
    }

    public String[] getTutorialTitle() {
        return tutorialTitle;
    }

    public String[] getTutorialFiles() {
        return tutorialFiles;
    }
}
