package com.vpr.pruebatiles.util;

import java.io.File;

public final class Constantes {

    // Resource Manager (R)
    public static final String ATLAS = "characters.atlas";

    // Camera
    public static final float SCALE = 2.0f;

    // Box2D
    public static final float PPM = 8; // pixels per meter

    //////////////////////////////////////////////
    //////////////Internal folders////////////////
    //////////////////////////////////////////////
    public static String levelsFolder = "levels/";
    public static String fontsFolder = "fonts/";
    public static String skinsFolder = "ui/";
    public static String playerCharactersFolder = "player_characters/";

    //////////////////////////////////////////////
    ///////////////Skins, fonts///////////////////
    //////////////////////////////////////////////
    public static String alterebroFont = fontsFolder + "alterebro.ttf";
    public static String skinAtlas = "uiskin.atlas";
    public static String skinJson = skinsFolder + "uiskin.json";


    //////////////////////////////////////////////
    ////////////////////TileMap///////////////////
    //////////////////////////////////////////////

    // tiles
    public static final int TILE_WIDTH = 16;
    public static final int TILE_HEIGHT = 16;

    // levels, maps
    public static final String hubMap = levelsFolder + "hub.tmx";
    public static final String dungeonMap = levelsFolder + "dungeonType1.tmx";

    // layers, names
    public static final String collisions_layer = "collisions";
    public static final String spawn_layer = "spawn";
    public static final String shops_layer = "shops";
    public static final String doors_layer = "doors";
    public static final String player_spawn = "player_spawn";


    //////////////////////////////////////////////
    //////////////////Dungeon rooms////////////////
    //////////////////////////////////////////////
    // types of rooms
    public enum RoomType {
        normal, perk, easterEgg, miniBoss, finalBoss
    }

    public static final int MIN_ROOMS = 10;
    public static final int MAX_ROOMS  = 20;

    //////////////////////////////////////////////
    //////////////////Player input////////////////
    //////////////////////////////////////////////

    // name references to image files for animations
    public static final String ninjaIdle = playerCharactersFolder + "ninjaIdle";
    public static final String ninjaJump = playerCharactersFolder + "ninjaJumpRight";
    public static final String ninjaWalkLeft = playerCharactersFolder + "ninjaWalkLeft";
    public static final String ninjaWalkRight = playerCharactersFolder + "ninjaWalkRight";
    public static final String ninjaFall = playerCharactersFolder + "ninjaFallRight";

    // Care!!! The order is important
    public enum Actions {
        IDLE, JUMP, FALLING, WALK_LEFT, WALK_RIGHT, INTERACT, CLOSE_WINDOW
    }





}
