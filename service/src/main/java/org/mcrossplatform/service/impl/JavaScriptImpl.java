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

import com.google.j2objc.annotations.J2ObjCIncompatible;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.mcrossplatform.core.service.ServiceException;
import org.mcrossplatform.service.IJavaScript;


@J2ObjCIncompatible
public class JavaScriptImpl implements IJavaScript {
  private final ScriptEngineManager engineManager = new ScriptEngineManager();

  @Override
  public IJavaScriptEngine createEngine(String javascript) {
    try {
      ScriptEngine engine = engineManager.getEngineByName("JavaScript");
      engine.eval(javascript);
      return new JavaScriptEngineImpl(engine);
    } catch (ScriptException e) {
      throw new ServiceException(
          String.format("Exception creating javascript engin with script: '%s'", javascript), e);
    }
  }

  private static class JavaScriptEngineImpl implements IJavaScriptEngine {
    private final ScriptEngine engine;

    JavaScriptEngineImpl(ScriptEngine engine) {
      this.engine = engine;
    }

    @Override
    public Object evaluate(String function) {
      try {
        return engine.eval(function);
      } catch (ScriptException e) {
        throw new ServiceException(
            String.format("Exception evaluating javascript function: '%s'", function), e);
      }
    }
  }

}
