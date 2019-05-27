package com.vpr.pruebatiles.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.vpr.pruebatiles.handlers.Keys;
import com.vpr.pruebatiles.handlers.MyContactListener;
import com.vpr.pruebatiles.handlers.MyInputManager;
import com.vpr.pruebatiles.managers.CharacterState;
import com.vpr.pruebatiles.util.BodyCreator;

import java.util.Calendar;

import static com.vpr.pruebatiles.util.Constantes.PPM;

public class Player extends BasicEntity{

    // Constantes
    public final float DENSITY = 1f;
    public final float FRICTION = 0f;
    public final float SPEED = 4; //mps
    public final float JUMPING_FORCE = 700;
    public final float GRAVITY_SCALE = 8;

    public final int MAX_HEALT = 100;

    public final float BODY_ADJUST = 8;
    public final float bodyWidth = width - BODY_ADJUST;
    public final float bodyHeight = height;

    // Attributes

    public int coins;
    public int health;

    //public MyGameInputProcessor inputProcessor;
    public MyInputManager input;
    private World world;

    // input
    public boolean isJumping = false, isFalling = false, isWalking = false, isShopping;
    private float startPosition, lastPosition, forceAcc;
    private float jumpTimeCounter, jumpTime;

    private PolygonShape footSensor;

    // Animations
    public CharacterState.State state;

    public Player(String textureName, World world, Vector2 spawnPoint, MyContactListener contactListener, MyInputManager input) {
        super(textureName, world, spawnPoint, contactListener);

        // Attributes
        coins = 50;
        health = MAX_HEALT;

        this.input = input;
        this.world = world;
        state = CharacterState.State.IDLE;

        body = BodyCreator.createBody(world, spawnPoint.x, spawnPoint.y, bodyWidth, bodyHeight, DENSITY, FRICTION,
                true, BodyDef.BodyType.DynamicBody);
        body.getFixtureList().get(0).setUserData("player");
        body.setGravityScale(GRAVITY_SCALE);
        initFootSensor();

        jumpTime = 4f;
    }

    @Override
    public void dispose() {
        super.dispose();
        footSensor.dispose();
    }

    public CharacterState.State getState(){
        return state;
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

    public void keyPlayingInput(float dt){
        manageShop();
        manageJump();
        manageWalk();

        //System.out.println("Vel x: " + body.getLinearVelocity().x + "; Vel y: " + body.getLinearVelocity().y);
        managePlayerState();
    }

    public void keyPauseInput(float dt){
        manageShop();
    }

    public void managePlayerState(){

        // walk right
        if(body.getLinearVelocity().x > 0){

            if(body.getLinearVelocity().y > 0)
                state = CharacterState.State.JUMP_RIGHT;
            else if(body.getLinearVelocity().y < 0)
                state = CharacterState.State.FALL_RIGHT;
            else if(body.getLinearVelocity().y == 0)
                state = CharacterState.State.WALK_RIGHT;

        }
        // walk left
        else if(body.getLinearVelocity().x < 0){

            if(body.getLinearVelocity().y > 0)
                state = CharacterState.State.JUMP_LEFT;
            else if(body.getLinearVelocity().y < 0)
                state = CharacterState.State.FALL_LEFT;
            else if(body.getLinearVelocity().y == 0)
                state = CharacterState.State.WALK_LEFT;
        }
        // idle
        else if(body.getLinearVelocity().x == 0)
            state = CharacterState.State.IDLE;
    }

    public void manageWalk(){
        float horizontalForce = 0;
        if(input.isKeyDown(Keys.walkRight))
            horizontalForce = SPEED;
        if(input.isKeyDown(Keys.walkLeft))
            horizontalForce = -SPEED;

        body.setLinearVelocity(horizontalForce * SPEED, body.getLinearVelocity().y);
    }

    public void manageJump(){
        float verticalForce = 0;

        // TODO avoid bunny jump
        if(contactListener.isPlayerOnGround() && input.isKeyDown(Keys.jump)){
            isJumping = true;
            jumpTimeCounter = jumpTime;
            verticalForce = JUMPING_FORCE;
            body.applyForceToCenter(new Vector2(body.getLinearVelocity().x, verticalForce), false);
        }

        // player continues jumping higher if jumping key is pressed and it's in the air
        /*if(inputManager.isKeyPressed(Input.Keys.UP) && isJumping){

            if(jumpTimeCounter > 0){
                verticalForce = JUMPING_FORCE;

                body.applyForceToCenter(new Vector2(body.getLinearVelocity().x, verticalForce), false);
                jumpTimeCounter -= dt;
            }
            else
                isJumping = false; // when is falling
        }*/

        if(input.isKeyUp(Keys.jump))
            isJumping = false;
    }

    public void manageShop(){
        //  if player's not already shopping, is contacted with shop's sensor and press interact
        if(!isShopping && contactListener.isPlayerInShop() && input.isKeyDown(Keys.interact)){
            isShopping = true;
        }
        else if(isShopping && input.isKeyDown(Keys.escape))
            isShopping = false;
    }

    public boolean isEnteringDungeon(){
        if(contactListener.isPlayerInDungeonEntry() && input.isKeyDown(Keys.interact)) {
            //contactListener.resetFixtures();
            return true;
        }
        return false;
    }

    public boolean isPlayerInNextRoom(){
        if(contactListener.isPlayerInNextRoom() && input.isKeyDown(Keys.interact))
            return true;

        return false;
    }

    public boolean isPlayerInPreviousRoom(){
        if(contactListener.isPlayerInPreviousRoom() && Gdx.input.isKeyJustPressed(Input.Keys.E)){
            return true;
        }

        return false;
    }

    private void initFootSensor(){
        FixtureDef fdef = new FixtureDef();
        footSensor = new PolygonShape();
        footSensor.setAsBox(bodyWidth / 4 / PPM, 10 / 3 / PPM, new Vector2(0, -bodyHeight / 2 / PPM), 0);
        fdef.isSensor = true;
        fdef.shape = footSensor;
        body.createFixture(fdef).setUserData("player_foot");
    }

    public void setPosition(Vector2 position){
        body.setTransform(position.x / PPM, position.y / PPM, body.getAngle());
    }

}
