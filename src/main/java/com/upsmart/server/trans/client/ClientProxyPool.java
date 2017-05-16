package com.upsmart.server.trans.client;

import java.io.Closeable;

import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * pool for client proxy
 * 
 * @author Hang.Yu
 *
 */
public abstract class ClientProxyPool<T> implements Closeable
{
	protected GenericObjectPool<T> internalPool;

	/**
	 * Using this constructor means you have to set and initialize the
	 * internalPool yourself.
	 */
	public ClientProxyPool()
	{
	}

	public ClientProxyPool(final GenericObjectPoolConfig poolConfig, PooledObjectFactory<T> factory)
	{
		initPool(poolConfig, factory);
	}

	@Override
	public void close()
	{
		closeInternalPool();
	}

	protected void closeInternalPool()
	{
		try
		{
			internalPool.close();
		}
		catch (Exception e)
		{
			throw new RuntimeException("Could not destroy the pool", e);
		}
	}
	
	public void destroy()
	{
		closeInternalPool();
	}

	/**
	 * Return the number of instances currently borrowed from this pool. 
	 * Returns a negative value if this information is not available
	 * @return
	 */
	public int getNumActive()
	{
		if (this.internalPool == null || this.internalPool.isClosed())
		{
			return -1;
		}

		return this.internalPool.getNumActive();
	}
	
	/**
	 * return a resource 
	 * @return
	 */
	public T getResource()
	{
		try
		{
			return internalPool.borrowObject();
		}
		catch (Exception e)
		{
			throw new RuntimeException("Could not get a resource from the pool", e);
		}
	}
	
	private void initPool(final GenericObjectPoolConfig poolConfig, PooledObjectFactory<T> factory)
	{

		if (this.internalPool != null)
		{
			try
			{
				closeInternalPool();
			}
			catch (Exception e)
			{
			}
		}

		this.internalPool = new GenericObjectPool<T>(factory, poolConfig);
		//this.internalPool.setTestOnBorrow(true); // check the resource is valid or not when borrowed
	}

	public boolean isClosed()
	{
		return this.internalPool.isClosed();
	}


	public void returnResourceObject(final T resource)
	{
		if (resource == null)
		{
			return;
		}
		try
		{
			internalPool.returnObject(resource);
		}
		catch (Exception e)
		{
			throw new RuntimeException("Could not return the resource to the pool", e);
		}
	}

	public void returnBrokenResource(final T resource)
	{
		if (resource != null)
		{
			returnBrokenResourceObject(resource);
		}
	}

	protected void returnBrokenResourceObject(final T resource)
	{
		try
		{
			internalPool.invalidateObject(resource);
		}
		catch (Exception e)
		{
			throw new RuntimeException("Could not return the resource to the pool", e);
		}
	}

	public void returnResource(final T resource)
	{
		if (resource != null)
		{
			returnResourceObject(resource);
		}
	}
}
