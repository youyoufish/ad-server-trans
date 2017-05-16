ad-server-transfer使用手册
===================

v1.0.0
----------

----------

[环境配置](#jump1)

[注意事项](#jump2)

[使用方法](#jump4)

* [service](#jump4-1)

* [client](#jump4-2)


----------

## <span id="jump1">环境配置</span>

JDK1.7

## <span id="jump2">注意事项</span>

1) ThriftClient非线程安全。

## <span id="jump4">使用方法</span>

### <span id="jump4-1">service</span>

1) 继承 Contract，实现service动作
```
import com.upsmart.server.transfer.server.contract.Contract;

public class MyContract implements Contract
{
	@Override
	public boolean ping()
	{
		return true;
	}

	@Override
	public byte[] query(String name, String _cmd, long _ver, byte[] _data)
	{
		return null;
	}
}
```
2) 服务器启动
```
        ServerService s = null;
    	try
		{
			s = new ThriftService(); // 使用thrift协议的service
			
			Contract c = new MyContract();
			ServConnectionArgs args = new ThriftServConnectionArgs(
            8088, // 侦听端口 
            16,   // 工作线程
            c);   // mycontract实例
			
			System.out.println("service will be started...");
			s.start(args); // 启动
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
```
3) 服务器关闭
```
		try
		{
			if(null != s)
			{
				s.stop();
			}
			System.out.println("service stopped...");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

```


### <span id="jump4-2">client</span>

1) 方法一：
```
        ThriftCliConnectionArgs thriftArgs = new ThriftCliConnectionArgs(
    		"192.168.24.126", // 远端服务地址
			8088,             // 远端服务端口
			0,                // 连接超时时间
			"local-ClientTest"); // 本地调用者名
            
        ClientProxy proxy = new ThriftClient(); //使用thrift协议的client
    	proxy.connect(thriftArgs);
					
		proxy.ping(); // 接口1
					
		RecvData recvData = proxy.query("return_data",0, bytes); // 接口2
					
		proxy.close();
```

2) 方法二：client pool
```
        ThriftCliConnectionArgs thriftArgs = new ThriftCliConnectionArgs(
        	"192.168.24.126", // 远端服务地址
			8088,             // 远端服务端口
			0,                // 连接超时时间
			"local-ClientTest"); // 本地调用者名
            
		ClientProxyPool<ClientProxy> pool = new ThriftCLientPool(thriftArgs); //使用thrift协议的client
        
        ClientProxy proxy = pool.getResource();
        if(proxy.isOpen())
    	{
            proxy.ping(); // 接口1
					
		    RecvData recvData = proxy.query("return_data",0, bytes); // 接口2
        }
        pool.returnResource(proxy); // 归还连接
        
        pool.close();

```




