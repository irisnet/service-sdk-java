package iservice.sdk.entity;

/**
 * Created by Yelong on 2020/9/21.
 */
public class Mnemonic {
    private String address;
    private String mnemonic;

    public Mnemonic(String address, String mnemonic) {
        this.address = address;
        this.mnemonic = mnemonic;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMnemonic() {
        return mnemonic;
    }

    public void setMnemonic(String mnemonic) {
        this.mnemonic = mnemonic;
    }

    @Override
    public String toString() {
        return "Mnemonic{" +
                "address='" + address + '\'' +
                ", mnemonic='" + mnemonic + '\'' +
                '}';
    }
}
