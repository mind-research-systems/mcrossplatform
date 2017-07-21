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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class LogConfigurationReaderTest {
	private final static Level[] LOG_LEVELS = new Level[] {Level.ALL,Level.SEVERE,Level.WARNING,Level.INFO,Level.FINE,Level.FINER, Level.FINEST};
	private final static String[] LOG_MESSAGES = new String[]{"All","Severe","Warning","Info","Fine","Finer","Finest"};
	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Before
	public void resetLogManager() {
		LogManager.getLogManager().reset();
	}

	@Test
	public void readLogProperties_PropertiesFileFound_TwoHandlers() {
		// arrange & act
		LogConfigurationReader.readLogProperties();
		// assert
		final Handler[] handlers = LogManager.getLogManager().getLogger("").getHandlers();
		assertEquals(2,handlers.length);
	}

	@Test
	public void readLogProperties_PropertiesNotFileFound_ZeroHandlers() {
		// arrange & act
		LogConfigurationReader.readLogProperties("foo");
		// assert
		final Handler[] handlers = LogManager.getLogManager().getLogger("").getHandlers();
		assertEquals(0,handlers.length);
	}

	@Test
	public void readLogProperties_PropertiesNotAFile_FileNotFoundException() {
		// arrange & assert
		exception.expect(ResourceException.class);		
		exception.expectMessage("java.io.FileNotFoundException: ./src/test/resources/DirectoryNotAFile (Is a directory)");
		// act
		LogConfigurationReader.readLogProperties("DirectoryNotAFile");
	}
	
	@Test
	public void testConstructorIsPrivate() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
		// arrange
	  final Constructor<LogConfigurationReader> constructor = LogConfigurationReader.class.getDeclaredConstructor();
	  // assert
	  assertTrue(Modifier.isPrivate(constructor.getModifiers()));
	  // act
	  constructor.setAccessible(true);
	  constructor.newInstance();
	}	
	
	@Test
	public void log_AllLevels_LogFileWritten() throws Exception {
		// arrange
		removeFileIfExists("test.log");
		LogConfigurationReader.readLogProperties();
		Logger logger = Logger.getLogger("TestLogger");
		logger.setLevel(Level.ALL);
		// act
		for (int i=0;i<LOG_LEVELS.length;i++) {
			logger.log(LOG_LEVELS[i], LOG_MESSAGES[i]);
		}
		// assert
		List<String> logContent = readFile("target/test.log");
		assertEquals("Expected 7 logmessages",7,logContent.size());
		for (int i=0;i<7;i++) {
			assertTrue(logContent.get(i).endsWith(LOG_LEVELS[i] + ": " + LOG_MESSAGES[i]));
		}
	}
	
	private void removeFileIfExists(String name) {
		File f = new File(name);
		if (f.exists()) {
			f.delete();
		}
	}
	private List<String> readFile(String file) throws Exception{
		List<String> result = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader(file));		
		try {
		    String line = br.readLine();

		    while (line != null) {
		    	result.add(line);
		        line = br.readLine();
		    }
		} finally {
		    br.close();
		}		
		return result;
	}
}
