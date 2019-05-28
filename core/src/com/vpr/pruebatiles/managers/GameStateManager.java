package com.vpr.pruebatiles.managers;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.vpr.pruebatiles.Application;
import com.vpr.pruebatiles.states.*;
import com.vpr.pruebatiles.util.Constantes;

import java.util.HashMap;
import java.util.Stack;

public class GameStateManager {

    // Attributes
    private final Application app;
    private Stack<GameState> states;
    public enum State {
        LOADING_RES, SPLASH, MAIN_MENU, CONFIGURATION_MENU, CHARACTER_SELECTOR, PLAYING_HUB, LOADING_DUNGEON, PLAYING_DUNGEON,
    }
    public HashMap<String, TextureRegion> charactersRegion;
    public int currentCharacter;
    public Constantes.CharacterType playerType;
    public Music music;

    // Constructor
    public GameStateManager(final Application app){
        this.app = app;
        this.states = new Stack<GameState>();
        charactersRegion = new HashMap<String, TextureRegion>();
        currentCharacter = 0;
        playerType = Constantes.CharacterType.ninja;
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
                return new LoadingState(this, State.SPLASH);
            case SPLASH:
                return new SplashState(this, State.MAIN_MENU);
            case MAIN_MENU:
                return new MainMenuState(this);
            case CONFIGURATION_MENU:
                return new ConfigurationState(this, State.MAIN_MENU);
            case CHARACTER_SELECTOR:
                return new CharacterSelectorState(this);
            case PLAYING_HUB:
                return new HubState(this, Constantes.hubMap, music);
            case LOADING_DUNGEON:
                return new LoadingDungeonState(this, State.PLAYING_DUNGEON);
            case PLAYING_DUNGEON:
                music = R.getMusica(Constantes.MUSIC + "track1.mp3");
                return new DungeonState(this, music);
        }

        return null;
    }

    public Application application(){
        return app;
    }
}
