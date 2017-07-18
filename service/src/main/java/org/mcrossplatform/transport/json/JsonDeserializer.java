package org.mcrossplatform.transport.json;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.mcrossplatform.resource.ResourceCloser;

class JsonDeserializer implements Closeable {
	private final static Logger LOGGER = Logger.getLogger(JsonDeserializer.class.getName());
	private final BufferedReader jsonFromClient;

	JsonDeserializer(InputStream in) {
		super();
		this.jsonFromClient = new BufferedReader(new InputStreamReader(in));
	}
	
	JsonSerializable deserialize() throws IOException {
		String object = jsonFromClient.readLine();
		LOGGER.finest(object);
		if (object!=null) {
			StringTokenizer tokenizer = new StringTokenizer(object,JsonSerializer.TOKEN);
			if(tokenizer.hasMoreTokens()) {
				String clazz = tokenizer.nextToken();
				String json = tokenizer.nextToken();
				return createInstance(clazz,json);
			}
		}
		return null;
	}
	
	private JsonSerializable createInstance(String clazz, String json)  {
		try {
			LOGGER.finest(String.format("Deserialzing %s into class %s", json,clazz));
			Class<?> implClass = Class.forName(clazz);
			Constructor<?> ctor = implClass.getConstructor(String.class);
			return JsonSerializable.class.cast(ctor.newInstance(json));
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, String.format("Failed deserialzing %s into class %s", json,clazz), e);
			throw new RuntimeException(String.format("Failed deserialzing %s into class %s", json,clazz),e);
		}
	}

	public void close() {
		ResourceCloser.close(jsonFromClient);
	}
}
