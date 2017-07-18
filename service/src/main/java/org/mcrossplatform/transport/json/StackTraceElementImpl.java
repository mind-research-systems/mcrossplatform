package org.mcrossplatform.transport.json;

import java.util.ArrayList;
import java.util.List;

import org.mcrossplatform.validation.Validate;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class StackTraceElementImpl implements IStackTraceElement {
	private static final String CLASS = "declaringClass";
	private static final String METHOD = "methodName";
	private static final String FILE = "fileName";
	private static final String LINE = "lineNumber";
    private final String declaringClass;
    private final String methodName;
    private final String fileName;
    private final int    lineNumber;

	// ctor for json deserializer
	public StackTraceElementImpl(String json) {
		this(Json.parse(json));
	}

	private StackTraceElementImpl(JsonValue jsonValue) {
		Validate.jsonObject(jsonValue);
		JsonObject obj = jsonValue.asObject();
		this.declaringClass = obj.getString(CLASS, "");
		this.methodName = obj.getString(METHOD, "");
		this.fileName = getStringOrNull(obj,FILE);
		this.lineNumber = obj.getInt(LINE, -1);
	} 
	
	private String getStringOrNull(JsonObject obj, String member) {
		JsonValue value = obj.get(member);
		return value.isNull() ? null : value.asString();
	}
	@Override
	public JsonValue toJson() {
		JsonObject jsonObject = Json.object();
		jsonObject.add(CLASS, declaringClass);
		jsonObject.add(METHOD, methodName);
		jsonObject.add(FILE, fileName);
		jsonObject.add(LINE, lineNumber);
		return jsonObject;
	}
	
	public static IStackTraceElement[] newInstance(StackTraceElement[] elements) {
		List<IStackTraceElement> out = new ArrayList<IStackTraceElement>();
		for (StackTraceElement element : elements) {
			out.add(new StackTraceElementImpl(element));
		}
		return out.toArray(new IStackTraceElement[0]);
	}
	
	public static IStackTraceElement[] newInstance(JsonArray array) {
		List<IStackTraceElement> out = new ArrayList<IStackTraceElement>();
		for (JsonValue value : array) {
			out.add(new StackTraceElementImpl(value));
		}
		return out.toArray(new IStackTraceElement[0]);
	}
	
	
	public static JsonArray toJsonArray(IStackTraceElement[] elements) {
		JsonArray array = JsonArray.class.cast(Json.array());
		for (IStackTraceElement element : elements) {
			array.add(element.toJson());
		}
		return array;
	}
	
	private StackTraceElementImpl(StackTraceElement e) {
    	declaringClass = e.getClassName();
    	methodName = e.getMethodName();
    	fileName = e.getFileName();
    	lineNumber = e.getLineNumber();
    }

	StackTraceElementImpl(String declaringClass, String methodName,
			String fileName, int lineNumber) {
		super();
		this.declaringClass = declaringClass;
		this.methodName = methodName;
		this.fileName = fileName;
		this.lineNumber = lineNumber;
	}

	/* (non-Javadoc)
	 * @see ch.mrs.cp.remoting.junit.marshaller.json.IStackeTraceElement#getDeclaringClass()
	 */
	@Override
	public String getDeclaringClass() {
		return declaringClass;
	}

	/* (non-Javadoc)
	 * @see ch.mrs.cp.remoting.junit.marshaller.json.IStackeTraceElement#getMethodName()
	 */
	@Override
	public String getMethodName() {
		return methodName;
	}

	/* (non-Javadoc)
	 * @see ch.mrs.cp.remoting.junit.marshaller.json.IStackeTraceElement#getFileName()
	 */
	@Override
	public String getFileName() {
		return fileName;
	}

	/* (non-Javadoc)
	 * @see ch.mrs.cp.remoting.junit.marshaller.json.IStackeTraceElement#getLineNumber()
	 */
	@Override
	public int getLineNumber() {
		return lineNumber;
	}

}