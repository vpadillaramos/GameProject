package com.vpr.pruebatiles.handlers;

public class GameKeys {

    // Constants
    public static final int NUM_KEYS = 3;

    // Variables
    public static boolean[] keys;
    public static boolean[] pKeys; // preview keys

    public static final int JUMP = 0;
    public static final int WALK_LEFT = 1;
    public static final int WALK_RIGHT = 2;


    static {
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
