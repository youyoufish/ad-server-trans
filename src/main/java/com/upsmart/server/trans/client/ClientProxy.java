package com.upsmart.server.trans.client;

import java.io.Closeable;

import com.upsmart.server.trans.client.args.CliConnectionArgs;
import com.upsmart.server.trans.client.recvdata.RecvData;
import com.upsmart.server.trans.transinterface.TransferInfo;
import org.apache.thrift.TException;

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

	boolean ping() throws TException;

	RecvData query(String cmd, TransferInfo trans) ;
}
