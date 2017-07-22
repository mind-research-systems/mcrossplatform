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
package org.mcrossplatform.core.resource;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import org.mcrossplatform.core.resource.FileResourceLocator;


public class FileResourceLocatorTest {
	@Rule
	public ExpectedException exception = ExpectedException.none();
	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();
	
	@After
	public void resetResourceFolders() {
		FileResourceLocator.resetResourceFlders();
	}
	@Test
	public void fileExists_FileExistsInTest_ReturnsTrue() {
		// arrange & act
		final boolean result = FileResourceLocator.fileExists("logging.properties");
		// assert
		assertTrue(result);
	}

	@Test
	public void fileExists_FileExistsInMain_ReturnsTrue() {
		// arrange & act
		final boolean result = FileResourceLocator.fileExists("default-service-registry.properties");
		// assert
		assertTrue(result);
	}

	@Test
	public void fileExists_DirectoryExitsInTest_ReturnsTrue() {
		// arrange & act
		final boolean result = FileResourceLocator.fileExists("DirectoryNotAFile");
		// assert
		assertTrue(result);
	}

	@Test
	public void addResourceFolder_WithTmpDirAndTempFile_FileCanBeFound() throws IOException {
		// arrange 
		File tempDir = temporaryFolder.getRoot();
		temporaryFolder.newFile("myTempFile.txt");
		// act
		FileResourceLocator.addResourceFolder(tempDir.getAbsolutePath());
		// assert
		final boolean result = FileResourceLocator.fileExists("myTempFile.txt");
		assertTrue(result);
	}

	@Test
	public void fileExists_FileNotExists_ReturnsFalse() {
		// arrange & act
		final boolean result = FileResourceLocator.fileExists("foo");
		// assert
		assertFalse(result);
	}

	@Test
	public void fileExists_DirectoryExistsInRoot_ReturnsTrue() {
		// arrange & act
		final boolean result = FileResourceLocator.fileExists("src");
		// assert
		assertTrue(result);
	}


	@Test
	public void fileExists_FileExistsInGivenDirectory_ReturnsTrue() {
		// arrange & act
		final boolean result = FileResourceLocator.fileExists("./src/test/java/org/mcrossplatform/core/service/testservice","HelloServiceImpl.java");
		// assert
		assertTrue(result);
	}

	@Test
	public void findFile_FileNotExistsAndMayReturnNull_ReturnsNull() {
		// arrange & act
		final File result = FileResourceLocator.findFile("foo", true);
		// assert
		assertNull(result);
	}

	@Test
	public void findFile_FileNotExistsAndMayNotReturnNull_Exception() {
		// arrange && assert
		exception.expect(RuntimeException.class);
		exception.expectMessage("File: foo not found in path: [./, ./src/test/resources, ./src/main/resources].");
		final File result = FileResourceLocator.findFile("foo", false);
		// assert
		assertNull(result);
	}

	@Test
	public void testConstructorIsPrivate() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
		// arrange
	  final Constructor<FileResourceLocator> constructor = FileResourceLocator.class.getDeclaredConstructor();
	  // assert
	  assertTrue(Modifier.isPrivate(constructor.getModifiers()));
	  // act
	  constructor.setAccessible(true);
	  constructor.newInstance();
	}	
}
