package com.vpr.pruebatiles.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.vpr.pruebatiles.managers.GameStateManager;
import com.vpr.pruebatiles.managers.R;
import com.vpr.pruebatiles.util.BodyCreator;
import com.vpr.pruebatiles.util.CameraMethods;
import com.vpr.pruebatiles.util.TiledObjectUtil;

import static com.vpr.pruebatiles.util.Constantes.PPM;

public class PlayState extends GameState {

    // Constants
    private final int SPEED = 3; //mps
    private final int JUMPING_FORCE = 450;
    private final float SCALE = 2.0f;

    // Physics
    private float gravity = -10f;


    // Attributes
    private Box2DDebugRenderer b2dr;
    private World world;
    private Body player;

    // Map
    private OrthogonalTiledMapRenderer tmr;
    private TiledMap map;
    private float mapWidth, mapHeight;
    private Vector3 startOfMap, endOfMap;


    public PlayState(GameStateManager gsm) {
        super(gsm);

        // Initialization
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        // world
        world = new World(new Vector2(0, gravity), false);
        b2dr = new Box2DDebugRenderer();
        b2dr.SHAPE_STATIC.set(1, 1, 1, 0); // static bodies' color white
        b2dr.SHAPE_AWAKE.set(1, 0, 0, 0); // dynamic bodie's color red

        // TODO bodies
        player = BodyCreator.createBox(world, 40, 100, 32, 32, false);

        // TODO map
        map = new TmxMapLoader().load("levels/level1.tmx");
        tmr = new OrthogonalTiledMapRenderer(map);
        TiledObjectUtil.parseTiledObjectLayer(world, map.getLayers().get("collisions").getObjects());

        // getting dimensions of the map
        int mapTilesWidth, mapTilesHeight; // amount of tiles (width/height)
        int tilePixelWidth, tilePixelHeight; // pixels of a tile
        mapTilesWidth = map.getProperties().get("width", Integer.class);
        mapTilesHeight = map.getProperties().get("height", Integer.class);
        tilePixelWidth = map.getProperties().get("tilewidth", Integer.class);
        tilePixelHeight = map.getProperties().get("tileheight", Integer.class);

        mapWidth = mapTilesWidth * tilePixelWidth;
        mapHeight = mapTilesHeight * tilePixelHeight;
        startOfMap = new Vector3(camera.viewportWidth / SCALE, camera.viewportHeight / SCALE, 0);
        endOfMap = new Vector3(startOfMap.x + mapWidth, startOfMap.y + mapHeight, 0);

        //camera.position.set(endOfMap.x - camera.viewportWidth, endOfMap.y - mapHeight, endOfMap.z);
        //camera.position.set(startOfMap);

    }

    @Override
    public void update(float dt) {
        // stepping updates objects through the time
        world.step(1 / 60f, 6, 2); // 60fps, 6, 2 normalized values

        manageInput(dt);
        cameraUpdate();
        tmr.setView(camera);
        batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(R.getRegion("idle"), player.getPosition().x, player.getPosition().y);
        batch.end();

        tmr.render();

        b2dr.render(world, camera.combined.scl(PPM));

    }

    @Override
    public void dispose() {
        b2dr.dispose();
        world.dispose();
        tmr.dispose();
        map.dispose();
    }

    public void cameraUpdate(){

        CameraMethods.lerpToTarget(camera, player.getPosition());
        CameraMethods.setCameraBounds(camera, startOfMap.x, startOfMap.y,
                mapWidth - startOfMap.x * 2, mapHeight - startOfMap.y * 2);
        //CameraMethods.lockToTarget(camera, player.getPosition().scl(PPM));
        //viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //camera.zoom = 1f;
        //tmr.setView((OrthographicCamera) viewport.getCamera());

    }

    public void manageInput(float dt){

        int horizontalForce = 0;
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
            horizontalForce -= SPEED;
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            horizontalForce += SPEED;
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            //player.applyLinearImpulse(new Vector2(0, 6f), player.getWorldCenter(), true);
            player.applyForceToCenter(0, JUMPING_FORCE, false); // apply this force tu the body, forceX, forceY, wake
        }

        if(Gdx.input.isKeyPressed(Input.Keys.A))
            camera.position.x -= 5;
        if(Gdx.input.isKeyPressed(Input.Keys.D))
            camera.position.x += 5;
        if(Gdx.input.isKeyPressed(Input.Keys.W))
            camera.position.y += 5;
        if(Gdx.input.isKeyPressed(Input.Keys.S))
            camera.position.y -= 5;

        if(Gdx.input.isKeyPressed(Input.Keys.Z))
            camera.zoom -= .1f;
        if(Gdx.input.isKeyPressed(Input.Keys.X))
            camera.zoom += .1f;


        player.setLinearVelocity(horizontalForce * SPEED, player.getLinearVelocity().y);
    }
}
