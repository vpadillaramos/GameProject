package com.vpr.pruebatiles.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.vpr.pruebatiles.entities.Player;
import com.vpr.pruebatiles.states.PlayState;
import com.vpr.pruebatiles.util.CameraMethods;

import static com.vpr.pruebatiles.util.Constantes.SCALE;

public class CameraManager {

    // Attributes
    public OrthographicCamera camera;

    // Constructor
    public CameraManager(){
        // camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth() / SCALE, Gdx.graphics.getHeight() / SCALE);
        camera.position.set(0, 0, 0);
    }

    public void manageInput(Player player){
        if(Gdx.input.isKeyPressed(Input.Keys.A))
            camera.position.x -= 5;
        if(Gdx.input.isKeyPressed(Input.Keys.D))
            camera.position.x += 5;
        if(Gdx.input.isKeyPressed(Input.Keys.W))
            camera.position.y += 5;
        if(Gdx.input.isKeyPressed(Input.Keys.S))
            camera.position.y -= 5;

        if(Gdx.input.isKeyPressed(Input.Keys.Z))
            camera.zoom -= .1f;
        if(Gdx.input.isKeyPressed(Input.Keys.X))
            camera.zoom += .1f;

        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
            CameraMethods.lerpToTarget(camera, player.getPosition());
    }

}
