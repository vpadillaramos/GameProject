package com.vpr.pruebatiles.util;

import java.io.File;

public final class Constantes {

    // Resource Manager (R)
    public static final String ATLAS = "characters.atlas";

    // Camera
    public static final float SCALE = 2.0f;

    // Box2D
    public static final float PPM = 8; // pixels per meter

    // Tiles
    public static final int TILE_WIDTH = 64;
    public static final int TILE_HEIGHT = 64;
    public static final int CAMERA_TILES = 10;

    //////////////////////////////////////////////
    //////////////Internal folders////////////////
    //////////////////////////////////////////////
    private static String levelsFolder = "levels/";
    private static String fontsFolder = "fonts/";
    private static String skinsFolder = "ui/";
    private static String playerCharactersFolder = "player_characters/";

    //////////////////////////////////////////////
    ///////////////Skins, fonts///////////////////
    //////////////////////////////////////////////
    public static String alterebroFont = fontsFolder + "alterebro.ttf";
    public static String skinAtlas = "uiskin.atlas";
    public static String skinJson = skinsFolder + "uiskin.json";


    //////////////////////////////////////////////
    ///////////////TileMap references/////////////
    //////////////////////////////////////////////

    // levels, maps

    public static final String hubMap = levelsFolder + "hub.tmx";
    public static final String dungeonMap = levelsFolder + "dungeonType1.tmx";

    // layers, names
    public static final String collisions_layer = "collisions";
    public static final String player_spawn_layer = "player_spawn";
    public static final String shops_layer = "shops";
    public static final String dungeon_entry_layer = "dungeon_entry";
    public static final String spawn_point = "spawn_point";


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
        IDLE, JUMP, FALLING, WALK_LEFT, WALK_RIGHT, OPEN_WINDOW, CLOSE_WINDOW
    }





}
