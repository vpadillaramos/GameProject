package com.vpr.pruebatiles.util;

import com.badlogic.gdx.physics.box2d.*;

import static com.vpr.pruebatiles.util.Constantes.PPM;

public class BodyCreator {


    public static Body createBox(World world, int x, int y, float width, float height, boolean isStatic) {
        Body body;
        // body properties
        BodyDef bdef = new BodyDef();

        if(isStatic)
            bdef.type = BodyDef.BodyType.StaticBody;
        else
            bdef.type = BodyDef.BodyType.DynamicBody;

        bdef.position.set(x / PPM, y / PPM);
        bdef.fixedRotation = true;

        // initialize body
        body = world.createBody(bdef);

        PolygonShape shape = new PolygonShape();
        // this method creates a box from the center, in this case a 32x32 box
        shape.setAsBox(width / 2 / PPM, height / 2 / PPM); // giving variables to world

        // TODO physics
        FixtureDef fdef = new FixtureDef();
        fdef.density = 1.0f;
        fdef.friction = 1.0f;
        fdef.shape = shape;

        //playerBody.createFixture(shape, 1.0f); // density
        body.createFixture(fdef);
        shape.dispose();

        return body;
    }
}
