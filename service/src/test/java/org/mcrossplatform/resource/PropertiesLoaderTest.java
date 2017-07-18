package org.mcrossplatform.resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Properties;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class PropertiesLoaderTest {
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
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
		exception.expect(RuntimeException.class);
		exception.expectMessage("File: foo not found in path: [./, ./src/test/resources, ./src/main/resources].");
		// act
		PropertiesLoader.loadProperties("foo", false);
	}
	@Test
	public void loadProperties_DirectoryExists_Exception() {
		// arrange & assert
		exception.expect(RuntimeException.class);
		exception.expectMessage("./src/test/resources/DirectoryNotAFile (Is a directory)");
		// act
		PropertiesLoader.loadProperties("DirectoryNotAFile",false);
	}

	@Test
	public void loadProperties_FileExist_ContainsProperties() {
		// arrange & act
		final Properties result = PropertiesLoader.loadProperties("service-registry.properties", true);
		// assert
		assertEquals(2,result.size());
		assertEquals("org.mcrossplatform.service.locator.testservice.IHelloService",result.getProperty("service.interface.hello"));
		assertEquals("org.mcrossplatform.service.locator.testservice.HelloServiceImpl",result.getProperty("service.implementation.hello"));
	}
	
	
	@Test
	public void testConstructorIsPrivate() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
		// arrange
	  final Constructor<PropertiesLoader> constructor = PropertiesLoader.class.getDeclaredConstructor();
	  // assert
	  assertTrue(Modifier.isPrivate(constructor.getModifiers()));
	  // act
	  constructor.setAccessible(true);
	  constructor.newInstance();
	}	
}
