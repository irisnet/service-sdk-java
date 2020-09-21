package iservice.sdk.module;

import iservice.sdk.entity.Mnemonic;
import iservice.sdk.exception.ServiceSDKException;

/**
 * Key management service
 */
public interface IKeyService {

    /**
     * Creates a new key
     *
     * @param name Name of the key
     * @param password Password for encrypting the keystore
     * @return Bech32 address and mnemonic
     */
    Mnemonic addKey(String name, String password) throws ServiceSDKException;

    /**
     * Recovers a key
     *
     * @param name Name of the key
     * @param password Password for encrypting the keystore
     * @param mnemonic Mnemonic of the key
     * @param derive Derive a private key using the default HD path (default: true)
     * @param index The bip44 address index (default: 0)
     * @param saltPassword A passphrase for generating the salt, according to bip39
     * @return Bech32 address
     */
    String recoverKey(String name, String password, String mnemonic, boolean derive, int index, String saltPassword) throws ServiceSDKException;

    /**
     * Imports a key from keystore
     *
     * @param name Name of the key
     * @param password Password of the keystore
     * @param keystore Keystore json
     * @return Bech32 address
     */
    String importFromKeystore(String name, String password, String keystore);

    /**
     * Exports keystore of a key
     *
     * @param name Name of the key
     * @param keyPassword Password of the key
     * @param keystorePassword Password for encrypting the keystore
     * @return Keystore json
     */
    String exportKeystore(String name, String keyPassword, String keystorePassword);

    /**
     * Deletes a key
     *
     * @param name Name of the key
     * @param password Password of the key
     */
    void deleteKey(String name, String password) throws ServiceSDKException;

    /**
     * Gets address of a key
     *
     * @param name Name of the key
     * @return Bech32 address
     */
    String showAddress(String name) throws ServiceSDKException;
}
