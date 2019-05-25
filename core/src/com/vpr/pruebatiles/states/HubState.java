package com.vpr.pruebatiles.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.rafaskoberg.gdx.typinglabel.TypingLabel;
import com.vpr.pruebatiles.entities.Player;
import com.vpr.pruebatiles.handlers.GameKeys;
import com.vpr.pruebatiles.handlers.MyContactListener;
import com.vpr.pruebatiles.handlers.MyGameInputProcessor;
import com.vpr.pruebatiles.managers.*;
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
    private MyGameInputProcessor inputProcessor;
    private LevelManager levelManager;
    private SkinManager skinManager;
    private Box2dManager b2dManager;
    private SpriteManager spriteManager;

    // Listeners, managers
    private InputMultiplexer multiplexer;
    private MyContactListener contactListener;

    // Text printing
    private TypingLabel typingLabel;
    private Label label;

    // Shop Test
    private ShopWindow shopWindow;


    public HubState(GameStateManager gsm) {
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
        player = new Player(Constantes.ninjaIdle, b2dManager.world, levelManager.getPlayerSpawnPoint(), contactListener, inputProcessor);
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
        shopWindow = new ShopWindow(this, skinManager, skinManager.skin, "Perkadona");

        // Add input processors
        initMultiplexer();
    }

    @Override
    public void update(float dt) {
        //spriteManager.update(dt);

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
        //b2dManager.dispose();
        //levelManager.dispose();
        //skinManager.dispose();
    }

    private void initMultiplexer(){
        multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(inputProcessor);
        multiplexer.addProcessor(skinManager.stage);
        Gdx.input.setInputProcessor(multiplexer);
    }

    public void physicsTestingInput(){

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
            player.JUMPING_FORCE += 3f;
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
            player.JUMPING_FORCE -= 3f;
        }



        // Got to dungeon
        if(Gdx.input.isKeyJustPressed(Input.Keys.Q))
            gsm.setState(GameStateManager.State.LOADING_DUNGEON);
    }

    public void pause(){
        state = State.PAUSE;
    }

    public void playing(){
        state = State.PLAYING;
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
        physicsTestingInput();
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
