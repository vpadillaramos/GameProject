package com.vpr.pruebatiles.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.vpr.pruebatiles.handlers.GameKeys;
import com.vpr.pruebatiles.managers.R;
import com.vpr.pruebatiles.util.BodyCreator;

import static com.vpr.pruebatiles.util.Constantes.PPM;

public class Player {

    // Constants
    private final int SPEED = 2; //mps
    private final int JUMPING_FORCE = 100;

    // Atributos
    public Body body;
    private TextureRegion texture;
    public float width, height;
    public Vector2 spawnPoint;

    // Constructor
    public Player(World world, Vector2 spawnPoint){
        this.spawnPoint = new Vector2();
        this.spawnPoint.set(spawnPoint);

        texture = R.getRegion("idle");
        width = texture.getRegionWidth();
        height = texture.getRegionHeight();
        body = BodyCreator.createBox(world, spawnPoint.x, spawnPoint.y, width, height, BodyDef.BodyType.DynamicBody);
        body.getFixtureList().get(0).setUserData("player");
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
        if(GameKeys.isPressed(GameKeys.JUMP)) {
            body.applyForceToCenter(0, JUMPING_FORCE, true); // apply this force tu the body, forceX, forceY, wake
        }
        if(GameKeys.isDown(GameKeys.WALK_LEFT))
            horizontalForce -= SPEED;
        if(GameKeys.isDown(GameKeys.WALK_RIGHT))
            horizontalForce += SPEED;

        body.setLinearVelocity(horizontalForce * SPEED, body.getLinearVelocity().y);
    }
}
