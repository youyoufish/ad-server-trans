package com.upsmart.server.trans.server.args;

import com.upsmart.server.trans.server.contract.Contract;

public interface ServConnectionArgs
{
	/**
	 * 端口
	 * @return
	 */
	int getPort();

	/**
	 * 工作线程
	 * @return
	 */
	int getMaxWorkerThread();

	/**
	 * 请求超时时间
	 * @return
	 */
	int getRequestTimeout();

	/**
	 * 处理类
	 * @return
	 */
	Contract getContract();
}
