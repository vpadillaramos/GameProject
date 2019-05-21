package com.vpr.pruebatiles.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
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
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.kotcrab.vis.ui.VisUI;
import com.rafaskoberg.gdx.typinglabel.TypingLabel;
import com.vpr.pruebatiles.entities.Player;
import com.vpr.pruebatiles.handlers.GameKeys;
import com.vpr.pruebatiles.handlers.MyContactListener;
import com.vpr.pruebatiles.handlers.MyGameInputProcessor;
import com.vpr.pruebatiles.managers.GameStateManager;
import com.vpr.pruebatiles.managers.R;
import com.vpr.pruebatiles.util.BodyCreator;
import com.vpr.pruebatiles.util.CameraMethods;
import com.vpr.pruebatiles.util.TiledObjectUtil;
import com.vpr.pruebatiles.windows.ShopWindow;

import static com.vpr.pruebatiles.util.Constantes.PPM;

public class PlayState extends GameState {

    // Constants

    // Atributes
    public enum State {
        PLAYING, PAUSE
    }
    public State state;

    private float cameraZoom = 6;

    // Physics
    private float gravity = -9.8f;

    // World
    private Box2DDebugRenderer b2dr;
    private World world;

    // Player
    public Player player;
    private Vector2 spawnPoint;

    // Map
    private OrthogonalTiledMapRenderer tmr;
    private TiledMap map;
    private MapObjects collisionTiles;
    private int mapTilesWidth, mapTilesHeight; // amount of tiles
    private float mapWidth, mapHeight, tileWidth, tileHeight; // pixels
    private Vector3 startOfMap, endOfMap;

    // Listeners, managers
    private InputMultiplexer multiplexer;
    private MyGameInputProcessor inputProcessor;
    private MyContactListener contactListener;

    // Text printing
    private Stage stage;
    private Skin skin;
    private BitmapFont font;
    private TypingLabel typingLabel;

    // Shop Test
    //private Body shop;
    private ShopWindow shopWindow;


    public PlayState(GameStateManager gsm) {
        super(gsm);
        inputProcessor = new MyGameInputProcessor();

        // Initialization
        state = State.PLAYING;
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        // box2d initialization
        initBox2d();

        // map initialization
        initTiledMap("levels/level1.tmx");

        // player initialization
        player = new Player(world, spawnPoint, contactListener, inputProcessor);

        //camera.position.set(endOfMap.x - camera.viewportWidth, endOfMap.y - mapHeight, endOfMap.z);
        //camera.position.set(startOfMap);

        // Text test
        initTextPrinter();
        String text = "{SPEED=0.5}Hola, que tal, {COLOR=RED}esto{WAIT} es una prueba de {SHAKE}Typing label";
        typingLabel = new TypingLabel(text, skin);
        typingLabel.setWrap(true);
        typingLabel.setWidth(100);
        typingLabel.setPosition((camera.viewportWidth / 2),
                (camera.viewportHeight / 2));
        stage.addActor(typingLabel);


        // Shop test
        if(!VisUI.isLoaded())
            VisUI.load();
        shopWindow = new ShopWindow(this, stage, skin, "Perkadona");

        // Add input processors
        multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(inputProcessor);
        multiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    public void update(float dt) {


        if(player.openShop()) {
            shopWindow.show(true);
            pause();
        }

        switch (state){
            case PAUSE:
                if(player.closeShop()) {
                    shopWindow.show(false);
                    playing();
                }
                break;
            case PLAYING:
                // stepping updates objects through the time
                world.step(1 / 60f, 6, 2); // 60fps, 6, 2 normalized values
                cameraUpdate();
                tmr.setView(camera);
                batch.setProjectionMatrix(camera.combined);
                manageInput(dt);
                break;

            default:

                break;
        }
    }

    @Override
    public void render(float dt) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tmr.render();
        batch.begin();
        player.draw(batch);
        batch.end();
        b2dr.render(world, camera.combined.scl(PPM));

        // Text test
        stage.act(dt);
        stage.draw();
    }

    @Override
    public void dispose() {
        b2dr.dispose();
        world.dispose();
        tmr.dispose();
        map.dispose();
        stage.dispose();
        font.dispose();
        VisUI.dispose();
    }

    public void pause(){
        state = State.PAUSE;
    }

    public void playing(){
        state = State.PLAYING;
    }

