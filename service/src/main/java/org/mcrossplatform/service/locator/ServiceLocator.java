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

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.mcrossplatform.resource.PropertiesLoader;
import org.mcrossplatform.resource.ResourceException;

public final class ServiceLocator {
	private static final Logger LOGGER = Logger.getLogger(ServiceLocator.class.getName());
	private static Map<Class<?>,Object> SERVICES = new HashMap<Class<?>,Object>();
	private static final String DEFAULT_SERVICE_REGISTRY_FILE = "default-service-registry.properties";
	private static final String SERVICE_REGISTRY_FILE = "service-registry.properties";
	private static final String SERVICE_INTERFACE_PREFIX = "service.interface";
	private static final String SERVICE_IMPLEMENTATION_PREFIX = "service.implementation";
	private static final String NO_PERMISSION = "Illegal access or security restriction on class %s.";
	private static final String NO_DEFAULT_CTOR = "Cannot instantiate class %s with default ctor.";
	private static final String CLASS_NOT_FOUND = "Class %s not found.";
	
	private static boolean initialized = false;

	private static Set<String> getServiceNameKeys(final Properties properties) {
		final Set<String> names = new HashSet<String>();
		for (final Object key : properties.keySet()) {
			if (((String)key).startsWith(SERVICE_INTERFACE_PREFIX)) {
				names.add((String)key);
			}
		}
		return names;
	}
	
	private static synchronized void initialize() {
		if (!initialized) {
			loadServiceRegistry(DEFAULT_SERVICE_REGISTRY_FILE,false);
			loadServiceRegistry(SERVICE_REGISTRY_FILE,true);
			initialized = true;
		}
	}

	public static Set<Class<?>> listServices() {
		initialize();
		return Collections.unmodifiableSet(SERVICES.keySet());
	}
	public static void loadServiceRegistry(final String serviceRegistryFile) {
		initialize();
		loadServiceRegistry(serviceRegistryFile,true);
	}

	private static void loadServiceRegistry(final String serviceRegistryFile, final boolean mayReturnNull) {
		LOGGER.finer(String.format("Loading service registry file %s", serviceRegistryFile));
		final Properties p = PropertiesLoader.loadProperties(serviceRegistryFile,mayReturnNull);
		if (p!=null) {
			final Set<String> serviceNameKeys = getServiceNameKeys(p);
			for (final String serviceNameKey : serviceNameKeys) {				
				final String serviceName = serviceNameKey.substring(SERVICE_INTERFACE_PREFIX.length() + 1);
				final String implNameKey = SERVICE_IMPLEMENTATION_PREFIX + "." + serviceName;
				registerService(p.getProperty(serviceNameKey),p.getProperty(implNameKey));
			}
		}
	}

	public static <T> T lookup(final Class<T> service) {
		initialize();
		if (!SERVICES.containsKey(service)) {
			LOGGER.warning(String.format("Returning null for Service: %s", service.getName()));
		}
		return service.cast(SERVICES.get(service));
	}

	private static void register(final Class<?> service, final Object implementation) {
		LOGGER.finest(String.format("Registering %s with %s", service.getName(),implementation.getClass().getName()));
		SERVICES.put(service, implementation);
	}

	private static void registerService(final String ifaceClassName, final String implClassName) {
		try {
			final Class<?> iface = Class.forName(ifaceClassName); 
			final Object serviceImpl = Class.forName(implClassName).newInstance();
			register(iface, serviceImpl);
		} catch (final ClassNotFoundException e) {
			LOGGER.log(Level.SEVERE, String.format(CLASS_NOT_FOUND, implClassName), e);
			// no re throw on not found class: to avoid overwriting of general configuration with dummy implementation for target platforms
		} catch (final InstantiationException e) {
			LOGGER.log(Level.SEVERE, String.format(NO_DEFAULT_CTOR, implClassName), e);
			throw new ResourceException(e);
		} catch (final IllegalAccessException e) {
			LOGGER.log(Level.SEVERE, String.format(NO_PERMISSION, implClassName), e);
			throw new ResourceException(e);
		} 
	}

	public static synchronized void reset() {
		SERVICES.clear();
		initialized = false; 
	}
	
	private ServiceLocator() {
		// private ctor
	}
}
