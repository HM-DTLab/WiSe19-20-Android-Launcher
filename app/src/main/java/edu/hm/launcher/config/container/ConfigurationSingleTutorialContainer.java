package edu.hm.launcher.config.container;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConfigurationSingleTutorialContainer extends ConfigurationContainer {

    private final List<SingleTutorialContainer> tutorials = new ArrayList<>();

    public List<SingleTutorialContainer> getTutorials() {
        return Collections.unmodifiableList(tutorials);
    }

    public void add(SingleTutorialContainer container) {
        tutorials.add(container);
    }

}
