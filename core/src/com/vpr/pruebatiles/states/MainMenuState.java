package com.vpr.pruebatiles.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.vpr.pruebatiles.managers.GameStateManager;
import com.vpr.pruebatiles.managers.SkinManager;

public class MainMenuState extends GameState {


    // Componentes
    private Stage stage;
    private SkinManager skinManager;

    // Constructor
    public MainMenuState(final GameStateManager gsm) {
        super(gsm);
        skinManager = new SkinManager();

        stage = new Stage();

        Table menu = new Table();
        menu.setFillParent(true);
        stage.addActor(menu);

        // BOTON JUGAR
        TextButton btJugar = new TextButton("Jugar", skinManager.skin);
        btJugar.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gsm.setState(GameStateManager.State.PLAYING_HUB);
            }
        });

        // BOTON SALIR
        TextButton btSalir = new TextButton("Salir", skinManager.skin);
        btSalir.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                System.exit(0);
            }
        });

        // BOTON CONFIGURACION
        TextButton btConfiguracion = new TextButton("Configuracion", skinManager.skin);
        btConfiguracion.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });

        // TITULO
        VisLabel lbTitulo = new VisLabel("Juego");

        // Creditos
        VisLabel lbCreditos = new VisLabel("Victor Padilla");
        stage.addActor(lbCreditos);


        // MENU
        menu.row();
        menu.add(lbTitulo).center().width(200).height(100).pad(5);
        menu.row();
        menu.add(btJugar).center().width(200).height(80).pad(5);
        menu.row();
        menu.add(btSalir).center().width(200).height(80).pad(5);
        menu.row();
        menu.add(btConfiguracion).center().width(200).height(80).pad(5);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(float dt) {
        // Pintar
        Gdx.gl.glClearColor(0.3f, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(dt);
        stage.draw();

        skinManager.render(dt);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
