package me.pacco.goon.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import me.pacco.goon.CaptainGoonGame;
import me.pacco.goon.GameState;
import me.pacco.goon.entity.ammo.PlayerAmmo;
import me.pacco.goon.entity.enemy.BossShip;
import me.pacco.goon.entity.enemy.EnemyShip;
import me.pacco.goon.entity.enemy.RoundShip;
import me.pacco.goon.entity.player.PlayerShip;
import me.pacco.goon.ui.UIHandler;
import me.pacco.goon.world.ExplosionHandler;

import java.util.ArrayList;

public class GameScreen implements Screen {

    private static final Texture BACKGROUND_TEXTURE = new Texture(Gdx.files.internal("backgrounds/Planets.jpg"));

    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;
    public final CaptainGoonGame game;

    private PlayerShip playerShip;
    public ArrayList<EnemyShip> enemies = new ArrayList<>();

    private GameState state;

    private float secOver = 1; /* secOver traque le nombre de secondes depuis que le jeu est game over
                               le joueur ne pourra recommencer qu'après une certaine intervale (secBeforeQuit) */
    private float secBeforeQuit = 3;

    private Timer changingSateTimer = new Timer();
    private ExplosionHandler explosionHandler = new ExplosionHandler();
    private UIHandler uiHandler;

    public GameScreen(CaptainGoonGame game) {
        this.game = game;
        this.uiHandler = new UIHandler(game);
        this.shapeRenderer = new ShapeRenderer();
        this.camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // ajout du joueur
        this.playerShip = new PlayerShip(new Vector2(200, 200), camera, enemies);

        // ajout des enemies
        enemies.add(new BossShip(new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() - 150),
                0, Gdx.graphics.getWidth(), playerShip));
        enemies.add(new RoundShip(new Vector2(0, Gdx.graphics.getHeight() - 200),
                        0, Gdx.graphics.getWidth() / 2, playerShip));
        enemies.add(new RoundShip(new Vector2(Gdx.graphics.getWidth() - 50, Gdx.graphics.getHeight() - 200),
                Gdx.graphics.getWidth() / 2, Gdx.graphics.getWidth(), playerShip));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        // rendering
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);

        // updates
        playerShip.update(delta);

        for (EnemyShip enemy: enemies) {
            enemy.update(delta);
        }

        // draws
        game.batch.begin();
        game.batch.draw(BACKGROUND_TEXTURE, 0, 0);
        playerShip.draw(game.batch);
        for (EnemyShip enemy: enemies) {
            enemy.draw(game.batch);
        }

        explosionHandler.draw(game.batch, delta);

        handleGameOver();

        uiHandler.draw(game.batch);

        game.batch.end();


        // shapes (debug hitboxes)
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//        playerShip.drawShape(shapeRenderer);
//        for (EnemyShip enemy: enemies) {
//            enemy.drawShape(shapeRenderer);
//        }
        shapeRenderer.end();

        // remove destroyed ships
        removeOffScreenShips();

        // change screen when game is over
        if ((state == GameState.GAME_LOSE || state == GameState.GAME_WIN)) {
            secOver += delta;
            if (secOver >= secBeforeQuit && Gdx.input.justTouched()) {
                game.setScreen(new MainMenuScreen(game));
            }
        }
    }

    /* TODO doc */
    private void removeOffScreenShips() {
        ArrayList<EnemyShip> shipToDelete = new ArrayList<>();

        for (EnemyShip enemy: enemies) {
            if (enemy.getPosition().y > Gdx.graphics.getHeight()) {
                shipToDelete.add(enemy);
            }
        }

        for (EnemyShip enemyShip: shipToDelete) {
            enemies.remove(enemyShip);
        }
    }

    public void setState(GameState state) {
        this.state = state;
    }

    private void handleGameOver() {
        //      game over message
        if (playerShip.isDestroyed()) {
            game.font.draw(game.batch, "GAME OVER",
                    (Gdx.graphics.getWidth() / 2f) - 50,
                    Gdx.graphics.getHeight() / 2f);

            setState(GameState.GAME_LOSE);
        } else if (enemies.size() == 0) {
            game.font.draw(game.batch, "YOU SAVE THE WORLD !!!",
                    (Gdx.graphics.getWidth() / 2f) - 150,
                    Gdx.graphics.getHeight() / 2f);
            setState(GameState.GAME_WIN);
        }
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
