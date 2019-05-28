package com.vpr.pruebatiles.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.rafaskoberg.gdx.typinglabel.TypingLabel;
import com.vpr.pruebatiles.entities.Player;
import com.vpr.pruebatiles.handlers.MyContactListener;
import com.vpr.pruebatiles.handlers.MyInputManager;
import com.vpr.pruebatiles.managers.*;
import com.vpr.pruebatiles.util.CameraMethods;
import com.vpr.pruebatiles.util.Constantes;
import com.vpr.pruebatiles.windows.ShopWindow;

import java.text.DecimalFormat;

public class HubState extends GameState {

    // Constants

    // Atributes
    public enum State {
        PLAYING, PAUSE
    }
    public State state;

    // Player
    public Player player;

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

    // Text printing
    private TypingLabel typingLabel;
    private Label label;

    // Shop Test
    private ShopWindow shopWindow;


    public HubState(GameStateManager gsm, String map) {
        super(gsm, map);

        // Initial state
        state = State.PLAYING;

        // Managers, listeners
        contactListener = new MyContactListener();
        //inputProcessor = new MyGameInputProcessor();
        inputManager = new MyInputManager();
        skinManager = new SkinManager();
        b2dManager = new Box2dManager(contactListener);
        levelManager = new LevelManager(b2dManager.world, map);

        // player initialization
        //player = new Player(Constantes.ninjaIdle, b2dManager.world, levelManager.getPlayerSpawnPoint(), contactListener, inputProcessor);
        player = new Player(gsm.playerType, b2dManager.world, levelManager.getPlayerSpawnPoint(), contactListener, inputManager);
        spriteManager = new SpriteManager(player);

        // Text test
        String text = "{SPEED=0.5}Hola, que tal, {COLOR=RED}esto{WAIT} es una prueba de {SHAKE}Typing label";
        typingLabel = new TypingLabel(text, skinManager.skin);
        //typingLabel.setWrap(true);
        //typingLabel.setWidth(100);
        //typingLabel.setPosition((cameraManager.camera.viewportWidth / 2), (cameraManager.camera.viewportHeight / 2));
        typingLabel.setPosition(cameraManager.camera.position.x - 100, cameraManager.camera.position.y + 280);

        //skinManager.stage.addActor(typingLabel);

        label = new Label("", skinManager.skin);
        label.setPosition(cameraManager.camera.position.x - 100, cameraManager.camera.position.y + 280);
        skinManager.stage.addActor(label);

        // Shop test
        shopWindow = new ShopWindow(this, skinManager, cameraManager, Constantes.perkShopName);

        // Add input processors
        initMultiplexer();
    }

    @Override
    public void update(float dt) {

        manageInput(dt);
        spriteManager.update(dt);
        if(player.isShopping){
            shopWindow.show(true);
            pause();
        }
        else {
            shopWindow.show(false);
            resume();
        }

        if(player.isEnteringDungeon())
            gsm.setState(GameStateManager.State.LOADING_DUNGEON);


        switch (state){
            case PAUSE:
                shopWindow.handleButtonClicks();
                break;
            case PLAYING:
                batch.setProjectionMatrix(cameraManager.camera.combined);
                b2dManager.update();
                cameraUpdate();
                levelManager.update(cameraManager.camera);
                break;
            default:

                break;
        }

        inputManager.update();
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

        DecimalFormat f = new DecimalFormat("##.00");

        label.setText("(T-G)Density: "+f.format(player.getDensity())+
                "\n(Y-H)Friction: "+f.format(player.getFriction())+
                "\n(U-J)Speed: "+f.format(player.SPEED)+
                "\n(I-K)JumpingForce: "+f.format(player.JUMPING_FORCE));

        skinManager.render(dt);
    }

    @Override
    public void dispose() {
        /*b2dManager.dispose();
        levelManager.dispose();
        skinManager.dispose();
        player.dispose();*/
    }

    private void initMultiplexer(){
        multiplexer = new InputMultiplexer();
        //multiplexer.addProcessor(inputProcessor);
        multiplexer.addProcessor(inputManager);
        multiplexer.addProcessor(skinManager.stage);
        Gdx.input.setInputProcessor(multiplexer);
    }

    /*public void physicsTestingInput(){

        // Keys that increase values
        if(Gdx.input.isKeyJustPressed(Input.Keys.T)){
            player.setDensity(player.getDensity() + 0.2f);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.Y)){
            player.setFriction(player.getFriction() + 0.2f);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.U)){
            player.SPEED += 0.5f;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.I)){
            player.JUMPING_FORCE += 10f;
        }

        // Keys that decease values
        if(Gdx.input.isKeyJustPressed(Input.Keys.G)){
            player.setDensity(player.getDensity() - 0.2f);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.H)){
            player.setFriction(player.getFriction() - 0.2f);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.J)){
            player.SPEED -= 0.5f;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.K)){
            player.JUMPING_FORCE -= 10f;
        }
    }*/

    public void pause(){
        state = State.PAUSE;
    }

    public void resume(){
        state = State.PLAYING;
    }

    public void cameraUpdate(){

        cameraManager.lerpToTarget(player.getPosition());
        cameraManager.update(levelManager.mapPixelWidth, levelManager.mapPixelHeight);
    }

    public void manageInput(float dt){

        switch (state){
            case PLAYING:
                cameraManager.manageInput(player);
                //physicsTestingInput();
                player.keyPlayingInput(dt);
                break;
            case PAUSE:
                player.keyPauseInput(dt);
                break;
        }
    }
}
