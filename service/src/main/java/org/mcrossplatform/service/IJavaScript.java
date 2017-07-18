package org.mcrossplatform.service;

/**
 * Initial version of javascript api without variable binding.
 * Just pass a javascript function and evaluate to an object.
 * @author donatmueller
 *
 */
public interface IJavaScript {
	IJavaScriptEngine createEngine(String javascript);
	
	static interface IJavaScriptEngine {
		Object evaluate(String function);
	}
}
