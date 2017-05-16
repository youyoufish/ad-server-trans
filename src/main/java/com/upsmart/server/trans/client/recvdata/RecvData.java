package com.upsmart.server.trans.client.recvdata;

import com.upsmart.server.trans.enums.RecvStatus;
import com.upsmart.server.trans.transinterface.TransferInfo;

/**
 * data from service
 * @author Hang.Yu
 *
 */
public class RecvData
{
	/**
	 * status for transfer by user
	 */
	public RecvStatus status;
	
	/**
	 * data
	 */
	public TransferInfo data;
}
