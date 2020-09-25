package iservice.sdk.module.impl;

import iservice.sdk.entity.Key;
import iservice.sdk.exception.ServiceSDKException;
import iservice.sdk.module.IKeyDAO;
import iservice.sdk.module.IKeyService;
import iservice.sdk.util.AddressUtils;
import org.bitcoinj.core.Bech32;
import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDUtils;
import org.bitcoinj.wallet.DeterministicKeyChain;
import org.bitcoinj.wallet.DeterministicSeed;
import org.bitcoinj.wallet.UnreadableWalletException;

import java.util.List;

/**
 * AbstractKeyServiceImpl
 *
 * @author Yelong
 * @since v1.0
 */
public abstract class AbstractKeyServiceImpl implements IKeyService {

    private static final String HD_PATH = "M/44H/118H/0H/0/0";
    private static final String HRP = "iaa";
    private IKeyDAO keyDAO;

    AbstractKeyServiceImpl(IKeyDAO keyDAO) {
        this.keyDAO = keyDAO != null ? keyDAO : new DefaultKeyDAOImpl();
    }

    @Override
    public String showAddress(String name) throws ServiceSDKException {
        Key key = this.keyDAO.read(name);
        return key.getAddress();
    }

    @Override
    public void deleteKey(String name, String password) throws ServiceSDKException {
        Key key = this.keyDAO.read(name);
        this.keyDAO.decrypt(key.getPrivKey(), password); // Check password
        this.keyDAO.delete(name);
    }

    final DeterministicKey generateDeterministicKey(String mnemonic) throws ServiceSDKException {
        DeterministicSeed seed;
        try {
            seed = new DeterministicSeed(mnemonic, null, "", 0);
        } catch (UnreadableWalletException e) {
            e.printStackTrace();
            throw new ServiceSDKException("Error generating deterministic key from mnemonic", e);
        }

        DeterministicKeyChain chain = DeterministicKeyChain.builder().seed(seed).build();
        List<ChildNumber> keyPath = HDUtils.parsePath(HD_PATH);
        return chain.getKeyByPath(keyPath, true);
    }

    final String toBech32(byte[] pubkeyHex) {
        byte[] bits = AddressUtils.convertBits(pubkeyHex, 0, pubkeyHex.length, 8, 5, true);
        return Bech32.encode(HRP, bits);
    }

    final void saveKey(String name, String password, String address, byte[] privKey) throws ServiceSDKException {
        byte[] encrypted = this.keyDAO.encrypt(privKey, password);
        Key key = new Key(address, encrypted);
        this.keyDAO.write(name, key);
    }

    public final Key getKey(String name, String password) throws ServiceSDKException {
        Key key = this.keyDAO.read(name);
        byte[] decrypted = this.keyDAO.decrypt(key.getPrivKey(), password);
        Key key1 = new Key(key.getAddress(), decrypted);
        return key1;
    }

}
