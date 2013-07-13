package com.jcerise.game.fantstrat.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Mapper;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.jcerise.game.fantstrat.components.GameMap;
import com.jcerise.game.fantstrat.utils.MapTools;

public class MapRenderSystem extends EntitySystem {

    @Mapper ComponentMapper<GameMap> gm;
    private SpriteBatch batch;
    private TextureAtlas atlas;
    private Array<TextureAtlas.AtlasRegion> textures;

    private OrthographicCamera camera;

    @SuppressWarnings("unchecked")
    public MapRenderSystem(OrthographicCamera camera) {
        super(Aspect.getAspectForAll(GameMap.class));
        this.camera = camera;
    }

    @Override
    protected void initialize() {
        batch = new SpriteBatch() ;
        atlas = new TextureAtlas(Gdx.files.internal("resources/textures/maptiles.atlas"),
                Gdx.files.internal("resources/textures"));
        textures = atlas.findRegions(MapTools.name);

    }

    @Override
    protected void processEntities(ImmutableBag<Entity> entities) {
        for (int i = 0; i < entities.size(); i ++) {
            process(entities.get(i));
        }
    }

    private void process(Entity e) {
        GameMap gameMap = gm.get(e);
        TextureRegion reg;

        int x, y;

        //Frustum culling

        //Get bottom left, and top right coordinates of camera viewport and convert into grid
        //coordinates for the map
        int x0 = MathUtils.floor(camera.frustum.planePoints[0].x / (float)MapTools.colMultiple) - 1;
        int y0 = MathUtils.floor(camera.frustum.planePoints[0].y / (float)MapTools.rowMultiple) - 1;
        int x1 = MathUtils.floor(camera.frustum.planePoints[2].x / (float)MapTools.colMultiple) + 2;
        int y1 = MathUtils.floor(camera.frustum.planePoints[2].y / (float)MapTools.rowMultiple) + 1;

        //Restrict the grid coordinates to realistic values
        if (x0 % 2 == 1) x0 -= 1;
        if (x0 < 0) x0 = 0;
        if (x1 > gameMap.width) x1 = gameMap.width;
        if (y0 < 0) y0 = 0;
        if (y1 > gameMap.height) y1 = gameMap.height;

        for (int row = y0; row < y1; row ++) {
            for (int col = x0; col < x1 - 1; col += 2) {
                //Render two columns at once, so we always have the offset correct
                //Render first column normally
                x = col* MapTools.colMultiple;
                y = row*MapTools.rowMultiple;
                reg = textures.get(gameMap.map[col][row]);
                batch.draw(reg, x, y, 0, 0, reg.getRegionWidth(), reg.getRegionHeight(), 1, 1, 0);

                //Render the second column shifted up slightly
                x += MapTools.colMultiple;
                y += MapTools.rowMultiple/2;
                reg = textures.get(gameMap.map[col+1][row]);
                batch.draw(reg, x, y, 0, 0, reg.getRegionWidth(), reg.getRegionHeight(), 1, 1, 0);
            }
            if (x1 >= gameMap.width) {
                //Finally, if we have an odd number of columns, render the last column normally
                int col = gameMap.width-1;
                x = col*MapTools.colMultiple;
                y = row*MapTools.rowMultiple;
                reg = textures.get(gameMap.map[col][row]);
                batch.draw(reg, x, y, 0, 0, reg.getRegionWidth(), reg.getRegionHeight(), 1, 1, 0);
            }
        }
    }

    @Override
    protected void begin() {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
    }

    @Override
    protected void end() {
        batch.end();
    }

    @Override
    protected boolean checkProcessing() {
        return true;
    }
}
