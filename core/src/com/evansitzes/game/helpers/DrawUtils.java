package com.evansitzes.game.helpers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.evansitzes.game.TwilightEternal;


public class DrawUtils {
    public static void draw(final TwilightEternal game, final Rectangle rectangle, final Color color) {
        game.batch.end();
        game.shapeRenderer.setProjectionMatrix(game.batch.getProjectionMatrix());
        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        game.shapeRenderer.setColor(color);
        game.shapeRenderer.rect(
                rectangle.x, rectangle.y,
                rectangle.width, rectangle.height);

        game.shapeRenderer.end();
        game.batch.begin();

    }
//
//    fun drawLine(gameContext: GameContext, x1: Float, y1: Float, x2: Float, y2:Float) {
//        gameContext.batch.end()
//        gameContext.shapeRenderer.projectionMatrix = gameContext.batch.projectionMatrix
//        gameContext.shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
//
//        gameContext.shapeRenderer.setColor(Color.GREEN)
//        gameContext.shapeRenderer.line(x1, y1, x2, y2)
//
//        gameContext.shapeRenderer.end()
//        gameContext.batch.begin()
//    }
}
