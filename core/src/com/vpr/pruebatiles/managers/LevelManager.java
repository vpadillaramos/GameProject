package com.vpr.pruebatiles.managers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.vpr.pruebatiles.util.Constantes;
import com.vpr.pruebatiles.util.TiledObjectUtil;

public class LevelManager {

    // Constants

    // References
    private World world;

    // Attributes
    private OrthogonalTiledMapRenderer tmr;
    private TiledMap map;

    private int mapTilesWidth, mapTilesHeight; // amount of tiles
    private float mapWidth, mapHeight, tileWidth, tileHeight; // pixels
    private Vector3 startOfMap, endOfMap;

    // Layers
    private MapLayer collisionLayer;
    private MapLayer shopLayer;

    // Constructor
    public LevelManager(World world, String initialMap){

        // references
        this.world = world;

        // load the first level/map
        map = new TmxMapLoader().load(initialMap);
        // init renderer
        tmr = new OrthogonalTiledMapRenderer(map);
        //init TileMap layers
        initCollisionLayer();
        initShopLayer();

    }

    // Methods
    public void render(){
        tmr.render();
    }

    public void update(OrthographicCamera camera){
        tmr.setView(camera);
    }

    public void dispose(){
        tmr.dispose();
        map.dispose();
    }

    private void initCollisionLayer(){
        collisionLayer = map.getLayers().get(Constantes.collisions_layer);
        MapObjects collisionTiles = collisionLayer.getObjects();
        TiledObjectUtil.parseTiledObjectLayer(world, collisionTiles);
    }

    private void initShopLayer(){
        shopLayer = map.getLayers().get(Constantes.shops_layer);
        MapObjects shopTiles = shopLayer.getObjects();
        TiledObjectUtil.loadShops(world, shopTiles);
    }

    private void initMapMeasures(){

    }

    public Vector2 getPlayerSpawnPoint(){
        Rectangle spawnRectangle = ((RectangleMapObject)map.getLayers().get(Constantes.player_spawn_layer)
                .getObjects().get(Constantes.spawn_point)).getRectangle();

        Vector2 spawnPoint = new Vector2(spawnRectangle.x, spawnRectangle.y);
        return spawnPoint;
    }
}
