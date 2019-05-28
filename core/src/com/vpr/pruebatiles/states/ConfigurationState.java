package com.vpr.pruebatiles.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.vpr.pruebatiles.handlers.Configuration;
import com.vpr.pruebatiles.managers.GameStateManager;
import com.vpr.pruebatiles.managers.SkinManager;

public class ConfigurationState extends GameState {

    // Components
    private Stage stage;
    private SkinManager skinManager;
    private TextButton soundButton;
    private TextButton musicButton;
    private TextButton backButton;

    public ConfigurationState(final GameStateManager gsm, final GameStateManager.State nextState) {
        super(gsm, nextState);

        stage = new Stage();
        skinManager = new SkinManager();

        Table menu = new Table();
        menu.setFillParent(true);
        stage.addActor(menu);

        backButton = new TextButton("Back", skinManager.skin);
        musicButton = new TextButton("", skinManager.skin);
        soundButton = new TextButton("", skinManager.skin);

        menu.row();
        menu.add(soundButton).center().width(200).height(80).pad(5);
        menu.row();
        menu.add(musicButton).center().width(200).height(80).pad(5);
        menu.row();
        menu.add(backButton).center().width(200).height(80).pad(5);

        // Listeners
        musicButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Configuration.setMusicEnabled(!Configuration.isMusicEnabled());
                actualizarTextoBotones();
            }
        });

        soundButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Configuration.setSoundEnabled(!Configuration.isSoundEnabled());
                actualizarTextoBotones();
            }
        });

        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gsm.setState(nextState);
            }
        });

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void update(float dt) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(dt);
        stage.draw();
    }

    @Override
    public void render(float dt) {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
    public void actualizarTextoBotones(){
        String aux  = Configuration.isMusicEnabled()?"Desactivar música":"Activar música";
        musicButton.setText(aux);
        aux = Configuration.isSoundEnabled()?"Desactivar sonido":"Activar sonido";
        soundButton.setText(aux);
    }

}
