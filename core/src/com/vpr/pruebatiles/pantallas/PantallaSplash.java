package com.vpr.pruebatiles.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.vpr.pruebatiles.Tiles;
import com.vpr.pruebatiles.managers.R;

public class PantallaSplash implements Screen {

    // Atributos
    private Texture splashTexture;
    private Image splashImage;
    private Stage stage;
    private boolean splashDone;

    private ShapeRenderer shapeRenderer;
    private float progress;


    // Constructor
    public PantallaSplash(){
        splashTexture = new Texture(Gdx.files.internal("splash/splash.jpg"));
        splashImage = new Image(splashTexture);

        shapeRenderer = new ShapeRenderer();
        progress = 0;
    }

    @Override
    public void show() {
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

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 1, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_CLEAR_VALUE);

        stage.act(delta);
        stage.draw();

        update();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(32, Gdx.graphics.getHeight() / 2 - 8, Gdx.graphics.getWidth() - 64, 16);

        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(32, Gdx.graphics.getHeight() / 2 - 8, progress * (Gdx.graphics.getWidth() - 64), 16);

        shapeRenderer.end();

    }

    public void update(){
        progress = MathUtils.lerp(progress, R.assets.getProgress(), 0.1f);

        // Comprueba si han cargado los datos
        if(R.update() && progress >= R.assets.getProgress() - .01f){
            // Si la animacion ha terminado se muestra el menu principal
            if(splashDone)
                ((Tiles) Gdx.app.getApplicationListener()).setScreen(new PantallaJuego());
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        splashTexture.dispose();
        stage.dispose();
    }
}
