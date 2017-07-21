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
package org.mcrossplatform.service.locator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mcrossplatform.resource.LogConfigurationReader;
import org.mcrossplatform.service.IExecutor;
import org.mcrossplatform.service.IMath;
import org.mcrossplatform.service.impl.ExecutorNullImpl;
import org.mcrossplatform.service.impl.MathImpl;
import org.mcrossplatform.service.locator.testservice.HelloServiceImpl;
import org.mcrossplatform.service.locator.testservice.IHaveNoImplementation;
import org.mcrossplatform.service.locator.testservice.IHelloService;
import org.mcrossplatform.service.locator.testservice.ShadowingMathImpl;

public class ServiceLocatorTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@BeforeClass
	public static void initializeLogging()  {
		LogConfigurationReader.readLogProperties();
	}
	
	@Before
	public void setup() {
		ServiceLocator.reset();
	}

	@Test
	public void add_MathServiceAddFunction_Result() {
		// arrange
		final IMath mathService = ServiceLocator.lookup(IMath.class);
		// act
		final int result = mathService.add(2, 3);
		// assert
		assertEquals(5,result);
		assertEquals(MathImpl.class,mathService.getClass());
	}
	@Test
	public void listServices_DefaultAndStandardServiceRegistry_Count3() {
		// arrange & act
		final Set<Class<?>> result = ServiceLocator.listServices();
		// assert
		assertEquals(3,result.size());
	}
	@Test
	public void lookup_CustomExecutorServiceRegisteredAndImplemented_Success() {
		// arrange 
		ServiceLocator.loadServiceRegistry("custom-service-registry.properties");
		// act
		final IMath mathService = ServiceLocator.lookup(IMath.class);
		// assert
		assertNotNull(mathService);
		assertEquals(ShadowingMathImpl.class,mathService.getClass());
	}
	
	@Test
	public void lookup_InextistingImplementation_NotAssigned() {
		// arrange  &  act
		ServiceLocator.loadServiceRegistry("nonexisting-service-registry.properties");
		// act
		final IHaveNoImplementation noImplementationService = ServiceLocator.lookup(IHaveNoImplementation.class);
		// assert
		assertNull(noImplementationService);
	}
	
	@Test
	public void lookup_UnaccessibleDefaultCtor_Exception() {
		// arrange  &  assert
		exception.expect(RuntimeException.class);
		exception.expectMessage("java.lang.IllegalAccessException: Class org.mcrossplatform.service.locator.ServiceLocator can not access a member of class org.mcrossplatform.service.locator.testservice.HelloServiceNoPublicCtorImpl with modifiers \"private\"");
		// act
		ServiceLocator.loadServiceRegistry("no-permission-service-registry.properties");
	}
	@Test
	public void lookup_NoDefaultCtor_Exception() {
		// arrange  &  assert
		exception.expect(RuntimeException.class);
		exception.expectMessage("java.lang.InstantiationException: org.mcrossplatform.service.locator.testservice.HelloServiceNoDefaultCtorImpl");
		// act
		ServiceLocator.loadServiceRegistry("no-public-ctor-service-registry.properties");
	}
	
	
	@Test
	public void lookup_ExecutorServiceRegisteredAndImplemented_Success() {
		// arrange & act
		final IExecutor executorService = ServiceLocator.lookup(IExecutor.class);
		// assert
		assertNotNull(executorService);
		assertEquals(ExecutorNullImpl.class,executorService.getClass());
	}
	
	@Test
	public void lookup_HelloServiceRegisteredAndImplemented_Success() {
		// arrange
		final IHelloService helloService = ServiceLocator.lookup(IHelloService.class);
		// act
		final String result = helloService.sayHello();
		// assert
		assertNotNull(helloService);
		assertEquals("hello",result);
		assertEquals(HelloServiceImpl.class,helloService.getClass());
	}
	
	@Test
	public void lookup_MathServiceRegisteredAndImplemented_Success() {
		// arrange & act
		final IMath mathService = ServiceLocator.lookup(IMath.class);
		// assert
		assertNotNull(mathService);
	}
	
	@Test
	public void lookup_UnknownService_Null() {
		// arrange & act
		final IUnknown unknownService = ServiceLocator.lookup(IUnknown.class);
		// assert
		assertNull(unknownService);
	}
	
	@Test
	public void testConstructorIsPrivate() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
		// arrange
	  final Constructor<ServiceLocator> constructor = ServiceLocator.class.getDeclaredConstructor();
	  // assert
	  assertTrue(Modifier.isPrivate(constructor.getModifiers()));
	  // act
	  constructor.setAccessible(true);
	  constructor.newInstance();
	}	
	
	private static interface IUnknown {}
}
