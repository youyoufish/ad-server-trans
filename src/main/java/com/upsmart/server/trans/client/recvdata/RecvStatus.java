package com.upsmart.server.trans.client.recvdata;

public enum RecvStatus
{
	UNKNOWN(0),
	
	SUCCESS(1000),
	
	CONNECTION_EXCEPTION(2000),
	
	CONNECTION_CLOSED(2001);

    private final int value;

    private RecvStatus(int value)
    {
        this.value = value;
    }

    public int getValue()
    {
        return value;
    }
}
