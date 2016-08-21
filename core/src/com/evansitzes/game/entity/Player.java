package com.evansitzes.game.entity;

import com.evansitzes.game.Configuration;
import com.evansitzes.game.GameScreen;
import com.evansitzes.game.TwilightEternal;
import com.evansitzes.game.resources.Textures;

import static com.evansitzes.game.entity.Player.Facing.*;
import static com.evansitzes.game.entity.Player.State.IDLE;
import static com.evansitzes.game.entity.Player.State.WALKING;

/**
 * Created by evan on 6/8/16.
 */
public class Player extends Entity {

    public static final int MOMENT_SPEED = 3;

    private final GameScreen screen;
    public SimpleSprite currentSprite;
    public AnimatedSprite animatedSprite;
    Configuration configuration;

    public State state;

    public enum State {
        IDLE, WALKING
    }

    public enum Facing {
        LEFT, RIGHT, UP, DOWN
    }

    public Facing direction;


    public Player(TwilightEternal game, final GameScreen screen) {
        super(game);
        this.screen = screen;
        this.state = IDLE;
        direction = DOWN;
        configuration = new Configuration();

        currentSprite = new SimpleSprite(game, Textures.People.PLAYER);
        animatedSprite = new AnimatedSprite(game);

        position.x = new Configuration().startingPositionX;
        position.y = new Configuration().startingPositionY;
//        locate(Configuration.startingPositionX, Configuration.startingPositionY);
        animatedSprite.position.set(configuration.startingPositionX, configuration.startingPositionY, 0);
        currentSprite.position.set(configuration.startingPositionX, configuration.startingPositionY, 0);

//        this.position.set(700, 700, 0);
//        rectangle = currentSprite.rectangle;

    }

    @Override
    public void handle(final float delta) {

        // handle motion
        handleInput(delta);

        if (state == IDLE) {
            currentSprite.handle(delta);
        } else if (state == WALKING) {

            animatedSprite.setCurrentDirection(direction);

            if (direction == RIGHT) {
                position.x += MOMENT_SPEED;
            } else if (direction == LEFT) {
                position.x -= MOMENT_SPEED;
            } if (direction == UP) {
                position.y += MOMENT_SPEED;
            } else if (direction == DOWN) {
                position.y -= MOMENT_SPEED;
            }

            this.rectangle.set(position.x, position.y, 30, 30);
            locate(position.x, position.y);
            animatedSprite.position.set(position.x, position.y, 0);
            currentSprite.position.set(position.x, position.y, 0);

            animatedSprite.handle(delta);
            state = IDLE;
        }
    }

    public void reversePosition() {
        float newXPosition = configuration.width - position.x;
        float newYPosition = position.y - 180;

        this.rectangle.set(newXPosition, newYPosition, 30, 30);
        this.position.x = newXPosition;
        position.x = newXPosition;
        this.position.y = newYPosition;
        position.y = newYPosition;
        locate(newXPosition, newYPosition);
        animatedSprite.position.set(newXPosition, newYPosition, 0);
        currentSprite.position.set(newXPosition, newYPosition, 0);

    }

    private void handleInput(final float delta) {

    }

    private void checkBounds() {
    }

}
