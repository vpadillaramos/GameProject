package com.vpr.pruebatiles;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.vpr.pruebatiles.managers.CameraManager;
import com.vpr.pruebatiles.managers.GameStateManager;

import static com.vpr.pruebatiles.util.Constantes.SCALE;


public class Application extends ApplicationAdapter {

    /**
     * NOTES
     * Scaling: pixels per meter. If a variable from the world is giving to it, you have to divide it by PPM,
     * but if you are getting the variable, you have tu multiply
     */

    // Atributos
    private SpriteBatch batch;

    //private OrthographicCamera camera;

    private Box2DDebugRenderer b2ddr;
    private World world;
    private Body player;
    private Body platform;

    // Map
    private OrthogonalTiledMapRenderer tmr;
    private TiledMap map;

    public CameraManager cameraManager;
    public GameStateManager gsm;


    @Override
    public void create() {
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        batch = new SpriteBatch();

        // camera
        /*camera = new OrthographicCamera();
        camera.setToOrtho(false, width / SCALE, height / SCALE);*/
        cameraManager = new CameraManager();
        gsm = new GameStateManager(this);
    }

    @Override
    public void render() {
        gsm.update(Gdx.graphics.getDeltaTime());
        gsm.render(Gdx.graphics.getDeltaTime());
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
