package com.adchina.server.transfer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import com.upsmart.server.common.utils.StringUtil;
import com.upsmart.server.trans.client.ClientProxy;
import com.upsmart.server.trans.client.ThriftClient;
import com.upsmart.server.trans.client.args.ThriftCliConnectionArgs;
import com.upsmart.server.trans.client.recvdata.RecvData;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ClientTest
{
	List<byte[]> randomBytes = new ArrayList<>();

	@Before
	public void CreateRandomBytes() throws IOException
	{
		String fileName = StringUtil.combinePath(new File(".").getCanonicalPath(), "src/test/java/testdata/test.txt");
		
		InputStream in = null;
		try
		{
			in = new FileInputStream(fileName);
			
			// 一次读多个字节,读入多个字节到字节数组中,byteread为一次读入的字节数
			int byteread = 0;
			final int count = 1048576; // 1MB = 1024*1024
			byte[] tempbytes = new byte[count]; 
			while ((byteread = in.read(tempbytes)) != -1) 
			{
				byte[] tempbytes2 = new byte[count*50];
				for(int i=0; i<50; i++)
				{
					System.arraycopy(tempbytes,0,tempbytes2,i*count,tempbytes.length);
				}
				randomBytes.add(tempbytes2.clone());
				System.out.println(String.format("%d bytes to be loaded from file", count*50));
			}
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
		}
		finally
		{
			if (in != null)
			{
				try
				{
					in.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	@Test
	public void ClientToServerTest() throws Exception
	{
		System.out.println("start.");

		final int THREAD_COUNT = 3;
		final CyclicBarrier barrier = new CyclicBarrier(THREAD_COUNT + 1);
		Thread[] threads = new Thread[THREAD_COUNT];

		for (int i = 0; i < THREAD_COUNT; i++)
		{
			threads[i] = new Thread(new EnRunnable(barrier));
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

		System.out.println("end.");
	}

	class EnRunnable implements Runnable
	{
		private CyclicBarrier barrier;

		public EnRunnable(CyclicBarrier cb)
		{
			barrier = cb;
		}

		public void run()
		{
			try
			{
				ThriftCliConnectionArgs thriftArgs = new ThriftCliConnectionArgs(
						"192.168.24.126",
						8088,
						0,
						"local-ClientTest");
				
				for (int i = 0; i < randomBytes.size(); ++i)
				{
					byte[] bytes = randomBytes.get(i);
					
					ClientProxy proxy = new ThriftClient();
					proxy.connect(thriftArgs);
					
					proxy.ping();
					System.out.println(String.format("%d thread,%d bytes to be sended.", 
							Thread .currentThread().getId(), bytes.length));
					
					RecvData recvData = proxy.query("return_data",0, bytes);

					Assert.assertTrue(Arrays.equals(recvData.data, bytes));
					proxy.close();
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
				System.out.print(String.format("%d thread end!\r\n", Thread
						.currentThread().getId()));
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
