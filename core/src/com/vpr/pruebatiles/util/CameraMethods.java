package com.vpr.pruebatiles.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import static com.vpr.pruebatiles.util.Constantes.PPM;

public class CameraMethods {

    // Constants
    private static final float LERP = .1f;
    private static final float CAMERA_ADJUST = Gdx.graphics.getHeight() / 6;

    /*
    camera interpolation formula -> a + (b - a) * lerp
    a = camera position, b = target position
     */

    /**
     * Camera locks and follows straightly the passed target. Target position needs a scale camera.scl(scale);
     * @param camera
     * @param targetPosition
     */
    public static void lockToTarget(Camera camera, Vector2 targetPosition){
        Vector3 position = camera.position;
        position.x = targetPosition.x;
        position.y = targetPosition.y + CAMERA_ADJUST;
        camera.position.set(position);

        camera.update();
    }

    /**
     * Camera locks and follows smoothly the passed target using interpolation. Target position doesn't nees to be scaled
     * @param camera
     * @param targetPosition
     */
    public static void lerpToTarget(Camera camera, Vector2 targetPosition){
        Vector3 position = camera.position;
        position.x = camera.position.x + (targetPosition.x * PPM - camera.position.x) * LERP;
        position.y = camera.position.y + (targetPosition.y * PPM - camera.position.y + CAMERA_ADJUST) * LERP;
        camera.position.set(position);

        //camera.update();
    }

    /**
     * Camera locks on the average distance between targetA and targetB
     * @param camera
     * @param targetAPosition
     * @param targetBPosition
     */
    public static void lockBetweenTargets(Camera camera, Vector2 targetAPosition, Vector2 targetBPosition){
        Vector3 position = camera.position;
        position.x = (targetAPosition.x / targetBPosition.x) / 2;
        position.y = (targetAPosition.y / targetBPosition.y) / 2;
        camera.position.set(position);

        camera.update();
    }

    /**
     * Camera locks on the average distance between targetA and targetB applying interpolation
     * @param camera
     * @param targetAPosition
     * @param targetBPosition
     */
    public static void lerpBetweenTargets(Camera camera, Vector2 targetAPosition, Vector2 targetBPosition){
        float avgX = (targetAPosition.x / targetBPosition.x) / 2;
        float avgY = (targetAPosition.y / targetBPosition.y) / 2;
        Vector3 position = camera.position;
        position.x = camera.position.x + (avgX- camera.position.x);
        position.y = camera.position.y + (avgY- camera.position.y);
        camera.position.set(position);

        camera.update();
    }


    public static boolean searchFocalPoints(Camera camera, Vector2[] points, Vector2 targetPosition, float dst){

        return false;
    }

}
