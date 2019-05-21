package com.vpr.pruebatiles.handlers;

import com.badlogic.gdx.physics.box2d.*;

import java.util.HashMap;

public class MyContactListener implements ContactListener {

    // Attributes

    // Fixtures list
    private String[] fixtureNames = {"player", "player_foot", "ground", "wall", "ceil", "shop"};
    private HashMap<String, Boolean> fixtures;
    public boolean playerCanJump;
    private enum FixtureId {
        A, B, NULL
    }

    private FixtureId fixtureId;

    public MyContactListener(){
        fixtures = new HashMap<String, Boolean>();
        for(String name : fixtureNames)
            fixtures.put(name, false);
    }

    /**
     * Called when 2 fixtures collides
     * @param c
     */
    public void beginContact(Contact c){
        Fixture fa = c.getFixtureA();
        Fixture fb = c.getFixtureB();

        // null control
        if(areFixturesNull(fa, fb)) return;
        System.out.println("BEGIN");
        updateFixtures(fa, fb, true);
    }

    /**
     * Called when 2 fixtures no longer collides
     * @param c
     */
    public void endContact(Contact c){
        System.out.println("END");
        updateFixtures(c.getFixtureA(), c.getFixtureB(), false);
    }

    /**
     * Called after collision detection and before collision handling
     * @param c
     * @param m
     */
    public void preSolve(Contact c, Manifold m) {

    }

    /**
     * Called after collision handling
     * @param c
     * @param ci
     */
    public void postSolve(Contact c, ContactImpulse ci){

    }

    private void updateFixtures(Fixture fa, Fixture fb, boolean isContactStarted){

        /*if(fa.getUserData().equals("player_foot") || fb.getUserData().equals("player_foot") &&
        fa.getUserData().equals("shop") || fb.getUserData().equals("shop")){
            isContactStarted = fixtures.get("player_foot");
        }*/

        boolean isContactA = isContactStarted;
        boolean isContactB = isContactStarted;

        if(fixturesDetected(fa, fb, "player_foot", "shop")){
            if(assignFixture(fa, fb, "player_foot") == FixtureId.A)
                isContactA = fixtures.get("player_foot");
            else if(assignFixture(fa, fb, "player_foot") == FixtureId.B)
                isContactB = fixtures.get("player_foot");
        }

        fixtures.put(fa.getUserData().toString(), isContactA);
        fixtures.put(fb.getUserData().toString(), isContactB);

        fixtures.put("player", true); // player always true

        for(String name : fixtureNames)
            System.out.println(name + " - " + fixtures.get(name));
    }

    private boolean areFixturesNull(Fixture fa, Fixture fb){
        if(fa == null || fb == null) return true;
        if(fa.getUserData() == null || fb.getUserData() == null) return true;

        return false;
    }

    /**
     * Checks if that user data equals to some of the 2 fixures passed, and returns if it's the fixture A, B or NULL
     * in case that user data is none of them
     * @param fa
     * @param fb
     * @param userData
     * @return
     */
    private FixtureId assignFixture(Fixture fa, Fixture fb, String userData){
        if(fa.getUserData().equals(userData) || fb.getUserData().equals(userData)) {
            if(fa.getUserData().equals(userData))
                return FixtureId.A;
            else
                return FixtureId.B;
        }

        return FixtureId.NULL;
    }

    private boolean fixturesDetected(Fixture fa, Fixture fb, String userDataA, String userDataB){
        if(fa.getUserData().equals(userDataA) || fb.getUserData().equals(userDataA) &&
                fa.getUserData().equals(userDataB) || fb.getUserData().equals(userDataB))
            return true;

        return false;
    }

    private boolean ignoreContact(Fixture fa, Fixture fb, String userDataIgnoring, String userDataIgnored){
        if(fa.getUserData().equals(userDataIgnoring) || fb.getUserData().equals(userDataIgnoring) &&
                fa.getUserData().equals(userDataIgnored) || fb.getUserData().equals(userDataIgnored)){
            return fixtures.get(userDataIgnoring);
        }

        return fixtures.get(userDataIgnoring);
    }

    private void resetFixtures(){
        for(String k : fixtures.keySet())
            fixtures.put(k, false);
    }

    public boolean canPlayerJump(){
        //TODO control double jump perk
        return isPlayerOnGround();
    }

    public boolean isPlayerOnGround(){
        return (fixtures.get("ground") && fixtures.get("player_foot"));
    }

    public boolean isPlayerInShop(){
        return (fixtures.get("player") && fixtures.get("shop"));
    }
}
