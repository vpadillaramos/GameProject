package com.vpr.pruebatiles.util;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static com.vpr.pruebatiles.util.Constantes.PPM;

public class TiledObjectUtil {

    /**
     * Creates a body on the passed World based in a MapObjects. These objects can be PolylineMapObject or
     * PolygonMapObject, if not it doesn't create a body
     * @param world
     * @param objects
     */
    public static void parseTiledObjectLayer(World world, MapObjects objects){
        for(MapObject object : objects){
            Shape shape;
            if(object instanceof PolylineMapObject){
                shape = getChainShape((PolylineMapObject) object);
            }
            else if(object instanceof  PolygonMapObject){
                shape = getPolygonShape((PolygonMapObject) object);
            }
            else {
                System.out.println(object.getClass());
                continue;
            }

            // Creates the static body
            Body body;
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            body = world.createBody(bodyDef);
            body.createFixture(shape, 1.0f);
            shape.dispose();
        }
    }

    private static ChainShape getChainShape(PolylineMapObject polyline){
        // because each line have 2 vertices, this manage a chain of vertices to not repeat vertices
        float[] vertices = polyline.getPolyline().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length / 2];

        for(int i = 0; i < worldVertices.length; i++)
            worldVertices[i] = new Vector2(vertices[i * 2] / PPM, vertices[i * 2 + 1] / PPM);

        ChainShape chainShape = new ChainShape();
        chainShape.createChain(worldVertices);
        return chainShape;
    }

    public static PolygonShape getPolygonShape(PolygonMapObject polygonMapObject){

        float[] vertices = polygonMapObject.getPolygon().getTransformedVertices();
        float[] worldVertices = new float[vertices.length];

        for(int i = 0; i < vertices.length; i++)
            worldVertices[i] = vertices[i] / PPM;

        PolygonShape polygon = new PolygonShape();
        polygon.set(worldVertices);
        return polygon;
    }

}
