package com.vpr.pruebatiles.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.vpr.pruebatiles.managers.GameStateManager;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class SplashState extends GameState {

    // Attributes
    private Stage stage;
    private Image splashImage;

    public SplashState(final GameStateManager gsm, final GameStateManager.State nextState) {
        super(gsm, nextState);
        stage = new Stage();


        Texture splashTexture = new Texture(Gdx.files.internal("splash/splash2.png"));
        splashImage = new Image(splashTexture);

        Runnable transitionRunnable = new Runnable() {
            @Override
            public void run() {
                gsm.setState(nextState);
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
