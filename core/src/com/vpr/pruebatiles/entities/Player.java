package com.vpr.pruebatiles.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.vpr.pruebatiles.managers.R;
import com.vpr.pruebatiles.util.BodyCreator;

import static com.vpr.pruebatiles.util.Constantes.PPM;

public class Player {

    // Atributos
    public Body body;
    private TextureRegion texture;

    // Constructor
    public Player(World world){
        texture = R.getRegion("idle");
        body = BodyCreator.createBox(world, 40, 100, texture.getRegionWidth(), texture.getRegionHeight(), BodyDef.BodyType.DynamicBody);
    }

    // Metodos
    public Vector2 getPosition(){
        return body.getPosition();
    }

    public void draw(SpriteBatch batch) {
        // centers texture respect the body
        batch.draw(texture, (body.getPosition().x * PPM) - (texture.getRegionWidth() / 2),
                (body.getPosition().y * PPM) - (texture.getRegionHeight() / 2));
    }
}
