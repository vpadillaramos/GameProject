package com.vpr.pruebatiles.handlers;

import com.badlogic.gdx.physics.box2d.*;

import java.util.HashMap;

public class MyContactListener implements ContactListener {


    // Constants
    // ud stands for UserData
    public final String udPlayer = "player";
    public final String udPlayerFoot = "player_foot";
    public final String udGround = "ground";
    public final String udWall = "wall";
    public final String udCeil = "ceil";
    public final String udShop = "shop";
    public final String udDungeonEntry = "dungeon_entry";
    public final String udNextRoom = "next_room";
    public final String udPreviousRoom = "previous_room";

    // Attributes

    // Fixtures list
    private String[] fixtureNames = {udPlayer, udPlayerFoot, udGround, udWall, udCeil, udShop, udDungeonEntry,
    udNextRoom};
    private HashMap<String, Boolean> fixtures;
    private enum FixtureId {
        A, B, NULL
    }

    private boolean previousUdPlayerFoot, previousUdGround;

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
        handlePlayerContact(c);
        handleFootContact(c);

        if(c.isEnabled()){

            updateFixtures(c, true);
            //System.out.println("BEGIN:"+c.getFixtureA().getUserData().toString()+" - "+c.getFixtureB().getUserData().toString());
            System.out.println("\nBEGIN");
            for(String k : fixtures.keySet()){
                System.out.print(k + " - " + fixtures.get(k) + "; ");
            }
        }
    }

    /**
     * Called when 2 fixtures no longer collides
     * @param c
     */
    public void endContact(Contact c){
        if(areFixturesNull(c)) return;
        handlePlayerContact(c);
        handleFootContact(c);

        if(c.isEnabled()) {

            updateFixtures(c, false);
            //System.out.println("END:" + c.getFixtureA().getUserData().toString() + " - " + c.getFixtureB().getUserData().toString());
            System.out.println("\nEND");
            for(String k : fixtures.keySet()){
                System.out.print(k + " - " + fixtures.get(k) + "; ");
            }
        }
    }

    /**
     * The player's foot will only be updated with ground, wall and ceil
     * @param c
     */
    private void handleFootContact(Contact c){
        disableContactBetween(c, udPlayerFoot, udShop);
        disableContactBetween(c, udPlayerFoot, udDungeonEntry);
        disableContactBetween(c, udPlayerFoot, udNextRoom);
        disableContactBetween(c, udPlayerFoot, udPreviousRoom);
        disableContactBetween(c, udPlayerFoot, udWall);
    }

    /**
     * The player's body will only be updated with room entries
     * @param c
     */
    private void handlePlayerContact(Contact c){
        disableContactBetween(c, udPlayer, udGround);
        disableContactBetween(c, udPlayer, udWall);
        disableContactBetween(c, udPlayer, udCeil);
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
        boolean cA = isContactStarted, cB = isContactStarted;
        /*if(!isContactStarted && isPlayerOnGround() && fixtureDetected(c, udPlayerFoot) &&
                !fixtureDetected(c, udGround) && !fixtureDetected(c, udShop)){
            cA = true;
            cB = true;
        }*/

        fixtures.put(c.getFixtureA().getUserData().toString(), cA);
        fixtures.put(c.getFixtureB().getUserData().toString(), cB);
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

    public void resetFixtures(){
        for(String k : fixtures.keySet())
            fixtures.put(k, false);
    }

    public boolean canPlayerJump(){
        //TODO control double jump perk
        return isPlayerOnGround();
    }

    public boolean isPlayerOnGround(){
        return (fixtures.get(udGround) && fixtures.get(udPlayerFoot));
    }

    public boolean isPlayerInShop(){
        return (fixtures.get(udPlayer) && fixtures.get(udShop));
    }

    public boolean isPlayerInDungeonEntry(){
        return (fixtures.get(udPlayerFoot) && fixtures.get(udDungeonEntry));
    }

    public boolean isPlayerInNextRoom(){
        return (fixtures.get(udPlayerFoot) && fixtures.get(udNextRoom));
    }


}
