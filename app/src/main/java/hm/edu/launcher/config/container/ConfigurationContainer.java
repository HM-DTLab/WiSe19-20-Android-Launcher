package hm.edu.launcher.config.container;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConfigurationContainer {

    private final List<AppContainer> apps = new ArrayList<>();

    public ConfigurationContainer() {
    }

    public List<AppContainer> getApps() {
        return Collections.unmodifiableList(apps);
    }

    public void add(AppContainer container) {
        apps.add(container);
    }
}
