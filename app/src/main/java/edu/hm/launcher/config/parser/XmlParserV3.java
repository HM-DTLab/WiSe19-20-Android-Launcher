package edu.hm.launcher.config.parser;

import android.util.Log;

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

import edu.hm.launcher.config.container.ConfigurationSingleTutorialContainer;
import edu.hm.launcher.config.container.ConfigurationTutorialContainer;
import edu.hm.launcher.config.container.SingleTutorialContainer;
import edu.hm.launcher.config.container.TutorialContainer;

public class XmlParserV3 implements IConfigurationParser {

    @Override
    public ConfigurationSingleTutorialContainer parseConfig(InputStream xmlStream) throws IOException, ConfigParseException {

        // Creates new container
        ConfigurationSingleTutorialContainer container = new ConfigurationSingleTutorialContainer();

        try {
            // Build document from stream
            DocumentBuilder xmlBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document xmlDocument = xmlBuilder.parse(xmlStream);

            // Then find all Tutorial nodes
            NodeList tutorialList = xmlDocument.getElementsByTagName("TutorialPart");

            // Create an tutorials buffer to sort them
            SingleTutorialContainer[] tutorials = new SingleTutorialContainer[tutorialList.getLength()];

            for (int i = 0; i < tutorialList.getLength(); i++) {
                if (tutorialList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    // Get node and parse id and name
                    final Element tutorialElement = (Element) tutorialList.item(i);

                    final NodeList imageContent = tutorialElement.getElementsByTagName("Image");
                    final Element contentElementImage = (Element) imageContent.item(0);
                    final String image = contentElementImage.getTextContent();

                    final NodeList descriptionContent = tutorialElement.getElementsByTagName("Description");
                    final Element contentElementDescription = (Element) descriptionContent.item(0);
                    final String description = contentElementDescription.getTextContent();

                    tutorials[i] = new SingleTutorialContainer(image, description);
                }
            }

            // Then construct container from TutorialsBuffer
            for (int i = 0; i < tutorials.length; i++) {
                SingleTutorialContainer tutorial = tutorials[i];
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
