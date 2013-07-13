package com.jcerise.game.fantstrat.utils;

public class HexMapGenerator {

    public HexMapGenerator() {

    }

    public int[][] getDiamondSquare() {
        DiamondSquare ds = new DiamondSquare();
        return ds.getMap();
    }

}
