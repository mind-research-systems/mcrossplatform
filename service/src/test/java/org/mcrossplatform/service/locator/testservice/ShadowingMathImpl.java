package org.mcrossplatform.service.locator.testservice;

import org.mcrossplatform.service.IMath;

/**
 * Default implementation of sample service IMath
 * @author donatmueller
 */
public class ShadowingMathImpl implements IMath {

	@Override
	public int add(final int a, final int b) {
		return a + b;
	}

}
