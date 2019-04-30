package com.vpr.pruebatiles.managers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import static com.vpr.pruebatiles.util.Constantes.ATLAS;

public class R {

    // Variables
    public static AssetManager assets = new AssetManager();

    /**
     * Carga todos los recursos, tanto sprites como sonidos y musica
     */
    public static void cargarRecursos(){
        // Cargo el atlas de texturas
        assets.load(ATLAS, TextureAtlas.class);

        // Cargo sonidos y musica
        /*assets.load("sounds" + File.separator + "nombre", Sound.class);
        assets.load("sounds" + File.separator + "nombre", Music.class);*/
    }

    public static boolean update(){
        return assets.update();
    }

    /**
     * Carga la secuencia de sprites que componen una animacion
     * @param nombre asignado a la animacion (EJ.: correr_1, correr_2)
     * @return
     */
    public static Array<TextureAtlas.AtlasRegion> getAnimacion(String nombre){
        return assets.get(ATLAS, TextureAtlas.class).findRegions(nombre);
    }

    /**
     * Carga un sonido
     * @param nombre de la pista de sonido
     * @return Sound
     */
    public static Sound getSonido(String nombre){
        return assets.get(nombre, Sound.class);
    }

    /**
     * Carga musica
     * @param nombre de la pista de musica
     * @return Music
     */
    public static Music getMusica(String nombre){
        return assets.get(nombre, Music.class);
    }

    /**
     * Carga una textura
     * @param nombre del sprite
     * @return TextureRegion
     */
    public static TextureRegion getRegion(String nombre){
        return assets.get(ATLAS, TextureAtlas.class).findRegion(nombre);
    }

    public static Sprite getSprite(String nombre){
        return assets.get(ATLAS, TextureAtlas.class).createSprite(nombre);
    }

}
