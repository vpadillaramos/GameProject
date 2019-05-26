package com.vpr.pruebatiles.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.vpr.pruebatiles.handlers.GameKeys;
import com.vpr.pruebatiles.handlers.MyContactListener;
import com.vpr.pruebatiles.handlers.MyGameInputProcessor;
import com.vpr.pruebatiles.managers.R;
import com.vpr.pruebatiles.util.BodyCreator;
import com.vpr.pruebatiles.util.Constantes;

import static com.vpr.pruebatiles.util.Constantes.PPM;

/*public class Player {

    // Constants
    private final float DENSITY = 1f;
    private final float FRICTION = 1f;
    private final int SPEED = 2; //mps
    private final int JUMPING_FORCE = 2000;


    // Atributos
    public Body body;
    public TextureRegion texture;
    public float width, height;
    public Vector2 spawnPoint;
    public MyContactListener contactListener;
    public MyGameInputProcessor inputProcessor;

    // input
    boolean isJumping = false, isFalling = false, isWalking = false;
    private boolean isShopping;
    private float startPosition, lastPosition, forceAcc;

    // Animations
    public Constantes.Actions action;

    // Constructor
    public Player(World world, Vector2 spawnPoint, MyContactListener contactListener, MyGameInputProcessor inputProcessor){
        this.spawnPoint = new Vector2();
        this.spawnPoint.set(spawnPoint);
        this.contactListener = contactListener;
        this.inputProcessor = inputProcessor;

        texture = R.getRegion(Constantes.ninjaIdle);
        System.out.println(Constantes.ninjaIdle);
        width = texture.getRegionWidth();
        height = texture.getRegionHeight();
        action = Constantes.Actions.IDLE;

        body = BodyCreator.createBody(world, spawnPoint.x, spawnPoint.y, width, height, DENSITY, FRICTION,
                true, BodyDef.BodyType.DynamicBody);
        body.getFixtureList().get(0).setUserData("player");

        initFootSensor();
    }

    // Methods
    public Vector2 getPosition(){
        return body.getPosition();
    }

    public void draw(SpriteBatch batch) {
        // centers texture respect the body
        batch.draw(texture, (body.getPosition().x - width / 2 / PPM),
                (body.getPosition().y - height / 2 / PPM), width / PPM, height / PPM);

    }

    public void setTexture(TextureRegion texture) {
        this.texture = texture;
    }

    public Constantes.Actions getAction(){
        return action;
    }

    public void checkKeys(){

        int horizontalForce = 0;
        int verticalForce = 0;


        if(GameKeys.isPressed(GameKeys.keyBindings.get(Constantes.Actions.JUMP))) {
            if(contactListener.canPlayerJump()) {
                action = Constantes.Actions.JUMP;
                isJumping = true;
                isWalking = false;
                isFalling = false;

                verticalForce = JUMPING_FORCE;
                body.applyForceToCenter(0, verticalForce, false); // apply this force tu the body, forceX, forceY, wake
                startPosition = body.getPosition().y;
                lastPosition = startPosition;
            }
        }

        if(GameKeys.isDown(GameKeys.keyBindings.get(Constantes.Actions.WALK_LEFT))) {
            action = Constantes.Actions.WALK_LEFT;
            isJumping = false;
            isWalking = true;
            isFalling = false;

            horizontalForce = -SPEED;
        }

        if(GameKeys.isDown(GameKeys.keyBindings.get(Constantes.Actions.WALK_RIGHT))) {
            action = Constantes.Actions.WALK_RIGHT;
            isJumping = false;
            isWalking = true;
            isFalling = false;

            horizontalForce = SPEED;
        }

        if(isJumping){
            if(body.getPosition().y < lastPosition){ // if body is falling
                isJumping = false;
                isWalking = false;
                isFalling = true;

                action = Constantes.Actions.FALLING;
                //forceAcc -= 0.8f;
                //body.applyForceToCenter(new Vector2(0, forceAcc), false);
            }
            else
                forceAcc = 0;

            lastPosition = body.getPosition().y;
        }

        if(!isWalking && isFalling && !isJumping) {
            action = Constantes.Actions.IDLE;
            isJumping = false;
            isWalking = false;
            isFalling = false;
        }

        body.setLinearVelocity(horizontalForce * SPEED, body.getLinearVelocity().y);
    }

    public boolean shopDetected(){
        if(contactListener.isPlayerInShop() && GameKeys.isPressed(GameKeys.keyBindings.get(Constantes.Actions.OPEN_WINDOW))){
            System.out.println("I'm in the shop");
            return true;
        }
        else
            return false;
    }

    public boolean openShop(){

        if(shopDetected() && GameKeys.isPressed(GameKeys.keyBindings.get(Constantes.Actions.OPEN_WINDOW))){
            isShopping = true;
            action = Constantes.Actions.OPEN_WINDOW;
            return true;
        }

        return false;
    }

    public boolean closeShop(){
        System.out.println("Want to close");
        if(isShopping && GameKeys.isPressed(GameKeys.keyBindings.get(Constantes.Actions.CLOSE_WINDOW))) {
            System.out.println("Closing shop");
            isShopping = false;
            action = Constantes.Actions.CLOSE_WINDOW;
            return true;
        }
        return false;
    }

    public boolean isOpeningShop(){
        if(openShop())
            return true;
        if(closeShop())
            return false;

        return false;
    }

    private void initFootSensor(){
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(10 / 2 / PPM, 10 / 2 / PPM, new Vector2(0, -height / 2 / PPM), 0);
        fdef.isSensor = true;
        fdef.shape = shape;
        body.createFixture(fdef).setUserData("player_foot");
    }
}*/

