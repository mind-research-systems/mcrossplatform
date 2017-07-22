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

package org.mcrossplatform.core.transport.json;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.junit.Test;
import org.mcrossplatform.core.transport.DeserializationException;
import org.mcrossplatform.core.transport.SerializationException;

public abstract class AbstractJsonSerializableTest<T extends JsonSerializable> {
  protected abstract Class<T> getType();

  protected abstract T createTestee();

  protected abstract void assertDeserializationResult(T obj);

  @Test
  public void serialize() throws SerializationException {
    // arrange
    T testee = createTestee();
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    JsonSerializer serializer = new JsonSerializer(out);
    // act
    serializer.serialize(testee);
    serializer.close();
    assertEquals(getSerializedObject(testee), out.toString());
  }

  @Test
  public void deserialize() throws DeserializationException {
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
    return String.format("%s%s%s%s%s", JsonSerializer.TOKEN, testee.getClass().getName(),
        JsonSerializer.TOKEN, testee.toJson(), JsonSerializer.NEXT_OBJECT);
  }
}
