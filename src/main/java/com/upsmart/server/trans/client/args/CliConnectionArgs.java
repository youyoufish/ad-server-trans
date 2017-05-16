package com.upsmart.server.trans.client.args;

import com.upsmart.server.trans.client.ClientProxy;

/**
 * class for connection at the client
 * 
 * @author Hang.Yu
 * @since 2015/06/30
 */
public interface CliConnectionArgs
{
	/**
	 * get remote service address
	 * @return address
	 */
	public String getAddress();
	
	/**
	 * get remote service port
	 * @return port
	 */
	public int getPort();
	
	/**
	 * get connection timeout
	 * @return milliseconds timeout
	 */
	public int getConnectionTimeout();
	
	/**
	 * client name
	 * @return name
	 */
	public String getName();
	
	/**
	 * create client proxy by this args
	 * @return client proxy 
	 * @throws Exception
	 */
	public ClientProxy createClientProxy() throws Exception;
}
