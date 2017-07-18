package org.mcrossplatform.service;


/**
 * Service to launch an external program.
 * @author donatmueller
 *
 */
public interface IExecutor {
	int execute(String programm, String... args) throws Exception;
}
