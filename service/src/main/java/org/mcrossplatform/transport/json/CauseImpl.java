package org.mcrossplatform.transport.json;

import org.mcrossplatform.validation.Validate;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;


public class CauseImpl implements ICause {
	private static final String MESSAGE = "message";
	private static final String CAUSE = "cause";
	private static final String STACKTRACE = "stacktrace";
	private final String message;
	private final ICause cause;
	private final IStackTraceElement[] stacktrace;

	// ctor for json deserializer
	public CauseImpl(String json) {
		this(Json.parse(json));
	}

	CauseImpl(JsonValue jsonValue) {
		Validate.jsonObject(jsonValue);
		JsonObject obj = jsonValue.asObject();
		this.message = obj.getString(MESSAGE, "");
		JsonValue jsonCause = obj.get(CAUSE);
		this.cause = jsonCause==Json.NULL ? null : new CauseImpl(jsonCause);
		JsonArray jsonArray = obj.get(STACKTRACE).asArray();
		this.stacktrace = StackTraceElementImpl.newInstance(jsonArray);
	}

	private CauseImpl(String message, ICause cause, IStackTraceElement[] stacktrace) {
		super();
		this.message = message;
		this.cause = cause;
		this.stacktrace = stacktrace;
	}

	public static CauseImpl newInstance(Throwable t) {
		if (t == null) {
			return null;
		}
		if (t.getCause()==null) {
			return new CauseImpl(t.toString(),null,StackTraceElementImpl.newInstance(t.getStackTrace()));				
		} else {
			return new CauseImpl(t.toString(), newInstance(t.getCause()),StackTraceElementImpl.newInstance(t.getStackTrace()));				
		}
	}
	
	@Override
	public Throwable toThrowable() {
		return getCause()==null ? new Throwable(getMessage()) : new Throwable(getMessage(), getCause().toThrowable());
	}
	
	@Override
	public JsonValue toJson() {
		JsonObject jsonObject = Json.object();
		jsonObject.add(MESSAGE, message);
		jsonObject.add(CAUSE, cause==null ? Json.NULL : cause.toJson());
		jsonObject.add(STACKTRACE, StackTraceElementImpl.toJsonArray(stacktrace));
		return jsonObject;
	}
	

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public ICause getCause() {
		return cause;
	}

	@Override
	public IStackTraceElement[] getStackTrace() {
		return stacktrace;
	}

}
