package com.vpr.pruebatiles.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.vpr.pruebatiles.handlers.MyContactListener;
import com.vpr.pruebatiles.util.BodyCreator;
import com.vpr.pruebatiles.util.Constantes;

import static com.vpr.pruebatiles.util.Constantes.PPM;

public class Bat extends BasicEnemy {
    public final float DENSITY = 1f;
    public final float FRICTION = 0f;
    public final float SPEED = 3.5f; //mps
    public final float JUMPING_FORCE = 00;
    public final float GRAVITY_SCALE = 0;

    public final float BODY_ADJUST = 8;
    public final float bodyWidth = width - BODY_ADJUST;
    public final float bodyHeight = height;

    private World world;

    private float startPosition, lastPosition, forceAcc;
    private boolean faceRight;

    public Bat(String textureName, World world, Vector2 spawnPoint, MyContactListener contactListener) {
        super(textureName, world, spawnPoint, contactListener);

        this.world = world;

        body = BodyCreator.createBody(world, spawnPoint.x, spawnPoint.y, bodyWidth, bodyHeight, DENSITY, FRICTION,
                true, BodyDef.BodyType.DynamicBody);
        body.getFixtureList().get(0).setUserData("demon_bat");
        body.getFixtureList().get(0).setSensor(true);
    }

    public Bat(World world, Vector2 spawnPoint, MyContactListener contactListener) {
        super(world, spawnPoint, contactListener);
    }

    @Override
    public void dispose() {
        super.dispose();
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

    public void setPosition(Vector2 position){
        body.setTransform(position.x / PPM, position.y / PPM, body.getAngle());
    }

    public void trackPlayer(Player player){
        float horizontalForce = 0;
        float verticalForce = 0;
        if(player.getPosition().x>body.getPosition().x)
            horizontalForce =SPEED;
        else
            horizontalForce=-SPEED;
        if(player.getPosition().y > body.getPosition().y)
            verticalForce =SPEED/2;
        else
            verticalForce=-SPEED/2;

        body.setLinearVelocity(horizontalForce * SPEED, verticalForce * SPEED);
    }

}
