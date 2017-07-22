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

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.mcrossplatform.core.resource.ResourceCloser;
import org.mcrossplatform.core.transport.DeserializationException;

class JsonDeserializer implements Closeable {
  private static final Logger LOGGER = Logger.getLogger(JsonDeserializer.class.getName());
  private final BufferedReader jsonFromClient;

  JsonDeserializer(InputStream in) {
    super();
    this.jsonFromClient = new BufferedReader(new InputStreamReader(in));
  }

  JsonSerializable deserialize() throws DeserializationException {
    String object = readNextJsonObject();
    LOGGER.finest(object);
    if (object != null) {
      StringTokenizer tokenizer = new StringTokenizer(object, JsonSerializer.TOKEN);
      if (tokenizer.hasMoreTokens()) {
        String clazz = tokenizer.nextToken();
        String json = tokenizer.nextToken();
        return createInstance(clazz, json);
      }
    }
    return null;
  }

  private String readNextJsonObject() throws DeserializationException {
    try {
      return jsonFromClient.readLine();
    } catch (IOException e) {
      throw new DeserializationException(e);
    }
  }
  
  private JsonSerializable createInstance(String clazz, String json)
      throws DeserializationException {
    try {
      LOGGER.finest(String.format("Deserialzing %s into class %s", json, clazz));
      Class<?> implClass = Class.forName(clazz);
      Constructor<?> ctor = implClass.getConstructor(String.class);
      return JsonSerializable.class.cast(ctor.newInstance(json));
    } catch (Exception e) {
      LOGGER.log(Level.SEVERE, String.format("Failed deserialzing %s into class %s", json, clazz),
          e);
      throw new DeserializationException(
          String.format("Failed deserialzing %s into class %s", json, clazz), e);
    }
  }

  public void close() {
    ResourceCloser.close(jsonFromClient);
  }
}
