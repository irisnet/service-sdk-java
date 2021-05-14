package irita.sdk;

import irita.sdk.client.IritaClient;
import irita.sdk.client.IritaClientOption;
import irita.sdk.exception.QueryException;
import irita.sdk.module.base.*;
import irita.sdk.module.keys.Key;
import irita.sdk.module.keys.KeyManager;
import irita.sdk.module.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
public class ServiceTest {
    private ServiceClient serviceClient;
    private BaseTx baseTx;
    private Key km;
    private final Logger log = Logger.getLogger("irita.sdk.ServiceTest");

    @BeforeEach
    public void init() {
        String mnemonic = "opera vivid pride shallow brick crew found resist decade neck expect apple chalk belt sick author know try tank detail tree impact hand best";
        km = new KeyManager(mnemonic);
        IritaClientOption option = IritaClientOption.getDefaultOption(km);

//        String nodeUri = "http://localhost:26657";
        String nodeUri = "http://10.1.4.9:26657";
        String lcd = "http://10.1.4.9:1317";
//        String lcd = "http://localhost:1317";
        String chainId = "test";
        IritaClient client = new IritaClient(nodeUri, lcd, chainId, option);
        serviceClient = client.getServiceClient();
        baseTx = new BaseTx(200000, new IritaClientOption.Fee("200000", "upoint"));

        assertEquals("iaa1ytemz2xqq2s73ut3ys8mcd6zca2564a5lfhtm3", km.getAddr());
    }

    // TODO
    @Test
    public void TestService() throws QueryException, IOException {
        Supplier<String> genServiceName = () -> "test" + System.currentTimeMillis();
        String serviceName = genServiceName.get();
        DefineServiceRequest defineReq = mockDefineReq(serviceName);
        ResultTx resultTx = serviceClient.defineService(defineReq, baseTx);
        log.info("defineService txHash: " + resultTx.getResult().getHash());

        ServiceDefinition definition = serviceClient.queryServiceDefinition(serviceName);
        assertEquals(serviceName, definition.getName());
        assertEquals(defineReq.getDescription(), definition.getDescription());
        assertEquals(defineReq.getAuthorDescription(), definition.getAuthorDescription());
        assertEquals(km.getAddr(), definition.getAuthor());
        assertEquals(defineReq.getSchemas(), definition.getSchemas());

        BindServiceRequest bindReq = mockBindReq(serviceName);
        resultTx = serviceClient.bindService(bindReq, baseTx);
        log.info("bindingService txHash: " + resultTx.getResult().getHash());

        ServiceBinding serviceBinding = serviceClient.queryServiceBinding(serviceName, km.getAddr());
        assertEquals(serviceName, serviceBinding.getServiceName());
        assertEquals(km.getAddr(), serviceBinding.getProvider());
        assertEquals(bindReq.getPricing(), serviceBinding.getPricing());

        // One Invoke
        CallServiceRequest callReq = mockCallServiceReq(serviceName);
        CallServiceResp callServiceResp = serviceClient.callService(callReq, baseTx);
        String reqCtxId = callServiceResp.getReqCtxId();
        log.info("reqCtxId: " + reqCtxId);
        log.info(callServiceResp.getResultTx().getResult().getHash());

        List<Request> requests = serviceClient.queryServiceRequests(serviceName, km.getAddr());
        String reqId = null;
        for (Request req : requests) {
            if (reqCtxId.equals(req.getRequestContextId())) {
                log.info("reqId: " + req.getId());
                reqId = req.getId();
            }
        }
        assertNotNull(reqId);

        Request request = serviceClient.queryServiceRequest(reqId);
        assertEquals(reqCtxId, request.getRequestContextId());
        assertEquals(serviceName, request.getServiceName());
        assertEquals(callReq.getInput(), request.getInput());

        ResponseServiceRequest responseReq = new ResponseServiceRequest();
        responseReq.setRequestId(reqId);
        String output = "{\"header\":{},\"body\":{\"last\":\"1:100\"}}";
        String testResult = "{\"code\":200,\"message\":\"\"}";
        responseReq.setOutput(output);
        responseReq.setResult(testResult);

        resultTx = serviceClient.responseService(responseReq, baseTx);
        log.info("responseService txHash: " + resultTx.getResult().getHash());

        Response response = serviceClient.queryServiceResponse(reqId);
        assertEquals(output, response.getOutput());

        List<Response> responses = serviceClient.queryServiceResponses(reqCtxId, 1);

        boolean flag = false;
        for (Response res : responses) {
            if (output.equals(res.getOutput())) {
                log.info("success get out put");
                flag = true;
            }
        }
        assertTrue(flag);
    }

    private DefineServiceRequest mockDefineReq(String serviceName) {
        String schemas = "{\"input\":{\"type\":\"object\"},\"output\":{\"type\":\"object\"},\"error\":{\"type\":\"object\"}}";

        DefineServiceRequest defineReq = new DefineServiceRequest();
        defineReq.setServiceName(serviceName);
        defineReq.setDescription("this is a test service");
        defineReq.setTags(null);
        defineReq.setAuthorDescription("service provider");
        defineReq.setSchemas(schemas);
        return defineReq;
    }

    private BindServiceRequest mockBindReq(String serviceName) {
        String pricing = "{\"price\":\"1upoint\"}";
        String options = "{}";

        BindServiceRequest bindReq = new BindServiceRequest();
        bindReq.setServiceName(serviceName);
        bindReq.setDeposit(new Coin("20000", "upoint"));
        bindReq.setPricing(pricing);
        bindReq.setOptions(options);
        bindReq.setQoS(10);
        return bindReq;
    }

    private CallServiceRequest mockCallServiceReq(String serviceName) {
        String input = "{\"header\":{},\"body\":{\"pair\":\"point-usdt\"}}";

        CallServiceRequest callReq = new CallServiceRequest();
        callReq.setServiceName(serviceName);

        ArrayList<String> providers = new ArrayList<>();
        providers.add(km.getAddr());
        callReq.setProviders(providers);
        callReq.setInput(input);
        callReq.setServiceFeeCap(new Coin("200", "upoint"));
        callReq.setTimeout(10);
        callReq.setRepeated(true);
        callReq.setRepeatedTotal(-1);
        return callReq;
    }

    @Test
    public void subscribeRequest() {
        List<Msg> msgs = serviceClient.subscribeRequest("test", "iaa1ytemz2xqq2s73ut3ys8mcd6zca2564a5lfhtm3");
        for (Msg msg : msgs) {
            System.out.println(msg.getValue().getInput());
        }

        List<Msg> otherMgs = serviceClient.subscribeRequest("test", "iaa1ytemz2xqq2s73ut3ys8mcd6zca2564a5lfhtm3", 0, 15006);
        for (Msg msg : otherMgs) {
            System.out.println(msg.getValue().getInput());
        }
    }

    @Test
    public void subscribeResponse() {
        List<Txs> txs = serviceClient.subscribeResponse("hello", "iaa1ytemz2xqq2s73ut3ys8mcd6zca2564a5lfhtm3", "iaa1ytemz2xqq2s73ut3ys8mcd6zca2564a5lfhtm3");
        for (Txs tx : txs) {
            System.out.println(tx.getRawLog());
        }

        List<Txs> otherTxs = serviceClient.subscribeResponse("hello", "iaa1ytemz2xqq2s73ut3ys8mcd6zca2564a5lfhtm3", "iaa1ytemz2xqq2s73ut3ys8mcd6zca2564a5lfhtm3",0,15006);
        for (Txs tx : otherTxs) {
            System.out.println(tx.getRawLog());
        }
    }
}
