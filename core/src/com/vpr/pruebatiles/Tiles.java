package com.vpr.pruebatiles;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.vpr.pruebatiles.pantallas.PantallaCarga;
import com.vpr.pruebatiles.pantallas.PantallaJuego;
import com.vpr.pruebatiles.pantallas.PantallaSplash;

public class Tiles extends Game {

	public SpriteBatch batch;

	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new PantallaSplash());
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}
