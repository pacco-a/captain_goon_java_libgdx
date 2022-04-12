package me.pacco.goon.entity.ammo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import me.pacco.goon.entity.Entity;
import me.pacco.goon.entity.enemy.EnemyShip;
import me.pacco.goon.world.ExplosionHandler;

import java.util.ArrayList;

public class PlayerAmmo implements Entity {

    private static final String TEXTURE_FILENAME = "sprites/ammo/bullets/glowtube_small.png";
    private static final Texture TEXTURE = new Texture(Gdx.files.internal(TEXTURE_FILENAME));
    private static final Vector2 TEXTURE_OFFSET = new Vector2(0, 0);

    private static final int WIDTH = 8;
    private static final int HEIGHT = 25;

    private Vector2 position;
    private final int speed = 400;

    private ArrayList<EnemyShip> enemies;
    private Rectangle shape;

    public PlayerAmmo(float x, float y, ArrayList<EnemyShip> enemies) {
        this.position = new Vector2(x, y);
        this.enemies = enemies;
        this.shape = new Rectangle(position.x, position.y, WIDTH, HEIGHT);
    }

    public Vector2 getPosition() {
        return position;
    }

    public Rectangle getShape() {
        return shape;
    }

    @Override
    public void update(float delta) {
        position.y += (speed * delta);

        this.shape.setPosition(position.x, position.y);

        handleCollisionWithShip();
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(TEXTURE, position.x + TEXTURE_OFFSET.x, position.y + TEXTURE_OFFSET.y);
    }

    @Override
    public void drawShape(ShapeRenderer renderer) {
        renderer.rect(position.x, position.y, WIDTH, HEIGHT);
    }

    private void handleCollisionWithShip() {
        for (EnemyShip enemy: enemies) {
            if (enemy.getShape().overlaps(this.shape)) {
                enemy.damageShip(10);
                this.position.y += 500; /* faire sortir la balle de la scene,
                                elle sera ensuite supprimée par le playership*/
            }
        }
    }
}
