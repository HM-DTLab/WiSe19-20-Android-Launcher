package edu.hm.launcher.config.container;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;

/**
 * Simple container for several sorted AppContainers
 */
public class ConfigurationContainer {

    private final List<AppContainer> apps = new ArrayList<>();

    public List<AppContainer> getApps() {
        return Collections.unmodifiableList(apps);
    }

    public void add(AppContainer container) {
        apps.add(container);
    }

    public void changeAt(int id, AppContainer app) {
        apps.set(id, app);
    }

    public void removeAt(int id) {
        apps.remove(id);
    }

    public DOMSource toXml() throws ParserConfigurationException {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = builder.newDocument();
        Element confRoot = document.createElement("configuration");
        document.appendChild(confRoot);

        for (int i = 0; i < apps.size(); i++) {
            Element appElement = document.createElement("app");
            appElement.setAttribute("id", String.valueOf(i));
            appElement.setTextContent(apps.get(i).getAppName());
            confRoot.appendChild(appElement);
        }
        return new DOMSource(document);
    }
}
