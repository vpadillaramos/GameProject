package com.vpr.pruebatiles.desktop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class PacketLauncher {

    public static void main(String[] args){
        TexturePacker.Settings settings = new TexturePacker.Settings();
        settings.maxWidth = 1024;
        settings.maxHeight = 1024;
        settings.filterMag = Texture.TextureFilter.Linear;
        settings.filterMin = Texture.TextureFilter.Linear;

        TexturePacker.process("android/assets/sprites", "android/assets", "sprites");
    }
}
