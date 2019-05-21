package com.vpr.pruebatiles.handlers;

import java.util.HashMap;
import java.util.Iterator;

public class GameKeys {

    // Constants
    private static int NUM_KEYS;

    // Variables
    public static boolean[] keys;
    public static boolean[] pKeys; // previous keys

    /*public static final int JUMP = 0;
    public static final int WALK_LEFT = 1;
    public static final int WALK_RIGHT = 2;
    public static final int SHOP_INTERACTION = 3;*/
    public static String[] keyNames = {"JUMP","WALK_LEFT","WALK_RIGHT","OPEN_WINDOW","CLOSE_WINDOW"};
    public static final HashMap<String, Integer> keyBindings = new HashMap<String, Integer>();


    static {
        NUM_KEYS = keyNames.length;
        for(int i = 0; i < NUM_KEYS; i++){
            keyBindings.put(keyNames[i], i);
        }

        keys = new boolean[NUM_KEYS];
        pKeys = new boolean[NUM_KEYS];
    }

    public static void update(){
        for(int i = 0; i < NUM_KEYS; i++){
            pKeys[i] = keys[i];
        }
    }

    public static void setKey(int k, boolean b){
        keys[k] = b;
    }

    public static boolean isDown(int k){
        return keys[k];
    }

    public static boolean isPressed(int k){
        return keys[k] && !pKeys[k];
    }
}
