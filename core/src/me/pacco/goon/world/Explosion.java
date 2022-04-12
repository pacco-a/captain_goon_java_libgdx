package me.pacco.goon.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Explosion {

    private Vector2 position;
    private Animation<TextureRegion> animation;
    private float elapsedTime = 0;


    public Explosion(Vector2 position, Animation<TextureRegion> animation) {
        this.position = position;
        this.animation = animation;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Animation<TextureRegion> getAnimation() {
        return animation;
    }

    public void draw(SpriteBatch batch) {
        elapsedTime += Gdx.graphics.getDeltaTime();

        batch.draw(getAnimation().getKeyFrame(elapsedTime, false),
                getPosition().x, getPosition().y);
    }
}
