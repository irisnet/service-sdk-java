package iservice.sdk.util;


import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Hash;
import org.web3j.crypto.MnemonicUtils;


import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Utils of Bip39Utils protocol.
 */
public class Bip39Utils {
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final int PRIVKEY_LEN = 32;

    /**
     * Generate a string of mnemonic words randomly.
     * @return String mnemonic words
     */
    public static String generateMnemonic() {
        byte[] initialEntropy = new byte[PRIVKEY_LEN];
        secureRandom.nextBytes(initialEntropy);

        String mnemonic = MnemonicUtils.generateMnemonic(initialEntropy);
        return mnemonic;
    }

    /**
     * Convert mnemonic words to PrivateKey
     * @param mnemonic words
     * @return BigInteger PrivateKey
     */
    public static BigInteger getPrivateKey(String mnemonic) {
        byte[] seed = MnemonicUtils.generateSeed(mnemonic, "");
        ECKeyPair ecKeyPair = ECKeyPair.create(Hash.sha256(seed));
        return ecKeyPair.getPrivateKey();
    }

    /**
     * Returns relevant PublicKey with PrivateKey
     * @param privateKey
     * @return BigInteger PublicKey
     */
    public static BigInteger getPublicKeyFromPrivate(BigInteger privateKey) {
        ECKeyPair keyPair = ECKeyPair.create(privateKey);
        return keyPair.getPublicKey();
    }

    /**
     * Valid a mnemonic string is legal
     * @param mnemonic words
     * @return boolean is legal
     */
    public static boolean validateMnemonic(String mnemonic) {
        return MnemonicUtils.validateMnemonic(mnemonic);
    }

}