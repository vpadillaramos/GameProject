package com.vpr.pruebatiles.managers;

public class CharacterState {

    // Care!!! The order is important
    public enum State {
        IDLE, JUMP_LEFT, JUMP_RIGHT, FALL_LEFT, FALL_RIGHT, WALK_LEFT, WALK_RIGHT, INTERACT, CLOSE_WINDOW
    }
}
