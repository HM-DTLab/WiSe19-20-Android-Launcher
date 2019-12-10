package edu.hm.launcher.config.parser;

/**
 * Static class to produce XmlParsers for a given version number.
 */
public class XmlFactory {

    /**
     * Returns a IConfigurationParser for a given version number.
     * @param version The version number.
     * @return A XmlIConfigurationParser.
     */
    public static IConfigurationParser getParser(int version) {
        return new XmlParserV1();
    }

}
