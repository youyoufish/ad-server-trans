package com.upsmart.server.trans.server.contract;

/**
 * service API
 * 
 * inherit this class for owner service
 * 
 * @author Hang.Yu
 *
 */
public interface Contract
{
	/**
	 * ping检查
	 * @return true or false
	 */
	boolean ping();
	
	/**
	 * 
	 * @param name
	 * 			name from client
	 * @param _cmd
	 * 			operation command
	 * @param _ver
	 * 			operation command version
	 * @param _data
	 * 			transfer data
	 * @return
	 * 			result data
	 */
	byte[] query(String name, String _cmd, long _ver, byte[] _data);
}
