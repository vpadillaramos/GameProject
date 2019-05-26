package com.vpr.pruebatiles.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.vpr.pruebatiles.Application;
import com.vpr.pruebatiles.managers.CameraManager;
import com.vpr.pruebatiles.managers.GameStateManager;

public abstract class GameState {

    // Attributes

    // References
    protected GameStateManager gsm;
    protected CameraManager cameraManager;
    protected Application app;
    protected SpriteBatch batch;

    protected FitViewport viewport;

    // Constructor used for playing states
    public GameState(GameStateManager gsm, String map){
        this.gsm = gsm;
        this.app = gsm.application();
        batch = app.getSpriteBatch();
        cameraManager = app.cameraManager;
        //camera = app.cameraManager.camera;
        //viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), cameraManager.camera);
    }

    public GameState(GameStateManager gsm){
        this.gsm = gsm;
        this.app = gsm.application();
        batch = app.getSpriteBatch();
        cameraManager = app.cameraManager;
    }

    // Constructor used for loading states
    public GameState(GameStateManager gsm, GameStateManager.State nextState){
        this.gsm = gsm;
        this.app = gsm.application();
        batch = app.getSpriteBatch();
        cameraManager = app.cameraManager;
    }

    public void resize(int w, int h){
        int thingsWidth = 16;
        int amountOfThings = 16;
        int width = thingsWidth * amountOfThings;
        int height = width * (w / h);
        cameraManager.camera.setToOrtho(false, width, height);
        //cameraManager.camera.setToOrtho(false, w, h);
    }

    public abstract void update(float dt);
    public abstract void render(float dt);
    public abstract void dispose();
}









