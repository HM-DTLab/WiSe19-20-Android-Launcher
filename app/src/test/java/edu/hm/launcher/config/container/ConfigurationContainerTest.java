package edu.hm.launcher.config.container;

import org.junit.Test;

import java.io.ByteArrayOutputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import static org.junit.Assert.assertEquals;

public class ConfigurationContainerTest {

    @Test
    public void addingOneLength() {
        ConfigurationContainer container = new ConfigurationContainer();

        container.add(new AppContainer("test1"));

        assertEquals(1, container.getApps().size());
    }

    @Test
    public void addingOneElement() {
        ConfigurationContainer container = new ConfigurationContainer();

        container.add(new AppContainer("test1"));

        assertEquals("test1", container.getApps().get(0).getPackageName());
    }

    @Test
    public void removingOneLength() {
        ConfigurationContainer container = new ConfigurationContainer();

        container.add(new AppContainer("test1"));
        container.removeAt(0);

        assertEquals(0, container.getApps().size());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void removingOneException() {
        ConfigurationContainer container = new ConfigurationContainer();

        container.removeAt(0);
    }

    @Test
    public void changeOneLength() {
        ConfigurationContainer container = new ConfigurationContainer();

        container.add(new AppContainer("test1"));
        container.changeAt(0, new AppContainer("test2"));

        assertEquals(1, container.getApps().size());
    }

    @Test
    public void changeOneElement() {
        ConfigurationContainer container = new ConfigurationContainer();

        container.add(new AppContainer("test1"));
        container.changeAt(0, new AppContainer("test2"));

        assertEquals("test2", container.getApps().get(0).getPackageName());
    }

    @Test
    public void addingMultiLength() {
        ConfigurationContainer container = new ConfigurationContainer();

        container.add(new AppContainer("test1"));
        container.add(new AppContainer("test2"));
        container.add(new AppContainer("test3"));

        assertEquals(3, container.getApps().size());
    }

    @Test
    public void addingMultiElement() {
        ConfigurationContainer container = new ConfigurationContainer();

        container.add(new AppContainer("test1"));
        container.add(new AppContainer("test2"));
        container.add(new AppContainer("test3"));

        assertEquals("test1", container.getApps().get(0).getPackageName());
        assertEquals("test2", container.getApps().get(1).getPackageName());
        assertEquals("test3", container.getApps().get(2).getPackageName());
    }

    @Test
    public void changeMultiLength() {
        ConfigurationContainer container = new ConfigurationContainer();

        container.add(new AppContainer("test1"));
        container.add(new AppContainer("test2"));
        container.add(new AppContainer("test3"));
        container.changeAt(0, new AppContainer("test2"));

        assertEquals(3, container.getApps().size());
    }

    @Test
    public void changeMultiElement() {
        ConfigurationContainer container = new ConfigurationContainer();

        container.add(new AppContainer("test1"));
        container.add(new AppContainer("test2"));
        container.add(new AppContainer("test3"));
        container.changeAt(0, new AppContainer("test3"));
        container.changeAt(1, new AppContainer("test1"));
        container.changeAt(2, new AppContainer("test2"));

        assertEquals("test1", container.getApps().get(1).getPackageName());
        assertEquals("test2", container.getApps().get(2).getPackageName());
        assertEquals("test3", container.getApps().get(0).getPackageName());
    }

    @Test
    public void toXmlSimple() throws ParserConfigurationException, TransformerException {
        ConfigurationContainer container = new ConfigurationContainer();

        container.add(new AppContainer("test1"));
        DOMSource source = container.toXml();

        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" +
                        "<configuration>" +
                        "<app id=\"0\">test1</app>" +
                        "</configuration>",
                getStringFromDOMSource(source));
    }

    @Test
    public void toXmlMulti() throws ParserConfigurationException, TransformerException {
        ConfigurationContainer container = new ConfigurationContainer();

        container.add(new AppContainer("test1"));
        container.add(new AppContainer("test2"));
        container.add(new AppContainer("test3"));
        DOMSource source = container.toXml();

        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" +
                        "<configuration>" +
                        "<app id=\"0\">test1</app>" +
                        "<app id=\"1\">test2</app>" +
                        "<app id=\"2\">test3</app>" +
                        "</configuration>",
                getStringFromDOMSource(source));
    }

    private String getStringFromDOMSource(DOMSource source) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        StreamResult result = new StreamResult(stream);
        transformer.transform(source, result);

        return new String(stream.toByteArray());
    }
}