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

import com.eclipsesource.json.JsonValue;
import java.io.Closeable;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.mcrossplatform.core.resource.ResourceCloser;
import org.mcrossplatform.core.transport.SerializationException;
import org.mcrossplatform.core.validation.Validate;



/**
 * Protocol: [#&lt;className&gt;#&lt;JsonValue&gt;\r\n].
 * 
 * @author donatmueller
 *
 */
class JsonSerializer implements Closeable {
  private static final Logger LOGGER = Logger.getLogger(JsonSerializer.class.getName());
  public static final String TOKEN = "\f";
  public static final String NEXT_OBJECT = "\r\n";
  private final OutputStreamWriter jsonToServer;

  JsonSerializer(OutputStream out) {
    this.jsonToServer = new OutputStreamWriter(out);
  }

  void serialize(JsonSerializable object) throws SerializationException {
    Validate.notNull(object);
    try {
      LOGGER.finest(
          String.format("Serializing %s from class %s", object, object.getClass().getName()));
      String clazz = object.getClass().getName();
      final JsonValue jsonObject = object.toJson();
      jsonToServer.write(TOKEN);
      jsonToServer.write(clazz);
      jsonToServer.write(TOKEN);
      jsonObject.writeTo(jsonToServer);
      jsonToServer.write(NEXT_OBJECT);
      jsonToServer.flush();
    } catch (Exception e) {
      String message = String.format("Failed serialzing %s from class %s", object,
          object.getClass().getName());
      LOGGER.log(Level.SEVERE, message, e);
      throw new SerializationException(message, e);
    }
  }

  public void close() {
    ResourceCloser.close(jsonToServer);
  }
}
