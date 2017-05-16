package com.upsmart.server.trans.server.contract;

import com.upsmart.server.trans.transinterface.BinaryData;
import com.upsmart.server.trans.transinterface.ClientInfo;
import com.upsmart.server.trans.transinterface.ITransfer;
import com.upsmart.server.trans.transinterface.TransferInfo;

/**
 * the proxy for service contract by thrift
 * 
 * @author Hang.Yu
 *
 */
public class ThriftContract implements ITransfer.Iface
{
	private Contract contract;
	public ThriftContract(Contract _contract)
	{
		this.contract = _contract;
	}
	
	@Override
	public boolean ping()
	{
		return contract.ping();
	}

	@Override
	public TransferInfo query(String cmd, TransferInfo trans)
	{
		TransferInfo t = new TransferInfo();
		t.setStatus(-1);
		if(null != trans)
		{
			byte[] data = contract.query(
					null == trans.getClientinfo() ? null:trans.getClientinfo().getUsername(),
					cmd,
					trans.getVersion(), 
					null == trans.getData() ? null : trans.getData().getData());
			
			ClientInfo clientInfo = new ClientInfo();
			//clientInfo.setVersion(0);
			clientInfo.setUsername("hang.yu");
			//clientInfo.setPassword("");
			//clientInfo.setIp("");
			//clientInfo.setPort("");
			//clientInfo.setTime("");
			
			BinaryData binaryData = new BinaryData();
			//binaryData.setVersion(0);
			//binaryData.setType(0);
			//binaryData.setLength(0);
			//binaryData.setCheckalgorithm("");
			//binaryData.setCheckcodes(null);
			binaryData.setData(data);
			
			t.setClientinfo(clientInfo);
			t.setData(binaryData);
			t.setStatus(1);
		}
		return t;
	}
}
