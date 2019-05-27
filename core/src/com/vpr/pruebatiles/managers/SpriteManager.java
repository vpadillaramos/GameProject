package com.vpr.pruebatiles.managers;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.vpr.pruebatiles.entities.Player;
import com.vpr.pruebatiles.util.Constantes;

public class SpriteManager {

    // Attributes
    private float time;

    private Player player;
    private Animation<TextureAtlas.AtlasRegion> playerJumpRight;
    private Animation<TextureAtlas.AtlasRegion> playerJumpLeft;
    private Animation<TextureAtlas.AtlasRegion> playerFallRight;
    private Animation<TextureAtlas.AtlasRegion> playerFallLeft;
    private Animation<TextureAtlas.AtlasRegion> playerWalkRight;
    private Animation<TextureAtlas.AtlasRegion> playerWalkLeft;


    // Constructor
    public SpriteManager(Player player){
        this.player = player;
        initPlayerAnimations();
    }


    // Methods
    public void update(float dt){
        updatePlayerAnimations(dt);
    }

    private void updatePlayerAnimations(float dt){
        TextureRegion currentTexture = new TextureRegion();
        time += dt;

        switch (player.getState()){
            case WALK_RIGHT:
                currentTexture = playerWalkRight.getKeyFrame(time, false);
                break;
            case WALK_LEFT:
                currentTexture = playerWalkLeft.getKeyFrame(time, false);
                break;
            case JUMP_RIGHT:
                currentTexture = playerJumpRight.getKeyFrame(time, false);
                break;
            case JUMP_LEFT:
                currentTexture = playerJumpLeft.getKeyFrame(time, false);
                break;
            case FALL_RIGHT:
                currentTexture = playerFallRight.getKeyFrame(time, false);
                break;
            case FALL_LEFT:
                currentTexture = playerFallLeft.getKeyFrame(time, false);
                break;
            case IDLE:
                currentTexture = R.getRegion(Constantes.ninjaIdle);
                break;
            default:

                break;
        }

        player.setTexture(currentTexture);
    }

    private void initPlayerAnimations(){
        playerJumpRight = new Animation(1.15f, R.getAnimacion(Constantes.ninjaJumpRight), Animation.PlayMode.NORMAL);
        playerJumpLeft = new Animation(1.15f, R.getAnimacion(Constantes.ninjaJumpLeft), Animation.PlayMode.NORMAL);
        playerFallRight = new Animation(0.1f, R.getAnimacion(Constantes.ninjaFallRight), Animation.PlayMode.NORMAL);
        playerFallLeft = new Animation(0.1f, R.getAnimacion(Constantes.ninjaFallLeft), Animation.PlayMode.NORMAL);
        playerWalkRight = new Animation(0.05f, R.getAnimacion(Constantes.ninjaWalkRight), Animation.PlayMode.LOOP);
        playerWalkLeft = new Animation(0.05f, R.getAnimacion(Constantes.ninjaWalkLeft), Animation.PlayMode.LOOP);
    }
}
