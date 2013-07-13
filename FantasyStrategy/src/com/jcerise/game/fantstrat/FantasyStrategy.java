package com.jcerise.game.fantstrat;

import com.artemis.World;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jcerise.game.fantstrat.systems.*;

public class FantasyStrategy implements Screen {

    public static int WINDOW_WIDTH = 1300;
    public static int WINDOW_HEIGHT = 720;

    OrthographicCamera camera;
    SpriteBatch batch;
    World world;
    Game game;

    private SpriteRenderSystem spriteRenderSystem;
    private HudRenderSystem hudRenderSystem;
    private MapRenderSystem mapRenderSystem;

    public FantasyStrategy(Game game) {
        this.game = game;

        batch = new SpriteBatch();
        camera = new OrthographicCamera();

        world = new World();
        mapRenderSystem  = world.setSystem(new MapRenderSystem(camera), true);
        spriteRenderSystem = world.setSystem(new SpriteRenderSystem(camera), true);
        hudRenderSystem = world.setSystem(new HudRenderSystem(camera),true);

        world.setSystem(new SpriteAnimationSystem());
        world.setSystem(new PlayerInputSystem(camera));
        world.initialize();

        EntityFactory.createMap(world).addToWorld();

    }

    @Override
    public void render (float delta) {

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        camera.update();

        world.setDelta(delta);
        world.process();

        mapRenderSystem.process();
        spriteRenderSystem.process();
        hudRenderSystem.process();
    }

    @Override
    public void resize (int width, int height) {
        WINDOW_WIDTH = width;
        WINDOW_HEIGHT = height;

        camera.setToOrtho(false, width,height);
    }

    @Override
    public void pause () {
    }

    @Override
    public void resume () {
    }

    @Override
    public void dispose () {
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }
}
