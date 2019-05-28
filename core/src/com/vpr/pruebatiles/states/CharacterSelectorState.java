package com.vpr.pruebatiles.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.vpr.pruebatiles.managers.CharacterRecord;
import com.vpr.pruebatiles.managers.GameStateManager;
import com.vpr.pruebatiles.managers.SkinManager;
import com.vpr.pruebatiles.util.Constantes;

public class CharacterSelectorState extends GameState {

    // Components
    private Stage stage;
    private SkinManager skinManager;

    private Image characterImage;
    private TextButton nextButton;
    private TextButton backButton;
    private TextButton startPlaying;

    private String currentPlayerType;

    public CharacterSelectorState(final GameStateManager gsm) {
        super(gsm);
        skinManager = new SkinManager();
        stage = new Stage();
        currentPlayerType = gsm.playerType.name(); // default

        Table menu = new Table();
        menu.setFillParent(true);
        stage.addActor(menu);

        HorizontalGroup horizontalGroup = new HorizontalGroup();
        horizontalGroup.space(30);

        characterImage = new Image(gsm.charactersRegion.get(CharacterRecord.characters[gsm.currentCharacter].name));
        nextButton = new TextButton(">>>>>", skinManager.skin);
        backButton = new TextButton("<<<<<", skinManager.skin);
        startPlaying = new TextButton("PLAY", skinManager.skin);

        nextButton.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y) {
                gsm.currentCharacter += 1;
                if(gsm.currentCharacter == CharacterRecord.characters.length)
                    gsm.currentCharacter = 0;

                currentPlayerType = CharacterRecord.characters[gsm.currentCharacter].name;
                characterImage.setDrawable(new TextureRegionDrawable(gsm.charactersRegion.get(currentPlayerType)));
            }
        });

        backButton.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y) {
                gsm.currentCharacter -= 1;
                if(gsm.currentCharacter < 0)
                    gsm.currentCharacter = CharacterRecord.characters.length - 1;

                currentPlayerType = CharacterRecord.characters[gsm.currentCharacter].name;
                characterImage.setDrawable(new TextureRegionDrawable(gsm.charactersRegion.get(currentPlayerType)));
            }
        });

        startPlaying.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gsm.playerType = Constantes.CharacterType.valueOf(currentPlayerType);
                gsm.setState(GameStateManager.State.PLAYING_HUB);
            }
        });


        // Add the components
        horizontalGroup.addActor(backButton);
        horizontalGroup.addActor(characterImage);
        horizontalGroup.addActor(nextButton);

        menu.row();
        menu.add(horizontalGroup);
        menu.row();
        menu.add(startPlaying).center().width(200).height(80).pad(5);


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
