package me.pacco.goon.entity.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import me.pacco.goon.entity.Entity;
import me.pacco.goon.entity.ammo.EnemyAmmo;
import me.pacco.goon.entity.ammo.PlayerAmmo;
import me.pacco.goon.entity.player.PlayerShip;
import me.pacco.goon.ui.UIHandler;
import me.pacco.goon.world.ExplosionHandler;

import java.util.ArrayList;

public abstract class EnemyShip implements Entity {

    /* APPEARANCE */
    private Texture texture;
    private Sprite sprite;
    private Vector2 textureOffset;
    private int width;
    private int height;
    private Rectangle shape;
    private Color color = new Color(1, 1, 1, 1);

    /* MOVEMENTS */
    private Vector2 position;
    private int speed = 0;
    private int maxSpeed;
    private int acceleration = 25;
    private int minXPosition;
    private int maxXPosition;
    private boolean goRight = true;

    /* GAMEPLAY */
    private int health = 100;
    private ArrayList<EnemyAmmo> bullets = new ArrayList<EnemyAmmo>();
    private Timer shootingTimer = new Timer();
    protected PlayerShip player;
    private boolean isBoss;

    public EnemyShip(Vector2 position, Texture texture, Vector2 textureOffset,
                     int width, int height, int minX, int maxX, int speed, PlayerShip player,
                     boolean isBoss) {
        this.isBoss = isBoss;
        this.position = position;
        this.texture = texture;
        this.sprite = new Sprite(texture);
        this.textureOffset = textureOffset;
        this.width = width;
        this.height = height;
        this.minXPosition = minX;
        this.maxXPosition = maxX;
        this.maxSpeed = speed;
        this.player = player;

        this.shape = new Rectangle(position.x, position.y, width, height);
        this.sprite.setColor(color);

        /* SHOOTING */
        shootingTimer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                bullets.add(getNewBullet());
            }
        }, 1, 1);
        shootingTimer.start();
    }

    public Rectangle getShape() {
        return shape;
    }

    @Override
    public void update(float delta) {

        if (goRight) {
            speed = Math.min(maxSpeed, speed + acceleration);
        } else {
            speed = Math.max(-maxSpeed, speed - acceleration);
        }

        position.x += (speed * delta);

        if (goRight && position.x > maxXPosition) {
            goRight = false;
        } else if (!goRight && position.x < minXPosition) {
            goRight = true;
        }

        this.shape.setPosition(position.x, position.y);

        sprite.setPosition(position.x + textureOffset.x, position.y + textureOffset.y);

        for (EnemyAmmo bullet: bullets) {
            bullet.update(delta);
        }

        removeOffScreenBullets();
    }

    @Override
    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
        for (EnemyAmmo bullet: bullets) {
            bullet.draw(batch);
        }
    }

    public Vector2 getPosition() {
        return position;
    }

    @Override
    public void drawShape(ShapeRenderer renderer) {
        renderer.rect(position.x, position.y, width, height);
        for (EnemyAmmo bullet: bullets) {
            bullet.drawShape(renderer);
        }
    }

    public void damageShip(int damages) {
        if (this.health <= 0) return;

        this.health = Math.max(0, this.health - damages);

        UIHandler.Singleton.setBossHealth(this.health);

        if (health <= 0) {
            ExplosionHandler.Singleton.createExplosion(new Vector2(
                    position.x - 100, position.y - 100
            ));
            this.position.y = 9999; /* faire sortir le ship de l'écran il sera alors supprimé par
                                       le GameScreen */
        }

        this.color.g = this.health / 100f;
        this.color.b = this.health / 100f;

        this.sprite.setColor(color);
    }

    public abstract EnemyAmmo getNewBullet();

    private void removeOffScreenBullets() {
        ArrayList<EnemyAmmo> toDeleteAmmo = new ArrayList<>();

        for (EnemyAmmo bullet: bullets) {
            if (bullet.getPosition().y < 0) {
                toDeleteAmmo.add(bullet);
            }
        }

        for (EnemyAmmo bullet: toDeleteAmmo) {
            bullets.remove(bullet);
        }
    }
}
