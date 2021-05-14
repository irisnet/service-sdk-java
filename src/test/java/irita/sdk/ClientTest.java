package irita.sdk;

import irita.sdk.client.IritaClient;
import irita.sdk.client.IritaClientOption;
import irita.sdk.module.bank.BankClient;
import irita.sdk.module.base.Account;
import irita.sdk.module.base.BaseClient;
import irita.sdk.module.keys.Key;
import irita.sdk.module.keys.KeyManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ClientTest {
    private IritaClient client;

    @BeforeEach
    public void init() {
        String mnemonic = "opera vivid pride shallow brick crew found resist decade neck expect apple chalk belt sick author know try tank detail tree impact hand best";
        Key km = new KeyManager(mnemonic);
        IritaClientOption option = IritaClientOption.getDefaultOption(km);

        String nodeUri = "http://localhost:26657";
        String lcd = "http://localhost:1317";
        String chainId = "irita";
        client = new IritaClient(nodeUri, lcd, chainId, option);

        assertEquals("iaa1ytemz2xqq2s73ut3ys8mcd6zca2564a5lfhtm3", km.getAddr());
    }

    @Test
    @Disabled
    public void newClient() {
        BaseClient baseClient = client.getBaseClient();
        String addr = "iaa1ytemz2xqq2s73ut3ys8mcd6zca2564a5lfhtm3";
        Account account = baseClient.queryAccount(addr);
        assertEquals(addr, account.getAddress());
    }

    @Test
    @Disabled
    public void send() throws IOException {
        BankClient bankClient = client.getBankClient();
        String res = bankClient.send("1", "iaa18xcshrf7qwjmmurxxxe6tezw7qeqzjaz2z5326");
        System.out.println(res);
    }
}
