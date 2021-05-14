package irita.sdk.util;

import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.MnemonicCode;
import org.bitcoinj.wallet.DeterministicKeyChain;
import org.bitcoinj.wallet.DeterministicSeed;
import org.bitcoinj.wallet.UnreadableWalletException;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Bip44Utils {
    public static String generateMnemonic() throws Exception {
        MnemonicCode mnemonicCode = new MnemonicCode();

        SecureRandom secureRandom = new SecureRandom();
        byte[] initialEntropy = new byte[16];
        secureRandom.nextBytes(initialEntropy);
        List<String> wd = mnemonicCode.toMnemonic(initialEntropy);

        if (wd == null || wd.size() != 12)
            throw new RuntimeException("generate word error");
        else {
            return wordsToMnemonic(wd);
        }
    }

    public static byte[] getSeed(String mnemonic) {
        List<String> words = mnemonicToWords(mnemonic);
        assert words.size() != 0;
        return MnemonicCode.toSeed(words, "");
    }

    private static List<String> mnemonicToWords(String mnemonic) {
        String[] w = mnemonic.split(" ");
        List<String> words = new ArrayList<>(w.length);
        Collections.addAll(words, w);
        return words;
    }

    private static String wordsToMnemonic(List<String> words) {
        StringBuffer buf = new StringBuffer();
        for (String word : words) {
            buf.append(buf);
        }
        return buf.toString();
    }

    public static DeterministicKey getDeterministicKey(String mnemonic, byte[] seed, String path) {
        DeterministicSeed deterministicSeed = null;
        try {
            deterministicSeed = new DeterministicSeed(mnemonic, seed, "", 0);
        } catch (UnreadableWalletException e) {
            e.printStackTrace();
        }

        DeterministicKeyChain deterministicKeyChain = DeterministicKeyChain.builder().seed(deterministicSeed).build();
        return deterministicKeyChain.getKeyByPath(parsePath(path), true);
    }

    private static List<ChildNumber> parsePath(String path) {
        String[] parsedNodes = path.replace("m", "").split("/");
        List<ChildNumber> nodes = new ArrayList<>();

        for (String n : parsedNodes) {
            n = n.replaceAll(" ", "");
            if (n.length() == 0) continue;
            boolean isHard = n.endsWith("'");
            if (isHard) n = n.substring(0, n.length() - 1);
            int nodeNumber = Integer.parseInt(n);
            nodes.add(new ChildNumber(nodeNumber, isHard));
        }

        return nodes;
    }
}
