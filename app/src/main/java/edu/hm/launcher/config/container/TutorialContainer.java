package edu.hm.launcher.config.container;

import android.graphics.drawable.Drawable;

public class TutorialContainer {

    private final String tutorialAppTitle;

    private final String tutorialFolder;

    private final String[] tutorialTitle;

    private final String[] tutorialFiles;

    private final String tutorialDrawable;

    public TutorialContainer(String tutorialAppTitle, String tutorialFolder, String[] tutorialTitle, String[] tutorialFiles, String tutorialDrawable) {
        this.tutorialAppTitle = tutorialAppTitle;
        this.tutorialFolder = tutorialFolder;
        this.tutorialFiles = tutorialFiles;
        this.tutorialTitle = tutorialTitle;
        this.tutorialDrawable = tutorialDrawable;
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

    public String getTutorialDrawable()   {
        return tutorialDrawable;
    }
}
