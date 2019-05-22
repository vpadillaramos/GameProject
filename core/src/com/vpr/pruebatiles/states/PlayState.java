package com.vpr.pruebatiles.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.kotcrab.vis.ui.VisUI;
import com.rafaskoberg.gdx.typinglabel.TypingLabel;
import com.vpr.pruebatiles.entities.Player;
import com.vpr.pruebatiles.handlers.GameKeys;
import com.vpr.pruebatiles.handlers.MyContactListener;
import com.vpr.pruebatiles.handlers.MyGameInputProcessor;
import com.vpr.pruebatiles.managers.*;
import com.vpr.pruebatiles.util.CameraMethods;
import com.vpr.pruebatiles.util.Constantes;
import com.vpr.pruebatiles.windows.ShopWindow;

import static com.vpr.pruebatiles.util.Constantes.PPM;

public class PlayState extends GameState {

    // Constants

    // Atributes
    public enum State {
        PLAYING, PAUSE
    }
    public State state;

    private float cameraZoom = .3f;

    // Physics
    private float gravity = -9.8f;

    // World
    /*private Box2DDebugRenderer b2dr;
    private World world;*/

    // Player
    public Player player;

    // Managers
    private MyGameInputProcessor inputProcessor;
    private LevelManager levelManager;
    private SkinManager skinManager;
    private Box2dManager b2dManager;

    // Listeners, managers
    private InputMultiplexer multiplexer;
    private MyContactListener contactListener;

    // Text printing
    private TypingLabel typingLabel;

    // Shop Test
    //private Body shop;
    private ShopWindow shopWindow;


    public PlayState(GameStateManager gsm) {
        super(gsm);

        // Initial state
        state = State.PLAYING;

        // Listeners
        contactListener = new MyContactListener();

        // Managers
        inputProcessor = new MyGameInputProcessor();
        skinManager = new SkinManager();
        b2dManager = new Box2dManager(contactListener);
        levelManager = new LevelManager(b2dManager.world, Constantes.hubMap);

        // player initialization
        player = new Player(b2dManager.world, levelManager.getPlayerSpawnPoint(), contactListener, inputProcessor);

        
        // Text test
        String text = "{SPEED=0.5}Hola, que tal, {COLOR=RED}esto{WAIT} es una prueba de {SHAKE}Typing label";
        typingLabel = new TypingLabel(text, skinManager.skin);
        typingLabel.setWrap(true);
        typingLabel.setWidth(100);
        typingLabel.setPosition((cameraManager.camera.viewportWidth / 2),
                (cameraManager.camera.viewportHeight / 2));
        skinManager.stage.addActor(typingLabel);


        // Shop test
        shopWindow = new ShopWindow(this, skinManager.stage, skinManager.skin, "Perkadona");

        // Add input processors
        multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(inputProcessor);
        multiplexer.addProcessor(skinManager.stage);
        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    public void update(float dt) {


        if(player.openShop()) {
            shopWindow.show(true);
            pause();
        }

        switch (state){
            case PAUSE:
                if(player.closeShop()) {
                    shopWindow.show(false);
                    playing();
                }
                break;
            case PLAYING:
                batch.setProjectionMatrix(cameraManager.camera.combined);
                manageInput(dt);

                b2dManager.upddate();
                cameraUpdate();
                levelManager.update(cameraManager.camera);

                break;

            default:

                break;
        }
    }

    @Override
    public void render(float dt) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        levelManager.render();

        batch.begin();
        player.draw(batch);
        batch.end();

        b2dManager.render(cameraManager.camera);
        skinManager.render(dt);
    }

    @Override
    public void dispose() {
        b2dManager.dispose();
        levelManager.dispose();
        skinManager.dispose();
    }

    public void pause(){
        state = State.PAUSE;
    }

    public void playing(){
        state = State.PLAYING;
    }

    public void cameraUpdate(){
        System.out.println(cameraManager.camera.position.x + " - " + cameraManager.camera.position.y);
        CameraMethods.lerpToTarget(cameraManager.camera, player.getPosition());
        //camera.zoom = cameraZoom;
        float startX = cameraManager.camera.viewportWidth * 3; // TODO hardcoded
        float startY = cameraManager.camera.viewportHeight * 3;
        //CameraMethods.setCameraBounds(camera, startX, startY, mapWidth - camera.viewportWidth, mapHeight - camera.viewportHeight);
        //CameraMethods.setCameraBounds(camera, startX, startY, mapWidth, mapHeight);
    }

    public void manageInput(float dt){
        managePlayerInput();
        cameraManager.manageInput(player);
    }


    private void managePlayerInput(){
        player.checkKeys();
        GameKeys.update();
        /*if(player.inShop) {
            shopWindow.show(true);
            state = State.PAUSE;
        }*/
    }
}