    public void cameraUpdate(){
        CameraMethods.lerpToTarget(camera, player.getPosition());
        camera.zoom = cameraZoom;
        float startX = camera.viewportWidth * 3; // TODO hardcoded
        float startY = camera.viewportHeight * 3;
        //CameraMethods.setCameraBounds(camera, startX, startY, mapWidth - camera.viewportWidth, mapHeight - camera.viewportHeight);
        CameraMethods.setCameraBounds(camera, startX, startY, mapWidth, mapHeight);
    }

    public void manageInput(float dt){
        managePlayerInput();
        manageCameraInput();
    }


    private void managePlayerInput(){
        player.checkKeys();
        GameKeys.update();
        /*if(player.inShop) {
            shopWindow.show(true);
            state = State.PAUSE;
        }*/
    }

    private void manageCameraInput(){
        if(Gdx.input.isKeyPressed(Input.Keys.A))
            camera.position.x -= 5;
        if(Gdx.input.isKeyPressed(Input.Keys.D))
            camera.position.x += 5;
        if(Gdx.input.isKeyPressed(Input.Keys.W))
            camera.position.y += 5;
        if(Gdx.input.isKeyPressed(Input.Keys.S))
            camera.position.y -= 5;

        if(Gdx.input.isKeyPressed(Input.Keys.Z))
            camera.zoom -= .1f;
        if(Gdx.input.isKeyPressed(Input.Keys.X))
            camera.zoom += .1f;

    }

    public void initBox2d(){
        // world
        world = new World(new Vector2(0, gravity), false);

        // Contact listener
        contactListener = new MyContactListener();
        world.setContactListener(contactListener);

        // Box2 renderer
        b2dr = new Box2DDebugRenderer();
        b2dr.SHAPE_STATIC.set(0, 0, 1, 0); // set static bodies' color blue
        b2dr.SHAPE_AWAKE.set(1, 0, 0, 0); // set dynamic bodie's color red
    }

    public void initTiledMap(String level){
        loadLevel(level); // load the current level
        initMapMeasures();

        tmr = new OrthogonalTiledMapRenderer(map); // map renderer

        // load collisions
        MapLayer collisionLayer = map.getLayers().get("collisions");
        MapLayer shopLayer = map.getLayers().get("shops");
        collisionTiles = collisionLayer.getObjects(); // obtain collision tiles to detect collisions

        setSpawnPoint();
        TiledObjectUtil.parseTiledObjectLayer(world, collisionLayer.getObjects());
        TiledObjectUtil.loadShops(world, shopLayer.getObjects());
    }

    private void loadLevel(String level){
        map = new TmxMapLoader().load(level);
    }

    private void initMapMeasures(){
        mapTilesWidth = map.getProperties().get("width", Integer.class);
        mapTilesHeight = map.getProperties().get("height", Integer.class);
        tileWidth = map.getProperties().get("tilewidth", Integer.class);
        tileHeight = map.getProperties().get("tileheight", Integer.class);


        mapWidth = mapTilesWidth * tileWidth;
        mapHeight = mapTilesHeight * tileHeight;
        //startOfMap = new Vector3(camera.viewportWidth / SCALE, camera.viewportHeight / SCALE, 0);
        //endOfMap = new Vector3(startOfMap.x + mapWidth, startOfMap.y + mapHeight, 0);
        startOfMap = new Vector3(0, 0, 0);
        endOfMap = new Vector3(startOfMap.x + mapWidth, startOfMap.y + mapHeight, 0);
    }

    private void setSpawnPoint(){
        // Sets de player spawn point
        Rectangle spawnPosition = ((RectangleMapObject)map.getLayers().get("player_spawn").getObjects().get("spawn_point")).getRectangle();
        spawnPoint = new Vector2();
        spawnPoint.set(new Vector2(spawnPosition.x, spawnPosition.y));
    }

    public void initTextPrinter(){
        stage = new Stage(); // where TypingLabels are added
        initFonts("fonts/alterebro.ttf", 30);
        initSkin("ui/uiskin.atlas", "ui/uiskin.json", font);
    }

    private void initFonts(String ttfFont, int size){
        font = new BitmapFont();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(ttfFont));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        params.size = size;
        font = generator.generateFont(params);
        generator.dispose();
    }

    private void initSkin(String skinAtlas, String skinJson, BitmapFont bitmapFont){
        skin = new Skin();
        skin.add("default-font", bitmapFont, BitmapFont.class);
        skin.addRegions(R.getTextureAtlas(skinAtlas));
        skin.load(Gdx.files.internal(skinJson));
        VisUI.load(skin);
    }
}
