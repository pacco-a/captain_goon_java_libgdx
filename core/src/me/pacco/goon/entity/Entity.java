package me.pacco.goon.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public interface Entity {

    public void update(float delta);
    public void draw(SpriteBatch batch);
    public void drawShape(ShapeRenderer renderer);

}
