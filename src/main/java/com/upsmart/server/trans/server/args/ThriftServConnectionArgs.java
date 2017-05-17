package com.upsmart.server.trans.server.args;

import com.upsmart.server.trans.server.contract.Contract;

public class ThriftServConnectionArgs implements ServConnectionArgs
{
	private int port;
	private int maxWorkerThread ;
	private int requestTimeout ;// 秒
	private Contract contract;

	public ThriftServConnectionArgs(int port, Contract contract){
		this(port, contract, Integer.MAX_VALUE, 20);
	}

	public ThriftServConnectionArgs(int port, Contract contract, int maxWorkerThread){
		this(port, contract, maxWorkerThread, 20);
	}

	public ThriftServConnectionArgs(int port, Contract contract, int maxWorkerThread, int requestTimeout)
	{
		this.port = port;
		this.contract = contract;
		this.maxWorkerThread = maxWorkerThread;
		this.requestTimeout = requestTimeout;
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
	public int getRequestTimeout() { return requestTimeout;}

	@Override
	public Contract getContract()
	{
		return contract;
	}
}
