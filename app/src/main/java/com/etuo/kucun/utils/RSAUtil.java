package com.etuo.kucun.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import static com.etuo.kucun.utils.Base64Rsa.getDecoder;
import static com.etuo.kucun.utils.Base64Rsa.getEncoder;


/**
 * @author : haoliang.sun
 * @date : 2019/1/24 13:37
 */
public final class RSAUtil {
    public static   final  String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCqygC+QR16Ex5dwnvW2pw4kPnxW1SNtq+PsB8cCnGqOvFJjzL1Sd1GkW2JqQDYrvfNebpx10Y49rtkL8p40Nl8QXuGHQL81r46Yt/3iNy8pWwSK0y19wxIagl/nh66xt5HR0KQ566A/W4SAqxmhu6kPbvimX3Y1JG8x7MgxCJ0cwIDAQAB";

    public static String rsa256Sign(String content, String privateKey, String charset) {
        try {
            PrivateKey priKey = getPrivateKeyFromPKCS8("RSA", new ByteArrayInputStream(privateKey.getBytes()));
            Signature signature = Signature.getInstance("SHA256WithRSA");
            signature.initSign(priKey);
            if (StringUtil.isEmpty(charset)) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(charset));
            }

            byte[] signed = signature.sign();
            return new String(Base64Rsa.getEncoder().encode(signed));
        } catch (Exception e) {
            throw new RuntimeException("RSAcontent = " + content + "; charset = " + charset + e.getMessage());
        }
    }

    private static void io(Reader in, Writer out, int bufferSize) throws IOException {
        if (bufferSize == -1) {
            bufferSize = 4096;
        }

        char[] buffer = new char[bufferSize];

        int amount;
        while((amount = in.read(buffer)) >= 0) {
            out.write(buffer, 0, amount);
        }

    }


    private static PrivateKey getPrivateKeyFromPKCS8(String algorithm, InputStream ins) throws Exception {
        if (ins != null && !StringUtil.isEmpty(algorithm)) {
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
            Reader reader = new InputStreamReader(ins, "UTF-8");
            StringWriter writer = new StringWriter();
            io(reader, writer, -1);

            byte[] encodedKey = writer.toString().getBytes();
            encodedKey = Base64Rsa.getDecoder().decode(encodedKey);
            return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
        } else {
            return null;
        }
    }


    public static String[] generateKey(String keyName, int keyLength) throws Exception {
        KeyPairGenerator keyPairGenerator;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance(keyName);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("不支持的算法名称：" + keyName);
        }

        SecureRandom secureRandom = new SecureRandom();
        keyPairGenerator.initialize(keyLength, secureRandom);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();
        byte[] privateKeyByte = getEncoder().encode(privateKey.getEncoded());
        byte[] publicKeyByte = getEncoder().encode(publicKey.getEncoded());
        String privateKeyValue = new String(privateKeyByte);
        String publicKeyValue = new String(publicKeyByte);

        String[] result = new String[]{privateKeyValue, publicKeyValue};

        return result;
    }


    public static boolean rsa256CheckContent(String content, String sign, String publicKey, String charset) {
        try {
            PublicKey pubKey = getPublicKeyFromX509("RSA", new ByteArrayInputStream(publicKey.getBytes()));
            Signature signature = Signature.getInstance("SHA256WithRSA");
            signature.initVerify(pubKey);
            if (StringUtil.isEmpty(charset)) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(charset));
            }

            return signature.verify(getDecoder().decode(sign.getBytes()));
        } catch (Exception e) {
            System.out.println("RSAcontent = " + content + ",sign=" + sign + ",charset = " + charset + e.getMessage());
            return false;
        }
    }


    public static PublicKey getPublicKeyFromX509(String algorithm, InputStream ins) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        StringWriter writer = new StringWriter();
        io(new InputStreamReader(ins), writer, -1);
        byte[] encodedKey = writer.toString().getBytes();
        encodedKey = getDecoder().decode(encodedKey);
        return keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
    }

    public static String rsaDecrypt(String content, String privateKey, String charset) {
        try {
            PrivateKey priKey = getPrivateKeyFromPKCS8("RSA", new ByteArrayInputStream(privateKey.getBytes()));
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(2, priKey);
            byte[] encryptedData = StringUtil.isEmpty(charset) ? getDecoder().decode(content.getBytes()) : getDecoder().decode(content.getBytes(charset));
            int inputLen = encryptedData.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;

            for(int i = 0; inputLen - offSet > 0; offSet = i * 128) {
                byte[] cache;
                if (inputLen - offSet > 128) {
                    cache = cipher.doFinal(encryptedData, offSet, 128);
                } else {
                    cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
                }

                out.write(cache, 0, cache.length);
                ++i;
            }

            byte[] decryptedData = out.toByteArray();
            out.close();
            return StringUtil.isEmpty(charset) ? new String(decryptedData) : new String(decryptedData, charset);
        } catch (Exception e) {
            System.out.println("EncodeContent = " + content + ",charset = " + charset + e.getMessage());
            return "";
        }
    }

    public static String rsaEncrypt(String content, String publicKey, String charset) throws Exception{
        try {
            PublicKey pubKey = getPublicKeyFromX509("RSA", new ByteArrayInputStream(publicKey.getBytes()));
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(1, pubKey);
            byte[] data = StringUtil.isEmpty(charset) ? content.getBytes() : content.getBytes(charset);
            int inputLen = data.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;

            for(int i = 0; inputLen - offSet > 0; offSet = i * 117) {
                byte[] cache;
                if (inputLen - offSet > 117) {
                    cache = cipher.doFinal(data, offSet, 117);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }

                out.write(cache, 0, cache.length);
                ++i;
            }
            byte[] encryptedData = getEncoder().encode(out.toByteArray());
            out.close();
            return StringUtil.isEmpty(charset) ? new String(encryptedData) : new String(encryptedData, charset);
        } catch (Exception e) {
            throw new Exception("EncryptContent = " + content + ",charset = " + charset + e.getMessage());
        }
    }

    public static String rsaEncrypt(String content){

        if (StringUtil.isEmpty(content)){
            return null;
        }
        String getRsaEncrypt = "";

        try {
            getRsaEncrypt = rsaEncrypt(content,publicKey,"UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return getRsaEncrypt;


    }



}
