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

package org.mcrossplatform.core.transport.json;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CauseImplTest extends AbstractJsonSerializableTest<CauseImpl> {

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
    assertEquals("java.lang.Exception: An Exception", obj.getMessage());
    assertTrue(obj.getStackTrace().length > 24);
  }
  
  @Test
  public void getCause_Null_ReturnCauseNull() {
    // arrange
    CauseImpl testee = createTestee();
    // act
    ICause result = testee.getCause();
    // assert
    assertNull(result);
  }
  
  @Test
  public void toThrowable_Null_ReturnThrowableNull() {
    // arrange
    CauseImpl testee = createTestee();
    // act
    Throwable result = testee.toThrowable();
    // assert
    assertNull(result.getCause());
  }
  
  @Test
  public void ctor_WithThrowable_CauseNotNull() {
    // arrange
    Exception e = new Exception("An Exception");
    e.initCause(new IllegalArgumentException("bar"));
    CauseImpl testee = CauseImpl.newInstance(e);
    // act
    Throwable result = testee.toThrowable();
    // assert
    assertNotNull(result.getCause());
  }
  
  @Test
  public void ctor_WithNull_ResultNull() {
    // arrange & act
    CauseImpl testee = CauseImpl.newInstance(null);
    // assert
    assertNull(testee);
  }
}
