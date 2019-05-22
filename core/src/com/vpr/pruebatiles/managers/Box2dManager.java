package com.vpr.pruebatiles.managers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.World;

import static com.vpr.pruebatiles.util.Constantes.PPM;

public class Box2dManager {

    // Attributes
    private Box2DDebugRenderer b2dr;
    public World world;
    private ContactListener contactListener;

    // Physics
    public float gravity;


    // Constructor
    public Box2dManager(ContactListener contactListener){
        gravity = -9.9f;
        this.contactListener = contactListener;

        world = new World(new Vector2(0, gravity), false);
        world.setContactListener(this.contactListener);

        b2dr = new Box2DDebugRenderer();
        b2dr.SHAPE_STATIC.set(0, 0, 1, 0); // set static bodies' color blue
        b2dr.SHAPE_AWAKE.set(1, 0, 0, 0); // set dynamic bodie's color red
    }

    // Methods
    public void upddate(){
        world.step(1 / 60f, 6, 2); // 60fps, 6, 2 standard values
    }

    public void render(OrthographicCamera camera){
        b2dr.render(world, camera.combined.scl(PPM));
    }

    public void dispose(){
        b2dr.dispose();
        world.dispose();
    }

    public void setGravity(float gravity){
        this.gravity = gravity;
    }

}
