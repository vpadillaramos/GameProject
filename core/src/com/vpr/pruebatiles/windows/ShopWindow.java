package com.vpr.pruebatiles.windows;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.vpr.pruebatiles.managers.CameraManager;
import com.vpr.pruebatiles.managers.R;
import com.vpr.pruebatiles.managers.SkinManager;
import com.vpr.pruebatiles.states.HubState;
import com.vpr.pruebatiles.util.Constantes;

import java.util.HashMap;

public class ShopWindow {

    // Attributes
    private HubState hubState;
    private SkinManager skinManager;
    private CameraManager cameraManager;
    public String title;
    public Window window;
    public Action action;

    // Components
    private Stack layout;
    private HashMap<String, ImageButton> perkButtons;
    public Label coinsLabel;
    public Label perkLabel;

    // Constructor
    public ShopWindow(HubState hubState, SkinManager skinManager, CameraManager cameraManager, String title){
        this.hubState = hubState;
        this.skinManager = skinManager;
        this.cameraManager = cameraManager;
        this.title = title;
        action = new MoveToAction();

        initWindow();
    }

    // Methods

    public void setCoinsLabel(String coins){
        coinsLabel.setText(coins);
    }

    private void initWindow(){
        windowSetUp();

        // Layout
        layout = new Stack();

        // Add buy button and price label
        HorizontalGroup group = new HorizontalGroup();
        group.addActor(new TextButton("Comprar", skinManager.skin));

        coinsLabel = new Label(String.valueOf(hubState.player.coins) + "$", skinManager.skin);
        group.addActor(coinsLabel);
        group.space(20);
        group.align(Align.bottom);
        group.padBottom(10);



        HorizontalGroup g = new HorizontalGroup();
        perkLabel = new Label("Select your perk", skinManager.skin);
        g.addActor(perkLabel);
        g.top();

        //layout.add(g);
        window.row().padBottom(10);
        window.add(perkLabel);
        window.row().padBottom(10);
        window.add(initPerks());
        window.row().padTop(10).padBottom(10);
        window.add(group);

        //window.add(layout);
        skinManager.stage.addActor(window);
    }

    public void show(boolean b){
        if(b){
            window.setVisible(true);
            window.setPosition((skinManager.stage.getWidth() / 2) - (window.getWidth() / 2),
                    skinManager.stage.getHeight());

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

        }
    }

    public void windowSetUp(){
        window = new Window(title, skinManager.skin);
        window.sizeBy(cameraManager.camera.viewportWidth / 2, cameraManager.camera.viewportHeight / 2);
        window.setVisible(false);
        window.setKeepWithinStage(false);
        window.setMovable(false);
        window.getTitleLabel().setAlignment(Align.center);
    }

    public HorizontalGroup initPerks(){

        ButtonGroup<ImageButton> buttonGroup = new ButtonGroup<ImageButton>();
        HorizontalGroup perkGroup = new HorizontalGroup();
        buttonGroup.setMinCheckCount(0);
        buttonGroup.setMaxCheckCount(1);

        // as many buttons as perks
        perkButtons = new HashMap<String, ImageButton>();
        for(String perk : Constantes.perks){
            ImageButton ib = new ImageButton(new TextureRegionDrawable(R.getRegion(perk+"1")), // this for button up
                    new TextureRegionDrawable(R.getRegion(perk+"2"))); // this for button down
            perkButtons.put(perk, ib); // add button to hashmap
            buttonGroup.add(ib); // add button to buttonGroup
            perkGroup.addActor(ib); // add button to layout group
        }

        perkGroup.padTop(20);
        perkGroup.padRight(20);
        perkGroup.padLeft(20);
        perkGroup.space(10);
        //layout.add(perkGroup);

        return perkGroup;
    }

    public void handleButtonClicks(){
        perkButtons.get(Constantes.doubleJumpPerk).addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                perkLabel.setText(Constantes.doubleJumpPerkShopName);
            }
        });

        perkButtons.get(Constantes.dashPerk).addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                perkLabel.setText(Constantes.dashPerkShopName);
            }
        });
    }
}
