namespace java com.upsmart.server.trans.transinterface 
 
struct ClientInfo
{
    1: required string time
    2: optional string ip,
    3: optional string username,
    4: optional string password
}

struct BinaryData
{
    1: optional i64 length,
    2: optional string checkalgorithm,
    3: optional binary checkcodes,
    4: optional binary data
}

struct TransferInfo
{
    1: required i64 version,
    2: required ClientInfo clientinfo,
    3: optional i64 type,
    4: optional i64 status,
    5: optional BinaryData data
}

service ITransfer
{
  bool ping(),
  TransferInfo query(1: string cmd, 2: TransferInfo trans)
}
