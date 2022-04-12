package me.pacco.goon.entity.ammo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import me.pacco.goon.entity.player.PlayerShip;

public class BossAmmo extends EnemyAmmo{
    private static final Texture TEXTURE =
            new Texture(Gdx.files.internal("sprites/ammo/aliendropping_small.png"));
    private static final Vector2 TEXTURE_OFFSET =
            new Vector2(-15, -10);

    private static final int SPEED = 500;

    public BossAmmo(Vector2 position, PlayerShip player) {
        super(TEXTURE, TEXTURE_OFFSET, SPEED, position.x, position.y, player);
    }
}
