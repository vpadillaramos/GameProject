package com.vpr.pruebatiles.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Player extends Sprite implements InputProcessor {

    // Atributos
    public Vector2 velocity;
    public float speed;
    public float gravity;
    public Vector2 position;
    public float velX;


    // Constructor
    public Player(Sprite sprite){
        super(sprite);
        speed = 5;
        position = new Vector2(5, 5);
        velX = 0;
    }

    // Metodos

    @Override
    public void draw(Batch batch) {
        update(Gdx.graphics.getDeltaTime());
        super.draw(batch);
    }

    public void update(float dt){

        // apply gravity and controls the vel never goes over or lower the speed
        velocity.y -= gravity * dt;
        if(velocity.y > speed)
            velocity.y = speed;
        else if(velocity.y < speed)
            velocity.y = -speed;

        setX(getX() + velocity.x * dt);
        setY(getY() + velocity.y * dt);

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
