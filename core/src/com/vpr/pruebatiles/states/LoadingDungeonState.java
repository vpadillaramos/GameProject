package com.vpr.pruebatiles.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.vpr.pruebatiles.managers.GameStateManager;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class LoadingDungeonState extends GameState {

    // Attributes
    private Stage stage;
    private Image splashImage;


    public LoadingDungeonState(final GameStateManager gsm, final GameStateManager.State nextState) {
        super(gsm, nextState);
        stage = new Stage();
        Texture imageTexture = new Texture(Gdx.files.internal("splash/loadingDungeon.png"));
        splashImage = new Image(imageTexture);
        //imageTexture.dispose(); TODO don't dispose this!!!!!!!!!

        Runnable transitionRunnable = new Runnable() {
            @Override
            public void run() {
                gsm.setState(nextState);
            }
        };

        splashImage.setPosition((stage.getWidth() / 2) - (splashImage.getWidth() / 2),
                (stage.getHeight() / 2) - (splashImage.getHeight() / 2));

        splashImage.addAction(sequence(
                alpha(0),
                fadeIn(2, Interpolation.pow2),
                rotateTo(-360 * 2, 3 * 2),
                fadeOut(2, Interpolation.pow2),
                run(transitionRunnable)
        ));

        stage.addActor(splashImage);
        stage.getActors().get(0).setOrigin(splashImage.getWidth() / 2, splashImage.getHeight() / 2);
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
