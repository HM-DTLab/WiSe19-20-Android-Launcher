package edu.hm.launcher.config.container;

public class SingleTutorialContainer {

    private final String image;

    private final String description;

    public SingleTutorialContainer(String image, String description) {
        this.image = image;
        this.description = description;
    }

    public String getImages() {
        return image;
    }

    public String getDescription() {
        return description;
    }
}
