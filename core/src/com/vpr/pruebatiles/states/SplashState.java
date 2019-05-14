package com.vpr.pruebatiles.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.vpr.pruebatiles.managers.GameStateManager;
import com.vpr.pruebatiles.managers.R;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/*public class SplashState extends GameState {

    // Attributes
    private float acc = 0;

    // Atributos
    private Texture splashTexture;
    private Image splashImage;
    private Stage stage;
    private boolean splashDone;

    private ShapeRenderer shapeRenderer;
    private float progress;

    // Constructor
    public SplashState(GameStateManager gsm) {
        super(gsm);

        splashTexture = new Texture(Gdx.files.internal("splash/splash.jpg"));
        splashImage = new Image(splashTexture);

        shapeRenderer = new ShapeRenderer();
        progress = 0;

        stage = new Stage();

        Table table = new Table();
        table.setFillParent(true);
        table.center();

        // Mostar la imagen con animacion
        splashImage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(1f),
                Actions.delay(1.5f), Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        splashDone = true;
                    }
                })));

        table.row().height(splashTexture.getHeight());
        table.add(splashImage).center();
        stage.addActor(table);
        R.cargarRecursos();

    }

    // Methods
    @Override
    public void update(float dt) {
        progress = MathUtils.lerp(progress, R.assets.getProgress(), 0.1f);
        acc += dt;

        // Comprueba si han cargado los datos
        if(R.update() && progress >= R.assets.getProgress() - .01f) {
            // Si la animacion ha terminado se muestra el menu principal
            if (splashDone) {
                // after 3 seconds, the game screen is showed
                if (acc >= 3) {
                    gsm.setState(GameStateManager.State.PLAY);
                }
                //((Tiles) Gdx.app.getApplicationListener()).setScreen(new PantallaJuego());
            }
        }
    }

    @Override
    public void render(float dt) {
        Gdx.gl.glClearColor(.25f, .25f, .25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(32, Gdx.graphics.getHeight() / 2 - 8, Gdx.graphics.getWidth() - 64, 16);

        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(32, Gdx.graphics.getHeight() / 2 - 8, progress * (Gdx.graphics.getWidth() - 64), 16);

        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        splashTexture.dispose();
        stage.dispose();
        shapeRenderer.dispose();
    }


}*/


public class SplashState extends GameState {

    // Attributes
    private Stage stage;
    private Image splashImage;

    public SplashState(final GameStateManager gsm) {
        super(gsm);
        stage = new Stage();


        Texture splashTexture = new Texture(Gdx.files.internal("splash/splash2.png"));
        splashImage = new Image(splashTexture);

        Runnable transitionRunnable = new Runnable() {
            @Override
            public void run() {
                gsm.setState(GameStateManager.State.PLAY);
            }
        };

        // animation
        splashImage.setOrigin(splashImage.getWidth() / 2, splashImage.getHeight() / 2);
        splashImage.setPosition(stage.getWidth() / 2 - 32, stage.getHeight() + 32);
        splashImage.addAction(Actions.sequence(alpha(0), scaleTo(.1f, .1f),
                parallel(fadeIn(2, Interpolation.pow2),
                        scaleTo(2, 2, 2.5f, Interpolation.pow5),
                        moveTo(stage.getWidth() / 2 - 32, stage.getHeight() / 2 - 32, 2, Interpolation.swing)),
                delay(3f), fadeOut(1.25f), run(transitionRunnable)
        ));

        stage.addActor(splashImage);

    }

    @Override
    public void update(float dt) {
        stage.act(dt);
    }

    @Override
    public void render(float dt) {
        Gdx.gl.glClearColor(1, 1, 1, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(dt);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
