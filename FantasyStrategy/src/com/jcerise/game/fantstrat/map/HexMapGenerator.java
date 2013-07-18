package com.jcerise.game.fantstrat.map;

import com.jcerise.game.fantstrat.components.Tile;

public class HexMapGenerator {

    public HexMapGenerator() {

    }

    public int[][] getDiamondSquare() {
        DiamondSquare ds = new DiamondSquare();
        return ds.getMap();
    }

}
