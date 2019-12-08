package hm.edu.launcher.config.parser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import hm.edu.launcher.config.container.AppContainer;
import hm.edu.launcher.config.container.ConfigurationContainer;

public class XmlParserV1 implements IConfigurationParser {

    @Override
    public ConfigurationContainer parseConfig(InputStream xmlStream) throws IOException, ConfigParseException {
        ConfigurationContainer container = new ConfigurationContainer();

        try {
            DocumentBuilder xmlBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

            Document xmlDocument = xmlBuilder.parse(xmlStream);

            NodeList appList = xmlDocument.getElementsByTagName("app");

            AppContainer[] apps = new AppContainer[appList.getLength()];

            for (int i = 0; i < appList.getLength(); i++) {
                if (appList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    final Element appElement = (Element) appList.item(i);
                    final int appId = Integer.parseInt(appElement.getAttribute("id"));
                    final String appName = appElement.getTextContent();
                    if(apps.length <= appId) {
                        throw new ConfigParseException(String.format("Id %d is bigger then length", appId));
                    }
                    if(apps[appId] != null) {
                        throw new ConfigParseException(String.format("Id %d is contained multiple times", appId));
                    }
                    apps[appId] = new AppContainer(appName);
                }
            }
            for (int i = 0; i < apps.length; i++) {
                AppContainer app = apps[i];
                if(app == null) {
                    throw new ConfigParseException(String.format("Id %d is not contained", i));
                }
                container.add(app);
            }

        } catch (ParserConfigurationException | SAXException e) {
            throw new IOException(e);
        }

        return container;
    }
}
