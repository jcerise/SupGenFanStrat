package com.jcerise.game.fantstrat.utils;

import com.jcerise.game.fantstrat.custom.GameMath;
import com.jcerise.game.fantstrat.custom.Pair;

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

}
