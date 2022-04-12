package me.pacco.goon.entity.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import me.pacco.goon.entity.ammo.EnemyAmmo;
import me.pacco.goon.entity.ammo.RoundShipAmmo;
import me.pacco.goon.entity.player.PlayerShip;

public class RoundShip extends EnemyShip{

    private static final String TEXTURE_FILENAME = "sprites/ships/roundysh_small.png";
    private static final Texture TEXTURE = new Texture(Gdx.files.internal(TEXTURE_FILENAME));
    private static final Vector2 TEXTURE_OFFSET = new Vector2(-25, -50);

    private static final int HEIGHT = 50;
    private static final int WIDTH = 50;

    private static final int SPEED = 250;

    public RoundShip(Vector2 position, int minX, int maxX, PlayerShip player) {
        super(position, TEXTURE, TEXTURE_OFFSET, WIDTH, HEIGHT, minX, maxX - WIDTH, SPEED, player, false);
    }

    @Override
    public EnemyAmmo getNewBullet() {
        return new RoundShipAmmo(getPosition(), player);
    }
}
