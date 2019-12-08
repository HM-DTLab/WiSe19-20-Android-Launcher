package hm.edu.launcher.config.parser;

import hm.edu.launcher.config.container.ConfigurationContainer;

public interface IConfigurationParser {

    ConfigurationContainer parseConfig(String path);

}
