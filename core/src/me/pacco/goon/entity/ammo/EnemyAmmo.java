package me.pacco.goon.entity.ammo;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import me.pacco.goon.entity.Entity;
import me.pacco.goon.entity.enemy.EnemyShip;
import me.pacco.goon.entity.player.PlayerShip;

public abstract class EnemyAmmo implements Entity {

    private Texture texture;
    private Vector2 textureOffset;
    private int speed;
    private Vector2 position;

    private static final int WIDTH = 8;
    private static final int HEIGHT = 25;

    private PlayerShip player;

    private Rectangle shape;

    public EnemyAmmo(Texture texture, Vector2 textureOffset,
                     int speed, float x, float y, PlayerShip player) {
        this.player = player;
        this.texture = texture;
        this.speed = speed;
        this.position = new Vector2(x, y);
        this.textureOffset = textureOffset;

        this.shape = new Rectangle(position.x, position.y, WIDTH, HEIGHT);
    }

    public Vector2 getPosition() {
        return position;
    }

    @Override
    public void update(float delta) {
        this.position.y -= speed * delta;
        this.shape.setPosition(position);
        handleCollisionWithPlayer();
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, position.x + textureOffset.x, position.y + textureOffset.y);
    }

    @Override
    public void drawShape(ShapeRenderer renderer) {
        renderer.rect(position.x, position.y, WIDTH, HEIGHT);
    }

    private void handleCollisionWithPlayer() {
        if (this.player.getShape().overlaps(this.shape)) {
            System.out.println("PLAYER TOUCHE !");
            this.player.damageShip(10);
            this.position.y = -100;
        }
    }
}
