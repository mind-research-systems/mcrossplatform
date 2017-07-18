package org.mcrossplatform.transport.json;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.Test;

public abstract class AbstractJsonSerializableTest<T extends JsonSerializable> {
	protected abstract Class<T> getType();
	protected abstract T createTestee();
	protected abstract void assertDeserializationResult(T obj);
	
	@Test
	public void serialize() throws IOException {
		// arrange
		T testee = createTestee(); 
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		JsonSerializer serializer = new JsonSerializer(out);
		// act
		serializer.serialize(testee);
		serializer.close();
		assertEquals(getSerializedObject(testee),out.toString());
	}

	@Test
	public void deserialize() throws IOException {
		// arrange
		T reference = createTestee(); 
		String testee = getSerializedObject(reference);
		ByteArrayInputStream in = new ByteArrayInputStream(testee.getBytes());
		JsonDeserializer deserializer = new JsonDeserializer(in);
		// act
		T result = getType().cast(deserializer.deserialize());
		deserializer.close();
		assertNotNull(result);
		assertEquals(reference.getClass(), result.getClass());
		assertDeserializationResult(result);
	}

	private String getSerializedObject(JsonSerializable testee) {
		return String.format("%s%s%s%s%s", JsonSerializer.TOKEN, testee.getClass().getName(),JsonSerializer.TOKEN,testee.toJson(),JsonSerializer.NEXT_OBJECT);
	}
}
