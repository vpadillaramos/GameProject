package com.vpr.pruebatiles.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.kotcrab.vis.ui.VisUI;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import com.vpr.pruebatiles.managers.GameStateManager;
import com.vpr.pruebatiles.managers.R;
import com.vpr.pruebatiles.util.Constantes;

import java.util.HashMap;

public class LoadingState extends GameState {

    // Attributes
    private ShapeRenderer shapeRenderer;
    private float progress;
    private float acc;
    private GameStateManager.State nextState;

    public LoadingState(GameStateManager gsm, GameStateManager.State nextState) {
        super(gsm, nextState);
        this.nextState = nextState;

        shapeRenderer = new ShapeRenderer();
        progress = 0;
        acc = 0;

        R.cargarRecursos();
    }

    @Override
    public void update(float dt) {
        progress = MathUtils.lerp(progress, R.assets.getProgress(), .1f);
        acc += dt;

        if(R.update() && progress >= R.assets.getProgress() - .01f){
            if(acc >= 3){
                initCharactersRegion();
                gsm.setState(nextState);
            }
        }
    }

    @Override
    public void render(float dt) {
        Gdx.gl.glClearColor(.25f, .25f, .25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // progress bar's border
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(32, Gdx.graphics.getHeight() / 2 - 8, Gdx.graphics.getWidth() - 64, 16);
        // progress bar's fill
        shapeRenderer.setColor(Color.PINK);
        shapeRenderer.rect(32, Gdx.graphics.getHeight() / 2 - 8, progress * (Gdx.graphics.getWidth() - 64), 16);

        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }

    private void initCharactersRegion(){
        gsm.charactersRegion.put(Constantes.CharacterType.ninja.name(), R.getRegion(Constantes.ninjaCharacter));
        gsm.charactersRegion.put(Constantes.CharacterType.warrior.name(), R.getRegion(Constantes.warriorCharacter));
        gsm.charactersRegion.put(Constantes.CharacterType.lancer.name(), R.getRegion(Constantes.lancerCharacter));
    }


}
