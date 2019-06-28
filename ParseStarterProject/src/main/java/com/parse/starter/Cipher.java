package com.parse.starter;

import java.util.Map;
import java.util.HashMap;

public class Cipher {
    //Cipher is currentyly using Synchronous Stream Cipher
    static int key;
    static char cmd;
    static String cipherTxt;
    static String plainTxt;
    static int p = 26;
    static Map<Character,Integer> charMap;
    static Map<Integer, Integer> mapKeyToValue;

    public Cipher() {
        initCharMap();
    }

    public static void initCharMap(){
        charMap = new HashMap<Character,Integer>();
        //generate 52 letters
        int counter = 0;
        //for uppercase
        for(int i = 65; i <= 90;i++){
            charMap.put((char)i,counter);
            counter++;
        }
    }

    public static void initMapKeyToValue(){
        mapKeyToValue = new HashMap<Integer,Integer>();
        int size = 0;

        int currentKey = 0;
        int previousKey = 0;
        for(int i = 0; i < size;i++){
            if (i == 0){
                currentKey = ((key*key) + 1) % p;
            }else{
                currentKey = ((previousKey*previousKey) + (i+1) ) % p;
            }
            mapKeyToValue.put(i+1,currentKey);
            previousKey = currentKey;
        }

        // System.out.println("Generate all keys");
        // for(Entry<Integer,Integer>entry:mapKeyToValue.entrySet()){
        // 	System.out.println(entry.getKey() + "|" + entry.getValue());
        // }
    }


    public static String encrypt(String plainTxt){
        initMapKeyToValue();
        //use plainTxt
        String cipherTxt = "";
        for(int i = 0; i < plainTxt.length();i++){
            int m = ( (int) plainTxt.charAt(i)) - 65;
            int c = (m + mapKeyToValue.get(i+1)) % 26;
            char cipher = (char) ( c + 65);
            cipherTxt += cipher;
        }
        return cipherTxt;
    }

    public static String decrypt(String cipherTxt){
        initMapKeyToValue();
        String plainTxt = "";
        //use cipherTxt
        for(int i = 0; i < cipherTxt.length();i++){
            int m = ( (int) cipherTxt.charAt(i)) - 65;
            int c = (m - mapKeyToValue.get(i+1)) % 26;

            if (c < 0 )
                c += 26;

            char plain = (char) ( c + 65);
            plainTxt += plain;
        }
        return plainTxt;
    }
}
