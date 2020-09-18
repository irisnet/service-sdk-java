package iservice.sdk.entity;

/**
 * Struct of keys
 */
public class Key {
    private String address;
    private String privKey;

    public Key(String address, String privKey) {
        this.address = address;
        this.privKey = privKey;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPrivKey() {
        return privKey;
    }

    public void setPrivKey(String privKey) {
        this.privKey = privKey;
    }

    @Override
    public String toString() {
        return "Key{" +
                "address='" + address + '\'' +
                ", privKey='" + privKey + '\'' +
                '}';
    }
}
