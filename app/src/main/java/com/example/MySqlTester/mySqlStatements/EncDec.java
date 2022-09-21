package com.example.MySqlTester.mySqlStatements;

import com.scottyab.aescrypt.AESCrypt;

import java.security.GeneralSecurityException;

public class EncDec {

    public static String encode(String text) {
        String key = "AbCdEfG123#";
        String result = null;
        try {
            result = AESCrypt.encrypt(key, text);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String decode(String text) {
        String key = "AbCdEfG123#";
        String result = null;
        try {
            if (text != null) {
                result = AESCrypt.decrypt(key, text);
            }
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return result;
    }
}
