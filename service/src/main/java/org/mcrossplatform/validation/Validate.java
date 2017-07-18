package org.mcrossplatform.validation;

import org.mcrossplatform.transport.json.JsonSerializable;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class Validate {
	public static void notNull(Object obj) {
		if (obj == null) {
			throw new RuntimeException("expected not to be null");
		}
	}
	
	public static void greaterThan(int minCount, int actualCount) {
		if (minCount < actualCount) {
			throw new RuntimeException(String.format("expected at least %s but not %s",minCount,actualCount));
		}
	}
	
	public static void jsonObject(JsonValue jsonValue) {
		if (!(jsonValue instanceof JsonObject)) {
			throw new IllegalArgumentException(String.format("JsobObject expected and not %s", jsonValue));
		}
	}

	public static void jsonSerializable(Object obj) {
		if (!(obj instanceof JsonSerializable)) {
			throw new IllegalArgumentException(String.format("Unsupported parameter type %s JsonSerializable expected.", obj == null ? "<null>" : obj.getClass()));
		}
	}

}
