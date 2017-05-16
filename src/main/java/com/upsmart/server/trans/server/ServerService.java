package com.upsmart.server.trans.server;

import com.upsmart.server.trans.server.args.ServConnectionArgs;

/**
 * Service API
 * @author Hang.Yu
 * @since 2015/06/30
 */
public interface ServerService
{
	/**
	 * start service
	 * @param args 
	 * @throws Exception
	 */
	void start(ServConnectionArgs args) throws Exception;
	
	/**
	 * stop service
	 * @throws Exception
	 */
	void stop() throws Exception;
}
