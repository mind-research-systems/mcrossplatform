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
