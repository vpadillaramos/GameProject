package com.vpr.pruebatiles.util;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;

import static com.vpr.pruebatiles.util.Constantes.PPM;

public class BodyCreator {

    // Attributes
    public static FixtureDef fdef;

    public static Body createBox(World world, float x, float y, float width, float height, BodyDef.BodyType bType) {

        // body definition
        BodyDef bDef = new BodyDef();
        bDef.type = bType;
        bDef.position.set(x / PPM, y / PPM);

        // initialize body
        Body body = world.createBody(bDef);

        PolygonShape shape = new PolygonShape();
        // this method creates a box from the center, in this case a 32x32 box
        shape.setAsBox(width / 2 / PPM, height / 2 / PPM); // divide by PPM cause giving variables to world

        // TODO physics
        fdef = new FixtureDef();
        fdef.shape = shape;
        fdef.density = 4.0f;
        fdef.friction = 1.0f;


        //playerBody.createFixture(shape, 1.0f); // density
        body.createFixture(fdef);
        shape.dispose();

        return body;
    }

    public static Body createCollisionBody(World world, Rectangle rectangle){
        // body definition
        BodyDef bDef = new BodyDef();
        bDef.type = BodyDef.BodyType.StaticBody;
        bDef.position.set((rectangle.x + rectangle.width / 2) / PPM, (rectangle.y + rectangle.height / 2) / PPM);
        Body body = world.createBody(bDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(rectangle.width / 2 / PPM, rectangle.height / 2 / PPM);

        fdef = new FixtureDef();
        fdef.shape = shape;

        body.createFixture(fdef);
        shape.dispose();

        return body;
    }
}
