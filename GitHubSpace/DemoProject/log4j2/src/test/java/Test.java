import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by cgz on 2019-04-27 23:06
 * 描述：
 */
public class Test {
    private  static final String AES="AES";
    private  static final String UTF8="UTF-8";

    /**
     * AES加密
     * @param content
     * @param pkey
     * @return
     * @throws DecoderException
     */
    private static byte[] encrypt(String content, String pkey) throws DecoderException {
        try {
            String private_key=pkey;
            byte[] encodeFormat=null;
            try {
                //秘钥 Hex解码为什么秘钥要进行解码，因为秘钥是某个秘钥明文进行了Hex编码后的值，所以在使用的时候要进行解码
                encodeFormat = Hex.decodeHex(private_key.toCharArray());
            } catch (DecoderException e) {
                e.printStackTrace();
            }
            SecretKeySpec key = new SecretKeySpec(encodeFormat, AES);
            // Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            // 加密内容进行编码
            byte[] byteContent = content.getBytes(UTF8);
            // 用密匙初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, key);
            // 正式执行加密操作
            byte[] result = cipher.doFinal(byteContent);
            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * AES解密
     * @param contents
     * @param password
     * @return
     * @throws DecoderException
     */
    private static byte[] decrypt(String contents, String password) throws DecoderException {
        try {
            //密文使用Hex解码
            byte[]content = Hex.decodeHex(contents.toCharArray());
            //秘钥 Hex解码为什么秘钥要进行解码，因为秘钥是某个秘钥明文进行了Hex编码后的值，所以在使用的时候要进行解码
            byte[] encodeFormat = Hex.decodeHex(password.toCharArray());
            SecretKeySpec key = new SecretKeySpec(encodeFormat, AES);
            // Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance(AES);
            // 用密匙初始化Cipher对象
            cipher.init(Cipher.DECRYPT_MODE, key);
            // 正式执行解密操作
            byte[] result = cipher.doFinal(content);
            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Aes加密
     * @param context 明文
     * @param private_key 秘钥
     * @return
     * @throws DecoderException
     */
    public static String  encryption(String context,String private_key) throws DecoderException{
        //加密后的明文也就变成了密文
        byte[] encryptResult = encrypt(context, private_key);
        //密码文Hex编码
        String encryptResultStr = Hex.encodeHexString(encryptResult);
        return encryptResultStr;
    }

    /**
     * Aes解密
     * @param context 密文
     * @param private_key 秘钥
     * @return
     * @throws DecoderException
     * @throws UnsupportedEncodingException
     */
    public static String decryption(String context,String private_key) throws DecoderException, UnsupportedEncodingException{
        //这里的密文解密前先进行了Hex解码
        byte[] decryptResult = decrypt(context, private_key);
        String result = new String(decryptResult, UTF8);
        return result;
    }

    public static String strTo16(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }
        return str;
    }
    public static void main(String[] args) throws UnsupportedEncodingException, DecoderException {

        System.out.println(strTo16("name"));

        //加密内容
        String content = "123456787654321";
        //AES加密解密秘钥
        String password = "11111111111111111111111111111111";
        // 加密
        System.out.println("加密前：" + content);
        // 调用加密方法
        String encryptResultStr = encryption(content, password);
        System.out.println("加密后：" + encryptResultStr);
        // 调用解密方法
        String result  = decryption(encryptResultStr, password);
        // 解密内容进行解码
        System.out.println("解密后：" + result);
    }
}
