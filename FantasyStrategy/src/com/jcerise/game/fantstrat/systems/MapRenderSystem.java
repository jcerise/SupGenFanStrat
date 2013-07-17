package com.jcerise.game.fantstrat.systems;

import com.artemis.systems.VoidEntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.jcerise.game.fantstrat.map.GameMap;
import com.jcerise.game.fantstrat.map.MapTools;

public class MapRenderSystem extends VoidEntitySystem {
    private SpriteBatch batch;
    private TextureAtlas atlas;
    private Array<TextureAtlas.AtlasRegion> textures;
    private OrthographicCamera camera;
    private GameMap gameMap;

    public MapRenderSystem(OrthographicCamera camera, GameMap gameMap) {
        this.camera = camera;
        this.gameMap = gameMap;
    }

    @Override
    protected void initialize() {
        batch = new SpriteBatch();
        // Load the map tiles into an Array
        atlas = new TextureAtlas(Gdx.files.internal("resources/textures/maptiles.atlas"),Gdx.files.internal("resources/textures"));
        textures = atlas.findRegions(MapTools.name);
    }

    @Override
    protected boolean checkProcessing() {
        return true;
    }

    protected void processSystem() {

        TextureRegion reg;
        int x, y;


        // Get bottom left and top right coordinates of camera viewport and convert
        // into grid coordinates for the map
        int x0 = MathUtils.floor(camera.frustum.planePoints[0].x / (float)MapTools.colMultiple) - 1;
        int y0 = MathUtils.floor(camera.frustum.planePoints[0].y / (float)MapTools.rowMultiple) - 1;
        int x1 = MathUtils.floor(camera.frustum.planePoints[2].x / (float)MapTools.colMultiple) + 2;
        int y1 = MathUtils.floor(camera.frustum.planePoints[2].y / (float)MapTools.rowMultiple) + 1;

        // Restrict the grid coordinates to realistic values
        if (x0 % 2 == 1) x0 -= 1;
        if (x0 < 0) x0 = 0;
        if (x1 > gameMap.width) x1 = gameMap.width;
        if (y0 < 0) y0 = 0;
        if (y1 > gameMap.height) y1 = gameMap.height;

        // Loop over everything in the window to draw.  Draw 2 columns at once
        for (int row = y0; row < y1; row++) {
            for (int col = x0; col < x1-1; col+=2) {
                x = col*MapTools.colMultiple;
                y = row*MapTools.rowMultiple;
                reg = textures.get(gameMap.map[col][row]);
                batch.draw(reg, x, y, 0, 0, reg.getRegionWidth(), reg.getRegionHeight(), 1, 1, 0);
                x += MapTools.colMultiple;
                y += MapTools.rowMultiple/2;
                reg = textures.get(gameMap.map[col+1][row]);
                batch.draw(reg, x, y, 0, 0, reg.getRegionWidth(), reg.getRegionHeight(), 1, 1, 0);
            }
            // Due to the map generation algorithm I use, there is guaranteed to be an odd number of columns.
            // Since I drew 2 columns at once above, the far right one won't be touched.  This bit is a little
            // silly because it draws the far right column, whether it is in the frustum or not.  Oh well...
            if (x1 >= gameMap.width) {
                int col = gameMap.width-1;
                x = col*MapTools.colMultiple;
                y = row*MapTools.rowMultiple;
                reg = textures.get(gameMap.map[col][row]);
                batch.draw(reg, x, y, 0, 0, reg.getRegionWidth(), reg.getRegionHeight(), 1, 1, 0);
            }

        }

        // This line can draw a small image of the whole map. Possible use as mini map, mayhaps?
        //batch.draw(gameMap.texture,0,0);
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
}
