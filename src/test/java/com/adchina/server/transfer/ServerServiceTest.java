package com.adchina.server.transfer;

import com.upsmart.server.trans.server.ServerService;
import com.upsmart.server.trans.server.ThriftService;
import com.upsmart.server.trans.server.args.ServConnectionArgs;
import com.upsmart.server.trans.server.args.ThriftServConnectionArgs;
import com.upsmart.server.trans.server.contract.Contract;

public class ServerServiceTest
{
	public static void main(String[] _args)
	{
		ServerService s = null;
		try
		{
			s = new ThriftService();
			
			Contract c = new MyContract();
			ServConnectionArgs args = new ThriftServConnectionArgs(8088, 16, c);
			
			System.out.println("service will be started...");
			s.start(args);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		try
		{
			if(null != s)
			{
				s.stop();
			}
			System.out.println("service stopped...");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
