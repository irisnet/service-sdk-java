package irita.sdk.module.base;

public class Coin {
    private String amount;
    private String denom;

    public Coin() {
    }

    public Coin(String amount, String denom) {
        this.amount = amount;
        this.denom = denom;
    }

    public String getDenom() {
        return denom;
    }

    public void setDenom(String denom) {
        this.denom = denom;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
