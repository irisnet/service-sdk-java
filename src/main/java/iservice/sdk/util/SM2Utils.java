package iservice.sdk.util;

import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.signers.DSAEncoding;
import org.bouncycastle.crypto.signers.StandardDSAEncoding;
import org.bouncycastle.math.ec.ECPoint;
import org.zz.gmhelper.BCECUtil;
import org.zz.gmhelper.SM2Util;

import java.io.IOException;
import java.math.BigInteger;

public class SM2Utils {

    public ECPoint getPubkeyFromPrivkey(BigInteger privkey) {
        ECPrivateKeyParameters privkeyParameters = BCECUtil.createECPrivateKeyParameters(privkey, SM2Util.DOMAIN_PARAMS);
        ECPublicKeyParameters pubkey = BCECUtil.buildECPublicKeyByPrivateKey(privkeyParameters);
        return pubkey.getQ();
    }

    public byte[] sign(BigInteger privkey, byte[] signdoc) throws CryptoException {
        ECPrivateKeyParameters privkeyParameters = BCECUtil.createECPrivateKeyParameters(privkey, SM2Util.DOMAIN_PARAMS);
        return SM2Util.sign(privkeyParameters, "1234567812345678".getBytes(), signdoc);
    }

    public boolean verify(BigInteger privkey, byte[] srcData, byte[] signature) {
        ECPrivateKeyParameters privkeyParameters = BCECUtil.createECPrivateKeyParameters(privkey, SM2Util.DOMAIN_PARAMS);
        ECPublicKeyParameters publicKeyParameters = BCECUtil.buildECPublicKeyByPrivateKey(privkeyParameters);
        return SM2Util.verify(publicKeyParameters, srcData, signature);
    }
    
    public BigInteger[] getRSFromSignature(byte[] signature) throws IOException {
        DSAEncoding encoding = new StandardDSAEncoding();
        return encoding.decode(SM2Util.DOMAIN_PARAMS.getN(), signature);
    }
}
