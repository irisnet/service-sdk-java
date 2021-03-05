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
import org.web3j.crypto.Hash;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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
        return new Key(key.getAddress(), decrypted);
    }

    public String writeArmorToFile(File filePath, String address, String context) throws FileNotFoundException {
        String fileName = generateFileName(address);
        File file = new File(filePath,fileName);
        PrintStream ps = new PrintStream(new FileOutputStream(file));
        ps.println(context);
        return fileName;
    }

    private String generateFileName(String address) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("'UTC--'yyyy-MM-dd'T'HH-mm-ss.nVV'--'");
        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
        return now.format(format) + address+ ".key";
    }

    public byte[] getPrefixAmino(String algoPrivKeyName) {
        byte[] hash = Hash.sha256(algoPrivKeyName.getBytes(StandardCharsets.UTF_8));
        int ptr = 0;
        while (hash[ptr] == 0) ptr++;
        ptr += 3;
        while (hash[ptr] == 0) ptr++;
        byte[] prefix = new byte[5];
        System.arraycopy(hash, ptr, prefix, 0, 4);
        prefix[4] = 32;
        return prefix;
    }
}
