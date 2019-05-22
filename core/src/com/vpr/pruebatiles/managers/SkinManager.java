package com.vpr.pruebatiles.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.kotcrab.vis.ui.VisUI;
import com.vpr.pruebatiles.util.Constantes;

public class SkinManager {

    // Attributes
    public Stage stage;

    // skin
    public Skin skin;

    // font
    private BitmapFont font;
    private int fontSize;
    private FileHandle mainFont = Gdx.files.internal(Constantes.alterebroFont);


    // Constructor
    public SkinManager(){
        stage = new Stage();
        fontSize = 30;

        initFonts();
        initSkin();
        loadVisUI();
    }

    // Methods
    private void loadVisUI(){
        if(!VisUI.isLoaded()) {
            VisUI.load();
            //VisUI.load(skin);
        }
    }

    public void render(float dt){
        stage.act(dt);
        stage.draw();
    }

    public void dispose(){
        stage.dispose();
        font.dispose();
        VisUI.dispose();
    }

    private void initFonts(){
        font = new BitmapFont();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(mainFont);
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();

        params.size = fontSize;
        font = generator.generateFont(params);
        generator.dispose();
    }

    public void initSkin(){
        skin = new Skin();
        skin.add("default-font", font, BitmapFont.class);
        skin.addRegions(R.getTextureAtlas(Constantes.skinAtlas));
        skin.load(Gdx.files.internal(Constantes.skinJson));
    }

    public void setFontSize(int fontSize){
        this.fontSize = fontSize;
    }

}
