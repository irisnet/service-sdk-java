package irita.sdk;

import irita.sdk.client.IritaClient;
import irita.sdk.client.IritaClientOption;
import irita.sdk.exception.ContractException;
import irita.sdk.module.base.BaseTx;
import irita.sdk.module.base.ResultTx;
import irita.sdk.module.keys.Key;
import irita.sdk.module.keys.KeyManager;
import irita.sdk.module.wasm.*;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
public class WasmTest {
    private WasmClient wasmClient;

    @BeforeEach
    public void init() {
        String mnemonic = "opera vivid pride shallow brick crew found resist decade neck expect apple chalk belt sick author know try tank detail tree impact hand best";
        Key km = new KeyManager(mnemonic);
        IritaClientOption option = IritaClientOption.getDefaultOption(km);

        String nodeUri = "http://localhost:26657";
        String lcd = "http://localhost:1317";
        String chainId = "irita";
        IritaClient client = new IritaClient(nodeUri, lcd, chainId, option);
        wasmClient = client.getWasmClient();

        assertEquals("iaa1ytemz2xqq2s73ut3ys8mcd6zca2564a5lfhtm3", km.getAddr());
    }


    // when you want to add new Contract to block-chain, remove the @Disabled
    @Test
    public void store() throws IOException {
        StoreRequest req = new StoreRequest();
        req.setWasmFile("src/test/resources/test.wasm");

        BaseTx baseTx = new BaseTx(2000000, new IritaClientOption.Fee("120", "stake"));
        String codeId = wasmClient.store(req, baseTx);
        assertTrue(StringUtils.isNotEmpty(codeId));
        System.out.println(codeId);
    }

    @Test
    public void instantiate() throws IOException {
        // code_id is res of store
        long codeId = 3L;

        Map<String, Object> initMsg = new HashMap<>();
        initMsg.put("start", 1);
        initMsg.put("end", 100);
        initMsg.put("candidates", new String[]{"iaa1qvty8x0c78am8c44zv2n7tgm6gfqt78j0verqa", "iaa1zk2tse0pkk87p2v8tcsfs0ytfw3t88kejecye5"});

        InstantiateRequest req = new InstantiateRequest();
        req.setCodeId(codeId);
        req.setInitMsg(initMsg);
        req.setLabel("test wasm");
        BaseTx baseTx = new BaseTx(2000000, new IritaClientOption.Fee("120", "stake"));

        String contractAddress = wasmClient.instantiate(req, baseTx);
        assertTrue(StringUtils.isNotEmpty(contractAddress));
        System.out.println(contractAddress);

        // test queryContractInfo
        ContractInfo contractInfo = null;
        try {
            contractInfo = wasmClient.queryContractInfo("iaa1sh36qn08g4cqg685cfzmyxqv2952q6r8m8znr6");
        } catch (ContractException e) {
            e.printStackTrace();
        }
        assertNotNull(contractInfo);
        System.out.println(contractInfo);
    }

    @Test
    public void execute() throws IOException {
        // contractAddress is res of instantiate
        String contractAddress = "iaa1wgh6adn8geywx0v78zs9azrqtqdegufu2wfles";
        Map<String, Object> args = new HashMap<>();
        args.put("candidate", "iaa1qvty8x0c78am8c44zv2n7tgm6gfqt78j0verqa");

        ContractABI execAbi = new ContractABI();
        execAbi.setArgs(args);
        execAbi.setMethod("vote");
        BaseTx baseTx = new BaseTx(2000000, new IritaClientOption.Fee("120", "stake"));

        ResultTx resultTx = wasmClient.execute(contractAddress, execAbi, null, baseTx);

        String height = resultTx.getResult().getHeight();
        assertTrue(Integer.parseInt(height) > 0);
        assertNotNull(resultTx.getResult().getHash());

        // test QueryContract
        ContractABI queryAbi = new ContractABI();
        queryAbi.setMethod("get_vote_info");

        String str = wasmClient.queryContract(contractAddress, queryAbi);
        assertNotNull(str);
        System.out.println(str);
    }

    @Test
    public void exportContractState() {
        String contractAddress = "iaa1pcknsatx5ceyfu6zvtmz3yr8auumzrdtrn8h4v";

        Map<String, String> res = wasmClient.exportContractState(contractAddress);
        System.out.println(res);
    }
}
