package irita.sdk.module.keys;

import java.io.IOException;
import java.math.BigInteger;

public interface Key {
    byte[] sign(String stdSignMsg);

    BigInteger getPrivKey();

    String getAddr();

    String getMnemonic();

    /**
     * export as keystore
     *
     * @param password password of keystore. The password is very important for recovery, so never forget it
     */
    String export(String password) throws IOException;
}
