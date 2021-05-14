package irita.sdk.client;

import irita.sdk.module.keys.Key;
import irita.sdk.module.keys.KeyManager;

public class IritaClientOption {
    private int gas;
    private final int gasLimit = 20000000;
    private Fee fee;
    private int maxTxsBytes;
    private String mode;
    private double gasAdjustment;
    private Key keyManager;

    private static final int defaultGas = 1;
    // TODO this amount
    private static final String defaultAmount = "130";
    private static final String defaultDenom = "stake";
    private static final int defaultMaxTxsBytes = 1073741824;
    private static final String defaultMode = "";
    private static final double defaultGasAdjustment = 1.0;

    public static class Fee {
        public String amount;
        public String denom;

        public Fee(String amount, String denom) {
            this.amount = amount;
            this.denom = denom;
        }

        public void toMin() {
            amount += "000000"; // 10的6次方
            denom = "u" + denom;
        }
    }

    private IritaClientOption() {
    }

    public static IritaClientOption getDefaultOption(Key keyManager) {
        IritaClientOption option = new IritaClientOption();
        option.gas = defaultGas;
        option.fee = new Fee(defaultAmount, defaultDenom);
        option.maxTxsBytes = defaultMaxTxsBytes;
        option.mode = defaultMode;
        option.gasAdjustment = defaultGasAdjustment;
        option.keyManager = keyManager;
        return option;
    }

    public IritaClientOption(int gas, Fee fees, int maxTxsBytes, String mode, double gasAdjustment, Key keyManager) {
        this.gas = gas;
        this.fee = fees;
        this.maxTxsBytes = maxTxsBytes;
        this.mode = mode;
        this.gasAdjustment = gasAdjustment;
        this.keyManager = keyManager;
    }

    public int getGas() {
        return gas;
    }

    public Fee getFee() {
        return fee;
    }

    public int getMaxTxsBytes() {
        return maxTxsBytes;
    }

    public String getMode() {
        return mode;
    }

    public double getGasAdjustment() {
        return gasAdjustment;
    }

    public Key getKeyManager() {
        return keyManager;
    }

    public int getGasLimit() {
        return gasLimit;
    }
}
