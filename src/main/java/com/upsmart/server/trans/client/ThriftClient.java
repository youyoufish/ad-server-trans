package com.upsmart.server.trans.client;

import com.upsmart.server.common.utils.StringUtil;
import com.upsmart.server.trans.client.args.CliConnectionArgs;
import com.upsmart.server.trans.client.args.ThriftCliConnectionArgs;
import com.upsmart.server.trans.client.recvdata.RecvData;
import com.upsmart.server.trans.transinterface.TransferInfo;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransportException;

import com.upsmart.server.trans.enums.RecvStatus;
import com.upsmart.server.trans.transinterface.ITransfer;

/**
 * thrift client, remote thrift service 
 * 
 * not thread safety
 * 
 * how to use：
 * 1.create a new instance of ThriftCliConnectionArgs
 * 2.ClientProxy proxy = new ThriftClient();
 *	 proxy.connect(thriftCliConnectionArgs);
 * 3.do
 * 4.proxy.close()
 * 
 * @author Hang.Yu
 *
 */
public class ThriftClient implements ClientProxy {
	private TSocket tTransport;
	private ITransfer.Client tTransferClient;
	private ThriftCliConnectionArgs thriftArgs;

	/**
	 * connect to remote service
	 */
	@Override
	public void connect(CliConnectionArgs args) throws TTransportException {
		if (null == args) {
			throw new RuntimeException("CliConnectionArgs is null!");
		}

		if (isOpen()) {
			throw new RuntimeException("ThriftClient is active! ");
		} else {
			close();
		}

		thriftArgs = (ThriftCliConnectionArgs) args;
		if (thriftArgs.getConnectionTimeout() <= 0) {
			tTransport = new TSocket(thriftArgs.getAddress(), thriftArgs.getPort());
		} else {
			tTransport = new TSocket(thriftArgs.getAddress(), thriftArgs.getPort(), thriftArgs.getConnectionTimeout());
		}
		tTransport.open();
		if (!isOpen()) {
			throw new RuntimeException("TTransport is not open!");
		}
		TProtocol tProtocol = new TBinaryProtocol(tTransport);
		tTransferClient = new ITransfer.Client(tProtocol);
	}

	/**
	 * close the connection
	 */
	@Override
	public void close() {
		if (null != tTransport) {
			try {
				tTransport.close();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * return true if the connection is open
	 */
	@Override
	public boolean isOpen() {
		if (tTransport != null) {
			return tTransport.isOpen();
		} else {
			return false;
		}
	}

	/**
	 * check the connection is active
	 */
	@Override
	public boolean ping() throws TException {
		if (null != tTransferClient) {
			return tTransferClient.ping();
		}
		return false;
	}

	@Override
	public RecvData query(String cmd, TransferInfo trans) {

		RecvData ret = new RecvData();
		ret.status = RecvStatus.UNKNOWN;

		if (StringUtil.isNullOrEmpty(cmd) || null == trans) {
			ret.status = RecvStatus.PARAMS_INVALID;
			return ret;
		}

		if (null != tTransferClient) {
			try {
				TransferInfo response = tTransferClient.query(cmd, trans);
				if(null != response){
					ret.data = response;
					ret.status = RecvStatus.SUCCESS;
				}
				else{
					ret.status = RecvStatus.NO_DATA;
				}

			} catch (TException ex) {
				ret.status = RecvStatus.CONNECTION_EXCEPTION;
			}
		} else {
			ret.status = RecvStatus.CONNECTION_CLOSED;
		}
		return ret;
	}
}
