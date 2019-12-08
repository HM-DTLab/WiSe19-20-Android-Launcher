package hm.edu.launcher.config;

import android.database.Observable;

import hm.edu.launcher.config.container.ConfigurationContainer;

public class ConfigurationManager extends Observable<ConfigurationContainer> {

    private ConfigurationContainer currentConfig;

    ConfigurationManager() {

    }


}
