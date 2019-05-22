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
    private static String levelsFolder = "levels" + File.separator;
    private static String fontsFolder = "fonts" + File.separator;
    private static String skinsFolder = "ui" + File.separator;

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

    // layers, names
    public static final String collisions_layer = "collisions";
    public static final String player_spawn_layer = "player_spawn";
    public static final String shops_layer = "shops";
    public static final String spawn_point = "spawn_point";





}
