package me.pacco.goon.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class ExplosionHandler {

    public static ExplosionHandler Singleton;

    public static final String ATLAS_FILE_NAME =
            "sprites/kisspng-sprite-explosion_pack/kisspng-sprite-explosion_pack.atlas";

    public static final TextureAtlas TEXTURE_ATLAS =
            new TextureAtlas(Gdx.files.internal(ATLAS_FILE_NAME));

    private ArrayList<Explosion> explosions = new ArrayList<>();

    public ExplosionHandler() {
        ExplosionHandler.Singleton = this;
    }

    public void update(float delta) {

    }

    public void draw(SpriteBatch batch, float delta) {

        for (Explosion explosion: explosions) {
            explosion.draw(batch);
        }
    }

    public void createExplosion(Vector2 position) {
        explosions.add(new Explosion(
            position,
            new Animation<TextureRegion>(1f/30f, TEXTURE_ATLAS.getRegions())
        ));
    }
}
