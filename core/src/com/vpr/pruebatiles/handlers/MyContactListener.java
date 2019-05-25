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

        // null control
        if(areFixturesNull(c)) return;
        disableAllContact(c, "player");

        if(c.isEnabled()){

            updateFixtures(c, true);
            //System.out.println("BEGIN:"+fa.getUserData().toString()+" - "+fb.getUserData().toString());
            /*System.out.println("BEGIN");
            for(String k : fixtures.keySet()){
                System.out.println(k + " - " + fixtures.get(k));
            }*/
        }
    }

    /**
     * Called when 2 fixtures no longer collides
     * @param c
     */
    public void endContact(Contact c){
        disableAllContact(c, "player");
        if(c.isEnabled()) {

            updateFixtures(c, false);
            //System.out.println("END:" + c.getFixtureA().getUserData().toString() + " - " + c.getFixtureB().getUserData().toString());
            /*System.out.println("END");
            for(String k : fixtures.keySet()){
                System.out.println(k + " - " + fixtures.get(k));
            }*/
        }
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

    private void updateFixtures(Contact c, boolean isContactStarted){

        /*if(fa.getUserData().equals("player_foot") || fb.getUserData().equals("player_foot") &&
        fa.getUserData().equals("shop") || fb.getUserData().equals("shop")){
            isContactStarted = fixtures.get("player_foot");
        }*/

        boolean isContactA = isContactStarted;
        boolean isContactB = isContactStarted;

        if(fixturesDetected(c, "player_foot", "shop")){
            if(assignFixture(c, "player_foot") == FixtureId.A)
                isContactA = fixtures.get("player_foot");
            else if(assignFixture(c, "player_foot") == FixtureId.B)
                isContactB = fixtures.get("player_foot");
        }

        fixtures.put(c.getFixtureA().getUserData().toString(), isContactA);
        fixtures.put(c.getFixtureB().getUserData().toString(), isContactB);


        /*for(String name : fixtureNames)
            System.out.println(name + " - " + fixtures.get(name));*/
    }

    private boolean areFixturesNull(Contact c){
        if(c.getFixtureA() == null || c.getFixtureB() == null) return true;
        if(c.getFixtureA().getUserData() == null || c.getFixtureB().getUserData() == null) return true;

        return false;
    }

    /**
     * Checks if that user data equals to some of the 2 fixures passed, and returns if it's the fixture A, B or NULL
     * in case that user data is none of them
     * @param c
     * @param userData
     * @return
     */
    private FixtureId assignFixture(Contact c, String userData){
        if(c.getFixtureA().getUserData().equals(userData) || c.getFixtureB().getUserData().equals(userData)) {
            if(c.getFixtureA().getUserData().equals(userData))
                return FixtureId.A;
            else
                return FixtureId.B;
        }

        return FixtureId.NULL;
    }

    /**
     * Check if the passed 2 userData are detected in the contact
     * @param c Contact between userDataA fixture and userDataB fixture
     * @param userDataA
     * @param userDataB
     * @return
     */
    private boolean fixturesDetected(Contact c, String userDataA, String userDataB){
        if(fixtureDetected(c, userDataA) && fixtureDetected(c, userDataB))
            return true;

        return false;
    }

    /**
     * Check if the passed userData is detected, no matter the other contact
     * @param c Contact
     * @param userData
     * @return
     */
    private boolean fixtureDetected(Contact c, String userData){
        if(c.getFixtureA().getUserData().equals(userData) || c.getFixtureB().getUserData().equals(userData))
            return true;

        return false;
    }

    /**
     * Set the contact enable to false (only in this current time step) if the passed 2 userData
     * are detected
     * @param c
     * @param userDataA
     * @param userDataB
     */
    private void disableContactBetween(Contact c, String userDataA, String userDataB){

        if(fixturesDetected(c, userDataA, userDataB)){
            c.setEnabled(false);
        }
    }

    /**
     * Set the contact enable to false (only in this current time step) if the passed userData
     * is detected, no matter the other contact
     * @param c
     * @param userData
     */
    private void disableAllContact(Contact c, String userData){
        if(fixtureDetected(c, userData))
            c.setEnabled(false);
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
