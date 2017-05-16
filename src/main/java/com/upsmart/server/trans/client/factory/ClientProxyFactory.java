package com.upsmart.server.trans.client.factory;

import java.util.concurrent.atomic.AtomicReference;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import com.upsmart.server.trans.client.ClientProxy;
import com.upsmart.server.trans.client.args.CliConnectionArgs;

/**
 * create client proxy
 * @author Hang.Yu
 * @since 2015/06/30
 */
public class ClientProxyFactory implements PooledObjectFactory<ClientProxy>
{
	private final AtomicReference<CliConnectionArgs> hostAndPort = new AtomicReference<CliConnectionArgs>();

	public ClientProxyFactory(final CliConnectionArgs args)
	{
		super();
		this.hostAndPort.set(args);
	}

	public void setHostAndPort(final CliConnectionArgs args)
	{
		this.hostAndPort.set(args);
	}

	/**
	 * 激活对象
	 */
	@Override
	public void activateObject(PooledObject<ClientProxy> pooledProxy)
			throws Exception
	{
		// nothing to do?
	}

	/**
	 * 销毁对象
	 */
	@Override
	public void destroyObject(PooledObject<ClientProxy> pooledProxy) throws Exception
	{
		final ClientProxy proxy = pooledProxy.getObject();
		if (null != proxy && proxy.isOpen())
		{
			try
			{
				proxy.close();
			}
			catch (Exception e)
			{
			}
		}
	}

	/**
	 * 创建对象实例，用于填充对象池
	 */
	@Override
	public PooledObject<ClientProxy> makeObject() throws Exception
	{
		final CliConnectionArgs hostAndPort = this.hostAndPort.get();
		final ClientProxy proxy = hostAndPort.createClientProxy();

		return new DefaultPooledObject<ClientProxy>(proxy);
	}

	/**
	 * 挂起一个对象，将对象还给对象池时被调用
	 */
	@Override
	public void passivateObject(PooledObject<ClientProxy> pooledProxy)
			throws Exception
	{
		// nothing to do?
	}

	/**
	 * 验证有效性
	 */
	@Override
	public boolean validateObject(PooledObject<ClientProxy> pooledProxy)
	{
		final ClientProxy proxy = pooledProxy.getObject();
		try
		{
			if(null == proxy)
			{
				return false;
			}
			proxy.ping();
			return true;
		}
		catch (final Exception e)
		{
			return false;
		}
	}
}
