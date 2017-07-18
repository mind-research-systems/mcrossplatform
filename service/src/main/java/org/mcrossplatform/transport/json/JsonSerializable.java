package org.mcrossplatform.transport.json;

import com.eclipsesource.json.JsonValue;

public interface JsonSerializable {
	JsonValue toJson();
}