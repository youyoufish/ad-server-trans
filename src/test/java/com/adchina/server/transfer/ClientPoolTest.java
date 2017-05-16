package com.adchina.server.transfer;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import com.upsmart.server.common.utils.StringUtil;
import com.upsmart.server.trans.client.ClientProxy;
import com.upsmart.server.trans.client.ClientProxyPool;
import com.upsmart.server.trans.client.ThriftCLientPool;
import com.upsmart.server.trans.client.args.ThriftCliConnectionArgs;
import com.upsmart.server.trans.client.recvdata.RecvData;
import org.junit.Test;

public class ClientPoolTest
{
	@Test
	public void ClientToServerTest() throws Exception
	{
		System.out.println("start.");
		
		ThriftCliConnectionArgs thriftArgs = new ThriftCliConnectionArgs(
				"192.168.24.126",
				8088,
				0,
				"local-ClientPoolTest");
		ClientProxyPool<ClientProxy> pool = new ThriftCLientPool(thriftArgs);

		final int THREAD_COUNT = 10;
		final CyclicBarrier barrier = new CyclicBarrier(THREAD_COUNT + 1);
		Thread[] threads = new Thread[THREAD_COUNT];

		for (int i = 0; i < THREAD_COUNT; i++)
		{
			threads[i] = new Thread(new EnRunnable(barrier, pool));
			threads[i].start();
		}

		try
		{
			barrier.await();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		catch (BrokenBarrierException e)
		{
			e.printStackTrace();
		}
		
		ClientProxy proxy = pool.getResource();
		if(proxy.isOpen())
		{
			RecvData t = proxy.query("get_ping_times", 0, null);
			System.out.println(StringUtil.byteArrayToString(t.data));
		}
		else
		{
			System.out.println(String.format("proxy isn't open"));
		}
		System.out.println(pool.getNumActive());
		pool.close();
		
		System.out.println("end.");
	}
	
	class EnRunnable implements Runnable
	{
		private CyclicBarrier barrier;
		private ClientProxyPool<ClientProxy> pool;
		public EnRunnable(CyclicBarrier cb, ClientProxyPool<ClientProxy> p)
		{
			barrier = cb;
			pool = p;
		}

		public void run()
		{
			try
			{
				for(int i=0; i<10000; i++)
				{
					ClientProxy proxy = pool.getResource();
					if(proxy.isOpen())
					{
						proxy.ping();
					}
					else
					{
						System.out.println(String.format("proxy isn't open in thread%d", Thread.currentThread().getId()));
					}
					pool.returnResource(proxy);
				}
			}
			catch (Throwable e)
			{
				e.printStackTrace();
			}
			finally
			{
			}

			try
			{
				System.out.println(String.format("%d thread end!", Thread.currentThread().getId()));
				barrier.await();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			catch (BrokenBarrierException e)
			{
				e.printStackTrace();
			}
		}

	}
}
