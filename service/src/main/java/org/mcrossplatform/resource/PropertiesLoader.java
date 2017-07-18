/*
 * #%L
 * service
 * %%
 * Copyright (C) 2017 MRS Internet Service GmbH
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
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
