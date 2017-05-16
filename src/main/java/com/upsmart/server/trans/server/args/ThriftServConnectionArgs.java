package com.upsmart.server.trans.server.args;

import com.upsmart.server.trans.server.contract.Contract;

public class ThriftServConnectionArgs implements ServConnectionArgs
{
	private int port;
	private int maxWorkerThread;
	private Contract contract;

	public ThriftServConnectionArgs(int _port, int _maxWorkerThread, Contract _contract)
	{
		this.port = _port;
		this.maxWorkerThread = _maxWorkerThread;
		this.contract = _contract;
	}
	
	@Override
	public int getPort()
	{
		return port;
	}

	@Override
	public int getMaxWorkerThread()
	{
		return maxWorkerThread;
	}

	@Override
	public Contract getContract()
	{
		return contract;
	}
}
