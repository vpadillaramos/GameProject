package com.vpr.pruebatiles.util;

import java.io.File;

public final class Constantes {

    // Resource Manager (R)
    public static final String ATLAS = "sprites.atlas";

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
    public static String perksFolder = "perks/";
    public static String playerCharactersFolder = "player_characters/";
    public static String hudFolder = "hud/";


    //////////////////////////////////////////////
    /////////////////////HUD//////////////////////
    //////////////////////////////////////////////
    public static String healthBar = hudFolder + "healthBar";

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
    /////////////////////Shop/////////////////////
    //////////////////////////////////////////////
    public static final String perkShopName = "Perkadona";
    public static final String doubleJumpPerk = perksFolder + "doubleJump";
    public static final String doubleJumpPerkShopName = "Double Jump!";
    public static final String dashPerk = perksFolder + "dash";
    public static final String dashPerkShopName = "Dash!";
    public static final String[] perks = {doubleJumpPerk, dashPerk};
    public static final int doubleJumpPrice = 20;
    public static final int dashPrice = 40;

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

    // characters
    public enum CharacterType {
        warrior, ninja, lancer
    }

    public static final String ninjaCharacter = playerCharactersFolder + "ninjaCharacter";
    public static final String warriorCharacter = playerCharactersFolder + "warriorCharacter";
    public static final String lancerCharacter = playerCharactersFolder + "lancerCharacter";


    // name references to image files for animations

    public static final String idle = "Idle";
    public static final String jumpRight = "JumpRight";
    public static final String jumpLeft = "JumpLeft";
    public static final String walkRight = "WalkRight";
    public static final String walkLeft = "WalkLeft";
    public static final String fallRight = "FallRight";
    public static final String fallLeft = "FallLeft";

    public static final String warriorIdle = playerCharactersFolder + "warriorIdle";
    public static final String warriorJumpRight = playerCharactersFolder + "warriorJumpRight";
    public static final String warriorJumpLeft = playerCharactersFolder + "warriorJumpLeft";
    public static final String warriorWalkRight = playerCharactersFolder + "warriorWalkRight";
    public static final String warriorWalkLeft = playerCharactersFolder + "warriorWalkLeft";
    public static final String warriorFallRight = playerCharactersFolder + "warriorFallRight";
    public static final String warriorFallLeft = playerCharactersFolder + "warriorFallLeft";

    public static final String ninjaIdle = playerCharactersFolder + "ninjaIdle";
    public static final String ninjaJumpRight = playerCharactersFolder + "ninjaJumpRight";
    public static final String ninjaJumpLeft = playerCharactersFolder + "ninjaJumpLeft";
    public static final String ninjaWalkRight = playerCharactersFolder + "ninjaWalkRight";
    public static final String ninjaWalkLeft = playerCharactersFolder + "ninjaWalkLeft";
    public static final String ninjaFallRight = playerCharactersFolder + "ninjaFallRight";
    public static final String ninjaFallLeft = playerCharactersFolder + "ninjaFallLeft";

    public static final String lancerIdle = playerCharactersFolder + "lancerIdle";
    public static final String lancerJumpRight = playerCharactersFolder + "lancerJumpRight";
    public static final String lancerJumpLeft = playerCharactersFolder + "lancerJumpLeft";
    public static final String lancerWalkRight = playerCharactersFolder + "lancerWalkRight";
    public static final String lancerWalkLeft = playerCharactersFolder + "lancerWalkLeft";
    public static final String lancerFallRight = playerCharactersFolder + "lancerFallRight";
    public static final String lancerFallLeft = playerCharactersFolder + "lancerFallLeft";







}
