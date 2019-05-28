package com.vpr.pruebatiles.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.vpr.pruebatiles.entities.Bat;
import com.vpr.pruebatiles.entities.DemonHouse;
import com.vpr.pruebatiles.entities.Player;
import com.vpr.pruebatiles.entities.Room;
import com.vpr.pruebatiles.handlers.Configuration;
import com.vpr.pruebatiles.handlers.MyContactListener;
import com.vpr.pruebatiles.handlers.MyInputManager;
import com.vpr.pruebatiles.managers.*;
import com.vpr.pruebatiles.util.Constantes;

import java.util.ArrayList;

public class DungeonState extends GameState {

    // Attributes
    public enum State {
        PLAYING, PAUSE
    }
    public HubState.State state;
    public Music music;

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

    // Enemies
    private ArrayList<Bat> bats;
    private long timeSinceLastBat, batSpawnLapse = 2000;
    private ArrayList<DemonHouse> demonHouses;

    // Constructor
    public DungeonState(GameStateManager gsm, Music music) {
        super(gsm);
        this.music = music;

        dungeonRooms = new Room[Constantes.TOTAL_ROOMS];
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

        // init enemies
        bats = new ArrayList<Bat>();
        timeSinceLastBat = TimeUtils.millis();

        // load the first room
        levelManager = new LevelManager(b2dManager.world, dungeonRooms[currentRoom].mapName);
        player = new Player(gsm.playerType, b2dManager.world, levelManager.getPlayerSpawnPoint(), contactListener, inputManager);
        spriteManager = new SpriteManager(player);

        // HUD
        initHud();
        checkMusicConfig();
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
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        levelManager.render();
        b2dManager.render(cameraManager.camera);

        batch.begin();
        player.draw(batch);
        for(Bat bat : bats){
            bat.draw(batch);
            bat.trackPlayer(player);
        }
        batch.end();
        spawnBat();
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
        coinsLabel.setPosition(20, cameraManager.camera.viewportHeight * 1.7f);
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
            bats.clear();
            b2dManager.prepareForNewMap();
            levelManager.loadMap(Constantes.normal_room+(currentRoom+1)+".tmx");
            contactListener.resetFixtures();
            player.setPosition(levelManager.getPlayerSpawnPoint());
        }
    }

    private void spawnBat(){
        System.out.println((TimeUtils.millis() - timeSinceLastBat) + " - " + batSpawnLapse);
        if((TimeUtils.millis() - timeSinceLastBat) > batSpawnLapse){
            System.out.println("Bat spawned");
            bats.add(new Bat(Constantes.demonBat, b2dManager.world, levelManager.getBatSpawnPoint(), contactListener));
            timeSinceLastBat = System.currentTimeMillis();
        }
    }

    private void generateRandomDungeon(){

        for(int i = 0; i < Constantes.TOTAL_ROOMS -2; i++){

            dungeonRooms[i] = new Room(Constantes.normal_room + (i+1) + ".tmx", Constantes.RoomType.normal);
        }

        dungeonRooms[dungeonRooms.length-2] = new Room(Constantes.dropping_room, Constantes.RoomType.normal);
        dungeonRooms[dungeonRooms.length-1] = new Room(Constantes.boss_room_1, Constantes.RoomType.finalBoss);
    }

    private void checkMusicConfig(){
        if(Configuration.isMusicEnabled()){
            music.setLooping(true);
            music.setVolume(1f);
            music.play();
        }
    }
}
