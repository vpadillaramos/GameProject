package com.vpr.pruebatiles.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.vpr.pruebatiles.entities.Player;
import com.vpr.pruebatiles.handlers.GameKeys;
import com.vpr.pruebatiles.handlers.MyContactListener;
import com.vpr.pruebatiles.handlers.MyGameInputProcessor;
import com.vpr.pruebatiles.managers.*;
import com.vpr.pruebatiles.util.Constantes;

import java.text.DecimalFormat;

public class DungeonState extends GameState {

    // Attributes
    public enum State {
        PLAYING, PAUSE
    }
    public HubState.State state;

    // Player
    public Player player;

    // Managers
    private MyGameInputProcessor inputProcessor;
    private LevelManager levelManager;
    private SkinManager skinManager;
    private Box2dManager b2dManager;
    private SpriteManager spriteManager;

    // Listeners, managers
    private InputMultiplexer multiplexer;
    private MyContactListener contactListener;

    // Constructor
    public DungeonState(GameStateManager gsm) {
        super(gsm);

        // Initial state
        state = HubState.State.PLAYING;

        // Listeners
        contactListener = new MyContactListener();

        // Managers
        inputProcessor = new MyGameInputProcessor();
        skinManager = new SkinManager();
        b2dManager = new Box2dManager(contactListener);
        levelManager = new LevelManager(b2dManager.world, Constantes.dungeonMap);

        // player initialization
        player = new Player(Constantes.ninjaIdle, b2dManager.world, levelManager.getPlayerSpawnPoint(), contactListener, inputProcessor);
        spriteManager = new SpriteManager(player);

        // Add input processors
        initMultiplexer();
    }

    @Override
    public void update(float dt) {
        batch.setProjectionMatrix(cameraManager.camera.combined);
        manageInput(dt);

        b2dManager.upddate();
        cameraUpdate();
        levelManager.update(cameraManager.camera);
    }

    @Override
    public void render(float dt) {
        Gdx.gl.glClearColor(1, 0, 1, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        levelManager.render();
        b2dManager.render(cameraManager.camera);

        batch.begin();
        player.draw(batch);
        batch.end();

        skinManager.render(dt);
    }

    @Override
    public void dispose() {
        b2dManager.dispose();
        levelManager.dispose();
        skinManager.dispose();
    }

    private void initMultiplexer(){
        multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(inputProcessor);
        multiplexer.addProcessor(skinManager.stage);
        Gdx.input.setInputProcessor(multiplexer);
    }

    public void cameraUpdate(){
        //System.out.println(cameraManager.camera.position.x + " - " + cameraManager.camera.position.y);
        //CameraMethods.lerpToTarget(cameraManager.camera, player.getPosition());
        //cameraManager.lerpToTarget(player.getPosition());
        cameraManager.update();
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
