package org.mcrossplatform.transport.json;

import static org.junit.Assert.*;

public class CauseImplTest  extends AbstractJsonSerializableTest<CauseImpl> {

	@Override
	protected Class<CauseImpl> getType() {
		return CauseImpl.class;
	}

	@Override
	protected CauseImpl createTestee() {
		Exception e = new Exception("An Exception");
		return CauseImpl.newInstance(e);
	}

	@Override
	protected void assertDeserializationResult(CauseImpl obj) {
		assertEquals("java.lang.Exception: An Exception",obj.getMessage());
		assertTrue(obj.getStackTrace().length>24);
	}

}
