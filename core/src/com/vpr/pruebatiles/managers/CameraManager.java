package com.vpr.pruebatiles.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.vpr.pruebatiles.entities.Player;
import com.vpr.pruebatiles.util.CameraMethods;

public class CameraManager {

    // Constants
    public final float zoom = .7f;

    // Attributes
    public OrthographicCamera camera;

    // Constructor
    public CameraManager(){
        // camera
        camera = new OrthographicCamera();
        //camera.zoom = zoom;

        //camera.setToOrtho(false, Gdx.graphics.getWidth() / SCALE, Gdx.graphics.getHeight() / SCALE);
        camera.position.set(0, 0, 0);
    }

    public void update(float mapPixelWidth, float mapPixelHeight){
        float startX = camera.viewportWidth / 2;
        float startY = camera.viewportHeight / 2;
        //setCameraBounds(startX, startY, mapPixelWidth - camera.viewportWidth, mapPixelHeight + camera.viewportHeight / 2);
        camera.update();
    }

    public void setCameraBounds(float mapStartX, float mapStarY, float mapWidth, float mapHeight){
        CameraMethods.setCameraBounds(camera, mapStartX, mapStarY, mapWidth, mapHeight);
    }

    public void lockToTarget(Vector2 targetAPosition){
        CameraMethods.lockToTarget(camera, targetAPosition);
    }

    public void lerpToTarget(Vector2 targetPosition){
        CameraMethods.lerpToTarget(camera, targetPosition);
    }

    public void lockBetweenTargets(Vector2 targetAPosition, Vector2 targetBPosition){
        CameraMethods.lockBetweenTargets(camera, targetAPosition, targetBPosition);
    }

    public void lerpBetweenTargets(Vector2 targetAPosition, Vector2 targetBPosition){
        CameraMethods.lerpBetweenTargets(camera, targetAPosition, targetBPosition);
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
