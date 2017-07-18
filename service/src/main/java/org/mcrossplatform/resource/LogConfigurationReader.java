package org.mcrossplatform.resource;

import java.io.File;
import java.io.FileInputStream;
import java.util.logging.LogManager;

public class LogConfigurationReader {

	private static final String LOGGING_PROPERTIES = "logging.properties";
	public static void readLogProperties() {
		readLogProperties(LOGGING_PROPERTIES);
	}

	public static void readLogProperties(final String loggingProperties) {
		final File logProperties = FileResourceLocator.findFile(loggingProperties, true);
		if (logProperties!=null) {			
			try {
				LogManager.getLogManager().readConfiguration(new FileInputStream(logProperties));
			} catch (final Exception e) {
				throw new RuntimeException(e);
			} 
		} else {
			System.out.println(String.format("WARNING: Log properteis %s not found",loggingProperties));
		}

	}
	private LogConfigurationReader() {
		// private ctor
	}

}
