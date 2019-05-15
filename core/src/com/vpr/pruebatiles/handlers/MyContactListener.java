package com.vpr.pruebatiles.handlers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class MyContactListener implements ContactListener {

    /**
     * Called when 2 fixtures collides
     * @param c
     */
    public void beginContact(Contact c){

    }

    /**
     * Called when 2 fixtures no longer collides
     * @param c
     */
    public void endContact(Contact c){

    }

    /**
     * Called after collision detection and before collision handling
     * @param c
     * @param m
     */
    public void preSolve(Contact c, Manifold m){

    }

    /**
     * Called after collision handling
     * @param c
     * @param ci
     */
    public void postSolve(Contact c, ContactImpulse ci){

    }
}
