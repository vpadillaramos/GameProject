package com.vpr.pruebatiles;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.vpr.pruebatiles.managers.CameraManager;
import com.vpr.pruebatiles.managers.GameStateManager;
import com.vpr.pruebatiles.managers.SkinManager;

import static com.vpr.pruebatiles.util.Constantes.SCALE;


public class Application extends ApplicationAdapter {

    /**
     * NOTES
     * Scaling: pixels per meter. If a variable from the world is giving to it, you have to divide it by PPM,
     * but if you are getting the variable, you have tu multiply
     */

    // Atributos
    private SpriteBatch batch;

    public CameraManager cameraManager;
    public GameStateManager gsm;


    @Override
    public void create() {

        batch = new SpriteBatch();
        // Managers
        cameraManager = new CameraManager();
        gsm = new GameStateManager(this);
    }

    @Override
    public void render() {
        float dt = Gdx.graphics.getDeltaTime();
        gsm.update(dt);
        gsm.render(dt);
    }

    @Override
    public void resize(int width, int height) {

        gsm.resize((int) (width / SCALE), (int) (height / SCALE));
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        gsm.dispose();
        batch.dispose();
    }

    public SpriteBatch getSpriteBatch(){
        return batch;
    }

    /*public OrthographicCamera getCamera(){
        return camera;
    }*/
}
