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

import org.mcrossplatform.core.transport.json.JsonSerializable;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class Validate {
	public static void notNull(Object obj) {
		if (obj == null) {
			throw new IllegalArgumentException("expected not to be null");
		}
	}
	
	public static void greaterThan(int minCount, int actualCount) {
		if (actualCount < minCount) {
			throw new IllegalArgumentException(String.format("expected at least %s but not %s",minCount,actualCount));
		}
	}
	
	public static void jsonObject(JsonValue jsonValue) {
		if (!(jsonValue instanceof JsonObject)) {
			throw new IllegalArgumentException(String.format("JsonObject expected and not %s", jsonValue));
		}
	}

	public static void jsonSerializable(Object obj) {
		if (!(obj instanceof JsonSerializable)) {
			throw new IllegalArgumentException(String.format("Unsupported parameter type %s JsonSerializable expected.", obj == null ? "<null>" : obj.getClass().getName()));
		}
	}

}
