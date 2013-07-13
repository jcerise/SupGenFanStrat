package com.jcerise.game.fantstrat.components;

import com.artemis.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jcerise.game.fantstrat.custom.Animation;

public class SpriteAnimation extends Component {

    public Animation animation;
    public float stateTime;
    public float frameDuration;
    public int playMode;

    public TextureRegion getFrame() {
        return animation.getKeyFrame(stateTime);
    }

}
