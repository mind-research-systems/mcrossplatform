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

package org.mcrossplatform.core.validation;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import org.mcrossplatform.core.transport.json.JsonSerializable;

public final class Validate {

  private Validate() {
    // no public ctor
  }

  /**
   * Throws an IllegalArgumentException if obj is null.
   * 
   * @param obj
   *          Object
   */
  public static void notNull(Object obj) {
    if (obj == null) {
      throw new IllegalArgumentException("expected not to be null");
    }
  }

  /**
   * Throws an IllegalArgumentException if actualCount < minCount.
   * 
   * @param minCount
   *          minimal value
   * @param actualCount
   *          actual value
   */
  public static void greaterThan(int minCount, int actualCount) {
    if (actualCount < minCount) {
      throw new IllegalArgumentException(
          String.format("expected at least %s but not %s", minCount, actualCount));
    }
  }

  /**
   * Throws an IllegalArgumentException if JsonValue is not a JsonObject.
   * 
   * @param jsonValue
   *          JsonValue
   */
  public static void jsonObject(JsonValue jsonValue) {
    if (!(jsonValue instanceof JsonObject)) {
      throw new IllegalArgumentException(
          String.format("JsonObject expected and not %s", jsonValue));
    }
  }

  /**
   * Throws an IllegalArgumentException if Object is not JsonSerializable.
   * 
   * @param obj
   *          Object
   */
  public static void jsonSerializable(Object obj) {
    if (!(obj instanceof JsonSerializable)) {
      throw new IllegalArgumentException(
          String.format("Unsupported parameter type %s JsonSerializable expected.",
              obj == null ? "<null>" : obj.getClass().getName()));
    }
  }

}
