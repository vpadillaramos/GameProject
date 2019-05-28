package com.vpr.pruebatiles.managers;

import com.vpr.pruebatiles.util.Constantes;

public class CharacterRecord {

    // Attributes
    public String name;

    // Constructor
    public CharacterRecord(String name){
        this.name = name;
    }

    public static CharacterRecord[] characters = {
            new CharacterRecord(Constantes.CharacterType.ninja.name()),
            new CharacterRecord(Constantes.CharacterType.warrior.name()),
            new CharacterRecord(Constantes.CharacterType.lancer.name()),
    };

}
