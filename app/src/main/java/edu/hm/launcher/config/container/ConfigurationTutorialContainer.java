package edu.hm.launcher.config.container;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConfigurationTutorialContainer extends ConfigurationContainer {

    private final List<TutorialContainer> tutorials = new ArrayList<>();

    public List<TutorialContainer> getTutorials() {
        return Collections.unmodifiableList(tutorials);
    }

    public void add(TutorialContainer container) {
        tutorials.add(container);
    }
}
