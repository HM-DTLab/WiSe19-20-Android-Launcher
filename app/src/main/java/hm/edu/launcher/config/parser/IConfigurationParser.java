package hm.edu.launcher.config.parser;

import java.io.IOException;
import java.io.InputStream;

import hm.edu.launcher.config.container.ConfigurationContainer;

public interface IConfigurationParser {

    ConfigurationContainer parseConfig(InputStream fileStream) throws IOException, ConfigParseException;

}
