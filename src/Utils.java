import java.security.*;
import java.util.Random;

/**
 * This class will is reponsbible for the helper functions for the application
 *
 */
public class Utils {
    public static String randomHex() {
        Random r = new Random();
        return Integer.toHexString(r.nextInt());
    }

    public static String sha256Hex(String input) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(input.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for(byte b: hash) sb.append(String.format("%02x", b));
        return sb.toString();
    }
}