# irita-sdk-java

irita-sdk-java for opb

## Key Manger

### 1 recover

#### 1.1 recover from mnemonic

```java
        String mnemonic="xxx";
        Key km=new KeyManager(mnemonic);
```

#### 1.2 recover from privKey

```java
        String privKeyHex="3c49175daf981965679bf88d2690e22144424e16c84e9d397ddb58b63603eeec";
        BigInteger privKey=new BigInteger(privKeyHex,16);
        Key km=new KeyManager(privKey);
```

#### 1.3 recover from keystore

**read from str**

```java
String keystore="-----BEGIN TENDERMINT PRIVATE KEY-----\n"+
        "salt: 183EF9B57DEF8EF8C3AD9D21DE672E1B\n"+
        "type: sm2\n"+
        "kdf: bcrypt\n"+
        "\n"+
        "cpreEPwi0X3yIdsAIf94fR6s8L1TnDAQd/r4ifID6GmQX5a+4ehMmnTp2JjDpUe5\n"+
        "kpgRI7CzF0DjKpPLvY9V9ZSXJFN42LHWscxqQ1E=\n"+
        "=nJvd\n"+
        "-----END TENDERMINT PRIVATE KEY-----";

        InputStream input=new ByteArrayInputStream(keystore.getBytes(StandardCharsets.UTF_8));
        Key km=new KeyManager(input,"123456");
```

**read from file**

```java
        FileInputStream input=new FileInputStream("src/test/resources/priv.key");
        Key km=new KeyManager(input,"123456");
```

#### 1.4 recover from CaKeystore

```java
        FileInputStream input=new FileInputStream("src/test/resources/ca.JKS");
        Key km=KeyManager.recoverFromCAKeystore(input,"123456");
```

### 2 export

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

### 3 getPrivKey or getAddr

```java
public interface Key {
    BigInteger getPrivKey();

    String getAddr();
}
```

## How to use irita-sdk-java

### 1 use in normal java project

```java
        String mnemonic="opera vivid pride shallow brick crew found resist decade neck expect apple chalk belt sick author know try tank detail tree impact hand best";
        String opbUri="https://opbningxia.bsngate.com:18602";
        String projectId="xxx";
        String projectKey=null;

        Key km=new KeyManager(mnemonic);
        IritaClientOption.Fee fee=new IritaClientOption.Fee("2000000","uirita");
        IritaClientOption option=new IritaClientOption("10000000",fee,1073741824,"",1.0,km);
        OpbOption opbOption=new OpbOption(opbUri,projectId,projectKey);
        IritaClient client=new IritaClient(chainId,opbOption,option);

        WasmClient wasmClient=iritaClient.getWasmClient();
        CommunityGovClient comGovClient=iritaClient.getCommunityGovClient();
// get other client is as same as above
```

### 2 use at spring

Write next in application.yml

```yaml
irita:
  sdk:
    mnemonic: apart various produce pond bachelor size pumpkin gate pretty awake silver worth dust pledge pioneer patrol current fall escape lunar zero afraid this fish
    opbUri: https://opbningxia.bsngate.com:18602
    chainId: wenchangchain
    projectId: 7b3c53beda5c48c6b07d98804e156389
    contractAddr: iaa1cr8ard7tpvzf3g8n5llegc0fd92uuxeeuzt4s6
```

**Then use @ConfigurationProperties + @Component to register Bean.**
You can also use EnableConfigurationProperties(IritaSdkConfig.class) as you like

```java

@Configuration
@Data
@ConfigurationProperties(prefix = "irita.sdk")
public class IritaSdkConfig {
    private String mnemonic;
    private String opbUri;
    private String projectId;
    private String projectKey;
    private String chainId;
    private int gas;
    private String amount;
    private String denom;
    private String contractAddr;

    @Bean
    public IritaClient iritaClient() {
        Key km = new KeyManager(mnemonic);
        IritaClientOption.Fee fee = new IritaClientOption.Fee(getAmount(), getDenom());
        IritaClientOption option = new IritaClientOption(getGas(), fee, 1073741824, "", 1.0, km);

        OpbOption opbOption = new OpbOption(opbUri, projectId, projectKey);
        return new IritaClient(chainId, opbOption, option);
    }

    @Bean
    public WasmClient wasmClient(IritaClient iritaClient) {
        if (StringUtils.hasLength(contractAddr))
            ContractAddress.DEFAULT = contractAddr;

        return iritaClient.getWasmClient();
    }

    @Bean
    public CommunityGovClient comGovClient(IritaClient iritaClient) {
        return iritaClient.getCommunityGovClient();
    }

    public String getAmount() {
        if (StringUtils.hasLength(amount)) {
            return amount;
        }
        return "200000";
    }

    public String getDenom() {
        if (StringUtils.hasLength(denom)) {
            return denom;
        }
        return "uirita";
    }

    public int getGas() {
        return gas != 0 ? gas : 10000000;
    }
}
```

## Use CommunityGovClient

### 1. add department(添加部门管理员)

```java
        final String publicKey = "iaa1ytemz2xqq2s73ut3ys8mcd6zca2564a5lfhtm3";
        final String department = "测试部门";

        try {
            comGovClient.addDepartment(department, publicKey, baseTx);
        } catch (ContractException e) {
            // you can use log to record
            e.printStackTrace();
        }
```

### 2. add a member(添加一个成员)

```java
        String newAddr = "iaa1wfs050mv8taydn4cttsrhr5dq3tpdaemcm5sk2";

        try {
            comGovClient.addMember(newAddr, Role.HASH_ADMIN, baseTx);
        } catch (ContractException | IOException e) {
            e.printStackTrace();
        }
        // 关于角色见 Role.java
```

### 3. other operation(其他方法)

详见KeyMangerTest.java, WasmTest.java, OpbTest.java
