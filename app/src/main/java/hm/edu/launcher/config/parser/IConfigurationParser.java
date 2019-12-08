package hm.edu.launcher.config.parser;

import java.io.IOException;
import java.io.InputStream;

import hm.edu.launcher.config.container.ConfigurationContainer;


/**
 * A parser to parse a ConfigurationContainer from an InputStream.
 */
public interface IConfigurationParser {

    /**
     * Parses a ConfigurationContainer from an InputStream.
     * @param fileStream The to parse from stream.
     * @return The parsed to ConfigurationContainer.
     * @throws IOException When the xml could not be parsed from the Stream.
     * @throws ConfigParseException When the xml has invalid content.
     */
    ConfigurationContainer parseConfig(InputStream fileStream) throws IOException, ConfigParseException;

}
