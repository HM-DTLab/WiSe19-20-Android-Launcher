package edu.hm.launcher.config.parser;

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

import edu.hm.launcher.config.container.ConfigurationTutorialContainer;
import edu.hm.launcher.config.container.TutorialContainer;

public class XmlParserTutorial implements IConfigurationParser {

    @Override
    public ConfigurationTutorialContainer parseConfig(InputStream xmlStream) throws IOException, ConfigParseException {

        // Creates new container
        ConfigurationTutorialContainer container = new ConfigurationTutorialContainer();

        try {
            // Build document from stream
            DocumentBuilder xmlBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document xmlDocument = xmlBuilder.parse(xmlStream);

            // Then find all Tutorial nodes
            NodeList tutorialList = xmlDocument.getElementsByTagName("AppGroup");

            // Create an tutorials buffer to sort them
            TutorialContainer[] tutorials = new TutorialContainer[tutorialList.getLength()];

            for (int i = 0; i < tutorialList.getLength(); i++) {
                if (tutorialList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    // Get node and parse id and name
                    final Element tutorialElement = (Element) tutorialList.item(i);
                    final String title = tutorialElement.getAttribute("title");
                    final String folder = tutorialElement.getAttribute("folder");

                    final NodeList fileNode = tutorialElement.getElementsByTagName("file");
                    final NodeList tutorialTitleNode = tutorialElement.getElementsByTagName("title");

                    String[] file = new String[fileNode.getLength()];
                    String[] tutorialTitle = new String[tutorialTitleNode.getLength()];

                    for (int index = 0; index < fileNode.getLength(); index++)  {
                        file[index] = fileNode.item(index).toString();
                    }

                    for (int index = 0; index < tutorialTitleNode.getLength(); index++) {
                        tutorialTitle[index] = tutorialTitleNode.item(index).toString();
                    }

                    tutorials[i] = new TutorialContainer(title, folder, file, tutorialTitle);
                }
            }

            // Then construct container from TutorialsBuffer
            for (int i = 0; i < tutorials.length; i++) {
                TutorialContainer tutorial = tutorials[i];
                if(tutorial == null) {
                    throw new ConfigParseException(String.format("Id %d is not contained", i));
                }
                container.add(tutorial);
            }

        } catch (ParserConfigurationException | SAXException e) {
            throw new IOException(e);
        }

        return container;
    }
}
