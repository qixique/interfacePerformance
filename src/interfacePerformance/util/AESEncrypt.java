package interfacePerformance.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Date;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import com.sun.crypto.provider.SunJCE;

public class AESEncrypt {
    private static KeyGenerator keygen;
    private static SecretKey deskey;
    private static Cipher c;
    private static byte[] cipherByte;
//    private static final String PASSWORD = "zjport_tropjz";

    static {
        Security.addProvider(new SunJCE());
        try {
            keygen = KeyGenerator.getInstance("AES");
            byte[] passBytes = "zjport_tropjz".getBytes("utf-8");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(passBytes);
            keygen.init(128, secureRandom);

            deskey = keygen.generateKey();

            c = Cipher.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static String encryptor(String str) throws InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException, UnsupportedEncodingException {
        c.init(1, deskey);
        byte[] src = str.getBytes("utf-8");

        cipherByte = c.doFinal(src);
        return ByteHexHelper.bytesToHexString(cipherByte);
    }

    public static byte[] decryptor(byte[] buff) throws InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException {
        c.init(2, deskey);
        cipherByte = c.doFinal(buff);
        return cipherByte;
    }

    public static String decryptor(String hexString) throws InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException, UnsupportedEncodingException {
        byte[] hexArray = decryptor(ByteHexHelper.hexStr2ByteArray(hexString));
        return new String(hexArray, "utf-8");
    }

    public static void main(String[] args) throws Exception {
        String msg = "浣犲ソ!@#$%^&*";
        test(msg);

        msg = "";
        test(msg);

        msg = null;
        test(msg);
    }

    private static void test(String msg) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException,
            UnsupportedEncodingException {
        String encontent = encryptor(msg);
        String decontent = decryptor(encontent);
        System.out.println("date="+(new Date()).getTime());;
        System.out.println("=====鍘熸枃锛� "+ msg);
        System.out.println("=====鍔犲瘑鍚庯細" + encontent);
        System.out.println("=====瑙ｅ瘑鍚庯細" + decontent);
    }
}