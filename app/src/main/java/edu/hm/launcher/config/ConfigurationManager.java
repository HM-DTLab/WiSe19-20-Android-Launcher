package edu.hm.launcher.config;

import android.app.Activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Observable;

import edu.hm.launcher.config.container.AppContainer;
import edu.hm.launcher.config.container.ConfigurationContainer;
import edu.hm.launcher.config.parser.ConfigParseException;
import edu.hm.launcher.config.parser.IConfigurationParser;
import edu.hm.launcher.config.parser.XmlFactory;

/**
 * Manages the current configuration.
 * Loads and manages the current configuration while acting as a Observer for changes.
 */
public class ConfigurationManager extends Observable {

    /**
     * The current active configuration.
     */
    private ConfigurationContainer currentConfig = new ConfigurationContainer();

    /**
     * The main parent Activity.
     */
    private Activity mainActivity;

    public ConfigurationManager(Activity mainActivity) {
        this.mainActivity = mainActivity;
    }

    /**
     * Gets all Apps as an unmodifiable list.
     * @return The list.
     */
    public List<AppContainer> getApps() {
        return currentConfig.getApps();
    }

    /**
     * Adds a new App to the end of the list.
     * @param container The to add App.
     */
    public void add(AppContainer container) {
        currentConfig.add(container);
        setChanged();
        notifyObservers(currentConfig);
    }

    /**
     * Changes the App at the index id to the new App.
     * @param id The Apps index id.
     * @param app The new App.
     */
    public void changeAt(int id, AppContainer app) {
        currentConfig.changeAt(id, app);
        setChanged();
        notifyObservers(currentConfig);
    }

    /**
     * Removes the app at the index.
     * @param id The Apps index id.
     */
    public void removeAt(int id) {
        currentConfig.removeAt(id);
        setChanged();
        notifyObservers(currentConfig);
    }

    /**
     * Loads the config from a file.
     * If APPS_DIR/active_config.xml was found loads it, otherwise load config file from
     * assets/xml/config_template.xml.
     * @throws IOException
     */
    public void loadConfig() throws IOException {
        File savedConfig = new File(mainActivity.getFilesDir() + "/active_config.xml");
        InputStream xmlStream;
        if(savedConfig.exists()) {
            xmlStream = new FileInputStream(savedConfig);
        } else {
            xmlStream = mainActivity.getAssets().open("xml/config_template.xml");
        }
        IConfigurationParser parser = XmlFactory.getParser(1);
        try {
            currentConfig = parser.parseConfig(xmlStream);
        } catch (ConfigParseException e) {
            e.printStackTrace();
        }
        setChanged();
        notifyObservers(currentConfig);
    }

}
