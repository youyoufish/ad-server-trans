package com.upsmart.server.trans.server.args;

import com.upsmart.server.trans.server.contract.Contract;

public interface ServConnectionArgs
{
	int getPort();
	
	int getMaxWorkerThread();
	
	Contract getContract();
}
