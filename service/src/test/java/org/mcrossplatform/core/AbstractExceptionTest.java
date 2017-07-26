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

package org.mcrossplatform.core;

import java.lang.reflect.InvocationTargetException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mcrossplatform.core.transport.DeserializationException;

public abstract class AbstractExceptionTest<T extends Throwable> {
  @Rule
  public ExpectedException exceptionRule = ExpectedException.none();

  protected abstract Class<T> getType();


  @Test
  public void ctor_WithMessage_HasMessage() throws DeserializationException, InstantiationException,
      IllegalAccessException, IllegalArgumentException, InvocationTargetException,
      NoSuchMethodException, SecurityException, T {
    // arrange & pre assert
    exceptionRule.expect(getType());
    exceptionRule.expectMessage("foo");
    // act
    throwException("foo");
  }

  @Test
  public void ctor_WithThrowable_HasThrowable() throws DeserializationException,
      InstantiationException, IllegalAccessException, IllegalArgumentException,
      InvocationTargetException, NoSuchMethodException, SecurityException, T {
    // arrange & pre assert
    exceptionRule.expect(getType());
    exceptionRule.expectMessage("bar");
    // act
    throwException(new IllegalArgumentException("bar"));
  }

  @Test
  public void ctor_WithMessageAndThrowable_HasMessage() throws DeserializationException,
      InstantiationException, IllegalAccessException, IllegalArgumentException,
      InvocationTargetException, NoSuchMethodException, SecurityException, T {
    // arrange & pre assert
    exceptionRule.expect(getType());
    exceptionRule.expectMessage("foo");
    // act
    throwException("foo", new IllegalArgumentException("bar"));
  }

  private void throwException(String message)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException,
      InvocationTargetException, NoSuchMethodException, SecurityException, T {
    throw getType().getConstructor(String.class).newInstance(message);
  }

  private void throwException(Throwable t)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException,
      InvocationTargetException, NoSuchMethodException, SecurityException, T {
    throw getType().getConstructor(Throwable.class).newInstance(t);
  }

  private void throwException(String message, Throwable t)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException,
      InvocationTargetException, NoSuchMethodException, SecurityException, T {
    throw getType().getConstructor(String.class, Throwable.class).newInstance(message, t);
  }
}
