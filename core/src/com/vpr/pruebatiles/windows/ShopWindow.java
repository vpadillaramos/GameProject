package com.vpr.pruebatiles.windows;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.vpr.pruebatiles.managers.R;
import com.vpr.pruebatiles.managers.SkinManager;
import com.vpr.pruebatiles.states.HubState;
import com.vpr.pruebatiles.util.Constantes;

public class ShopWindow {

    // Attributes
    public HubState hubState;
    public SkinManager skinManager;
    public Skin skin;
    public String title;
    public Window window;
    public Action action;

    // Constructor
    public ShopWindow(HubState hubState, SkinManager skinManager, Skin skin, String title){
        this.hubState = hubState;
        this.skinManager = skinManager;
        this.skin = skin;
        this.title = title;
        action = new MoveToAction();

        initWindow();
    }

    // Methods
    private void initWindow(){
        window = new Window(title, skin);
        window.setVisible(false);
        window.setKeepWithinStage(false);
        window.setMovable(false);
        window.getTitleLabel().setAlignment(Align.center);

        // Layout
        Stack layout = new Stack();

        // Add buy button and price label
        HorizontalGroup group = new HorizontalGroup();
        group.addActor(new TextButton("Comprar", skin));
        group.addActor(new Label("12", skin));
        group.space(20);
        group.align(Align.bottom);
        group.padBottom(10);

        layout.add(group);


        // Add scrollpane perks
        HorizontalGroup perkGroup = new HorizontalGroup();
        perkGroup.addActor(new ImageButton(new TextureRegionDrawable(R.getRegion(Constantes.ninjaIdle)),
                new TextureRegionDrawable(R.getRegion(Constantes.ninjaIdle))));
        perkGroup.addActor(new ImageButton(new TextureRegionDrawable(R.getRegion(Constantes.ninjaIdle))));
        perkGroup.addActor(new ImageButton(new TextureRegionDrawable(R.getRegion(Constantes.ninjaIdle))));
        perkGroup.addActor(new ImageButton(new TextureRegionDrawable(R.getRegion(Constantes.ninjaIdle))));
        perkGroup.addActor(new ImageButton(new TextureRegionDrawable(R.getRegion(Constantes.ninjaIdle))));
        perkGroup.addActor(new ImageButton(new TextureRegionDrawable(R.getRegion(Constantes.ninjaIdle))));
        perkGroup.wrap(true);
        perkGroup.align(Align.center);
        layout.add(perkGroup);


        window.addActor(layout);
        skinManager.stage.addActor(window);

    }

    public void show(boolean b){

        if(b){
            window.setVisible(true);
            window.setPosition((skinManager.stage.getWidth() / 2) - (window.getWidth() / 2),
                    skinManager.stage.getHeight());

            /*window.addAction(Actions.moveTo((stage.getWidth() / 2) - (window.getWidth() / 2),
                    (stage.getHeight() / 2) - (window.getHeight() / 2), .1f));*/
            action = Actions.moveTo((skinManager.stage.getWidth() / 2) - (window.getWidth() / 2),
                    (skinManager.stage.getHeight() / 2) - (window.getHeight() / 2), .1f);
            window.addAction(action);
        }
        else {
            Action completeAction = new Action(){
                public boolean act( float delta ) {
                    window.setVisible(false);
                    return true;
                }
            };

            action = Actions.sequence(Actions.moveTo((skinManager.stage.getWidth() / 2) - (window.getWidth() / 2),
                    skinManager.stage.getHeight(), .1f),
                    completeAction);
            window.addAction(action);

            //window.addAction(Actions.moveTo((stage.getWidth() / 2) - (window.getWidth() / 2), stage.getHeight(), .1f));

        }
    }

}
