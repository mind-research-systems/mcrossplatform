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

import static org.junit.Assert.assertNull;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mcrossplatform.core.transport.DeserializationException;

public class JsonDeserializerTest {
  @Rule
  public ExpectedException exceptionRule = ExpectedException.none();

  @Test
  public void deserialize_Null_ReturnsNull() throws DeserializationException {
    // arrange
    InputStream in = new ByteArrayInputStream("".getBytes());
    final JsonDeserializer testee = new JsonDeserializer(in);
    // act
    JsonSerializable result = testee.deserialize();
    testee.close();
    // assert
    assertNull(result);
  }

  @Test
  public void deserialize_StreamClosed_ThrowsDeserializationException()
      throws DeserializationException {
    // arrange & pre assert
    InputStream in = new ByteArrayInputStream("".getBytes());
    final JsonDeserializer testee = new JsonDeserializer(in);
    exceptionRule.expect(DeserializationException.class);
    // act
    testee.close();
    testee.deserialize();
  }

  @Test
  @Ignore
  public void deserialize_UnklnownClass_ThrowsDeserializationException()
      throws DeserializationException {
    // arrange & pre assert
    InputStream in = new ByteArrayInputStream(
        (JsonSerializer.TOKEN + "foo" + JsonSerializer.TOKEN + "bar" + JsonSerializer.NEXT_OBJECT)
            .getBytes());
    final JsonDeserializer testee = new JsonDeserializer(in);
    exceptionRule.expect(DeserializationException.class);
    // act
    testee.deserialize();
    testee.close();
  }
}
