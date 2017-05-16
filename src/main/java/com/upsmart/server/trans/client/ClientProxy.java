package com.upsmart.server.trans.client;

import java.io.Closeable;

import com.upsmart.server.trans.client.args.CliConnectionArgs;
import com.upsmart.server.trans.client.recvdata.RecvData;

/**
 * client API
 * 
 * @author Hang.Yu
 * @since 2015/06/30
 */
public interface ClientProxy extends Closeable
{
	/**
	 * connect to remote service
	 * 
	 * @param args
	 * 			parameters for connection
	 * @throws Exception
	 */
	void connect(CliConnectionArgs args) throws Exception;

	/**
	 * return true if the connection is open
	 * 
	 * @return true or false
	 */
	boolean isOpen();
	
	/**
	 * check the connection is active 
	 * 
	 * @return true or false
	 * @throws Exception
	 */
	boolean ping() throws Exception;
	
	/**
	 * query data from remote service
	 * 
	 * @param cmd
	 * 			string for command  
	 * @param ver
	 * 			version for command
	 * @param bytes
	 * 			data transfered to remote service
	 * @return 
	 * 			data return from remote service
	 * @throws Exception
	 */
	RecvData query(String cmd, long ver, byte[] bytes) throws Exception;
}
