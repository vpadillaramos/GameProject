package com.vpr.pruebatiles.handlers;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;

public class MyGameInputProcessor extends InputAdapter {

    public boolean keyDown(int k){
        if(k == Keys.UP)
            GameKeys.setKey(GameKeys.JUMP, true);
        if(k == Keys.LEFT)
            GameKeys.setKey(GameKeys.WALK_LEFT, true);
        if(k == Keys.RIGHT)
            GameKeys.setKey(GameKeys.WALK_RIGHT, true);

        return true;
    }

    public boolean keyUp(int k){
        if(k == Keys.UP)
            GameKeys.setKey(GameKeys.JUMP, false);
        if(k == Keys.LEFT)
            GameKeys.setKey(GameKeys.WALK_LEFT, false);
        if(k == Keys.RIGHT)
            GameKeys.setKey(GameKeys.WALK_RIGHT, false);

        return true;
    }
}
