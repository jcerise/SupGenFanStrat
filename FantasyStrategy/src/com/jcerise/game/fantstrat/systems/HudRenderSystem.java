package com.jcerise.game.fantstrat.systems;

import com.artemis.systems.VoidEntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jcerise.game.fantstrat.FantasyStrategy;

public class HudRenderSystem extends VoidEntitySystem {

    private SpriteBatch batch;
    private BitmapFont font;

    public HudRenderSystem(OrthographicCamera camera) {
    }

    @Override
    protected void initialize() {

        batch = new SpriteBatch();

        Texture fontTexture = new Texture(Gdx.files.internal("fonts/normal_0.png"));
        fontTexture.setFilter(TextureFilter.Linear, TextureFilter.MipMapLinearLinear);
        TextureRegion fontRegion = new TextureRegion(fontTexture);
        font = new BitmapFont(Gdx.files.internal("fonts/normal.fnt"), fontRegion, false);
        font.setUseIntegerPositions(false);
    }

    @Override
    protected void begin() {
        batch.begin();
    }

    @Override
    protected void processSystem() {
        batch.setColor(1, 1, 1, 1);
        font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 20, FantasyStrategy.WINDOW_HEIGHT - 20);
        font.draw(batch, "Active entities: " + world.getEntityManager().getActiveEntityCount(), 20, FantasyStrategy.WINDOW_HEIGHT - 40);
        font.draw(batch, "Total created: " + world.getEntityManager().getTotalCreated(), 20, FantasyStrategy.WINDOW_HEIGHT - 60);
        font.draw(batch, "Total deleted: " + world.getEntityManager().getTotalDeleted(), 20, FantasyStrategy.WINDOW_HEIGHT - 80);
    }

    @Override
    protected void end() {
        batch.end();
    }

}
