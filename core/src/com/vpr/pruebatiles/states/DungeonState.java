package com.vpr.pruebatiles.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.vpr.pruebatiles.entities.Player;
import com.vpr.pruebatiles.entities.Room;
import com.vpr.pruebatiles.handlers.MyContactListener;
import com.vpr.pruebatiles.handlers.MyInputManager;
import com.vpr.pruebatiles.managers.*;
import com.vpr.pruebatiles.util.Constantes;

public class DungeonState extends GameState {

    // Attributes
    public enum State {
        PLAYING, PAUSE
    }
    public HubState.State state;

    // Player
    public Player player;
    public Room[] dungeonRooms;
    public int currentRoom;

    // Managers
    //private MyGameInputProcessor inputProcessor;
    private MyInputManager inputManager;
    private LevelManager levelManager;
    private SkinManager skinManager;
    private Box2dManager b2dManager;
    private SpriteManager spriteManager;

    // Listeners
    private InputMultiplexer multiplexer;
    private MyContactListener contactListener;

    // HUD
    private Image healthBar;
    private Label coinsLabel;

    // Constructor
    public DungeonState(GameStateManager gsm) {
        super(gsm);

        dungeonRooms = new Room[2];
        //GameKeys.resetKeysStatus();

        // Initial state
        state = HubState.State.PLAYING;

        // Managers, listeners
        contactListener = new MyContactListener();
        //inputProcessor = new MyGameInputProcessor();
        inputManager = new MyInputManager();
        skinManager = new SkinManager();
        b2dManager = new Box2dManager(contactListener);


        // Add input processors
        initMultiplexer();


        // generate rooms
        generateRandomDungeon();
        currentRoom = 0;


        // load the first room
        levelManager = new LevelManager(b2dManager.world, dungeonRooms[currentRoom].mapName);
        player = new Player(gsm.playerType, b2dManager.world, levelManager.getPlayerSpawnPoint(), contactListener, inputManager);
        spriteManager = new SpriteManager(player);


        // HUD
        initHud();
    }

    @Override
    public void update(float dt) {
        batch.setProjectionMatrix(cameraManager.camera.combined);
        manageInput(dt);
        spriteManager.update(dt);
        b2dManager.update();
        cameraUpdate();
        levelManager.update(cameraManager.camera);

        updateHud();
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

    private void updateHud(){
        healthBar.setWidth(cameraManager.camera.viewportWidth * player.health);
        coinsLabel.setText(player.coins + "$");
    }

    private void initHud(){
        healthBar = new Image(new TextureRegion(R.getRegion(Constantes.healthBar)));
        healthBar.setPosition(0, 0);
        healthBar.setHeight(16);
        skinManager.stage.addActor(healthBar);

        coinsLabel = new Label(player.coins + "$", skinManager.skin);
        coinsLabel.setPosition(0 + 20, cameraManager.camera.viewportHeight * 1.7f);
        coinsLabel.setColor(new Color(.83f, .62f, .21f, 1));
        skinManager.stage.addActor(coinsLabel);
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
        //multiplexer.addProcessor(inputProcessor);
        multiplexer.addProcessor(inputManager);
        multiplexer.addProcessor(skinManager.stage);
        Gdx.input.setInputProcessor(multiplexer);
    }

    public void cameraUpdate(){
        cameraManager.lerpToTarget(player.getPosition());
        cameraManager.update(levelManager.mapPixelWidth, levelManager.mapPixelHeight);
    }

    public void manageInput(float dt){

        cameraManager.manageInput(player);

        switch (state){
            case PLAYING:
                cameraManager.manageInput(player);
                player.keyPlayingInput(dt);
                manageRoomsInteraction();
                break;
            case PAUSE:
                player.keyPauseInput(dt);
                break;
        }
    }

    private void manageRoomsInteraction(){
        if(player.isPlayerInNextRoom()){
            currentRoom++;

            b2dManager.prepareForNewMap();
            levelManager.loadMap(Constantes.levelsFolder + "normal_room_"+(currentRoom+1)+".tmx");
            contactListener.resetFixtures();
            player.setPosition(levelManager.getPlayerSpawnPoint());
        }
    }

    private void generateRandomDungeon(){
        dungeonRooms[0] = new Room(Constantes.levelsFolder + "normal_room_1.tmx", Constantes.RoomType.normal);
        dungeonRooms[1] = new Room(Constantes.levelsFolder + "normal_room_2.tmx", Constantes.RoomType.normal);
    }
}