public class Player extends BasicEntity{

    // Constantes
    public float DENSITY = 1f;
    public float FRICTION = 0f;
    public float SPEED = 4; //mps
    public float JUMPING_FORCE = 1700;

    public final float BODY_ADJUST = 8;
    public final float bodyWidth = width - BODY_ADJUST;
    public final float bodyHeight = height;

    // Attributes
    public MyGameInputProcessor inputProcessor;


    // input
    boolean isJumping = false, isFalling = false, isWalking = false;
    private boolean isShopping;
    private float startPosition, lastPosition, forceAcc;

    private PolygonShape footSensor;

    // Animations
    public Constantes.Actions action;


    // Constructor
    public Player(String textureName, World world, Vector2 spawnPoint, MyContactListener contactListener, MyGameInputProcessor inputProcessor) {
        super(textureName, world, spawnPoint, contactListener);

        this.inputProcessor = inputProcessor;
        action = Constantes.Actions.IDLE;

        body = BodyCreator.createBody(world, spawnPoint.x, spawnPoint.y, bodyWidth, bodyHeight, DENSITY, FRICTION,
                true, BodyDef.BodyType.DynamicBody);
        /*body = BodyCreator.createBody(world, spawnPoint.x, spawnPoint.y, 100, 100, DENSITY, FRICTION,
                true, BodyDef.BodyType.DynamicBody);*/
        body.getFixtureList().get(0).setUserData("player");

        initFootSensor();
    }

    @Override
    public void dispose() {
        super.dispose();
        footSensor.dispose();
    }

    public Constantes.Actions getAction(){
        return action;
    }

    public void setDensity(float density){
        body.getFixtureList().get(0).setDensity(density);
        body.resetMassData();
    }

    public float getDensity(){
        return body.getFixtureList().get(0).getDensity();
    }

    public void setFriction(float friction){
        body.getFixtureList().get(0).setFriction(friction);
        body.resetMassData();
    }

    public float getFriction(){
        return body.getFixtureList().get(0).getFriction();
    }

