package hm.edu.launcher.config;

import android.app.Activity;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Observable;

import hm.edu.launcher.config.container.AppContainer;
import hm.edu.launcher.config.container.ConfigurationContainer;
import hm.edu.launcher.config.parser.ConfigParseException;
import hm.edu.launcher.config.parser.IConfigurationParser;
import hm.edu.launcher.config.parser.XmlFactory;

public class ConfigurationManager extends Observable {

    private ConfigurationContainer currentConfig;
    private Activity mainActivity;

    ConfigurationManager(Activity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public List<AppContainer> getApps() {
        return currentConfig.getApps();
    }

    public void add(AppContainer container) {
        currentConfig.add(container);
    }

    public void changeAt(int id, AppContainer app) {
        currentConfig.changeAt(id, app);
    }

    public void removeAt(int id) {
        currentConfig.removeAt(id);
    }

    public void loadConfig() throws IOException {
        IConfigurationParser parser = XmlFactory.getParser(1);
        InputStream xmlStream = mainActivity.getAssets().open("xml/config_template.xml");
        try {
            currentConfig = parser.parseConfig(xmlStream);
        } catch (ConfigParseException e) {
            e.printStackTrace();
        }
        setChanged();
        notifyObservers(currentConfig);
    }

}
