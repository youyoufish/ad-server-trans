package com.upsmart.server.trans.server;

import com.upsmart.server.trans.server.contract.Contract;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.server.TThreadPoolServer.Args;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;

import com.upsmart.server.trans.server.args.ServConnectionArgs;
import com.upsmart.server.trans.server.args.ThriftServConnectionArgs;
import com.upsmart.server.trans.transinterface.ITransfer;

/**
 * thrift tcp service
 * 
 * how to use：
 * 1)inherit Contract and implement the operate in the service.
 * 2)create ThriftServConnectionArgs.
 * 3)ServerService service = new ThriftService();
 *   service.start(thriftServConnectionArgs);
 * 4)waiting query from client.
 * 5)service.stop()
 * 
 * @author Hang.Yu
 *
 */
public class ThriftService implements ServerService
{
	private TServer server;

	/**
	 * start thrift service
	 */
	@Override
	public void start(ServConnectionArgs args) throws TTransportException
	{
		ThriftServConnectionArgs thriftServConnectionArgs = (ThriftServConnectionArgs)args;
		
		// create a new contract of thrift
		Contract contract = thriftServConnectionArgs.getContract();
		
		// 设置服务及端口
		TServerTransport serverTransport = new TServerSocket(thriftServConnectionArgs.getPort());

		Args tThreadPoolServerArgs = new TThreadPoolServer.Args(serverTransport);
		tThreadPoolServerArgs.processor(new ITransfer.Processor(contract));
		tThreadPoolServerArgs.maxWorkerThreads(thriftServConnectionArgs.getMaxWorkerThread());
		server = new TThreadPoolServer(tThreadPoolServerArgs);

		// 启动服务
		server.serve();
	}

	/**
	 * stop thrift service
	 */
	public void stop()
	{
		if (null != server)
		{
			server.stop();
		}
	}
}
