package com.vpr.pruebatiles.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.vpr.pruebatiles.Application;
import com.vpr.pruebatiles.managers.GameStateManager;

public abstract class GameState {

    // Attributes
    protected GameStateManager gsm;
    protected Application app;
    protected SpriteBatch batch;
    protected OrthographicCamera camera;
    protected FitViewport viewport;

    // Constructor
    public GameState(GameStateManager gsm){
        this.gsm = gsm;
        this.app = gsm.application();
        batch = app.getSpriteBatch();
        camera = app.getCamera();
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
    }

    public void resize(int w, int h){
        camera.setToOrtho(false, w, h);
    }

    public abstract void update(float dt);
    public abstract void render(float dt);
    public abstract void dispose();
}









