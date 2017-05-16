package com.adchina.server.transfer;

import com.upsmart.server.common.utils.DateUtil;
import com.upsmart.server.common.utils.StringUtil;
import com.upsmart.server.trans.server.contract.Contract;
import com.upsmart.server.trans.transinterface.BinaryData;
import com.upsmart.server.trans.transinterface.ClientInfo;
import com.upsmart.server.trans.transinterface.TransferInfo;
import org.apache.thrift.TException;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class MyContract implements Contract {
	private final AtomicInteger value = new AtomicInteger(0);

	@Override
	public boolean ping() {
		value.addAndGet(1);
		return true;
	}

	@Override
	public TransferInfo query(String _cmd, TransferInfo trans) throws TException {

		ClientInfo ci = new ClientInfo();
		ci.setUsername("client");
		ci.setTime(DateUtil.format(new Date(), "yyyyMMdd HHmmss"));

		TransferInfo ret = new TransferInfo();
		ret.setVersion(1);
		ret.setClientinfo(ci);

		if (_cmd.equals("get_ping_times")) {
			String s = String.format("%s %s %d %d", trans.clientinfo.getUsername(), _cmd, trans.getVersion(), value.get());

			BinaryData data = new BinaryData();
			data.setData(s.getBytes());

			ret.setData(data);
		}
		else if (_cmd.equals("return_data")) {

		}

		return ret;

	}
}