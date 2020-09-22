package iservice.sdk.module.impl;

import iservice.sdk.entity.Key;
import iservice.sdk.entity.Mnemonic;
import iservice.sdk.exception.ServiceSDKException;
import iservice.sdk.module.IKeyDAO;
import iservice.sdk.util.Bip39Utils;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Utils;
import org.bitcoinj.crypto.DeterministicKey;
import org.web3j.crypto.*;
import org.web3j.utils.Numeric;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

/**
 * @author Yelong
 */
public class DefaultKeyServiceImpl extends AbstractKeyServiceImpl {

    public DefaultKeyServiceImpl(IKeyDAO keyDAO) {
        super(keyDAO);
    }

    @Override
    public Mnemonic addKey(String name, String password) throws ServiceSDKException {
        String mnemonic = Bip39Utils.generateMnemonic();
        DeterministicKey dk = super.generateDeterministicKey(mnemonic);
        byte[] encoded = dk.getPubKeyPoint().getEncoded(true);
        byte[] hash = Hash.sha256hash160(encoded);
        String addr = super.toBech32(hash);
        super.saveKey(name, password, addr, dk.getPrivKeyBytes());
        return new Mnemonic(addr, mnemonic);
    }

    @Override
    public String recoverKey(String name, String password, String mnemonic, boolean derive, int index, String saltPassword) throws ServiceSDKException {
        DeterministicKey dk = super.generateDeterministicKey(mnemonic);
        byte[] encoded = dk.getPubKeyPoint().getEncoded(true);
        byte[] hash = Hash.sha256hash160(encoded);
        String addr = super.toBech32(hash);
        super.saveKey(name, password, addr, dk.getPrivKeyBytes());
        return addr;
    }

    @Override
    public String importFromKeystore(String name, String password, String keystore) throws ServiceSDKException, IOException {
        try {
            Credentials credentials = WalletUtils.loadJsonCredentials(password, keystore);
            ECKeyPair keyPair = credentials.getEcKeyPair();
            BigInteger privKey = keyPair.getPrivateKey();
            byte[] encoded = ECKey.publicPointFromPrivate(privKey).getEncoded(true);
            byte[] hash = Hash.sha256hash160(encoded);
            String addr = super.toBech32(hash);
            super.saveKey(name, password, addr, Utils.bigIntegerToBytes(privKey, 32));
            return addr;
        } catch (CipherException e) {
            e.printStackTrace();
            throw new ServiceSDKException(e.getMessage(), e);
        }
    }

    @Override
    public String exportKeystore(String name, String keyPassword, String keystorePassword, File destinationDirectory) throws ServiceSDKException, IOException {
        Key key = super.getKey(name, keyPassword);
        ECKeyPair keyPair = ECKeyPair.create(key.getPrivKey());
        String path;
        try {
            path = WalletUtils.generateWalletFile(keystorePassword, keyPair, destinationDirectory, true);
        } catch (CipherException e) {
            e.printStackTrace();
            throw new ServiceSDKException(e.getMessage(), e);
        }
        return path;
    }

    @Override
    public String signTx(String stdTx, String name, String password, boolean offline) throws ServiceSDKException {
        // TODO implement stdTx
        Key key = super.getKey(name, password);
        ECKeyPair keyPair = ECKeyPair.create(key.getPrivKey());
        byte[] bytes = Numeric.hexStringToByteArray(stdTx);
        byte[] hash = Sha256Hash.hash(bytes);
        Sign.SignatureData signature = Sign.signMessage(hash, keyPair, false);
        String r = Numeric.toHexString(signature.getR());
        String s = Numeric.toHexString(signature.getS());
        return new StringBuilder(Numeric.cleanHexPrefix(r))
                .append(Numeric.cleanHexPrefix(s))
                .toString();
    }
}
