package iservice.sdk.module;

import iservice.sdk.entity.Key;
import iservice.sdk.exception.ServiceSDKException;
import iservice.sdk.util.AESUtils;

/**
 * Key DAO Interface, to be implemented by apps if they need the key management.
 */
public interface IKeyDAO {

    /**
     * Save the encrypted private key to app
     *
     * @param name Name of the key
     * @param key The encrypted private key object
     * @throws ServiceSDKException if the save fails.
     */
    void write(String name, Key key) throws ServiceSDKException;

    /**
     * Get the encrypted private key by name
     *
     * @param name Name of the key
     * @return The encrypted private key object or null
     */
    Key read(String name);

    /**
     * Delete the key by name
     * @param name Name of the key
     * @throws ServiceSDKException if the deletion fails.
     */
    void delete(String name) throws ServiceSDKException;

    /**
     * Optional function to encrypt the private key by yourself. Default to AES Encryption
     *
     * @param privKey The plain private key
     * @param password The password to encrypt the private key
     * @return The encrypted private key
     * @throws ServiceSDKException if encrypt failed
     */
    default byte[] encrypt(byte[] privKey, String password) throws ServiceSDKException {
        try {
            return AESUtils.encrypt(privKey, password);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceSDKException("Private key encrypt failed", e);
        }
    }

    /**
     * Optional function to decrypt the private key by yourself. Default to AES Decryption
     *
     * @param encrptedPrivKey The encrpted private key
     * @param password The password to decrypt the private key
     * @return The plain private key
     * @throws ServiceSDKException if decrypt failed
     */
    default byte[] decrypt(byte[] encrptedPrivKey, String password) throws ServiceSDKException {
        try {
            return AESUtils.decrypt(encrptedPrivKey, password);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceSDKException("Private key decrypt failed", e);
        }
    }
}
