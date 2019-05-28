package com.vpr.pruebatiles.states;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.vpr.pruebatiles.managers.GameStateManager;
import com.vpr.pruebatiles.managers.SkinManager;

public class GameSavesState extends GameState {


    // Components
    private Stage stage;
    private SkinManager skinManager;

    public GameSavesState(GameStateManager gsm, String map) {
        super(gsm, map);
        stage = new Stage();
        skinManager = new SkinManager();

        Table menu = new Table();
        menu.setFillParent(true);
        stage.addActor(menu);


    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(float dt) {

    }

    @Override
    public void dispose() {

    }
}
