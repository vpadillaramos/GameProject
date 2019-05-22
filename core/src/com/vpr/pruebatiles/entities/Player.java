package com.vpr.pruebatiles.entities;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.vpr.pruebatiles.handlers.GameKeys;
import com.vpr.pruebatiles.handlers.MyContactListener;
import com.vpr.pruebatiles.handlers.MyGameInputProcessor;
import com.vpr.pruebatiles.managers.R;
import com.vpr.pruebatiles.util.BodyCreator;

import static com.vpr.pruebatiles.util.Constantes.PPM;

public class Player {

    // Constants
    private final int SPEED = 5; //mps
    private final int JUMPING_FORCE = 150;
    // Atributos
    public Body body;
    private TextureRegion texture;
    public float width, height;
    public Vector2 spawnPoint;
    public MyContactListener contactListener;
    public MyGameInputProcessor inputProcessor;

    // input
    private boolean isJumping, isShopping;
    private float startPosition, lastPosition, forceAcc;

    // Constructor
    public Player(World world, Vector2 spawnPoint, MyContactListener contactListener, MyGameInputProcessor inputProcessor){
        this.spawnPoint = new Vector2();
        this.spawnPoint.set(spawnPoint);
        this.contactListener = contactListener;
        this.inputProcessor = inputProcessor;

        texture = R.getRegion("ninjaIdle");
        width = texture.getRegionWidth();
        height = texture.getRegionHeight();

        body = BodyCreator.createBox(world, spawnPoint.x, spawnPoint.y, width, height, true, BodyDef.BodyType.DynamicBody);
        body.getFixtureList().get(0).setUserData("player");

        initFootSensor();
    }

    // Metodos
    public Vector2 getPosition(){
        return body.getPosition();
    }

    public void draw(SpriteBatch batch) {
        // centers texture respect the body
        batch.draw(texture, (body.getPosition().x * PPM) - (width / 2),
                (body.getPosition().y * PPM) - (height / 2));

    }

    public void checkKeys(){

        int horizontalForce = 0;

        if(GameKeys.isPressed(GameKeys.keyBindings.get("JUMP"))) {
            if(contactListener.canPlayerJump()) {
                body.applyForceToCenter(0, JUMPING_FORCE, false); // apply this force tu the body, forceX, forceY, wake
                isJumping = true;
                startPosition = body.getPosition().y;
                lastPosition = startPosition;
            }
        }

        if(GameKeys.isDown(GameKeys.keyBindings.get("WALK_LEFT"))) {
            horizontalForce = -SPEED;
        }

        if(GameKeys.isDown(GameKeys.keyBindings.get("WALK_RIGHT"))) {
            horizontalForce = SPEED;
        }

        if(isJumping){
            if(body.getPosition().y < lastPosition){ // if body is falling
                forceAcc -= 0.8f;
                body.applyForceToCenter(new Vector2(0, forceAcc), false);
            }
            else
                forceAcc = 0;

            lastPosition = body.getPosition().y;
        }

        body.setLinearVelocity(horizontalForce * SPEED, body.getLinearVelocity().y);
    }

    public boolean shopDetected(){
        if(contactListener.isPlayerInShop() && GameKeys.isPressed(GameKeys.keyBindings.get("OPEN_WINDOW"))){
            System.out.println("I'm in the shop");
            return true;
        }
        else
            return false;
    }

    public boolean openShop(){

        if(shopDetected() && GameKeys.isPressed(GameKeys.keyBindings.get("OPEN_WINDOW"))){
            isShopping = true;
            return true;
        }

        return false;
    }

    public boolean closeShop(){
        System.out.println("Want to close");
        if(isShopping && GameKeys.isPressed(GameKeys.keyBindings.get("CLOSE_WINDOW"))) {
            System.out.println("Closing shop");
            isShopping = false;
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
}
