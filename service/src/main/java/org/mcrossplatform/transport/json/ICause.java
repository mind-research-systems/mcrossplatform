package org.mcrossplatform.transport.json;


public interface ICause extends JsonSerializable {
	String getMessage();
	ICause getCause();
	IStackTraceElement[] getStackTrace();
	Throwable toThrowable();
}
