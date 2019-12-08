package hm.edu.launcher.config;

import android.app.Activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Observable;

import hm.edu.launcher.config.container.AppContainer;
import hm.edu.launcher.config.container.ConfigurationContainer;
import hm.edu.launcher.config.parser.ConfigParseException;
import hm.edu.launcher.config.parser.IConfigurationParser;
import hm.edu.launcher.config.parser.XmlFactory;

/**
 * Manages the current configuration.
 * Loads and manages the current configuration while acting as a Observer for changes.
 */
public class ConfigurationManager extends Observable {

    /**
     * The current active configuration.
     */
    private ConfigurationContainer currentConfig;

    /**
     * The main parent Activity.
     */
    private Activity mainActivity;

    ConfigurationManager(Activity mainActivity) {
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
    }

    /**
     * Changes the App at the index id to the new App.
     * @param id The Apps index id.
     * @param app The new App.
     */
    public void changeAt(int id, AppContainer app) {
        currentConfig.changeAt(id, app);
    }

    /**
     * Removes the app at the index.
     * @param id The Apps index id.
     */
    public void removeAt(int id) {
        currentConfig.removeAt(id);
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
