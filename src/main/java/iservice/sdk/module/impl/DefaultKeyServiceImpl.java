package iservice.sdk.module.impl;

import iservice.sdk.entity.Key;
import iservice.sdk.entity.Mnemonic;
import iservice.sdk.exception.ServiceSDKException;
import iservice.sdk.module.IKeyDAO;
import iservice.sdk.util.Bip39Utils;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.Utils;
import org.bitcoinj.crypto.DeterministicKey;
import org.web3j.crypto.*;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;

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
    public String importFromKeystore(String name, String keyPassword, String keystorePassword, String keystore) throws ServiceSDKException, IOException {
        try {
            Credentials credentials = WalletUtils.loadJsonCredentials(keystorePassword, keystore);
            ECKeyPair keyPair = credentials.getEcKeyPair();
            BigInteger privKey = keyPair.getPrivateKey();

            byte[] encoded = ECKey.publicPointFromPrivate(privKey).getEncoded(true);
            byte[] hash = Hash.sha256hash160(encoded);
            String addr = super.toBech32(hash);
            super.saveKey(name, keyPassword, addr, Utils.bigIntegerToBytes(privKey, 32));
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

        String fileName;
        try {
            fileName = WalletUtils.generateWalletFile(keystorePassword, keyPair, destinationDirectory, true);
        } catch (CipherException e) {
            e.printStackTrace();
            throw new ServiceSDKException(e.getMessage(), e);
        }
        return fileName;
    }

}
