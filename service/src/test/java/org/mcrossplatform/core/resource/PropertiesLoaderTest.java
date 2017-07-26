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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Properties;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mcrossplatform.test.CauseMatcher;

public class PropertiesLoaderTest {

  @Rule
  public ExpectedException exceptionRule = ExpectedException.none();

  @Test
  public void loadProperties_FileNotExistsMyReturnNull_ReturnsNull() {
    // arrange & act
    final Properties result = PropertiesLoader.loadProperties("foo", true);
    // assert
    assertNull(result);
  }

  @Test
  public void loadProperties_FileNotExistsMyNotReturnNull_Exception() {
    // arrange & assert
    exceptionRule.expect(ResourceException.class);
    exceptionRule.expectMessage(
        "File: foo not found in path: [./, ./src/test/resources, ./src/main/resources].");
    // act
    PropertiesLoader.loadProperties("foo", false);
  }

  @Test
  public void loadProperties_DirectoryExists_Exception() {
    // arrange & assert
    exceptionRule.expect(RuntimeException.class);
    exceptionRule.expectCause(new CauseMatcher(FileNotFoundException.class,
        "./src/test/resources/DirectoryNotAFile (Is a directory)"));
    // act
    PropertiesLoader.loadProperties("DirectoryNotAFile", false);
  }

  @Test
  public void loadProperties_FileExist_ContainsProperties() {
    // arrange & act
    final Properties result = PropertiesLoader.loadProperties("service-registry.properties", true);
    // assert
    assertEquals(2, result.size());
    assertEquals("org.mcrossplatform.core.service.testservice.IHelloService",
        result.getProperty("service.interface.hello"));
    assertEquals("org.mcrossplatform.core.service.testservice.HelloServiceImpl",
        result.getProperty("service.implementation.hello"));
  }

  @Test
  public void testConstructorIsPrivate() throws NoSuchMethodException, IllegalAccessException,
      InvocationTargetException, InstantiationException {
    // arrange
    final Constructor<PropertiesLoader> constructor = PropertiesLoader.class
        .getDeclaredConstructor();
    // assert
    assertTrue(Modifier.isPrivate(constructor.getModifiers()));
    // act
    constructor.setAccessible(true);
    constructor.newInstance();
  }
}
