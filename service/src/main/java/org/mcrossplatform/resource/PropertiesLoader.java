package org.mcrossplatform.resource;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PropertiesLoader {
	private static final Logger LOGGER = Logger.getLogger(PropertiesLoader.class.getName());
	
	public static Properties loadProperties(final String propertiesFileName, final boolean mayReturnNull) {
		return loadProperties(null,propertiesFileName,mayReturnNull);
	}

	public static Properties loadProperties(final String resourcefolder, final String propertiesFileName, final boolean mayReturnNull) {
		final File f = FileResourceLocator.findFile(resourcefolder, propertiesFileName, mayReturnNull);
		if (f != null) {
			LOGGER.finest(String.format("Processing: %s ", f.getAbsolutePath()));
			try (final FileInputStream fis = new FileInputStream(f)) {
				final Properties properties = new Properties();
				properties.load(fis);
				return properties;
			} catch (final Exception e) {
				LOGGER.log(Level.SEVERE,
						e.getMessage(), e);
				throw new RuntimeException(e);
			} 
		}
		return null;
	}
	
	private PropertiesLoader() {
	}
}
