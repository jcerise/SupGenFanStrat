package com.jcerise.game.fantstrat;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.jcerise.game.fantstrat.utils.ImagePacker;

public class Launcher extends Game {

    @Override
    public void create() {
        setScreen(new FantasyStrategy(this));
    }

    public static void main(String[] args) {
        ImagePacker.run();

        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.width=FantasyStrategy.WINDOW_WIDTH;
        cfg.height=FantasyStrategy.WINDOW_HEIGHT;
        cfg.useGL20=true;
        cfg.title = "Super Generic Fantasy Strategy Game";
        cfg.vSyncEnabled = false;
        cfg.resizable=false;
        new LwjglApplication(new Launcher(), cfg);
    }
}
