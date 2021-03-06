package com.evansitzes.game.entity.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.evansitzes.game.Configuration;
import com.evansitzes.game.TwilightEternal;
import com.evansitzes.game.entity.Entity;
import com.evansitzes.game.helpers.DirectionEnum;
import com.evansitzes.game.helpers.Textures;

import static com.evansitzes.game.helpers.DirectionEnum.*;

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

    public AnimatedSprite(final TwilightEternal game) {
        super(game);

        rightWalking = Textures.People.WALKING_RIGHT;
        leftWalking = Textures.People.WALKING_LEFT;
        upWalking = Textures.People.WALKING_UP;
        downWalking = Textures.People.WALKING_DOWN;
        currentAnimation = new Animation(animationSpeed, Textures.People.WALKING_RIGHT);

//        locate(370, 600);
        this.position.set(Configuration.STARTING_POSITION_X, Configuration.STARTING_POSITION_Y);
    }

    @Override
    public void handle(final float delta) {
        stateTime += delta;
        currentAnimation = new Animation(animationSpeed, currentWalking);
        currentFrame = currentAnimation.getKeyFrame(stateTime, looping);

        game.batch.draw(currentFrame, position.x, position.y);
    }

    public void setCurrentDirection(final DirectionEnum direction) {
        if (direction == LEFT) {
            currentWalking = leftWalking;
        } else if (direction == RIGHT) {
            currentWalking = rightWalking;
        } else if (direction == UP) {
            currentWalking = upWalking;
        } else if (direction == DOWN) {
            currentWalking = downWalking;
        }
    }
}
