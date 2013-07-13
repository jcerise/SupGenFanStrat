package com.jcerise.game.fantstrat.components;

import com.artemis.Component;
import com.jcerise.game.fantstrat.utils.HexMapGenerator;

public class GameMap extends Component {

    public int[][] map;
    public int width, height;

    public GameMap() {
        HexMapGenerator hmg = new HexMapGenerator();
        map = hmg.getDiamondSquare();
        width =  map.length;
        height = map[0].length;
    }
}
