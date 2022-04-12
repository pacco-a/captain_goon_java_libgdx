package me.pacco.goon.entity.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import me.pacco.goon.entity.ammo.BossAmmo;
import me.pacco.goon.entity.ammo.EnemyAmmo;
import me.pacco.goon.entity.player.PlayerShip;

public class BossShip extends EnemyShip{

    private static final String TEXTURE_FILENAME = "sprites/ships/spco_small.png";
    public static final Texture TEXTURE = new Texture(Gdx.files.internal(TEXTURE_FILENAME));
    private static final Vector2 TEXTURE_OFFSET = new Vector2(-35, -30);

    private static final int HEIGHT = 100;
    private static final int WIDTH = 80;

    private static final int SPEED = 200;

    public BossShip(Vector2 position, int minX, int maxX, PlayerShip player) {
        super(position, TEXTURE, TEXTURE_OFFSET, WIDTH, HEIGHT, minX, maxX - WIDTH, SPEED, player, true);
    }

    @Override
    public EnemyAmmo getNewBullet() {
        return new BossAmmo(getPosition(), player);
    }
}
