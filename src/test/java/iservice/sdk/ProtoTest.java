package iservice.sdk;

import cosmos.base.v1beta1.CoinOuterClass;
import org.junit.Assert;
import org.junit.Test;

public class ProtoTest {

    @Test
    public void testCoin() {
        CoinOuterClass.Coin coin = CoinOuterClass.Coin.newBuilder().setAmount("10").setDenom("iris").build();
        Assert.assertEquals(coin.toByteArray().length, 9);
    }
}
