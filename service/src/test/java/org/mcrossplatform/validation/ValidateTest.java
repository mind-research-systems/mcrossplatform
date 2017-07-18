package org.mcrossplatform.validation;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ValidateTest {
	
	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();
	
	@Test
	public void notNull_ThrowsException() {
		// arrange & pre assert
		exceptionRule.expect(IllegalArgumentException.class);
		exceptionRule.expectMessage("expected not to be null");
		// act
		Validate.notNull(null);
	}
	@Test
	public void greaterThan_ThrowsException() {
		// arrange & pre assert
		exceptionRule.expect(IllegalArgumentException.class);
		exceptionRule.expectMessage("expected at least 5 but not 3");
		// act
		Validate.greaterThan(5,3);
	}
	@Test
	public void jsonObject_ThrowsException() {
		// arrange & pre assert
		exceptionRule.expect(IllegalArgumentException.class);
		exceptionRule.expectMessage("JsonObject expected and not null");
		// act
		Validate.jsonObject(null);
	}
	@Test
	public void jsonSerializable_ThrowsException() {
		// arrange & pre assert
		exceptionRule.expect(IllegalArgumentException.class);
		exceptionRule.expectMessage("Unsupported parameter type <null> JsonSerializable expected.");
		// act
		Validate.jsonSerializable(null);
	}
}
