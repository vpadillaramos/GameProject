package com.vpr.pruebatiles.managers;

import com.vpr.pruebatiles.Application;
import com.vpr.pruebatiles.states.*;

import java.util.Stack;

public class GameStateManager {

    // Attributes
    private final Application app;
    private Stack<GameState> states;
    public enum State {
        LOADING_RES, SPLASH, MAIN_MENU, PLAY, LOADING_DUNGEON, PLAYING_DUNGEON
    }

    // Constructor
    public GameStateManager(final Application app){
        this.app = app;
        this.states = new Stack<GameState>();
        this.setState(State.LOADING_RES);
    }

    // Methods
    public void update(float dt){
        states.peek().update(dt);
    }

    public void render(float dt){
        states.peek().render(dt);
    }

    public void resize(int w, int h){
        states.peek().resize(w, h);
    }

    public void dispose(){
        for(GameState gs : states){
            gs.dispose();
        }

        states.clear();
    }

    public void setState(State state){
        if(states.size() > 0)
            states.pop().dispose();

        states.push(getState(state));
    }

    public GameState getState(State state){
        switch (state){
            case LOADING_RES:
                return new LoadingState(this);
            case SPLASH:
                return new SplashState(this);
            case PLAY:
                return new HubState(this);
            case LOADING_DUNGEON:
                return new LoadingDungeonState(this);
            case PLAYING_DUNGEON:
                return new DungeonState(this);
        }

        return null;
    }

    public Application application(){
        return app;
    }
}
