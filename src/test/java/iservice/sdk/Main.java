package iservice.sdk;


import iservice.sdk.util.Bip39Utils;
import org.bitcoinj.core.Bech32;
import org.web3j.crypto.Bip32ECKeyPair;
import org.web3j.crypto.Bip39Wallet;

import java.math.BigInteger;

/**
 * Created by mitch on 2020/9/16.
 */
public class Main {

    public static void main(String[] args) {
//        ServiceClient sc = ServiceClientBuilder.create().addListener(new TestConsumerListener()).addListener(new TestProviderListener()).build();
//        TestRequest req = new TestRequest();
//        req.setS1("req1");
//        req.setS2("req2");
//        System.out.println("----------------- Consumer -----------------");
//        sc.callService(req);

        String m = Bip39Utils.generateMnemonic();
        System.out.println(m);

        BigInteger privateKey = Bip39Utils.getPrivateKey(m);
        System.out.println(privateKey);

        BigInteger pubkey = Bip39Utils.getPublicKeyFromPrivate(privateKey);
        System.out.println(pubkey);
        System.out.println(pubkey.toByteArray());


        Bech32.Bech32Data data = Bech32.decode("iaa1ewed0ds2syhv4qn6fjhx2avma0j2sp6d594tht");
        System.out.println(data.hrp);
        System.out.println(data.data);

        System.out.println(Bech32.encode("iaa", data.data));

        Bip32ECKeyPair.create(privateKey);
        System.out.println(Bech32.encode("iaa", pubkey.toByteArray()));

    }
}
