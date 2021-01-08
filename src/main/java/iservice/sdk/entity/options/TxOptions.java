package iservice.sdk.entity.options;

public class TxOptions {
  public String chainID;
  public Fee fee;
  public int gasLimit = 200000;

  public TxOptions(String chainID, String amount, String denom) {
    this.chainID = chainID;
    this.fee = new Fee(amount, denom);
  }

  public TxOptions(String chainID, String amount, String denom, int gasLimit) {
    this.chainID = chainID;
    this.fee = new Fee(amount, denom);
    this.gasLimit = gasLimit;
  }

  public static class Fee {
    public String amount;
    public String denom;

    public Fee(String amount, String denom) {
      this.amount = amount;
      this.denom = denom;
    }
  }
}
