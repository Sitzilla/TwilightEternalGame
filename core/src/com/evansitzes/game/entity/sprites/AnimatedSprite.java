package com.evansitzes.game.entity.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.evansitzes.game.TwilightEternal;
import com.evansitzes.game.entity.Entity;
import com.evansitzes.game.resources.Textures;

/**
 * Created by evan on 6/8/16.
 */
public class AnimatedSprite extends Entity {
    private Animation currentAnimation;
//    private final TextureRegion[] animationFrames;

    private TextureRegion currentFrame;
    private TextureRegion[] rightWalking;
    private TextureRegion[] leftWalking;
    private TextureRegion[] upWalking;
    private TextureRegion[] downWalking;
    private TextureRegion[] currentWalking;

    private float stateTime;
    private boolean looping = true;
    private float animationSpeed = 1f/6f;

    public AnimatedSprite(TwilightEternal game) {
        super(game);

        rightWalking = Textures.People.WALKING_RIGHT;
        leftWalking = Textures.People.WALKING_LEFT;
        upWalking = Textures.People.WALKING_UP;
        downWalking = Textures.People.WALKING_DOWN;
        currentAnimation = new Animation(animationSpeed, Textures.People.WALKING_RIGHT);

        locate(370, 600);

    }

    @Override
    public void handle(float delta) {
        stateTime += delta;
        currentAnimation = new Animation(animationSpeed, currentWalking);
        currentFrame = currentAnimation.getKeyFrame(stateTime, looping);

        game.batch.draw(currentFrame, position.x, position.y);
    }

    public void setCurrentDirection(PlayerSprite.Facing direction) {
        if (direction == PlayerSprite.Facing.LEFT) {
            currentWalking = leftWalking;
        } else if (direction == PlayerSprite.Facing.RIGHT) {
            currentWalking = rightWalking;
        } else if (direction == PlayerSprite.Facing.UP) {
            currentWalking = upWalking;
        } else if (direction == PlayerSprite.Facing.DOWN) {
            currentWalking = downWalking;
        }
    }
}
