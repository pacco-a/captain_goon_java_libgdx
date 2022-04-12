package me.pacco.goon.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import me.pacco.goon.CaptainGoonGame;

public class MainMenuScreen implements Screen {

    OrthographicCamera camera;
    private final CaptainGoonGame game;

    public MainMenuScreen(CaptainGoonGame game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.getData().setScale(2);
        game.font.draw(game.batch, "Welcome to Captain Goon !!!", 400, 450);
        game.font.draw(game.batch, "Tap anywhere to begin", 430, 400);
        game.batch.end();

        if (Gdx.input.justTouched()) {
            game.setScreen(new GameScreen(game));
            dispose();
        }
    }

    @Override
    public void show() {

    }


    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
