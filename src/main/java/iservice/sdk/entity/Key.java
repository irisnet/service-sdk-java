package iservice.sdk.entity;

import org.bitcoinj.core.Utils;

/**
 * Struct of keys
 */
public class Key {
    private String address;
    private byte[] privKey;

    public Key(String address, byte[] privKey) {
        this.address = address;
        this.privKey = privKey;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public byte[] getPrivKey() {
        return privKey;
    }

    public void setPrivKey(byte[] privKey) {
        this.privKey = privKey;
    }

    @Override
    public String toString() {
        return "Key{" +
                "address='" + address + '\'' +
                ", privKey='" + Utils.HEX.encode(privKey) + '\'' +
                '}';
    }
}
