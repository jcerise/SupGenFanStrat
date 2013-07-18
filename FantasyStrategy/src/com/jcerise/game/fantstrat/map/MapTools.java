package com.jcerise.game.fantstrat.map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.jcerise.game.fantstrat.utils.FloatPair;
import com.jcerise.game.fantstrat.utils.GameMath;
import com.jcerise.game.fantstrat.utils.Pair;

public class MapTools {

    public static final int colMultiple = 34;
    public static final int rowMultiple = 38;
    public static final String name = "hex";

    public static Pair[] getNeighbors(int x, int y, int n) {
        Pair[] coordinates = new Pair[3 * (n*n + n)];
        int i = 0;
        int min;
        for (int row = y-n; row<y+n+1; row++) {
            min = GameMath.min(2 * (row - y + n), n, -2 * (row - y - n) + 1);
            for (int col = x-min; col < x+min+1; col++) {
                if (x==col && y==row) continue;
                else if (x % 2 == 0) coordinates[i]=new Pair(col,2*y-row);
                else coordinates[i] = new Pair(col,row);
                i++;
            }
        }
        return coordinates;
    }

    public static Pair[] getNeighbors(int x, int y) {
        return getNeighbors(x, y, 1);
    }

    public static Pair window2world(float x, float y, OrthographicCamera camera) {
        Vector3 pos = new Vector3(x,y,0);
        camera.unproject(pos);
        int posx = (int)((pos.x - 6f) / (float)colMultiple);
        int posy = (int)((pos.y - (float)rowMultiple*(posx%2)/2) / (float)rowMultiple);
        return new Pair(posx,posy);
    }

    public static FloatPair world2window(int x, int y) {
        float posX= 6f + (x + 0.5f)*34;
        float posY= 38*(0.5f + 0.5f*(x%2) + y);

        return new FloatPair(posX, posY);
    }

}