    public void checkKeys(){

        float horizontalForce = 0;
        float verticalForce = 0;


        if(GameKeys.isPressed(GameKeys.keyBindings.get(Constantes.Actions.JUMP))) {
            if(contactListener.canPlayerJump()) {
                action = Constantes.Actions.JUMP;
                isJumping = true;
                isWalking = false;
                isFalling = false;

                verticalForce = JUMPING_FORCE;
                body.applyForceToCenter(0, verticalForce, false); // apply this force tu the body, forceX, forceY, wake
                startPosition = body.getPosition().y;
                lastPosition = startPosition;
            }
        }

        if(GameKeys.isDown(GameKeys.keyBindings.get(Constantes.Actions.WALK_LEFT))) {
            action = Constantes.Actions.WALK_LEFT;
            isJumping = false;
            isWalking = true;
            isFalling = false;

            horizontalForce = -SPEED;
        }

        if(GameKeys.isDown(GameKeys.keyBindings.get(Constantes.Actions.WALK_RIGHT))) {
            action = Constantes.Actions.WALK_RIGHT;
            isJumping = false;
            isWalking = true;
            isFalling = false;

            horizontalForce = SPEED;
        }

        if(isJumping){
            if(body.getPosition().y < lastPosition){ // if body is falling
                isJumping = false;
                isWalking = false;
                isFalling = true;

                action = Constantes.Actions.FALLING;
                //forceAcc -= 0.8f;
                //body.applyForceToCenter(new Vector2(0, forceAcc), false);
            }
            else
                forceAcc = 0;

            lastPosition = body.getPosition().y;
        }

        if(!isWalking && isFalling && !isJumping) {
            action = Constantes.Actions.IDLE;
            isJumping = false;
            isWalking = false;
            isFalling = false;
        }

        body.setLinearVelocity(horizontalForce * SPEED, body.getLinearVelocity().y);
    }

    public boolean shopDetected(){
        if(contactListener.isPlayerInShop() && GameKeys.isPressed(GameKeys.keyBindings.get(Constantes.Actions.INTERACT))){
            System.out.println("I'm in the shop");
            return true;
        }
        else
            return false;
    }

    public boolean openShop(){

        if(shopDetected() && GameKeys.isPressed(GameKeys.keyBindings.get(Constantes.Actions.INTERACT))){
            isShopping = true;
            action = Constantes.Actions.INTERACT;
            return true;
        }

        return false;
    }

    public boolean closeShop(){
        System.out.println("Want to close");
        if(isShopping && GameKeys.isPressed(GameKeys.keyBindings.get(Constantes.Actions.CLOSE_WINDOW))) {
            System.out.println("Closing shop");
            isShopping = false;
            action = Constantes.Actions.CLOSE_WINDOW;
            return true;
        }
        return false;
    }

    public boolean isEnteringDungeon(){
        if(contactListener.isPlayerInDungeonEntry() &&
                GameKeys.isPressed(GameKeys.keyBindings.get(Constantes.Actions.INTERACT))) {
            //contactListener.resetFixtures();
            return true;
        }


        return false;
    }

    public boolean isPlayerInNextRoom(){
        if(contactListener.isPlayerInNextRoom()) {
            //System.out.println("player is in next room");
            //if(GameKeys.isPressed(GameKeys.keyBindings.get(Constantes.Actions.INTERACT))){
            if(Gdx.input.isKeyPressed(Input.Keys.E)){
                System.out.println("pressing E");
                return true;
            }
            return false;
        }
        //System.out.println("false");
        return false;
    }

    public boolean isOpeningShop(){
        if(openShop())
            return true;
        if(closeShop())
            return false;

        return false;
    }

    private void initFootSensor(){
        FixtureDef fdef = new FixtureDef();
        footSensor = new PolygonShape();
        footSensor.setAsBox(bodyWidth / 4 / PPM, 10 / 3 / PPM, new Vector2(0, -height / 2 / PPM), 0);
        fdef.isSensor = true;
        fdef.shape = footSensor;
        body.createFixture(fdef).setUserData("player_foot");
    }

    public void setPosition(Vector2 position){
        body.setTransform(position.x / PPM, position.y / PPM, body.getAngle());
    }

}
