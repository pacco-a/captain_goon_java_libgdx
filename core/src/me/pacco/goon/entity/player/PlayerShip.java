package me.pacco.goon.entity.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import me.pacco.goon.entity.Entity;
import me.pacco.goon.entity.ammo.PlayerAmmo;
import me.pacco.goon.entity.enemy.EnemyShip;
import me.pacco.goon.ui.UIHandler;
import me.pacco.goon.world.ExplosionHandler;

import java.util.ArrayList;

public class PlayerShip implements Entity {

    private static final String TEXTURE_FILE_NAME = "sprites/ships/blueships1_small.png";
    public static final Texture TEXTURE = new Texture(Gdx.files.internal(TEXTURE_FILE_NAME));
    private static final Vector2 TEXTURE_OFFSET = new Vector2(-25, -10);

    private static final int SHIP_WIDTH = 50;
    private static final int SHIP_HEIGHT = 100;

    private Sprite sprite;
    private Color color = new Color(1, 1, 1, 1);

    private final Camera gameCamera;

    private Vector2 position;
    private ArrayList<PlayerAmmo> bullets = new ArrayList<>();

    private ArrayList<EnemyShip> enemies;
    private boolean justShooted = false;
    private Rectangle shape;

    private int health = 100;
    private boolean isDestroyed = false;

    public PlayerShip(Vector2 position, Camera camera, ArrayList<EnemyShip> enemies) {
        this.enemies = enemies;
        this.position = position;
        this.gameCamera = camera;
        this.shape = new Rectangle(position.x, position.y, SHIP_WIDTH, SHIP_HEIGHT);
        this.sprite = new Sprite(TEXTURE);
        this.sprite.setColor(color);
    }

    public void update(float delta) {

        if (isDestroyed) return;

        handleMovements();
        handleShoots();

        for (PlayerAmmo bullet: bullets) {
            bullet.update(delta);
        }

        removeOffScreenBullets();
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (isDestroyed) return;

        this.sprite.draw(batch);
        /*batch.draw(TEXTURE, position.x + TEXTURE_OFFSET.x, position.y + TEXTURE_OFFSET.y);*/
        for (PlayerAmmo bullet: bullets) {
            bullet.draw(batch);
        }
    }

    @Override
    public void drawShape(ShapeRenderer renderer) {
        if (isDestroyed) return;

        renderer.rect(position.x, position.y, SHIP_WIDTH, SHIP_HEIGHT);
        for (PlayerAmmo bullet: bullets) {
            bullet.drawShape(renderer);
        }
    }

    public Rectangle getShape() {
        return shape;
    }

    private void handleMovements() {
        Vector3 touchPos = new Vector3();
        touchPos.set(
                Math.max(0 + TEXTURE.getWidth() / 2f, Math.min(Gdx.graphics.getWidth() - TEXTURE.getWidth() / 2f, Gdx.input.getX())),
                Math.min(Gdx.graphics.getHeight() - TEXTURE.getHeight() / 2f, Math.max(Gdx.input.getY(), 600)),
                0
        );
        gameCamera.unproject(touchPos);

        position.x = (int) touchPos.x - SHIP_WIDTH / 2f;
        position.y = (int) touchPos.y - SHIP_HEIGHT / 2f;

        this.shape.setPosition(position);
        this.sprite.setPosition(position.x + TEXTURE_OFFSET.x, position.y + TEXTURE_OFFSET.y);
    }

    private void handleShoots() {
        if (Gdx.input.isTouched()) {
            if (!justShooted) {
                System.out.println("shooting !");
                bullets.add(new PlayerAmmo(position.x + SHIP_WIDTH / 2f, position.y + SHIP_HEIGHT, enemies));
            }
            justShooted = true;
        } else {
            justShooted = false;
        }
    }

    private void removeOffScreenBullets() {
        ArrayList<PlayerAmmo> toDeleteAmmo = new ArrayList<>();

        for (PlayerAmmo bullet: bullets) {
            if (bullet.getPosition().y > Gdx.graphics.getHeight()) {
                toDeleteAmmo.add(bullet);
            }
        }

        for (PlayerAmmo bullet: toDeleteAmmo) {
            bullets.remove(bullet);
        }
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public void damageShip(int damages) {
        if (this.health <= 0) return;

        this.health = Math.max(0, this.health - damages);

        UIHandler.Singleton.setPlayerHealth(this.health);

        if (health <= 0) {
            ExplosionHandler.Singleton.createExplosion(new Vector2(
                    position.x - 100, position.y - 100
            ));
            this.isDestroyed = true;
        }

        this.color.g = this.health / 100f;
        this.color.b = this.health / 100f;

        this.sprite.setColor(color);
    }

}
