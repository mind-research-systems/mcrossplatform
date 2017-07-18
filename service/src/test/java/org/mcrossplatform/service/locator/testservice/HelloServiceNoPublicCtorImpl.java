package org.mcrossplatform.service.locator.testservice;

public class HelloServiceNoPublicCtorImpl implements IHelloService {

	private HelloServiceNoPublicCtorImpl() {
		// private ctor
	}

	@Override
	public String sayHello() {
		return null;
	}

}
