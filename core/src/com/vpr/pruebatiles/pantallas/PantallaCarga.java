package com.vpr.pruebatiles.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.vpr.pruebatiles.Tiles;
import com.vpr.pruebatiles.managers.R;

public class PantallaCarga implements Screen {

    // Atributos
    private ShapeRenderer shapeRenderer;
    private float progress;

    // Constructor
    public PantallaCarga(){
        shapeRenderer = new ShapeRenderer();
        progress = 0;
    }

    public void update(float dt){
        progress = MathUtils.lerp(progress, R.assets.getProgress(), 0.1f);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float dt) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(dt);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(32, Gdx.graphics.getHeight() / 2 - 8, Gdx.graphics.getWidth() - 64, 16);

        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(32, Gdx.graphics.getHeight() / 2 - 8, progress * (Gdx.graphics.getWidth() - 64), 16);

        shapeRenderer.end();
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
        shapeRenderer.dispose();
    }
}
