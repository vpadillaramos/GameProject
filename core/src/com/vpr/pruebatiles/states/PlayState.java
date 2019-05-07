package com.vpr.pruebatiles.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.vpr.pruebatiles.managers.GameStateManager;
import com.vpr.pruebatiles.managers.R;
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


    public PlayState(GameStateManager gsm) {
        super(gsm);

        // Initialization
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        // world
        world = new World(new Vector2(0, gravity), false);
        b2dr = new Box2DDebugRenderer();

        // TODO bodies
        player = createBox(40, 100, 32, 32, false);

        // TODO map
        map = new TmxMapLoader().load("levels/level1.tmx");
        tmr = new OrthogonalTiledMapRenderer(map);
        TiledObjectUtil.parseTiledObjectLayer(world, map.getLayers().get("collisions").getObjects());

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

    private Body createBox(int x, int y, float width, float height, boolean isStatic) {
        Body playerBody;
        // properties of the player's body
        BodyDef bdef = new BodyDef();

        if(isStatic)
            bdef.type = BodyDef.BodyType.StaticBody;
        else
            bdef.type = BodyDef.BodyType.DynamicBody;

        bdef.position.set(x / PPM, y / PPM);
        bdef.fixedRotation = true;

        // initialize body
        playerBody = world.createBody(bdef);

        PolygonShape shape = new PolygonShape();
        // this method creates a box from the center, in this case a 32x32 box
        shape.setAsBox(width / 2 / PPM, height / 2 / PPM); // giving variables to world

        // TODO physics
        FixtureDef fdef = new FixtureDef();
        fdef.density = 1.0f;
        fdef.friction = 1.0f;
        fdef.shape = shape;

        //playerBody.createFixture(shape, 1.0f); // density
        playerBody.createFixture(fdef);
        shape.dispose();

        return playerBody;
    }

    public void cameraUpdate(){
        CameraMethods.lerpToTarget(camera, player.getPosition());
        //CameraMethods.lockToTarget(camera, player.getPosition().scl(PPM));
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.zoom = .6f;
        tmr.setView((OrthographicCamera) viewport.getCamera());

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

        player.setLinearVelocity(horizontalForce * SPEED, player.getLinearVelocity().y);
    }
}
