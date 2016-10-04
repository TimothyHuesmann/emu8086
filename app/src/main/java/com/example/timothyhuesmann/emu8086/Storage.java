package com.example.timothyhuesmann.emu8086;

/**
 * Created by timothyhuesmann on 9/27/16.
 */

public abstract class Storage {
    protected String name;
    protected int size;

    protected String toHex(String val){
        val = val.toLowerCase();
        try{
            return Integer.toHexString(Integer.parseInt(val));
        }catch(Exception e) {
            char suffix = val.charAt(val.length() - 1);
            if (suffix == 'd' && !val.startsWith("0x")) {
                return Integer.toHexString(Integer.parseInt(val.substring(0, val.length() - 1)));
            } else if (suffix == 'b' && !val.startsWith("0x")) {
                return Integer.toHexString(Integer.parseInt(val.substring(0, val.length() - 1), 2));
            } else if (suffix == 'o') {
                return Integer.toHexString(Integer.parseInt(val.substring(0, val.length() - 1), 8));
            } else {
                val = val.replace("h", "").trim();
                val = val.replace("x", "").trim();
                return val;
            }
        }
    }
}
