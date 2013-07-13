package com.jcerise.game.fantstrat;

import com.artemis.Entity;
import com.artemis.World;
import com.jcerise.game.fantstrat.components.GameMap;

public class EntityFactory {

    public static Entity createMap(World world) {
        Entity e = world.createEntity();
        e.addComponent(new GameMap());
        return e;
    }

}
