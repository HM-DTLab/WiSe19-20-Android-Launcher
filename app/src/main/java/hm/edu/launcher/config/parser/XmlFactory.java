package hm.edu.launcher.config.parser;

public class XmlFactory {

    public static IConfigurationParser getParser(int id) {
        return new XmlParserV1();
    }

}
