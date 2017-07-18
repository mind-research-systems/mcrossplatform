package org.mcrossplatform.transport.json;

public interface IStackTraceElement extends JsonSerializable  {

	public abstract String getDeclaringClass();

	public abstract String getMethodName();

	public abstract String getFileName();

	public abstract int getLineNumber();

}