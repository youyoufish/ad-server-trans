package com.upsmart.server.trans.client;

import com.upsmart.server.trans.client.factory.ClientProxyFactory;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import com.upsmart.server.trans.client.args.CliConnectionArgs;

/**
 * pool for thrift client
 * 
 * @author Hang.Yu
 *
 */
public class ThriftCLientPool extends ClientProxyPool<ClientProxy>
{
	public ThriftCLientPool(final CliConnectionArgs _args)
	{
		super(new GenericObjectPoolConfig(), new ClientProxyFactory(_args));
	}

	/**
	 * return a resource 
	 */
	@Override
	public ThriftClient getResource()
	{
		ThriftClient proxy = (ThriftClient)super.getResource();
		return proxy;
	}

	/**
	 * return a resource which is broken
	 * @param resource
	 */
	public void returnBrokenResource(final ThriftClient resource)
	{
		if (resource != null)
		{
			returnBrokenResourceObject(resource);
		}
	}

	/**
	 * return a resource which is active
	 * @param resource
	 */
	public void returnResource(final ThriftClient resource)
	{
		if (resource != null)
		{
			returnResourceObject(resource);
		}
	}

}
