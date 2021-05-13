# service-sdk-java

service-sdk-java支持bsn(opb)

## Key的管理

### 1 恢复key

#### 1.1 从助记词恢复key

```java
        String mnemonic = "xxx";
        Key km = new KeyManager(mnemonic);
```

#### 1.2 从私钥hex(编码)恢复key

```java
        String privKeyHex = "3c49175daf981965679bf88d2690e22144424e16c84e9d397ddb58b63603eeec";
        BigInteger privKey = new BigInteger(privKeyHex,16);
        Key km = new KeyManager(privKey);
```

#### 1.3 从tendermint keystore恢复key

**从字符串读取**

```java
String keystore = "-----BEGIN TENDERMINT PRIVATE KEY-----\n"+
        "salt: 183EF9B57DEF8EF8C3AD9D21DE672E1B\n"+
        "type: sm2\n"+
        "kdf: bcrypt\n"+
        "\n"+
        "cpreEPwi0X3yIdsAIf94fR6s8L1TnDAQd/r4ifID6GmQX5a+4ehMmnTp2JjDpUe5\n"+
        "kpgRI7CzF0DjKpPLvY9V9ZSXJFN42LHWscxqQ1E=\n"+
        "=nJvd\n"+
        "-----END TENDERMINT PRIVATE KEY-----";

        InputStream input = new ByteArrayInputStream(keystore.getBytes(StandardCharsets.UTF_8));
        Key km = new KeyManager(input,"123456");
```

**从文件中读取**

```java
        FileInputStream input = new FileInputStream("src/test/resources/priv.key");
        Key km = new KeyManager(input,"123456");
```

#### 1.4 从ca keystore恢复key

```java
        FileInputStream input = new FileInputStream("src/test/resources/ca.JKS");
        Key km = KeyManager.recoverFromCAKeystore(input,"123456");
```

### 2 导出key

```java
public interface Key {
    /**
     * export as keystore
     *
     * @param password password of keystore. The password is very important for recovery, so never forget it
     */
    String export(String password) throws IOException;
}
```

### 3 获得私钥/获得地址

```java
public interface Key {
    BigInteger getPrivKey();

    String getAddr();
}
```

## 怎么使用 service-sdk-java

### 1. 初始化客户端

本地网络初始化如下：

```java
        String mnemonic = "opera vivid pride shallow brick crew found resist decade neck expect apple chalk belt sick author know try tank detail tree impact hand best";
        String opbUri = "https://opbningxia.bsngate.com:18602";
        String projectId = "xxx";
        String projectKey = null;

        Key km = new KeyManager(mnemonic);
        IritaClientOption.Fee fee = new IritaClientOption.Fee("2000000","uirita");
        IritaClientOption option = new IritaClientOption("10000000",fee,1073741824,"",1.0,km);
        OpbOption opbOption = new OpbOption(opbUri,projectId,projectKey);
        IritaClient client = new IritaClient(chainId,opbOption,option);

        ServiceClient serviceClient = iritaClient.getServiceClient();
```

bsn(opb) 网络初始化如下：


```java
        String mnemonic = "opera vivid pride shallow brick crew found resist decade neck expect apple chalk belt sick author know try tank detail tree impact hand best";
        String opbUr i= "https://opbningxia.bsngate.com:18602";
        String projectId = "xxx";
        String projectKey = "xxx";

        Key km = new KeyManager(mnemonic);
        IritaClientOption.Fee fee = new IritaClientOption.Fee("2000000","uirita");
        IritaClientOption option = new IritaClientOption("10000000",fee,1073741824,"",1.0,km);
        OpbOption opbOption = new OpbOption(opbUri,projectId,projectKey);
        IritaClient client = new IritaClient(chainId,opbOption,option);

        ServiceClient serviceClient = iritaClient.getServiceClient();
```

## 使用Service模块

### 1.定义一个服务

```java
        // 定义一次交易的费用
        BaseTx baseTx = new BaseTx(200000,new IritaClientOption.Fee("200000","uirita"));

        String schemas = "{\"input\":{\"type\":\"object\"},\"output\":{\"type\":\"object\"},\"error\":{\"type\":\"object\"}}";
        String serviceName = "test";

        DefineServiceRequest defineReq = new DefineServiceRequest();
        defineReq.setServiceName(serviceName);
        defineReq.setDescription("this is a test service");
        defineReq.setTags(null);
        defineReq.setAuthorDescription("service provider");
        defineReq.setSchemas(schemas);

        ResultTx resultTx = serviceClient.defineService(defineReq,baseTx);
```

