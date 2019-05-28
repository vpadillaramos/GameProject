package com.vpr.pruebatiles.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.vpr.pruebatiles.handlers.MyContactListener;
import com.vpr.pruebatiles.managers.R;
import com.vpr.pruebatiles.util.Constantes;

import static com.vpr.pruebatiles.util.Constantes.PPM;

public class BasicEntity {

    // Attributes
    public Body body;
    public TextureRegion texture;
    public float width, height;
    public Vector2 spawnPoint;
    public MyContactListener contactListener;


    // Constructor
    public BasicEntity(String textureName, World world, Vector2 spawnPoint, MyContactListener contactListener){
        this.texture = R.getRegion(textureName);
        this.spawnPoint = new Vector2(spawnPoint);
        this.contactListener = contactListener;

        width = texture.getRegionWidth();
        height = texture.getRegionHeight();
    }

    public BasicEntity(Constantes.CharacterType type , World world, Vector2 spawnPoint, MyContactListener contactListener){

        switch (type){
            case ninja:
                this.texture = R.getRegion(Constantes.ninjaIdle);
                break;
            case warrior:
                this.texture = R.getRegion(Constantes.warriorIdle);
                break;
            case lancer:
                this.texture = R.getRegion(Constantes.lancerIdle);
                break;
        }

        this.spawnPoint = new Vector2(spawnPoint);
        this.contactListener = contactListener;

        width = texture.getRegionWidth();
        height = texture.getRegionHeight();
    }

    // Methods
    public Vector2 getPosition(){
        return body.getPosition();
    }

    public void setTexture(TextureRegion texture){
        this.texture = texture;
    }

    public void draw(SpriteBatch batch){
        batch.draw(texture, (body.getPosition().x - width / 2 / PPM),
                (body.getPosition().y - height / 2 / PPM), width / PPM, height / PPM);
    }

    public void dispose(){
        texture.getTexture().dispose();
    }

}
