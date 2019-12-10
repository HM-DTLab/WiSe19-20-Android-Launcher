package edu.hm.launcher.config.parser;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import edu.hm.launcher.config.container.ConfigurationContainer;

import static org.junit.Assert.assertEquals;

public class XmlParserV1Test {

    @Test
    public void loadingSimpleLength() throws IOException, ConfigParseException {
        InputStream xmlStream = getSimpleInputStream();
        IConfigurationParser parser = XmlFactory.getParser(1);

        ConfigurationContainer config = parser.parseConfig(xmlStream);

        assertEquals(1, config.getApps().size());
    }

    @Test
    public void loadingSimpleElement() throws IOException, ConfigParseException {
        InputStream xmlStream = getSimpleInputStream();
        IConfigurationParser parser = XmlFactory.getParser(1);

        ConfigurationContainer config = parser.parseConfig(xmlStream);

        assertEquals("com.test1", config.getApps().get(0).getAppName());
    }

    @Test
    public void loadingComplexLength() throws IOException, ConfigParseException {
        InputStream xmlStream = getComplexInputStream();
        IConfigurationParser parser = XmlFactory.getParser(1);

        ConfigurationContainer config = parser.parseConfig(xmlStream);

        assertEquals(3, config.getApps().size());
    }

    @Test
    public void loadingComplexElement() throws IOException, ConfigParseException {
        InputStream xmlStream = getComplexInputStream();
        IConfigurationParser parser = XmlFactory.getParser(1);

        ConfigurationContainer config = parser.parseConfig(xmlStream);

        assertEquals("com.test1", config.getApps().get(0).getAppName());
        assertEquals("com.test2", config.getApps().get(1).getAppName());
        assertEquals("com.test3", config.getApps().get(2).getAppName());
    }

    @Test(expected = ConfigParseException.class)
    public void loadingBrokenMultiId() throws IOException, ConfigParseException {
        String xml =
                "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                        "<configuration>\n" +
                        "<app id=\"0\">com.test1</app>\n" +
                        "<app id=\"0\">com.test2</app>\n" +
                        "<app id=\"1\">com.test3</app>\n" +
                        "</configuration>";

        IConfigurationParser parser = XmlFactory.getParser(1);

        parser.parseConfig(new ByteArrayInputStream(xml.getBytes()));
    }

    @Test(expected = ConfigParseException.class)
    public void loadingBrokenOutOfRangeId() throws IOException, ConfigParseException {
        String xml =
                "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                        "<configuration>\n" +
                        "<app id=\"0\">com.test1</app>\n" +
                        "<app id=\"1\">com.test2</app>\n" +
                        "<app id=\"3\">com.test3</app>\n" +
                        "</configuration>";

        IConfigurationParser parser = XmlFactory.getParser(1);

        parser.parseConfig(new ByteArrayInputStream(xml.getBytes()));
    }

    private InputStream getSimpleInputStream() {
        String xml =
                "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                        "<configuration>\n" +
                        "<app id=\"0\">com.test1</app>\n" +
                        "</configuration>";

        return new ByteArrayInputStream(xml.getBytes());
    }

    private InputStream getComplexInputStream() {
        String xml =
                "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                        "<configuration>\n" +
                        "<app id=\"0\">com.test1</app>\n" +
                        "<app id=\"1\">com.test2</app>\n" +
                        "<app id=\"2\">com.test3</app>\n" +
                        "</configuration>";

        return new ByteArrayInputStream(xml.getBytes());
    }
}