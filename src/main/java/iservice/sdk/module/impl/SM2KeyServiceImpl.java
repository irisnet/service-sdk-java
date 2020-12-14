package iservice.sdk.module.impl;

import iservice.sdk.entity.Mnemonic;
import iservice.sdk.exception.ServiceSDKException;
import iservice.sdk.module.IKeyDAO;
import iservice.sdk.util.Bip39Utils;

import iservice.sdk.util.SM2Utils;
import org.bitcoinj.crypto.DeterministicKey;
import org.bouncycastle.math.ec.ECPoint;
import org.web3j.crypto.*;

import java.io.File;
import java.math.BigInteger;

/**
 * @author Yelong
 */
public class SM2KeyServiceImpl extends AbstractKeyServiceImpl {

    public SM2KeyServiceImpl(IKeyDAO keyDAO) {
        super(keyDAO);
    }

    @Override
    public Mnemonic addKey(String name, String password) throws ServiceSDKException {
        String mnemonic = Bip39Utils.generateMnemonic();
        DeterministicKey dk = super.generateDeterministicKey(mnemonic);

        SM2Utils sm2Utils = new SM2Utils();
        ECPoint pubkey = sm2Utils.getPubkeyFromPrivkey(dk.getPrivKey());

        byte[] encoded = pubkey.getEncoded(true);
        byte[] hash = Hash.sha256hash160(encoded);
        String addr = super.toBech32(hash);
        super.saveKey(name, password, addr, dk.getPrivKeyBytes());
        return new Mnemonic(addr, mnemonic);
    }

    @Override
    public String recoverKey(String name, String password, String mnemonic, boolean derive, int index,
            String saltPassword) throws ServiceSDKException {
        DeterministicKey dk = super.generateDeterministicKey(mnemonic);

        SM2Utils sm2Utils = new SM2Utils();
        ECPoint pubkey = sm2Utils.getPubkeyFromPrivkey(dk.getPrivKey());

        byte[] encoded = pubkey.getEncoded(true);
        byte[] hash = Hash.sha256(encoded);
        byte[] pre20 = new byte[20];
        System.arraycopy(hash, 0, pre20, 0, 20);
        String addr = super.toBech32(pre20);
        super.saveKey(name, password, addr, dk.getPrivKeyBytes());
        return addr;
    }
    @Override
    public String importFromKeystore(String name, String password, String keystore) {
        throw new Error("The import method is not supported at this time");
    }

    @Override
    public String exportKeystore(String name, String keyPassword, String keystorePassword, File destinationDirectory) {
        throw new Error("The export method is not supported at this time");
    }
}
