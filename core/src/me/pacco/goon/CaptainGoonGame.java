package me.pacco.goon;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import me.pacco.goon.screen.MainMenuScreen;

import java.util.ArrayList;

public class CaptainGoonGame extends Game {

	public BitmapFont font;
	public SpriteBatch batch;
	public Texture img;

	private ArrayList<Screen> screens = new ArrayList<>();

	/* défini le screen courant et l'ajoute si pas déjà présent dans l'array screens */
	public void useScreen(Screen screen) {
		if (!this.screens.contains(screen)) {
			this.screens.add(screen);
		}
		this.setScreen(screen);
	}

	@Override
	public void create () {
		font = new BitmapFont();
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");

		useScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
