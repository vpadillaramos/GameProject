package com.vpr.pruebatiles.handlers;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;

public class MyGameInputProcessor extends InputAdapter {

    public boolean keyDown(int k){
        /*if(k == Keys.UP)
            GameKeys.setKey(GameKeys.JUMP, true);
        if(k == Keys.LEFT)
            GameKeys.setKey(GameKeys.WALK_LEFT, true);
        if(k == Keys.RIGHT)
            GameKeys.setKey(GameKeys.WALK_RIGHT, true);*/

        switch (k){
            case Keys.UP:
                GameKeys.setKey(GameKeys.keyBindings.get("JUMP"), true);
                break;
            case Keys.LEFT:
                GameKeys.setKey(GameKeys.keyBindings.get("WALK_LEFT"), true);
                break;
            case Keys.RIGHT:
                GameKeys.setKey(GameKeys.keyBindings.get("WALK_RIGHT"), true);
                break;
            case Keys.E:
                GameKeys.setKey(GameKeys.keyBindings.get("OPEN_WINDOW"), true);
                break;
            case Keys.ESCAPE:
                GameKeys.setKey(GameKeys.keyBindings.get("CLOSE_WINDOW"), true);
                break;
            default:
                    break;
        }

        return true;
    }

    public boolean keyUp(int k){
        /*if(k == Keys.UP)
            GameKeys.setKey(GameKeys.JUMP, false);
        if(k == Keys.LEFT)
            GameKeys.setKey(GameKeys.WALK_LEFT, false);
        if(k == Keys.RIGHT)
            GameKeys.setKey(GameKeys.WALK_RIGHT, false);*/

        switch (k){
            case Keys.UP:
                GameKeys.setKey(GameKeys.keyBindings.get("JUMP"), false);
                break;
            case Keys.LEFT:
                GameKeys.setKey(GameKeys.keyBindings.get("WALK_LEFT"), false);
                break;
            case Keys.RIGHT:
                GameKeys.setKey(GameKeys.keyBindings.get("WALK_RIGHT"), false);
                break;
            case Keys.E:
                GameKeys.setKey(GameKeys.keyBindings.get("OPEN_WINDOW"), false);
                break;
            case Keys.ESCAPE:
                GameKeys.setKey(GameKeys.keyBindings.get("CLOSE_WINDOW"), false);
                break;
            default:
                break;
        }

        return true;
    }
}
