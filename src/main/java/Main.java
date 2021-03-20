/*
 *  @author albua
 *  created on 19/03/2021
 */

import ui.GUI;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");

        String passwd = "parrolaa";
        md.update(passwd.getBytes());
        byte[] digest = md.digest();


        System.out.println(digest);


        //GUI.main(args);
    }
}
