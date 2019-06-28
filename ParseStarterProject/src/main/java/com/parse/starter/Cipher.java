package com.parse.starter;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

public class Cipher {
    //Cipher is using Synchronous Stream Cipher
    //Initialize new Map for Character To Integer
    Map<Character,Integer> alpha = new HashMap<Character,Integer>();
    //Initialize new Map for Integer to Character
    Map<Integer,Character> rAlpha = new HashMap<Integer,Character>();

    private int key = 10;
    private int size = 0;

    public Cipher() {
        //Calling functions to initialize map with initialize value
        initAlpha(alpha);
        initRAlpha(rAlpha);
    }
    //Initializing map with empty value
    public void initAlpha (Map<Character,Integer> alpha){
        int j = 0;
        for(char i = 'A';i <= 'Z';i++) {
            alpha.put(i, j);
            j++;
        }
    }

    //Initializing map with empty value
    public void initRAlpha(Map<Integer,Character> rAlpha) {
        int i = 0;
        for(char j = 'A'; j <= 'Z'; j++) {
            rAlpha.put(i, j);
            i++;
        }
    }

    public String encrypt(String toEncrypt){
        String result = "";

        size = toEncrypt.length();
        ArrayList<Integer> k = initKey(key,size);
        ArrayList<Character> ch = new ArrayList<Character>();
        for(char c : toEncrypt.toCharArray()) {
            ch.add(c);
        }

        result = doEncrypt(alpha,rAlpha,ch,k);
        return result;
    }

    public String doEncrypt (Map<Character,Integer> alpha, Map<Integer,Character> rAlpha, ArrayList<Character> c, ArrayList<Integer> key) {
        String ret = "";
        int value = 0;
        char x = '\0';
        boolean smallcase = false;
        boolean put = false;
        ArrayList<Character> temp = new ArrayList<Character>();
        for(int i = 0 ; i < c.size(); i++) {
            if(Character.isLetter(c.get(i))) {
                if(Character.isLowerCase(c.get(i))) {
                    value = alpha.get(Character.toUpperCase(c.get(i)));
                    smallcase = true;
                    put = false;
                }else {
                    value = alpha.get(c.get(i));
                    put = false;
                }

            }else {
                temp.add(c.get(i));
                put = true;
            }

            if(put == false) {
                value = (value + key.get(i)) % 26;
                x = rAlpha.get(value);

                if(smallcase == true) {
                    x = Character.toLowerCase(x);
                    smallcase = false;
                }
                temp.add(x);
            }

        }

        for(char y: temp) {
            ret += y;
        }

        return ret;
    }

    public String decrypt(String toDecrypt){
        String result ="";

        size = toDecrypt.length();
        ArrayList<Integer> k = initKey(key,size);
        ArrayList<Character> ch = new ArrayList<Character>();
        for(char c : toDecrypt.toCharArray()) {
            ch.add(c);
        }
        result = doDecrypt(alpha,rAlpha,ch,k);
        return result;
    }

    //Decryption Function
    public String doDecrypt (Map<Character,Integer> alpha, Map<Integer,Character> rAlpha, ArrayList<Character> c, ArrayList<Integer> key) {
        String ret = "";
        int value = 0;
        char x = 'x';
        boolean smallCase = false;
        boolean put = false;
        ArrayList<Character> temp = new ArrayList<Character>();
        for(int i = 0 ; i < c.size(); i++) {
            if(Character.isLetter(c.get(i))) {
                if(Character.isLowerCase(c.get(i))) {
                    value = alpha.get(Character.toUpperCase(c.get(i)));
                    smallCase = true;
                    put = false;
                }else {
                    value = alpha.get(c.get(i));
                    put = false;
                }
            }else {
                temp.add(c.get(i));
                put = true;
            }

            if(put == false) {
                value = calculate(value,key.get(i));
                x = rAlpha.get(value);

                if(smallCase == true) {
                    x = Character.toLowerCase(x);
                    smallCase = false;
                }
                temp.add(x);
            }
        }

        for(char y: temp) {
            ret += y;
        }

        return ret;
    }

    //Calculating value for a given plain text
    public int calculate(int x,int key) {
        int ret = 0;

        ret = (x - key) % 26;

        if(ret < 0) {
            ret = 26 - Math.abs(ret);
        }

        return ret;
    }

    //Initialize the keys using the key provided
    public ArrayList<Integer> initKey (int key,int size){
        ArrayList<Integer> ret = new ArrayList<Integer>();
        int temp = 0;
        for(int i = 0 ; i < size ; i++) {
            if(i == 0) {
                temp = ((int)Math.pow(key, 2) + 1) % 26;
                ret.add(temp);
            }else {
                temp = ((int)Math.pow(ret.get(i-1), 2) + (i+1)) % 26;
                ret.add(temp);
            }
        }

        return ret;
    }

}
