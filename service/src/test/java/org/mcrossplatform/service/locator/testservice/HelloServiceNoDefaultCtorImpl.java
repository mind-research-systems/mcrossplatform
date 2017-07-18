package org.mcrossplatform.service.locator.testservice;

public class HelloServiceNoDefaultCtorImpl implements IHelloService {

	public HelloServiceNoDefaultCtorImpl(final String gugus) {
		// no default ctor
	}

	@Override
	public String sayHello() {
		return null;
	}

}
