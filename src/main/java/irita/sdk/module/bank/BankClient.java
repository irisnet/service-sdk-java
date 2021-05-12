package irita.sdk.module.bank;

import cosmos.bank.v1beta1.Tx;
import cosmos.base.v1beta1.CoinOuterClass;
import cosmos.tx.v1beta1.TxOuterClass;
import irita.sdk.client.Client;
import irita.sdk.module.base.WrappedRequest;

import java.io.IOException;

public class BankClient extends Client {
    public BankClient(Client client) {
        this.nodeUri = client.getNodeUri();
        this.lcd = client.getLcd();
        this.chainId = client.getChainId();
        this.opbOption = client.getOpbOption();
        this.option = client.getOption();
    }

    public String send(String amount, String toAddress) throws IOException {
        Tx.MsgSend msg = Tx.MsgSend.newBuilder()
                .addAmount(CoinOuterClass.Coin.newBuilder()
                        .setAmount(amount)
                        .setDenom(this.option.getFee().denom)
                        .build())
                .setFromAddress(option.getKeyManager().getAddr())
                .setToAddress(toAddress)
                .build();

        TxOuterClass.TxBody body = super.buildTxBody(msg);
        TxOuterClass.Tx tx = super.signTx(null, body, false);
        return httpUtils().post(getTxUri(), new WrappedRequest<>(tx));
    }
}
