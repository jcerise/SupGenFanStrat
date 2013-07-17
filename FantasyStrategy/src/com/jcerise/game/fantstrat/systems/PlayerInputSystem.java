package com.jcerise.game.fantstrat.systems;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.jcerise.game.fantstrat.components.Player;
import com.jcerise.game.fantstrat.components.Tile;
import com.jcerise.game.fantstrat.map.GameMap;
import com.jcerise.game.fantstrat.map.MapTools;
import com.jcerise.game.fantstrat.utils.FloatPair;
import com.jcerise.game.fantstrat.utils.Pair;

public class PlayerInputSystem extends EntityProcessingSystem implements InputProcessor {

    private OrthographicCamera camera;
    private Vector3 mouseVector;
    private GameMap gameMap;
    private World world;

    @SuppressWarnings("unchecked")
    public PlayerInputSystem(OrthographicCamera camera, World world, GameMap gameMap) {
        super(Aspect.getAspectForAll(Player.class));
        this.camera=camera;
        this.world = world;
        this.gameMap = gameMap;
    }

    @Override
    protected void initialize() {
        Gdx.input.setInputProcessor(this);
        System.out.println("Player Input intialized");
    }

    @Override
    protected void process(Entity e) {
        System.out.println("Player Input Process called");
        mouseVector = new Vector3(Gdx.input.getX(),Gdx.input.getY(),0);
        camera.unproject(mouseVector);
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Pair coords = MapTools.window2world(Gdx.input.getX(), Gdx.input.getY(), camera);
        int entityId = gameMap.getEntityAt(coords.x, coords.y);
        if (entityId > -1) {
            Entity e = world.getEntity(entityId);
            System.out.println(e.getComponent(Tile.class).getName());
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Vector3 delta = new Vector3(-camera.zoom*Gdx.input.getDeltaX(), camera.zoom*Gdx.input.getDeltaY(),0);
        camera.translate(delta);

        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        if ((camera.zoom > 0.2f || amount == 1) && (camera.zoom < 30 || amount == -1)) camera.zoom += amount*0.1;
        return false;
    }

}
