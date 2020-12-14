package iservice.sdk.module;

import cosmos.tx.v1beta1.TxOuterClass;
import iservice.sdk.exception.ServiceSDKException;
import org.bouncycastle.crypto.CryptoException;

import java.io.IOException;

/**
 * @author Yelong
 */
public interface ITxService {


    /**
     * Single sign a transaction
     *
     * @param txBody   TxBody to be signed
     * @param name     Name of the key to sign the tx
     * @param password Password of the key
     * @param offline  Offline signing, default `false`
     * @return The signed tx
     * @throws ServiceSDKException if the signing failed
     */
    TxOuterClass.Tx signTx(TxOuterClass.TxBody txBody, String name, String password, boolean offline) throws ServiceSDKException, IOException, CryptoException;


}