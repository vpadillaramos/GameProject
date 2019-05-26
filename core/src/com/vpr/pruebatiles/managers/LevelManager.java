package com.vpr.pruebatiles.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.vpr.pruebatiles.util.Constantes;
import com.vpr.pruebatiles.util.TiledObjectUtil;

public class LevelManager {

    // Constants

    // References
    private World world;

    // Attributes
    private OrthogonalTiledMapRenderer tmr;
    public TiledMap map;

    private int mapTilesWidth, mapTilesHeight; // amount of tiles
    private float mapWidth, mapHeight, tileWidth, tileHeight; // pixels
    private Vector3 startOfMap, endOfMap;

    // Layers
    private MapLayer collisionLayer;
    private MapLayer shopLayer;
    private MapLayer doorsLayer;

    // Constructor
    public LevelManager(World world, String initialMap){
        // references
        this.world = world;

        // load the first level/map
        map = new TmxMapLoader().load(initialMap);

        // init renderer
        tmr = new OrthogonalTiledMapRenderer(map);

        loadMapLayers(initialMap);

    }

    // Methods
    public void render(){
        tmr.render();
    }

    public void update(OrthographicCamera camera){
        tmr.setView(camera);
    }

    public void dispose(){
        map.dispose();
        tmr.dispose();
    }

    private void loadMapLayers(String map){
        //init TileMap layers
        initCollisionLayer();
        initDoorsLayer();
        if(map.equals(Constantes.hubMap)){
            initShopLayer();
        }
    }

    private void initCollisionLayer(){
        collisionLayer = map.getLayers().get(Constantes.collisions_layer);
        MapObjects collisionTiles = collisionLayer.getObjects();
        TiledObjectUtil.parseTiledObjectLayer(world, collisionTiles);
    }

    private void initShopLayer(){
        shopLayer = map.getLayers().get(Constantes.shops_layer);
        MapObjects shopTiles = shopLayer.getObjects();
        TiledObjectUtil.loadSensors(world, shopTiles);
    }

    private void initDoorsLayer(){
        doorsLayer = map.getLayers().get(Constantes.doors_layer);
        MapObjects doorsTiles = doorsLayer.getObjects();
        TiledObjectUtil.loadSensors(world, doorsTiles);
    }

    private void initMapMeasures(){

    }

    public Vector2 getPlayerSpawnPoint(){
        Rectangle spawnRectangle = ((RectangleMapObject)map.getLayers().get(Constantes.spawn_layer)
                .getObjects().get(Constantes.player_spawn)).getRectangle();

        Vector2 spawnPoint = new Vector2(spawnRectangle.x, spawnRectangle.y);
        return spawnPoint;
    }

    public void setWorld(World world){
        this.world = world;
    }

    public void loadMap(final String newMap){
        /*map = new TmxMapLoader().load(mapName);
        tmr.setMap(map);
        loadMapLayers(mapName);*/


        map = new TmxMapLoader().load(newMap);
        tmr.getMap().dispose();
        tmr = new OrthogonalTiledMapRenderer(map);
        loadMapLayers(newMap);
    }
}
