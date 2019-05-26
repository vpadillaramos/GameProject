package com.vpr.pruebatiles.handlers;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.Array;

public class MyInputManager implements InputProcessor {


    // Constants


    // Attributes
    public Array<KeyState> keyStates;
    //public Array<TouchState> touchStates;

    // Constructor
    public MyInputManager(){
        keyStates = new Array<KeyState>();

        for(int i = 0; i < 256; i++){
            keyStates.add(new KeyState(i));
        }
    }

    public void update(){
        for(int i = 0; i < keyStates.size; i++){
           keyStates.get(i).pressed = false;
           keyStates.get(i).up = false;
        }
    }


    @Override
    public boolean keyDown(int keycode) {

        keyStates.get(keycode).pressed = true;
        keyStates.get(keycode).down = true;

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {

        keyStates.get(keycode).down = false;
        keyStates.get(keycode).up = true;

        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    public boolean isKeyPressed(int keycode){
        return keyStates.get(keycode).pressed;
    }

    public boolean isKeyDown(int keycode){
        return keyStates.get(keycode).down;
    }
    public boolean isKeyUp(int keycode){
        return keyStates.get(keycode).up;
    }


    // Methods for phone screens
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }


    public class InputState {
        // Attributes
        public boolean pressed = false;
        public boolean down = false;
        public boolean up = false;
    }

    public class KeyState extends InputState {
        public int key;

        public KeyState(int keyCode) {
            this.key = keyCode;
        }
    }

    // TODO phone input
    public class TouchState extends InputState {

    }
}


