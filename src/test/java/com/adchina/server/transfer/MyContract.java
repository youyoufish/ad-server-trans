package com.adchina.server.transfer;

import com.upsmart.server.common.utils.StringUtil;
import com.upsmart.server.trans.server.contract.Contract;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.atomic.AtomicInteger;

public class MyContract implements Contract
{
	private final AtomicInteger value = new AtomicInteger(0);

	@Override
	public boolean ping()
	{
		value.addAndGet(1);
		return true;
	}

	@Override
	public byte[] query(String name, String _cmd, long _ver, byte[] _data)
	{
		if(_cmd.equals("get_ping_times"))
		{
			String ret = String.format("%s %s %d %d",name, _cmd, _ver, value.get());
			try
			{
				return StringUtil.stringToByteArray(ret);
			}
			catch (UnsupportedEncodingException e)
			{
				e.printStackTrace();
			}
		}
		
		if(_cmd.equals("return_data"))
		{
			return _data;
		}
		
		return null;
	}
}