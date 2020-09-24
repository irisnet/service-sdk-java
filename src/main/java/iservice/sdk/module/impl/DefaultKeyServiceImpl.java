package iservice.sdk.module.impl;

import com.google.protobuf.ByteString;
import cosmos.base.crypto.v1beta1.Crypto;
import cosmos.base.v1beta1.CoinOuterClass;
import cosmos.tx.signing.v1beta1.Signing;
import cosmos.tx.v1beta1.TxOuterClass;
import iservice.sdk.core.ServiceClient;
import iservice.sdk.core.ServiceClientFactory;
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
import java.util.Base64;

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
    public TxOuterClass.Tx signTx(TxOuterClass.TxBody txBody, String name, String password, boolean offline) throws ServiceSDKException {

//        Key key = super.getKey(name, password);
//        ECKeyPair keyPair = ECKeyPair.create(key.getPrivKey());
//
//        ServiceClient serviceClient = ServiceClientFactory.getInstance().getClient();
//        TxOuterClass.AuthInfo ai = TxOuterClass.AuthInfo.newBuilder()
//                .addSignerInfos(
//                        TxOuterClass.SignerInfo.newBuilder()
//                                .setPublicKey(Crypto.PublicKey.newBuilder().setSecp256K1(ByteString.copyFrom(keyPair.getPublicKey().toByteArray())))
//                                .setModeInfo(TxOuterClass.ModeInfo.newBuilder().setSingle(TxOuterClass.ModeInfo.Single.newBuilder().setMode(Signing.SignMode.SIGN_MODE_DIRECT)))
//                                .setSequence(0))
//                .setFee(TxOuterClass.Fee.newBuilder().setGasLimit(200000).addAmount(CoinOuterClass.Coin.newBuilder().setAmount("1").setDenom("stake"))).build();
//
//        TxOuterClass.SignDoc signdoc = TxOuterClass.SignDoc.newBuilder()
//                .setBodyBytes(body.toByteString())
//                .setAuthInfoBytes(ai.toByteString())
//                .setAccountNumber(9)
//                .setChainId("test")
//                .build();
//
//
//        // TODO implement stdTx
//
////        byte[] bytes = Numeric.hexStringToByteArray(stdTx);
//        byte[] hash = Sha256Hash.hash(bytes);
//        Sign.SignatureData signature = Sign.signMessage(hash, keyPair, false);
//        String r = Numeric.toHexString(signature.getR());
//        String s = Numeric.toHexString(signature.getS());
//        return new StringBuilder(Numeric.cleanHexPrefix(r))
//                .append(Numeric.cleanHexPrefix(s))
//                .toString();
        return null;
    }
}
