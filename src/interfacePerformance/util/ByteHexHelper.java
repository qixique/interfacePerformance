package interfacePerformance.util;
public class ByteHexHelper {
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if ((src == null) || (src.length <= 0)) {
            return null;
        }
        for (int i = 0; i < src.length; ++i) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }

        return stringBuilder.toString();
    }

    public static byte[] hexStr2ByteArray(String hexString) {
        if ((hexString == null) || ("".equals(hexString))) {
            throw new IllegalArgumentException("this hexString must not be empty");
        }
        hexString = hexString.toLowerCase();
        byte[] byteArray = new byte[hexString.length() / 2];
        int k = 0;
        for (int i = 0; i < byteArray.length; ++i) {
            byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xFF);
            byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xFF);
            byteArray[i] = (byte) (high << 4 | low);
            k += 2;
        }

        return byteArray;
    }

    public static byte[] hex2Byte(String hex) {
        String digital = "0123456789ABCDEF";
        char[] hex2char = hex.toCharArray();
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; ++i) {
            int temp = digital.indexOf(hex2char[(2 * i)]) * 16;
            temp += digital.indexOf(hex2char[(2 * i + 1)]);
            bytes[i] = (byte) (temp & 0xFF);
        }

        return bytes;
    }
}
