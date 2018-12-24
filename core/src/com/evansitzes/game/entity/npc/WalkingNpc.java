package com.evansitzes.game.entity.npc;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.evansitzes.game.TwilightEternal;
import com.evansitzes.game.helpers.DirectionEnum;
import com.evansitzes.game.helpers.DrawUtils;
import com.evansitzes.game.helpers.WalkingStateEnum;

public abstract class WalkingNpc extends Npc {

    protected TextureRegion[] currentWalkingTexture;
    protected WalkingNpctTextures textures;

    private WalkingStateEnum walkingState;
    private Animation currentAnimation;
    private DirectionEnum currentDirection = DirectionEnum.STANDING;
    private TextureRegion currentFrame;
    private boolean looping = true;
    private float stateTime;
    private float walkingWidth;
    private float turnCountdown = 1;
    private float animationSpeed = 1f/4f;

    public static final int MOMENT_SPEED = 1;

    public WalkingNpc(final TwilightEternal game) {
        super(game);
    }

    public abstract void draw(float delta);

    public void setWalkingWidth(final float walkingWidth) {
        this.walkingWidth = walkingWidth;
        this.turnCountdown = walkingWidth;
    }

    public void setWalkingState(final WalkingStateEnum walkingState) {
        this.walkingState = walkingState;

        if (walkingState == WalkingStateEnum.WALKING_LEFT_AND_RIGHT) {
            currentDirection = DirectionEnum.RIGHT;
            currentWalkingTexture = textures.rightWalkingTexture;
        } else if (walkingState == WalkingStateEnum.WALKING_UP_AND_DOWN) {
            currentDirection = DirectionEnum.UP;
            currentWalkingTexture = textures.upWalkingTexture;
        }
    }

    protected void handleMovement(final float delta) {
        stateTime += delta;
        handleTurning(delta, textures);

        currentAnimation = new Animation(animationSpeed, currentWalkingTexture);

        currentFrame = currentAnimation.getKeyFrame(stateTime, looping);

        game.batch.draw(currentFrame, position.x, position.y);

        switch (currentDirection) {
            case LEFT:
                position.x -= MOMENT_SPEED;
                break;
            case RIGHT:
                position.x += MOMENT_SPEED;
                break;
            case UP:
                position.y += MOMENT_SPEED;
                break;
            case DOWN:
                position.y -= MOMENT_SPEED;
                break;
        }


        this.hitBox.set(position.x, position.y, this.hitBox.width, this.hitBox.height);

        if (game.debug) {
            DrawUtils.draw(game, hitBox, Color.BLUE);
        }
    }

    private void handleTurning(final float delta, final WalkingNpctTextures textures) {
        turnCountdown -= delta;

        if (turnCountdown < 0) {
            turnCountdown = walkingWidth;

            switch (currentDirection) {
                case LEFT:
                    currentDirection = DirectionEnum.RIGHT;
                    currentWalkingTexture = textures.rightWalkingTexture;
                    break;
                case RIGHT:
                    currentDirection = DirectionEnum.LEFT;
                    currentWalkingTexture = textures.leftWalkingTexture;
                    break;
                case UP:
                    currentDirection = DirectionEnum.DOWN;
                    currentWalkingTexture = textures.downWalkingTexture;
                    break;
                case DOWN:
                    currentDirection = DirectionEnum.UP;
                    currentWalkingTexture = textures.upWalkingTexture;
                    break;
            }

        }
    }
}
