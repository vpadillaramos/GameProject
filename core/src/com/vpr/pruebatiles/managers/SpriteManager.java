package com.vpr.pruebatiles.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.vpr.pruebatiles.entities.Player;
import com.vpr.pruebatiles.util.Constantes;

public class SpriteManager {

    // Attributes
    private float time;

    private Player player;
    private Animation<TextureAtlas.AtlasRegion> playerJump;
    private Animation<TextureAtlas.AtlasRegion> playerFall;
    private Animation<TextureAtlas.AtlasRegion> playerWalkLeft;
    private Animation<TextureAtlas.AtlasRegion> playerWalkRight;


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
        time += dt;

        switch (player.getAction()){
            case JUMP:
                player.setTexture(playerJump.getKeyFrame(time, false));
                break;
            case FALLING:
                player.setTexture(playerFall.getKeyFrame(time, false));
                break;
            case WALK_LEFT:
                player.setTexture(playerWalkLeft.getKeyFrame(time, false));
                break;
            case WALK_RIGHT:
                player.setTexture(playerWalkRight.getKeyFrame(time, false));
                break;
            case IDLE:
                player.setTexture(R.getRegion(Constantes.ninjaIdle));
                break;
            default:
                player.setTexture(R.getRegion(Constantes.ninjaIdle));
                break;
        }
    }

    private void initPlayerAnimations(){
        playerJump = new Animation(1.15f, R.getAnimacion(Constantes.ninjaJump), Animation.PlayMode.NORMAL);
        playerFall = new Animation(0.1f, R.getAnimacion(Constantes.ninjaFall), Animation.PlayMode.NORMAL);
        playerWalkLeft = new Animation(0.1f, R.getAnimacion(Constantes.ninjaWalkLeft), Animation.PlayMode.LOOP);
        playerWalkRight = new Animation(0.1f, R.getAnimacion(Constantes.ninjaWalkRight), Animation.PlayMode.LOOP);
    }

}
