package org.mcrossplatform.transport.json;


public class StackTraceElementImplTest extends AbstractJsonSerializableTest<StackTraceElementImpl> {

	@Override
	protected Class<StackTraceElementImpl> getType() {
		return StackTraceElementImpl.class;
	}

	@Override
	protected StackTraceElementImpl createTestee() {
		return new StackTraceElementImpl("MyClass","MyMethod",null,42);
	}

	@Override
	protected void assertDeserializationResult(StackTraceElementImpl obj) {
System.out.println(obj);		
	}

}
