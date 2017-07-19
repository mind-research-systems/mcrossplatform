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
package org.mcrossplatform.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mcrossplatform.service.IJavaScript;
import org.mcrossplatform.service.IJavaScript.IJavaScriptEngine;

public class JavaScriptImplTest {

	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();
	
	@Test
	public void evaluate_TwoPlusSeven_ReuturnsNine() {
		// arrange
		final IJavaScript testee = new JavaScriptImpl();
		// act
		IJavaScriptEngine engine = testee.createEngine("function sum(a, b) { return a + b; }");
		final Object result = engine.evaluate("sum(2.0, 7);");
		// assert
		assertNotNull(result);
		assertTrue(Number.class.isInstance(result));
		assertEquals(9.0,result);
	}
	
	@Test
	public void createEngine_IllegalFunction_Exception() {
		// arrange & pre assert
		final IJavaScript testee = new JavaScriptImpl();
		exceptionRule.expect(RuntimeException.class);
		exceptionRule.expectMessage("Exception creating javascript engin with script: 'function var foo'");
		// act
		testee.createEngine("function var foo");
	}
	
	@Test
	public void createEngine_IllegalInvocation_Exception() {
		// arrange & pre assert
		final IJavaScript testee = new JavaScriptImpl();
		exceptionRule.expect(RuntimeException.class);
		IJavaScriptEngine engine = testee.createEngine("function foo() { return 'bar'; }");
		exceptionRule.expectMessage("Exception evaluating javascript function: 'bar();'");
		// act
		engine.evaluate("bar();");
	}
	
}