### 2.绑定一个服务

```java
BindServiceRequest bindReq=mockBindReq(serviceName);
        String pricing = "{\"price\":\"1uirita\"}";
        String options = "{}";

        BindServiceRequest bindReq = new BindServiceRequest();
        bindReq.setServiceName(serviceName);
        bindReq.setDeposit(new Coin("20000","uirita"));
        bindReq.setPricing(pricing);
        bindReq.setOptions(options);
        bindReq.setQoS(10);

        ResultTx resultTx = serviceClient.bindService(bindReq,baseTx);
```

### 3.一次服务调用

```java
        String input="{\"header\":{},\"body\":{\"pair\":\"uirita-usdt\"}}";
        CallServiceRequest callReq = new CallServiceRequest();
        callReq.setServiceName(serviceName);

        ArrayList<String> providers = new ArrayList<>();
        providers.add(km.getAddr());
        callReq.setProviders(providers);
        callReq.setInput(input);
        callReq.setServiceFeeCap(new Coin("200","upoint"));
        callReq.setTimeout(10);
        callReq.setRepeated(true);
        callReq.setRepeatedTotal(-1);

        CallServiceResp callServiceResp = serviceClient.callService(callReq,baseTx);
```

### 4.一次服务响应

```java
        ResponseServiceRequest responseReq = new ResponseServiceRequest();
        responseReq.setRequestId(reqId);
        String output = "{\"header\":{},\"body\":{\"last\":\"1:100\"}}";
        String testResult = "{\"code\":200,\"message\":\"\"}";
        responseReq.setOutput(output);
        responseReq.setResult(testResult);

        ResultTx resultTx = serviceClient.responseService(responseReq,baseTx);
```

### 5.订阅服务请求

客户端需要轮询调用subscribeRequest(...,最小高度，最大高度)方法
不设置最小高度或最大高度，传入Null
当处理完高度为0 - 1500高度。
下一次指定为 1501 - 新的高度

```java
        int minHeight = 0;
        int maxHeight = 15006;
        List<Msg> otherMgs = serviceClient.subscribeRequest("test","iaa1ytemz2xqq2s73ut3ys8mcd6zca2564a5lfhtm3",minHeight,maxHeight);
        for(Msg msg: otherMgs){
            System.out.println(msg.getValue().getInput());
        }
```

### 6.订阅服务响应

客户端需要轮询调用subscribeResponse(...,最小高度，最大高度)方法
不设置最小高度或最大高度，传入Null
当处理完高度为0 - 1500高度。
下一次指定为 1501 - 新的高度

```java
        int minHeight = 0;
        int maxHeight = 15006;
        List<Txs> otherTxs = serviceClient.subscribeResponse("hello","iaa1ytemz2xqq2s73ut3ys8mcd6zca2564a5lfhtm3","iaa1ytemz2xqq2s73ut3ys8mcd6zca2564a5lfhtm3",maxHeight,maxHeight);
        for(Txs tx: otherTxs){
            System.out.println(tx.getRawLog());
        }
```

### 7.service查询

```java
    // 参数见实际调用
    serviceClient.queryServiceDefinition(...)
    serviceClient.queryServiceBinding(...)
    serviceClient.queryServiceBindings(...)
    serviceClient.queryServiceRequest(...)
    serviceClient.queryServiceRequests(...)
    serviceClient.queryRequestsByReqCtx(...)
    serviceClient.queryServiceResponse(...)
    serviceClient.queryServiceResponses(...)
    serviceClient.queryRequestContext(...)
```

### 8.使用service例子

详见ServiceTest.java（代码中使用的是本地网络）

如要使用opb网络使用下列代码初始化客户端
```java
        String mnemonic = "opera vivid pride shallow brick crew found resist decade neck expect apple chalk belt sick author know try tank detail tree impact hand best";
        String opbUri = "https://opbningxia.bsngate.com:18602";
        String projectId = "xxx";
        String projectKey = null;

        Key km = new KeyManager(mnemonic);
        IritaClientOption.Fee fee = new IritaClientOption.Fee("2000000","uirita");
        IritaClientOption option = new IritaClientOption("10000000",fee,1073741824,"",1.0,km);
        OpbOption opbOption = new OpbOption(opbUri,projectId,projectKey);
        IritaClient client = new IritaClient(chainId,opbOption,option);

        ServiceClient serviceClient = iritaClient.getServiceClient();
```
