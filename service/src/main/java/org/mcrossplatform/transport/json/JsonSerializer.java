package org.mcrossplatform.transport.json;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.mcrossplatform.resource.ResourceCloser;
import org.mcrossplatform.validation.Validate;

import com.eclipsesource.json.JsonValue;

/**
 * Protocol: [#<className>#<JsonValue>]
 * @author donatmueller
 *
 */
class JsonSerializer implements Closeable {
	private final static Logger LOGGER = Logger.getLogger(JsonSerializer.class.getName());
	final static String TOKEN="\f";
	final static String NEXT_OBJECT="\r\n";
	private final OutputStreamWriter jsonToServer;

	JsonSerializer(OutputStream out) {
		this.jsonToServer = new OutputStreamWriter(out);
	}

	void serialize(JsonSerializable object) throws IOException {
		Validate.notNull(object);
		try {
			LOGGER.finest(String.format("Serializing %s from class %s", object,object.getClass().getName()));
			String clazz = object.getClass().getName();
			JsonValue jsonObject = object.toJson();
			jsonToServer.write(TOKEN);
			jsonToServer.write(clazz);
			jsonToServer.write(TOKEN);
			jsonObject.writeTo(jsonToServer);
			jsonToServer.write(NEXT_OBJECT);
			jsonToServer.flush();
		} catch (Exception e) {
			String message = String.format("Failed serialzing %s from class %s", object,object.getClass().getName());
			LOGGER.log(Level.SEVERE, message, e);
			throw new RuntimeException(message,e);
		}
	}

	public void close() {
		ResourceCloser.close(jsonToServer);
	}
}
