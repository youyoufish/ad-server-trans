package com.upsmart.server.trans.client.args;

import org.apache.thrift.transport.TTransportException;

import com.upsmart.server.trans.client.ClientProxy;
import com.upsmart.server.trans.client.ThriftClient;

/**
 * class for connection at the thrift client
 * 
 * @author Hang.Yu
 * @since 2015/06/30
 */
public class ThriftCliConnectionArgs implements CliConnectionArgs
{
	private String 	address;
	private int 	port;
	private int 	connectionTimeout;

	/**
	 * construction
	 * 
	 * @param _address
	 * @param _port
	 * @param _connectionTimeout
	 */
	public ThriftCliConnectionArgs(
			final String _address, 
			final int _port,
			final int _connectionTimeout)
	{
		this.address = _address;
		this.port = _port;
		this.connectionTimeout = _connectionTimeout;
	}

	/**
	 * get remote service address
	 * example: 192.168.24.126
	 * @return address
	 */
	@Override
	public String getAddress()
	{
		return address;
	}

	/**
	 * get remote service port
	 * example: 8888
	 * @return port
	 */
	@Override
	public int getPort()
	{
		return port;
	}

	/**
	 * get connection timeout
	 * example: 5000
	 * @return milliseconds timeout
	 */
	@Override
	public int getConnectionTimeout()
	{
		return connectionTimeout;
	}

	/**
	 * create client proxy by this args
	 * @return client proxy 
	 * @throws TTransportException
	 */
	@Override
	public ClientProxy createClientProxy() throws TTransportException
	{
		ThriftClient client = new ThriftClient();
		client.connect(this);
		return client;
	}
}
