package com.vpr.pruebatiles.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.vpr.pruebatiles.Application;
import com.vpr.pruebatiles.managers.GameStateManager;

public abstract class GameState {

    // Attributes
    protected GameStateManager gsm;
    protected Application app;
    protected SpriteBatch batch;
    protected OrthographicCamera camera;

    // Constructor
    public GameState(GameStateManager gsm){
        this.gsm = gsm;
        this.app = gsm.application();
        batch = app.getSpriteBatch();
        camera = app.getCamera();
    }

    public void resize(int w, int h){
        camera.setToOrtho(false, w, h);
    }

    public abstract void update(float dt);
    public abstract void render();
    public abstract void dispose();
}









