package com.evansitzes.game.resources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Random;

/**
 * Created by evan on 6/8/16.
 */
public class Textures {

    public TextureRegion getRandomNpc() {
        return new Npcs().VILLAGER;
    }

    public TextureRegion getGuard() {
        return new Npcs().GUARD;
    }

    public static class TitleScreen {
        public static final Texture LOGO = new Texture("letterings/twilight_eternal.png");
        public static final Texture PRESS_ANY_BUTTON = new Texture("letterings/press_any_button.png");
    }

    public static class People {
        public static final TextureRegion PLAYER = loadSimplePlayer();

        public static final TextureRegion[] WALKING_RIGHT = loadWalkingRight();
        public static final TextureRegion[] WALKING_LEFT = loadWalkingLeft();
        public static final TextureRegion[] WALKING_UP = loadWalkingUp();
        public static final TextureRegion[] WALKING_DOWN = loadWalkingDown();

        private static TextureRegion[] loadWalkingLeft() {
            return loadWalking(1);
        }

        private static TextureRegion[] loadWalkingRight() {
            return loadWalking(2);
        }

        private static TextureRegion[] loadWalkingUp() {
            return loadWalking(3);
        }

        private static TextureRegion[] loadWalkingDown() {
            return loadWalking(0);
        }

        private static TextureRegion[] loadWalking(final int index) {
            TextureRegion[] walkingFrames = new TextureRegion[3];
            TextureRegion[][] tmp = loadSprite();

            for (int i = 0; i < 3; i++) {
                walkingFrames[i] = tmp[index][i];
            }
            return walkingFrames;
        }

        private static TextureRegion loadSimplePlayer() {
            return loadSprite()[0][1];
        }

        private static TextureRegion[][] loadSprite() {
            final int frameColumns = 12;
            final int frameRows = 8;

            return splitTextureRegion(frameColumns, frameRows, new Texture(Gdx.files.internal("sprites/final_fantasy_characters.png")));
        }

    }

    public class Npcs {
        public final TextureRegion VILLAGER = loadRandomVillager();
        public final TextureRegion GUARD = loadVillager()[4][1];

        // TODO this is pretty janky
        private TextureRegion loadRandomVillager() {
            TextureRegion[][] sheet = loadVillager();
            // valid numbers x = 0, 4, y = 4, 7, 10
            int x;
            int y;

            if (Math.random() < 0.5) {
                x = 0;
            } else {
                x = 4;
            }

            Random random = new Random();
            int i = random.nextInt(3 - 1 + 1) + 1;

            if (i == 1) {
                y = 4;
            } else if (i == 2) {
                y = 7;
            } else {
                y = 10;
            }

            return sheet[x][y];
        }

        private TextureRegion[][] loadVillager() {
            final int frameColumns = 12;
            final int frameRows = 8;

            return splitTextureRegion(frameColumns, frameRows, new Texture(Gdx.files.internal("sprites/final_fantasy_characters.png")));
        }
//
//        private static TextureRegion[][] loadGuard() {
//            final int frameColumns = 12;
//            final int frameRows = 8;
//
//            return splitTextureRegion(frameColumns, frameRows, new Texture(Gdx.files.internal("sprites/knight.png")));
//        }
    }

    public static class Enemies {
        public static final TextureRegion GOBLIN_WARRIOR = loadEnemy()[0][1];
        public static final TextureRegion EXPLOSION = loadExplosion()[1][3];

        private static TextureRegion[][] loadEnemy() {
            final int frameColumns = 6;
            final int frameRows = 8;

            return splitTextureRegion(frameColumns, frameRows, new Texture(Gdx.files.internal("enemy/goblins.png")));
        }

        private static TextureRegion[][] loadExplosion() {
            final int frameColumns = 5;
            final int frameRows = 3;

            return splitTextureRegion(frameColumns, frameRows, new Texture(Gdx.files.internal("enemy/explosion.png")));
        }
    }

    private static TextureRegion[][] splitTextureRegion(final int columns, final int rows, final Texture sheet) {
        TextureRegion[] walkFrames;

        TextureRegion[][] tmp = TextureRegion.split(sheet, sheet.getWidth() /
                columns, sheet.getHeight() / rows);
        walkFrames = new TextureRegion[columns * rows];
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }

        return tmp;
    }

}
