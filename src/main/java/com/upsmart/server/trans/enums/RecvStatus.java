package com.upsmart.server.trans.enums;

public enum RecvStatus
{
	UNKNOWN(0),
	SUCCESS(1),
    NO_DATA(1001),
    PARAMS_INVALID(1002),
	CONNECTION_EXCEPTION(2000),
	CONNECTION_CLOSED(2001);

    private final int value;
    RecvStatus(int value)
    {
        this.value = value;
    }
    public int getValue()
    {
        return value;
    }
}
