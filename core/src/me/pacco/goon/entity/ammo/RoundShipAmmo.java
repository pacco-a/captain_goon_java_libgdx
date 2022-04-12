package me.pacco.goon.entity.ammo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import me.pacco.goon.entity.player.PlayerShip;

public class RoundShipAmmo extends EnemyAmmo{

    private static final Texture TEXTURE =
            new Texture(Gdx.files.internal("sprites/ammo/wship-4.png"));
    private static final Vector2 TEXTURE_OFFSET =
            new Vector2(-4, -5);

    private static final int SPEED = 500;

    public RoundShipAmmo(Vector2 position, PlayerShip player) {
        super(TEXTURE, TEXTURE_OFFSET, SPEED, position.x, position.y, player);
    }
}
