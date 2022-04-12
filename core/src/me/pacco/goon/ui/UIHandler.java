package me.pacco.goon.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import me.pacco.goon.CaptainGoonGame;
import me.pacco.goon.entity.enemy.BossShip;
import me.pacco.goon.entity.player.PlayerShip;

public class UIHandler {

    public static UIHandler Singleton;

    private Integer playerHealth = 100;
    private Integer bossHealth = 100;

    private CaptainGoonGame game;

    private Sprite playerSprite;
    private Sprite bossSprite;

    public UIHandler(CaptainGoonGame game) {
        UIHandler.Singleton = this;

        this.game = game;

        playerSprite = new Sprite(PlayerShip.TEXTURE);
        bossSprite = new Sprite(BossShip.TEXTURE);

        playerSprite.setPosition(-20, 0);
        playerSprite.setScale(0.3f);

        bossSprite.setPosition(Gdx.graphics.getWidth() - 115, 0);
        bossSprite.setScale(0.3f);
    }

    public void draw(SpriteBatch batch) {
        playerSprite.draw(batch);
        bossSprite.draw(batch);

        game.font.draw(batch, playerHealth.toString() + "%", 50, 80);
        game.font.draw(batch, bossHealth.toString() + "%", Gdx.graphics.getWidth() - 130, 80);
    }

    public void setPlayerHealth(Integer playerHealth) {
        this.playerHealth = playerHealth;
    }

    public void setBossHealth(Integer bossHealth) {
        this.bossHealth = bossHealth;
    }
}
