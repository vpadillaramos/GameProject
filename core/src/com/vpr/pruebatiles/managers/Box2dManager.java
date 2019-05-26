package com.vpr.pruebatiles.managers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import static com.vpr.pruebatiles.util.Constantes.PPM;

public class Box2dManager {

    // Constantes
    public final float GRAVITY = 9.9f;

    // Attributes
    private Box2DDebugRenderer b2dr;
    public World world;
    private ContactListener contactListener;

    // Physics
    public float gravity;


    // Constructor
    public Box2dManager(ContactListener contactListener){
        gravity = -GRAVITY;
        this.contactListener = contactListener;

        world = new World(new Vector2(0, gravity), false);
        world.setContactListener(this.contactListener);


        b2dr = new Box2DDebugRenderer();
        b2dr.SHAPE_STATIC.set(0, 0, 1, 0); // set static bodies' color blue
        b2dr.SHAPE_AWAKE.set(1, 0, 0, 0); // set dynamic bodie's color red
    }

    // Methods
    public void update(){
        world.step(1 / 60f, 6, 2); // 60fps, 6, 2 standard values
    }

    public void render(OrthographicCamera camera){
        b2dr.render(world, camera.combined.scl(PPM));
    }

    public void dispose(){
        //world.dispose(); TODO don't dispose this!!!!!!!!!
        b2dr.dispose();
    }

    public void setGravity(float gravity){
        this.gravity = gravity;
    }

    /**
     * Delete all bodies from the world to load the new map bodies. It keeps player's body
     */
    public void prepareForNewMap(){
        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);

        for(Body b : bodies){
            if(b.getFixtureList().get(0).getUserData().equals("player"))
                continue;
            world.destroyBody(b);
        }
    }
}
