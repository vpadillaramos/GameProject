package com.vpr.pruebatiles;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.vpr.pruebatiles.managers.GameStateManager;
import com.vpr.pruebatiles.managers.R;
import com.vpr.pruebatiles.util.TiledObjectUtil;

import static com.vpr.pruebatiles.util.Constantes.PPM;

public class Application extends ApplicationAdapter {

    /**
     * NOTES
     * Scaling: pixels per meter. If a variable from the world is giving to it, you have to divide it by PPM,
     * but if you are getting the variable, you have tu multiply
     */

    // Constantes
    private final int SPEED = 3; //mps
    private final int JUMPING_FORCE = 450;
    private final float SCALE = 2.0f;

    // fisicas
    private float gravity = -10f;

    // Atributos
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Box2DDebugRenderer b2ddr;
    private World world;
    private Body player;
    private Body platform;

    // Map
    private OrthogonalTiledMapRenderer tmr;
    private TiledMap map;

    private GameStateManager gsm;


    @Override
    public void create() {
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        batch = new SpriteBatch();

        // camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, width / SCALE, height / SCALE);

        gsm = new GameStateManager(this);
    }

    @Override
    public void render() {
        gsm.update(Gdx.graphics.getDeltaTime());
        gsm.render();
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

    public OrthographicCamera getCamera(){
        return camera;
    }
}
