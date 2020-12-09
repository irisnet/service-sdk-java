package iservice.sdk.util.SM2Utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Arrays;

import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECPoint;

public class SM2 {
    private static BigInteger n = new BigInteger(
            "FFFFFFFE" + "FFFFFFFF" + "FFFFFFFF" + "FFFFFFFF" + "7203DF6B" + "21C6052B" + "53BBF409" + "39D54123", 16);
    private static BigInteger p = new BigInteger(
            "FFFFFFFE" + "FFFFFFFF" + "FFFFFFFF" + "FFFFFFFF" + "FFFFFFFF" + "00000000" + "FFFFFFFF" + "FFFFFFFF", 16);
    private static BigInteger a = new BigInteger(
            "FFFFFFFE" + "FFFFFFFF" + "FFFFFFFF" + "FFFFFFFF" + "FFFFFFFF" + "00000000" + "FFFFFFFF" + "FFFFFFFC", 16);
    private static BigInteger b = new BigInteger(
            "28E9FA9E" + "9D9F5E34" + "4D5A9E4B" + "CF6509A7" + "F39789F5" + "15AB8F92" + "DDBCBD41" + "4D940E93", 16);
    private static BigInteger gx = new BigInteger(
            "32C4AE2C" + "1F198119" + "5F990446" + "6A39C994" + "8FE30BBF" + "F2660BE1" + "715A4589" + "334C74C7", 16);
    private static BigInteger gy = new BigInteger(
            "BC3736A2" + "F4F6779C" + "59BDCEE3" + "6B692153" + "D0A9877C" + "C62A4740" + "02DF32E5" + "2139F0A0", 16);
    private static ECDomainParameters ecc_bc_spec;
    private static int w = (int) Math.ceil(n.bitLength() * 1.0 / 2) - 1;
    private static BigInteger _2w = new BigInteger("2").pow(w);
    private static final int DIGEST_LENGTH = 32;

    private static SecureRandom random = new SecureRandom();
    private static ECCurve.Fp curve;
    private static ECPoint G;

