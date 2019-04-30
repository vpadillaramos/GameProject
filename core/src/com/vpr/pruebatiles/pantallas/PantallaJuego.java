package com.vpr.pruebatiles.pantallas;
/*
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.vpr.pruebatiles.entities.Player;
import com.vpr.pruebatiles.managers.R;

public class PantallaJuego implements Screen {

    // Variables
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;

    private Player player;

    // Constructor
    public PantallaJuego(){

    }

    @Override
    public void show() {
        map = new TmxMapLoader().load("levels/level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        camera = new OrthographicCamera();

        player = new Player(R.getSprite("player/idle"));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 1, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.setView(camera);
        renderer.render();
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.position.set(width/2, height/2, 0);
        camera.update();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
    }
}
*/

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.vpr.pruebatiles.managers.R;
import com.vpr.pruebatiles.util.TiledObjectUtil;

import static com.vpr.pruebatiles.util.Constantes.PPM;

public class PantallaJuego implements Screen {
    /**
     * NOTES
     * Scaling: pixels per meter. If a variable from the world is giving to it, you have to divide it by PPM,
     * but if you are getting the variable, you have tu multiply
     */

    // Constantes
    private final int SPEED = 5; //mps
    private final int JUMPING_FORCE = 300;
    private final float SCALE = 2.0f;

    // fisicas
    private float gravity = -9.8f;

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



    @Override
    public void show() {
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        batch = new SpriteBatch();

        // camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, width / SCALE, height / SCALE);

        // world
        world = new World(new Vector2(0, gravity), false);
        b2ddr = new Box2DDebugRenderer();

        // bodies
        player = createBox(40, 100, 32, 32, false);
        platform = createBox(0, 0, 64, 32, true);

        // map
        map = new TmxMapLoader().load("levels/level1.tmx");
        tmr = new OrthogonalTiledMapRenderer(map);
        TiledObjectUtil.parseTiledObjectLayer(world, map.getLayers().get("collisions").getObjects());
    }

    @Override
    public void render(float delta) {
        update(Gdx.graphics.getDeltaTime());

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(R.getRegion("idle"), player.getPosition().x, player.getPosition().y);
        batch.end();

        tmr.render();

        b2ddr.render(world, camera.combined.scl(PPM));
    }

    private void update(float dt) {
        // stepping updates objects through the time
        world.step(1 / 60f, 6, 2); // 60fps, 6, 2 normalized values


        manageInput(dt);
        cameraUpdate(dt);
        tmr.setView(camera);
        batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width / SCALE, height / SCALE);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        world.dispose();
        b2ddr.dispose();
        batch.dispose();
        tmr.dispose();
        map.dispose();
    }

    private Body createBox(int x, int y, float width, float height, boolean isStatic) {
        Body playerBody;
        // properties and physics of the player's body
        BodyDef def = new BodyDef();

        if(isStatic)
            def.type = BodyDef.BodyType.StaticBody;
        else
            def.type = BodyDef.BodyType.DynamicBody;

        def.position.set(x / PPM, y / PPM);
        def.fixedRotation = true;
        // initialize body
        playerBody = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        // this method creates a box from the center, in this case a 32x32 box
        shape.setAsBox(width / 2 / PPM, height / 2 / PPM); // giving variables to world

        playerBody.createFixture(shape, 1.0f); // density
        shape.dispose();

        return playerBody;
    }

    public void cameraUpdate(float dt){
        Vector3 position = camera.position;

        // camera interpolation formula -> a + (b - a) * lerp
        // a = camera position, b = target position
        position.x = camera.position.x + (player.getPosition().x * PPM - camera.position.x) * .1f; // getting variable from world
        position.y = camera.position.y + (player.getPosition().y * PPM - camera.position.y) * .1f;
        camera.position.set(position);

        camera.update();
    }

    public void manageInput(float dt){

        int horizontalForce = 0;

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
            horizontalForce -= 1;
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            horizontalForce += 1;
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP))
            player.applyForceToCenter(0, JUMPING_FORCE, false); // apply this force tu the body, forceX, forceY, wake

        player.setLinearVelocity(horizontalForce * SPEED, player.getLinearVelocity().y);
    }
}