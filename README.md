# mcrossplatform
mobile development framework for applications with native ui and shared business logic

[![Build Status](https://travis-ci.org/mrs-internet-service-gmbh/mcrossplatform.svg?branch=master)](https://travis-ci.org/mrs-internet-service-gmbh/mcrossplatform) [![Coverage Status](https://coveralls.io/repos/github/mrs-internet-service-gmbh/mcrossplatform/badge.svg?branch=master)](https://coveralls.io/github/mrs-internet-service-gmbh/mcrossplatform?branch=master) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/f6dcf3bcf423493e904eeac037697fae)](https://www.codacy.com/app/donat-mueller/mcrossplatform?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=mrs-internet-service-gmbh/mcrossplatform&amp;utm_campaign=Badge_Grade)

# vision
This project provides a framework for crossplatform mobile application development. We, [among others](https://www.martinfowler.com/bliki/CrossPlatformMobile.html) believe  that the presentation layer of a mobile application should be implemented in native technology. But implementing the business layer of the application from scratch for each platform is an **expensive**, **error prone** and **boring** job. Our approach is to provide the business logic as a Micro Service to the presentation layer. First you design your Service Interface Layer for your application. Then you can program your native mobile application against that interface. The Business Layer is written in Java with Junit & Logging by RemoteSuite & RemoteLogging support for unit and integration tests for your business logic. 

![Mobile Architecture](https://user-images.githubusercontent.com/11026671/28636943-21593eba-7240-11e7-8abb-09f43ce223ad.gif)

The framework provides infrastructure for resource and service location to the presentation layer. And from the Business Layer to the operating system a Resource Access Layer that provides services that encapsulate platform specific strategies to access distributed resources.
# service 
The service module defines its services in the package `org.mcrossplatform.service` and a stub or cross platform independent implementation in the package `org.mcrossplatform.service.impl`.
The framework `mcrossplatform` it self provides infrastructure code for the following topics in the package `org.mcrossplatform.core`:
* File access (class: FileResourceLocator)
* Logging (class: LogConfigurationReader)
* Configuration (class: PropertiesLoader)
* Transport (feature: serializing / deserializing support for format: json, types: throwable stacktrace elements)
* Validation (class: Validate)
* Service (class: ServiceLocator)

The above features build the runtime support for the framework. Beside that integration support is provided in the package `org.mcrossplatform.test`. 

![service-structure](https://user-images.githubusercontent.com/11026671/28636969-34853016-7240-11e7-98b2-48eafd70a6c1.png)

As mentioned before the package `org.mcrossplatform.service` contains interfaces exposed to the presentation and business layer. The package `org.mcrossplatform.service.impl` a default implementation that must be platform independent. The `org.mcrossplatform.core.service` package contains the ServiceLocator that resolves implementations to the api's. It uses the classes in the `org.mcrossplatform.core.resource` package to locate and load `service-registry.properties` from the default resource folders: 
* ./
* ./src/test/resources
* ./src/main/resources

Example of a service registry configuration:

<img width="541" alt="service-registry" src="https://user-images.githubusercontent.com/11026671/28640121-04eccb60-724b-11e7-9179-ba8e1df3ce51.png">


The following convention applies to configuration files: `<feature>.<property>.<name>`. For Services the feature name is **service**. There are two properties **interface** and **implementation**. And in the above sample the two services are named **math** and **executor**. The **interface** has a value of the full qualified interface name: `ch.mrs.cp.service.IMath` and `ch.mrs.cp.service.IExecutor`. The implementation has a value of the full qualified **implementation** class name: `ch.mrs.cp.service.impl.MathImpl` and `ch.mrs.cp.service.impl.ExecutorNullImpl`. For each platform different a implementation is assigned to a service interface. A new definition overwrites an inherited one. 