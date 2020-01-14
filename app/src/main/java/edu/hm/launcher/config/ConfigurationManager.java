package edu.hm.launcher.config;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

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

    private final PackageManager packageManager;

    public ConfigurationManager(Activity mainActivity) {
        this.mainActivity = mainActivity;
        packageManager = mainActivity.getPackageManager();
        addObserver(this::saveConfig);
    }

    public ConfigurationManager(Activity mainActivity, ConfigurationContainer container) {
        this.mainActivity = mainActivity;
        addObserver(this::saveConfig);
        currentConfig = container;
        packageManager = mainActivity.getPackageManager();
        setAllAppsPackageManager();
    }

    /**
     * Gets all Apps as an unmodifiable list.
     * @return The list.
     */
    public List<AppContainer> getApps() {
        return new ArrayList<>(currentConfig.getApps());
    }

    /**
     * Adds a new App to the end of the list.
     * @param container The to add App.
     * @return Whether the app was added, false if already added
     */
    public boolean add(AppContainer container) {
        if(contains(container.getPackageName())) return false;

        currentConfig.add(container);
        setAppPackageManager(container);
        setChanged();
        notifyObservers(currentConfig);
        return true;
    }

    /**
     * Changes the App at the index id to the new App.
     * @param id The Apps index id.
     * @param app The new App.
     */
    public void changeAt(int id, AppContainer app) {
        currentConfig.changeAt(id, app);
        setAppPackageManager(app);
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

    public void remove(String packageName) {
        List<AppContainer> allApps = currentConfig.getApps();
        for (int i = 0; i < allApps.size(); i++) {
            if(allApps.get(i).getPackageName().equals(packageName)) {
                removeAt(i);
                return;
            }
        }
    }

    /**
     * Checks whether the given packagename is already contained in the current config.
     * @param packageName
     * @return
     */
    public boolean contains(String packageName) {
        List<AppContainer> allApps = currentConfig.getApps();
        for (int i = 0; i < allApps.size(); i++) {
            if(allApps.get(i).getPackageName().equals(packageName)) {
                return true;
            }
        }
        return false;
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
        setAllAppsPackageManager();
        setChanged();
        notifyObservers(currentConfig);
    }

    /**
     * Sets the package manager of all AppContainer, therefor loading their label and icon.
     */
    public void setAllAppsPackageManager() {
        for (AppContainer app : getApps()) {
            setAppPackageManager(app);
        }
    }

    /**
     * Sets the package managers off an AppContainer
     * @param app
     */
    private void setAppPackageManager(AppContainer app) {
        try {
            app.setPackageManager(packageManager);
        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(mainActivity.getApplicationContext(),
                    e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Saves the current config to the /active_config.xml file.
     * @param ob (ignored).
     * @param arg (ignored).
     */
    private void saveConfig(Observable ob, Object arg) {
        try {
            DOMSource source = currentConfig.toXml();
            try(FileOutputStream writer = new FileOutputStream(
                    new File(mainActivity.getFilesDir() + "/active_config.xml"))) {
                TransformerFactory.newInstance().newTransformer()
                        .transform(source, new StreamResult(writer));
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

}