    /**
     * Print in hex
     *
     * @param b
     */
    public static void printHexString(byte[] b) {
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            System.out.print(hex.toUpperCase());
        }
        System.out.println();
    }

    /**
     * Random number generator
     *
     * @param max
     * @return
     */
    private static BigInteger random(BigInteger max) {

        BigInteger r = new BigInteger(256, random);
        // int count = 1;

        while (r.compareTo(max) >= 0) {
            r = new BigInteger(128, random);
            // count++;
        }

        // System.out.println("count: " + count);
        return r;
    }

    /**
     * Determine whether the array is all zeros
     *
     * @param buffer
     * @return
     */
    private boolean allZero(byte[] buffer) {
        for (int i = 0; i < buffer.length; i++) {
            if (buffer[i] != 0)
                return false;
        }
        return true;
    }

    /**
     * Encrypt with public key
     *
     * @param input
     *            Encrypted plaintext
     * @param publicKey
     * @return
     */
    public byte[] encrypt(String input, ECPoint publicKey) {

        byte[] inputBuffer = input.getBytes();

        byte[] C1Buffer;
        ECPoint kpb;
        byte[] t;
        do {
            /* 1. generate random number k, k belong to [1, n-1] */
            BigInteger k = random(n);

            /* 2. Calculate elliptic curve point C1 = [k]G = (x1, y1) */
            ECPoint C1 = G.multiply(k);
            C1Buffer = C1.getEncoded(false);

            /* 3. Calculate elliptic curve point S = [h]Pb */
            BigInteger h = ecc_bc_spec.getH();
            if (h != null) {
                ECPoint S = publicKey.multiply(h);
                if (S.isInfinity())
                    throw new IllegalStateException();
            }

            /* 4. Calculate [k]PB = (x2, y2) */
            kpb = publicKey.multiply(k).normalize();

            /* 5. Calculate t = KDF(x2||y2, klen) */
            byte[] kpbBytes = kpb.getEncoded(false);
            t = KDF(kpbBytes, inputBuffer.length);
            // DerivationFunction kdf = new KDF1BytesGenerator(new
            // ShortenedDigest(new SHA256Digest(), DIGEST_LENGTH));
            //
            // t = new byte[inputBuffer.length];
            // kdf.init(new ISO18033KDFParameters(kpbBytes));
            // kdf.generateBytes(t, 0, t.length);
        } while (allZero(t));

        /* 6. Calculate C2=M^t */
        byte[] C2 = new byte[inputBuffer.length];
        for (int i = 0; i < inputBuffer.length; i++) {
            C2[i] = (byte) (inputBuffer[i] ^ t[i]);
        }

        /* 7. Calculate C3 = Hash(x2 || M || y2) */
        byte[] C3 = sm3hash(kpb.getXCoord().toBigInteger().toByteArray(), inputBuffer,
                kpb.getYCoord().toBigInteger().toByteArray());

        /* 8. Export ciphertext C=C1 || C2 || C3 */

        byte[] encryptResult = new byte[C1Buffer.length + C2.length + C3.length];

        System.arraycopy(C1Buffer, 0, encryptResult, 0, C1Buffer.length);
        System.arraycopy(C2, 0, encryptResult, C1Buffer.length, C2.length);
        System.arraycopy(C3, 0, encryptResult, C1Buffer.length + C2.length, C3.length);

        return encryptResult;
    }

    /**
     * Decrypt with private key
     *
     * @param encryptData
     * @param privateKey
     * @return
     */
    public String decrypt(byte[] encryptData, BigInteger privateKey) {
        byte[] C1Byte = new byte[65];
        System.arraycopy(encryptData, 0, C1Byte, 0, C1Byte.length);

        ECPoint C1 = curve.decodePoint(C1Byte).normalize();

        /*
         * Calculate elliptic curve point S = [h]C1, Is it infinite
         */
        BigInteger h = ecc_bc_spec.getH();
        if (h != null) {
            ECPoint S = C1.multiply(h);
            if (S.isInfinity())
                throw new IllegalStateException();
        }
        /* Calculate [dB]C1 = (x2, y2) */
        ECPoint dBC1 = C1.multiply(privateKey).normalize();

        /* Calculate t = KDF(x2 || y2, klen) */
        byte[] dBC1Bytes = dBC1.getEncoded(false);
        int klen = encryptData.length - 65 - DIGEST_LENGTH;
        byte[] t = KDF(dBC1Bytes, klen);
        // DerivationFunction kdf = new KDF1BytesGenerator(new
        // ShortenedDigest(new SHA256Digest(), DIGEST_LENGTH));
        // if (debug)
        // System.out.println("klen = " + klen);
        // kdf.init(new ISO18033KDFParameters(dBC1Bytes));
        // kdf.generateBytes(t, 0, t.length);

        if (allZero(t)) {
            System.err.println("all zero");
            throw new IllegalStateException();
        }

        /* 5 Calculate M'=C2^t */
        byte[] M = new byte[klen];
        for (int i = 0; i < M.length; i++) {
            M[i] = (byte) (encryptData[C1Byte.length + i] ^ t[i]);
        }

        /* 6 Calculate u = Hash(x2 || M' || y2) */
        byte[] C3 = new byte[DIGEST_LENGTH];

        System.arraycopy(encryptData, encryptData.length - DIGEST_LENGTH, C3, 0, DIGEST_LENGTH);
        byte[] u = sm3hash(dBC1.getXCoord().toBigInteger().toByteArray(), M,
                dBC1.getYCoord().toBigInteger().toByteArray());
        if (Arrays.equals(u, C3)) {
            try {
                return new String(M, "UTF8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return null;
        }
        return null;

    }

    // /**
    // * SHA Digest
    // * @param x2
    // * @param M
    // * @param y2
    // * @return
    // */
    // private byte[] calculateHash(BigInteger x2, byte[] M, BigInteger y2) {
    // ShortenedDigest digest = new ShortenedDigest(new SHA256Digest(),
    // DIGEST_LENGTH);
    // byte[] buf = x2.toByteArray();
    // digest.update(buf, 0, buf.length);
    // digest.update(M, 0, M.length);
    // buf = y2.toByteArray();
    // digest.update(buf, 0, buf.length);
    //
    // buf = new byte[DIGEST_LENGTH];
    // digest.doFinal(buf, 0);
    //
    // return buf;
    // }

    /**
     * Judge whether it is within the scope
     *
     * @param param
     * @param min
     * @param max
     * @return
     */
    private boolean between(BigInteger param, BigInteger min, BigInteger max) {
        if (param.compareTo(min) >= 0 && param.compareTo(max) < 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Judge whether the generated public key is legal or not
     *
     * @param publicKey
     * @return
     */
    private boolean checkPublicKey(ECPoint publicKey) {

        if (!publicKey.isInfinity()) {

            BigInteger x = publicKey.getXCoord().toBigInteger();
            BigInteger y = publicKey.getYCoord().toBigInteger();

            if (between(x, new BigInteger("0"), p) && between(y, new BigInteger("0"), p)) {

                BigInteger xResult = x.pow(3).add(a.multiply(x)).add(b).mod(p);

                BigInteger yResult = y.pow(2).mod(p);

                if (yResult.equals(xResult) && publicKey.multiply(n).isInfinity()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * generate key pair
     *
     * @return
     */
    public SM2KeyPair generateKeyPair() {

        BigInteger d = random(n.subtract(new BigInteger("1")));

        SM2KeyPair keyPair = new SM2KeyPair(G.multiply(d).normalize(), d);

        if (checkPublicKey(keyPair.getPublicKey())) {
            return keyPair;
        } else {
            return null;
        }
    }

    public SM2() {
        curve = new ECCurve.Fp(p, // q
                a, // a
                b); // b
        G = curve.createPoint(gx, gy);
        ecc_bc_spec = new ECDomainParameters(curve, G, n);
    }

    /**
     * Export public key to local
     *
     * @param publicKey
     * @param path
     */
    public void exportPublicKey(ECPoint publicKey, String path) {
        File file = new File(path);
        try {
            if (!file.exists())
                file.createNewFile();
            byte buffer[] = publicKey.getEncoded(false);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(buffer);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Import public key from local
     *
     * @param path
     * @return
     */
    public ECPoint importPublicKey(String path) {
        File file = new File(path);
        try {
            if (!file.exists())
                return null;
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            byte buffer[] = new byte[16];
            int size;
            while ((size = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, size);
            }
            fis.close();
            return curve.decodePoint(baos.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * export private key to local
     *
     * @param privateKey
     * @param path
     */
    public void exportPrivateKey(BigInteger privateKey, String path) {
        File file = new File(path);
        try {
            if (!file.exists())
                file.createNewFile();
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(privateKey);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Import private key from local
     *
     * @param path
     * @return
     */
    public BigInteger importPrivateKey(String path) {
        File file = new File(path);
        try {
            if (!file.exists())
                return null;
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            BigInteger res = (BigInteger) (ois.readObject());
            ois.close();
            fis.close();
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Concatenated byte array
     *
     * @param params
     * @return
     */
    private static byte[] join(byte[]... params) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] res = null;
        try {
            for (int i = 0; i < params.length; i++) {
                baos.write(params[i]);
            }
            res = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * sm3 Digest
     *
     * @param params
     * @return
     */
    private static byte[] sm3hash(byte[]... params) {
        byte[] res = null;
        try {
            res = SM3.hash(join(params));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Gets an array of user ID bytes
     *
     * @param IDA
     * @param aPublicKey
     * @return
     */
    private static byte[] ZA(String IDA, ECPoint aPublicKey) {
        byte[] idaBytes = IDA.getBytes();
        int entlenA = idaBytes.length * 8;
        byte[] ENTLA = new byte[] { (byte) (entlenA & 0xFF00), (byte) (entlenA & 0x00FF) };
        byte[] ZA = sm3hash(ENTLA, idaBytes, a.toByteArray(), b.toByteArray(), gx.toByteArray(), gy.toByteArray(),
                aPublicKey.getXCoord().toBigInteger().toByteArray(),
                aPublicKey.getYCoord().toBigInteger().toByteArray());
        return ZA;
    }

    /**
     * Sign
     *
     * @param M
     *            Signature information
     * @param IDA
     *            Unique identity of signer
     * @param keyPair
     *            Key pair of signer
     * @return Signature
     */
    public Signature sign(String M, String IDA, SM2KeyPair keyPair) {
        byte[] ZA = ZA(IDA, keyPair.getPublicKey());
        byte[] M_ = join(ZA, M.getBytes());
        BigInteger e = new BigInteger(1, sm3hash(M_));
        // BigInteger k = new BigInteger(
        // "6CB28D99 385C175C 94F94E93 4817663F C176D925 DD72B727 260DBAAE
        // 1FB2F96F".replace(" ", ""), 16);
        BigInteger k;
        BigInteger r;
        do {
            k = random(n);
            ECPoint p1 = G.multiply(k).normalize();
            BigInteger x1 = p1.getXCoord().toBigInteger();
            r = e.add(x1);
            r = r.mod(n);
        } while (r.equals(BigInteger.ZERO) || r.add(k).equals(n));

        BigInteger s = ((keyPair.getPrivateKey().add(BigInteger.ONE).modInverse(n))
                .multiply((k.subtract(r.multiply(keyPair.getPrivateKey()))).mod(n))).mod(n);

        return new Signature(r, s);
    }

    /**
     * Verify signature
     *
     * @param M
     *            Signature information
     * @param signature
     * @param IDA
     *            Unique identity of signer
     * @param aPublicKey
     *            Public key of signer
     * @return true or false
     */
    public boolean verify(String M, Signature signature, String IDA, ECPoint aPublicKey) {
        if (!between(signature.r, BigInteger.ONE, n))
            return false;
        if (!between(signature.s, BigInteger.ONE, n))
            return false;

        byte[] M_ = join(ZA(IDA, aPublicKey), M.getBytes());
        BigInteger e = new BigInteger(1, sm3hash(M_));
        BigInteger t = signature.r.add(signature.s).mod(n);

        if (t.equals(BigInteger.ZERO))
            return false;

        ECPoint p1 = G.multiply(signature.s).normalize();
        ECPoint p2 = aPublicKey.multiply(t).normalize();
        BigInteger x1 = p1.add(p2).normalize().getXCoord().toBigInteger();
        BigInteger R = e.add(x1).mod(n);
        if (R.equals(signature.r))
            return true;
        return false;
    }

    /**
     * Key derivation function
     *
     * @param Z
     * @param klen
     *            Generate a klen byte key
     * @return
     */
    private static byte[] KDF(byte[] Z, int klen) {
        int ct = 1;
        int end = (int) Math.ceil(klen * 1.0 / 32);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            for (int i = 1; i < end; i++) {
                baos.write(sm3hash(Z, SM3.toByteArray(ct)));
                ct++;
            }
            byte[] last = sm3hash(Z, SM3.toByteArray(ct));
            if (klen % 32 == 0) {
                baos.write(last);
            } else
                baos.write(last, 0, klen % 32);
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Transport entity class
     *
     * @author Potato
     *
     */
    private static class TransportEntity implements Serializable {
        final byte[] R; //Point R
        final byte[] S; //Verify S
        final byte[] Z; //User ID
        final byte[] K; //Public key

        public TransportEntity(byte[] r, byte[] s,byte[] z,ECPoint pKey) {
            R = r;
            S = s;
            Z=z;
            K=pKey.getEncoded(false);
        }
    }

    /**
     * Key Exchange
     *
     * @author Potato
     *
     */
    public static class KeyExchange {
        BigInteger rA;
        ECPoint RA;
        ECPoint V;
        byte[] Z;
        byte[] key;

        String ID;
        SM2KeyPair keyPair;

        public KeyExchange(String ID,SM2KeyPair keyPair) {
            this.ID=ID;
            this.keyPair = keyPair;
            this.Z=ZA(ID, keyPair.getPublicKey());
        }

        /**
         * Key exchange_1
         *
         * @return
         */
        public TransportEntity keyExchange_1() {
            rA = random(n);
            // rA=new BigInteger("83A2C9C8 B96E5AF7 0BD480B4 72409A9A 327257F1
            // EBB73F5B 073354B2 48668563".replace(" ", ""),16);
            RA = G.multiply(rA).normalize();
            return new TransportEntity(RA.getEncoded(false), null,Z,keyPair.getPublicKey());
        }

        /**
         * Key agreement responder
         *
         * @param entity
         * @return
         */
        public TransportEntity keyExchange_2(TransportEntity entity) {
            BigInteger rB = random(n);
            // BigInteger rB=new BigInteger("33FE2194 0342161C 55619C4A 0C060293
            // D543C80A F19748CE 176D8347 7DE71C80".replace(" ", ""),16);
            ECPoint RB = G.multiply(rB).normalize();

            this.rA=rB;
            this.RA=RB;

            BigInteger x2 = RB.getXCoord().toBigInteger();
            x2 = _2w.add(x2.and(_2w.subtract(BigInteger.ONE)));

            BigInteger tB = keyPair.getPrivateKey().add(x2.multiply(rB)).mod(n);
            ECPoint RA = curve.decodePoint(entity.R).normalize();

            BigInteger x1 = RA.getXCoord().toBigInteger();
            x1 = _2w.add(x1.and(_2w.subtract(BigInteger.ONE)));

            ECPoint aPublicKey=curve.decodePoint(entity.K).normalize();
            ECPoint temp = aPublicKey.add(RA.multiply(x1).normalize()).normalize();
            ECPoint V = temp.multiply(ecc_bc_spec.getH().multiply(tB)).normalize();
            if (V.isInfinity())
                throw new IllegalStateException();
            this.V=V;

            byte[] xV = V.getXCoord().toBigInteger().toByteArray();
            byte[] yV = V.getYCoord().toBigInteger().toByteArray();
            byte[] KB = KDF(join(xV, yV, entity.Z, this.Z), 16);
            key = KB;
            System.out.print("Negotiated private key B:");
            printHexString(KB);
            byte[] sB = sm3hash(new byte[] { 0x02 }, yV,
                    sm3hash(xV, entity.Z, this.Z, RA.getXCoord().toBigInteger().toByteArray(),
                            RA.getYCoord().toBigInteger().toByteArray(), RB.getXCoord().toBigInteger().toByteArray(),
                            RB.getYCoord().toBigInteger().toByteArray()));
            return new TransportEntity(RB.getEncoded(false), sB,this.Z,keyPair.getPublicKey());
        }

        /**
         * Key exchange_2
         *
         * @param entity
         */
        public TransportEntity keyExchange_3(TransportEntity entity) {
            BigInteger x1 = RA.getXCoord().toBigInteger();
            x1 = _2w.add(x1.and(_2w.subtract(BigInteger.ONE)));

            BigInteger tA = keyPair.getPrivateKey().add(x1.multiply(rA)).mod(n);
            ECPoint RB = curve.decodePoint(entity.R).normalize();

            BigInteger x2 = RB.getXCoord().toBigInteger();
            x2 = _2w.add(x2.and(_2w.subtract(BigInteger.ONE)));

            ECPoint bPublicKey=curve.decodePoint(entity.K).normalize();
            ECPoint temp = bPublicKey.add(RB.multiply(x2).normalize()).normalize();
            ECPoint U = temp.multiply(ecc_bc_spec.getH().multiply(tA)).normalize();
            if (U.isInfinity())
                throw new IllegalStateException();
            this.V=U;

            byte[] xU = U.getXCoord().toBigInteger().toByteArray();
            byte[] yU = U.getYCoord().toBigInteger().toByteArray();
            byte[] KA = KDF(join(xU, yU,
                    this.Z, entity.Z), 16);
            key = KA;
            System.out.print("Negotiated private key A:");
            printHexString(KA);
            byte[] s1= sm3hash(new byte[] { 0x02 }, yU,
                    sm3hash(xU, this.Z, entity.Z, RA.getXCoord().toBigInteger().toByteArray(),
                            RA.getYCoord().toBigInteger().toByteArray(), RB.getXCoord().toBigInteger().toByteArray(),
                            RB.getYCoord().toBigInteger().toByteArray()));
            if(Arrays.equals(entity.S, s1))
                System.out.println("B->A Key confirmation successful");
            else
                System.out.println("B->A Key confirmation failed");
            byte[] sA= sm3hash(new byte[] { 0x03 }, yU,
                    sm3hash(xU, this.Z, entity.Z, RA.getXCoord().toBigInteger().toByteArray(),
                            RA.getYCoord().toBigInteger().toByteArray(), RB.getXCoord().toBigInteger().toByteArray(),
                            RB.getYCoord().toBigInteger().toByteArray()));

            return new TransportEntity(RA.getEncoded(false), sA,this.Z,keyPair.getPublicKey());
        }

        /**
         * Key exchange_4
         *
         * @param entity
         */
        public void keyExchange_4(TransportEntity entity) {
            byte[] xV = V.getXCoord().toBigInteger().toByteArray();
            byte[] yV = V.getYCoord().toBigInteger().toByteArray();
            ECPoint RA = curve.decodePoint(entity.R).normalize();
            byte[] s2= sm3hash(new byte[] { 0x03 }, yV,
                    sm3hash(xV, entity.Z, this.Z, RA.getXCoord().toBigInteger().toByteArray(),
                            RA.getYCoord().toBigInteger().toByteArray(), this.RA.getXCoord().toBigInteger().toByteArray(),
                            this.RA.getYCoord().toBigInteger().toByteArray()));
            if(Arrays.equals(entity.S, s2))
                System.out.println("A->B Key confirmation successful");
            else
                System.out.println("A->B Key confirmation failed");
        }
    }

    public static void main(String[] args) throws UnsupportedEncodingException {

        SM2 sm02 = new SM2();
        // BigInteger px = new BigInteger(
        // "0AE4C779 8AA0F119 471BEE11 825BE462 02BB79E2 A5844495 E97C04FF
        // 4DF2548A".replace(" ", ""), 16);
        // BigInteger py = new BigInteger(
        // "7C0240F8 8F1CD4E1 6352A73C 17B7F16F 07353E53 A176D684 A9FE0C6B
        // B798E857".replace(" ", ""), 16);
        // ECPoint publicKey = sm02.curve.createPoint(px, py);
        // BigInteger privateKey = new BigInteger(
        // "128B2FA8 BD433C6C 068C8D80 3DFF7979 2A519A55 171B1B65 0C23661D
        // 15897263".replace(" ", ""), 16);

        // SM2KeyPair keyPair = sm02.generateKeyPair();
        // ECPoint publicKey=keyPair.getPublicKey();
        // BigInteger privateKey=keyPair.getPrivateKey();
        // sm02.exportPublicKey(publicKey, "E:/publickey.pem");
        // sm02.exportPrivateKey(privateKey, "E:/privatekey.pem");

        System.out.println("-----------------Public key encryption and decryption-----------------");
        ECPoint publicKey = sm02.importPublicKey("E:/publickey.pem");
        BigInteger privateKey = sm02.importPrivateKey("E:/privatekey.pem");
        byte[] data = sm02.encrypt("test encrypt aaaaaaaaaaa123aabb", publicKey);
        System.out.print("ciphertext:");
        SM2.printHexString(data);
        System.out.println("Decrypted plaintext:" + sm02.decrypt(data, privateKey));

        System.out.println("-----------------Sign and verify-----------------");
        String IDA = "Heartbeats";
        String M = "Information to sign";
        Signature signature = sm02.sign(M, IDA, new SM2KeyPair(publicKey, privateKey));
        System.out.println("User ID:" + IDA);
        System.out.println("Signature information:" + M);
        System.out.println("Signature:" + signature);
        System.out.println("Verify signature:" + sm02.verify(M, signature, IDA, publicKey));

        System.out.println("-----------------Key agreement-----------------");
        String aID = "AAAAAAAAAAAAA";
        SM2KeyPair aKeyPair = sm02.generateKeyPair();
        KeyExchange aKeyExchange = new KeyExchange(aID,aKeyPair);

        String bID = "BBBBBBBBBBBBB";
        SM2KeyPair bKeyPair = sm02.generateKeyPair();
        KeyExchange bKeyExchange = new KeyExchange(bID,bKeyPair);
        TransportEntity entity1 = aKeyExchange.keyExchange_1();
        TransportEntity entity2 = bKeyExchange.keyExchange_2(entity1);
        TransportEntity entity3 = aKeyExchange.keyExchange_3(entity2);
        bKeyExchange.keyExchange_4(entity3);
    }

    public static class Signature {
        BigInteger r;
        BigInteger s;

        public Signature(BigInteger r, BigInteger s) {
            this.r = r;
            this.s = s;
        }

        public BigInteger getR() {
            return this.r;
        }

        public BigInteger getS() {
            return this.s;
        }

        public String toString() {
            return r.toString(16) + "," + s.toString(16);
        }
    }

    public ECPoint getPubKeyFromPrivKey(BigInteger d) {
        return G.multiply(d).normalize();
    }
}