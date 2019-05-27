package com.vpr.pruebatiles.util;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.vpr.pruebatiles.entities.Player;

import static com.vpr.pruebatiles.util.Constantes.PPM;

public class BodyCreator {

    // Attributes

    public static Body createBody(World world, float x, float y, float width, float height, float density,
                                        float friction, boolean fixedRotation, BodyDef.BodyType bType) {

        // body definition
        BodyDef bDef = new BodyDef();
        bDef.type = bType;
        bDef.position.set(x / PPM, y / PPM);
        bDef.fixedRotation = fixedRotation;

        // initialize body
        Body body = world.createBody(bDef);

        PolygonShape shape = new PolygonShape();
        // this method creates a box from the center, in this case a 32x32 box
        shape.setAsBox(width / 2 / PPM, height / 2 / PPM); // divide by PPM cause giving variables to world

        // TODO physics
        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        fdef.density = density;
        fdef.friction = friction;

        //playerBody.createFixture(shape, 1.0f); // density
        body.createFixture(fdef);
        shape.dispose();

        return body;
    }

    public static Body createSensor(World world, String userData, float x, float y, float width, float height){

        BodyDef bDef = new BodyDef();
        bDef.position.set(x / PPM, y / PPM);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / PPM, height / PPM);

        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        fdef.isSensor = true;

        Body body = world.createBody(bDef);
        body.createFixture(fdef).setUserData(userData);
        shape.dispose();
        return body;

    }

    /*public static Body createSword(Player player){
        FixtureDef fdef = new FixtureDef();
        PolygonShape swordSensor;
        swordSensor = new PolygonShape();
        swordSensor.setAsBox(player.bodyWidth / 4 / PPM, 10 / PPM, new Vector2(player.bodyWidth / 2 / PPM, player.bodyHeight / 2 / PPM), 0);
        fdef.isSensor = true;
        fdef.shape = swordSensor;

        Body b =
        body.createFixture(fdef).setUserData("sword");
    }*/
}
