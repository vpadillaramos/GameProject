package com.vpr.pruebatiles.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.vpr.pruebatiles.util.Constantes;

public class Configuration {

    private static Preferences preferences = Gdx.app.getPreferences(Constantes.CONFIGURATION);

    public static boolean isMusicEnabled(){
        return preferences.getBoolean(Constantes.MUSIC);
    }

    public static void setMusicEnabled(boolean b){
        preferences.putBoolean(Constantes.MUSIC, b);
        preferences.flush();
    }

    public static boolean isSoundEnabled(){
        return preferences.getBoolean(Constantes.SOUND);
    }

    public static void setSoundEnabled(boolean b){
        preferences.putBoolean(Constantes.SOUND, b);
        preferences.flush();
        System.out.println(isSoundEnabled());
    }

}
