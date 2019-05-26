package com.vpr.pruebatiles.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.vpr.pruebatiles.entities.Player;
import com.vpr.pruebatiles.entities.Room;
import com.vpr.pruebatiles.handlers.GameKeys;
import com.vpr.pruebatiles.handlers.MyContactListener;
import com.vpr.pruebatiles.handlers.MyGameInputProcessor;
import com.vpr.pruebatiles.managers.*;
import com.vpr.pruebatiles.util.Constantes;

/*public class DungeonState extends GameState {

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

        b2dManager.update();
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
        player.dispose();
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
        //if(player.inShop) {
        //    shopWindow.show(true);
        //    state = State.PAUSE;
        //}
    }
}*/

public class DungeonState extends GameState {

    // Attributes
    public enum State {
        PLAYING, PAUSE
    }
    public HubState.State state;

    // Player
    public Player player;
    public Room[] dungeonRooms;

    // Managers
    private MyGameInputProcessor inputProcessor;
    private LevelManager levelManager;
    private SkinManager skinManager;
    private Box2dManager b2dManager;
    private SpriteManager spriteManager;

    // Listeners
    private InputMultiplexer multiplexer;
    private MyContactListener contactListener;

    // Constructor
    public DungeonState(GameStateManager gsm, String map) {
        super(gsm, map);

        dungeonRooms = new Room[2];
        GameKeys.resetKeysStatus();

        // Initial state
        state = HubState.State.PLAYING;

        // Managers, listeners
        contactListener = new MyContactListener();
        inputProcessor = new MyGameInputProcessor();
        skinManager = new SkinManager();
        b2dManager = new Box2dManager(contactListener);


        // Add input processors
        initMultiplexer();


        // generate rooms
        generateRandomDungeon();


        // load the first room
        levelManager = new LevelManager(b2dManager.world, dungeonRooms[0].mapName);
        player = new Player(Constantes.ninjaIdle, b2dManager.world, levelManager.getPlayerSpawnPoint(), contactListener, inputProcessor);
        spriteManager = new SpriteManager(player);

    }

    @Override
    public void update(float dt) {
        batch.setProjectionMatrix(cameraManager.camera.combined);
        manageInput(dt);

        b2dManager.update();
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
        skinManager.dispose();
        b2dManager.dispose();
        levelManager.dispose();
        player.dispose();
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
        cameraManager.lerpToTarget(player.getPosition());
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
        manageEnvironmentInteraction();
    }

    private void managePlayerInput(){
        player.checkKeys();
        GameKeys.update();
    }

    private void manageEnvironmentInteraction(){

        if(player.isPlayerInNextRoom()){
            System.out.println("environment");

            b2dManager.prepareForNewMap();
            levelManager.loadMap(Constantes.levelsFolder + "normal_room_2.tmx");
            player.setPosition(levelManager.getPlayerSpawnPoint());

        }
    }

    private void generateRandomDungeon(){
        dungeonRooms[0] = new Room(Constantes.levelsFolder + "normal_room_1.tmx", Constantes.RoomType.normal);
    }
}
