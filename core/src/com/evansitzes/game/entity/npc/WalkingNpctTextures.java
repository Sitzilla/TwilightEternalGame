package com.evansitzes.game.entity.npc;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class WalkingNpctTextures {

    public final TextureRegion[] leftWalkingTexture;
    public final TextureRegion[] rightWalkingTexture;
    public final TextureRegion[] upWalkingTexture;
    public final TextureRegion[] downWalkingTexture;

    public WalkingNpctTextures(final TextureRegion[] leftWalkingTexture,
                               final TextureRegion[] rightWalkingTexture,
                               final TextureRegion[] upWalkingTexture,
                               final TextureRegion[] downWalkingTexture) {
        this.leftWalkingTexture = leftWalkingTexture;
        this.rightWalkingTexture = rightWalkingTexture;
        this.upWalkingTexture = upWalkingTexture;
        this.downWalkingTexture = downWalkingTexture;
    }
}
