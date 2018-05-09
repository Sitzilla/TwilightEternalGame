package com.evansitzes.game.physics;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.evansitzes.game.TwilightEternal;
import com.evansitzes.game.helpers.DirectionEnum;

import static com.evansitzes.game.helpers.DirectionEnum.*;

public class CollisionHelper {

    public static boolean isCollision(final TwilightEternal game, final DirectionEnum direction, final TiledMapTileLayer collisionLayer) {
        final int currentXTile = (int) ((game.player.position.x + 5) / 32);
        final int currentYTile = (int) ((game.player.position.y + 5) / 32);
        final Rectangle collisionRectangle = new Rectangle();

        if (direction == LEFT) {
            final Rectangle futurePosition = new Rectangle(game.player.position.x, game.player.position.y, 32, 32);
            futurePosition.setX(futurePosition.x - 10);

            if (collisionLayer.getCell(currentXTile - 1, currentYTile - 1) != null) {
                collisionRectangle.set((currentXTile - 1) * 32, (currentYTile - 1) * 32, 32, 32);

                if (futurePosition.overlaps(collisionRectangle)) {
                    return true;
                }
            }
            if (collisionLayer.getCell(currentXTile - 1, currentYTile) != null) {
                collisionRectangle.set((currentXTile - 1) * 32, currentYTile * 32, 32, 32);

                if (futurePosition.overlaps(collisionRectangle)) {
                    return true;
                }
            }

            if (collisionLayer.getCell(currentXTile - 1, currentYTile + 1)  != null) {
                collisionRectangle.set((currentXTile - 1) * 32, (currentYTile + 1) * 32, 32, 32);

                if (futurePosition.overlaps(collisionRectangle)) {
                    return true;
                }
            }
        }

        if (direction == RIGHT) {
            final Rectangle futurePosition = new Rectangle(game.player.position.x, game.player.position.y, 32, 32);
            futurePosition.setX(futurePosition.x + game.player.hitBox.width + 10);

            if (collisionLayer.getCell(currentXTile + 1, currentYTile - 1) != null) {
                collisionRectangle.set((currentXTile + 1) * 32, (currentYTile - 1) * 32, 32, 32);

                if (futurePosition.overlaps(collisionRectangle)) {
                    return true;
                }
            }
            if (collisionLayer.getCell(currentXTile + 1, currentYTile) != null) {
                collisionRectangle.set((currentXTile + 1) * 32, currentYTile * 32, 32, 32);

                if (futurePosition.overlaps(collisionRectangle)) {
                    return true;
                }
            }

            if (collisionLayer.getCell(currentXTile + 1, currentYTile + 1)  != null) {
                collisionRectangle.set((currentXTile + 1) * 32, (currentYTile + 1) * 32, 32, 32);

                if (futurePosition.overlaps(collisionRectangle)) {
                    return true;
                }
            }
        }

        if (direction == UP) {
            final Rectangle futurePosition = new Rectangle(game.player.position.x, game.player.position.y, 32, 32);
            futurePosition.setY(futurePosition.y + game.player.hitBox.height + 10);

            if (collisionLayer.getCell(currentXTile + 1, currentYTile + 1) != null) {
                collisionRectangle.set((currentXTile + 1) * 32, (currentYTile + 1) * 32, 32, 32);

                if (futurePosition.overlaps(collisionRectangle)) {
                    return true;
                }
            }
            if (collisionLayer.getCell(currentXTile, currentYTile + 1) != null) {
                collisionRectangle.set(currentXTile * 32, (currentYTile + 1) * 32, 32, 32);

                if (futurePosition.overlaps(collisionRectangle)) {
                    return true;
                }
            }

            if (collisionLayer.getCell(currentXTile - 1, currentYTile + 1)  != null) {
                collisionRectangle.set((currentXTile - 1) * 32, (currentYTile + 1) * 32, 32, 32);

                if (futurePosition.overlaps(collisionRectangle)) {
                    return true;
                }
            }
        }

        if (direction == DOWN) {
            final Rectangle futurePosition = new Rectangle(game.player.position.x, game.player.position.y, 32, 32);
            futurePosition.setY(futurePosition.y - 10);

            if (collisionLayer.getCell(currentXTile + 1, currentYTile - 1) != null) {
                collisionRectangle.set((currentXTile + 1) * 32, (currentYTile - 1) * 32, 32, 32);

                if (futurePosition.overlaps(collisionRectangle)) {
                    return true;
                }
            }
            if (collisionLayer.getCell(currentXTile, currentYTile - 1) != null) {
                collisionRectangle.set(currentXTile * 32, (currentYTile - 1) * 32, 32, 32);

                if (futurePosition.overlaps(collisionRectangle)) {
                    return true;
                }
            }

            if (collisionLayer.getCell(currentXTile - 1, currentYTile - 1)  != null) {
                collisionRectangle.set((currentXTile - 1) * 32, (currentYTile - 1) * 32, 32, 32);

                if (futurePosition.overlaps(collisionRectangle)) {
                    return true;
                }
            }
        }

        return false;
    }
}
