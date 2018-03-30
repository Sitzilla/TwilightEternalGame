package com.evansitzes.game.helpers;

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
    public TextureRegion getMerchant() {
        return new Npcs().MERCHANT;
    }
    public TextureRegion getBlacksmith() {
        return new Npcs().BLACKSMITH;
    }

    public static class TitleScreen {
        public static final Texture LOGO = new Texture("letterings/twilight_eternal.png");
        public static final Texture PRESS_ANY_BUTTON = new Texture("letterings/press_any_button.png");
    }

    public static class People {
        public static final TextureRegion PLAYER = loadSimplePlayer();
        public static final TextureRegion BATTLE_PLAYER = loadBattlePlayer();
        public static final TextureRegion INVENTORY_PLAYER = loadInventoryPlayer();

        public static final TextureRegion PLAYER_PORTRAIT = loadPlayerPortrait();

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
        private static TextureRegion loadBattlePlayer() {
            return loadSprite()[1][1];
        }
        private static TextureRegion loadInventoryPlayer() {
            return loadInventorySprite()[0][0];
        }

        private static TextureRegion[][] loadSprite() {
            final int frameColumns = 12;
            final int frameRows = 8;

            return splitTextureRegion(frameColumns, frameRows, new Texture(Gdx.files.internal("sprites/final_fantasy_characters.png")));
        }

        private static TextureRegion[][] loadBattleSprite() {
            final int frameColumns = 1;
            final int frameRows = 1;

            return splitTextureRegion(frameColumns, frameRows, new Texture(Gdx.files.internal("enemy/main_character.png")));
        }

        private static TextureRegion[][] loadInventorySprite() {
            final int frameColumns = 1;
            final int frameRows = 1;

            return splitTextureRegion(frameColumns, frameRows, new Texture(Gdx.files.internal("sprites/inventory_player.png")));
        }

        private static TextureRegion loadPlayerPortrait() {
            final int frameColumns = 1;
            final int frameRows = 1;

            return splitTextureRegion(frameColumns, frameRows, new Texture(Gdx.files.internal("sprites/portrait.jpg")))[0][0];
        }
    }

    public static class Npcs {
        public static final TextureRegion VILLAGER = loadRandomVillager();
        public static final TextureRegion GUARD = loadSpriteSheet(12, 8, "sprites/final_fantasy_characters.png")[4][1];
        public static final TextureRegion MERCHANT = loadSpriteSheet(3, 4, "sprites/weird_purple_guy.png")[0][1];
        public static final TextureRegion BLACKSMITH = loadSpriteSheet(12, 8, "sprites/final_fantasy_characters.png")[4][7];
        public static final TextureRegion MAID = loadSpriteSheet(3, 4, "sprites/maid.png")[0][1];

        // TODO this is pretty janky
        private static TextureRegion loadRandomVillager() {
            TextureRegion[][] sheet = loadSpriteSheet(12, 8, "sprites/final_fantasy_characters.png");
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

        private static TextureRegion[][] loadSpriteSheet(final int columns, final int rows, final String path) {
            return splitTextureRegion(columns, rows, new Texture(Gdx.files.internal(path)));
        }

//        private static TextureRegion[][] loadVillager() {
//            final int frameColumns = 12;
//            final int frameRows = 8;
//
//            return splitTextureRegion(frameColumns, frameRows, new Texture(Gdx.files.internal("sprites/final_fantasy_characters.png")));
//        }
//
//        private static TextureRegion[][] loadMaid() {
//            final int frameColumns = 3;
//            final int frameRows = 8;
//
//            return splitTextureRegion(frameColumns, frameRows, new Texture(Gdx.files.internal("sprites/final_fantasy_characters.png")));
//        }
//
//        private static TextureRegion[][] loadMerchant() {
//            final int frameColumns = 3;
//            final int frameRows = 4;
//
//            return splitTextureRegion(frameColumns, frameRows, new Texture(Gdx.files.internal("sprites/weird_purple_guy.png")));
//        }
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
        public static final TextureRegion GOBLIN_WARRIOR_BATTLE = loadBattleEnemy()[0][0];
        public static final TextureRegion EXPLOSION = loadExplosion()[1][3];

        // TODO should be able to combine these
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

        private static TextureRegion[][] loadBattleEnemy() {
            final int frameColumns = 1;
            final int frameRows = 1;

            return splitTextureRegion(frameColumns, frameRows, new Texture(Gdx.files.internal("enemy/battle_goblin.png")));
        }
    }

    public static class Life {
        public static final TextureRegion LIFE_BAR = loadLife()[0][0];
        public static final TextureRegion LIFE_BAR_CONTAINER = loadLifeContainer()[0][0];

        private static TextureRegion[][] loadLife() {
            final int frameColumns = 1;
            final int frameRows = 1;

            return splitTextureRegion(frameColumns, frameRows, new Texture(Gdx.files.internal("colors/red.png")));
        }

        private static TextureRegion[][] loadLifeContainer() {
            final int frameColumns = 1;
            final int frameRows = 1;

            return splitTextureRegion(frameColumns, frameRows, new Texture(Gdx.files.internal("colors/black.png")));
        }

    }

    public static class Colors {
        public static final TextureRegion GREY = loadColor()[0][0];

        private static TextureRegion[][] loadColor() {
            final int frameColumns = 1;
            final int frameRows = 1;

            return splitTextureRegion(frameColumns, frameRows, new Texture(Gdx.files.internal("colors/grey.png")));
        }
    }

    public static class Items {

        public static final TextureRegion BACKPACK = loadInventory()[0][0];
        public static final TextureRegion SPELLS = loadInventory()[0][1];
        public static final TextureRegion SAVE = loadInventory()[0][2];

        public static final TextureRegion BLANK = loadItems()[0][15];

        public static final TextureRegion BRONZE_SWORD = loadItems()[4][0];
        public static final TextureRegion BRONZE_BOOTS = loadItems()[3][0];
        public static final TextureRegion BRONZE_PANTS = loadItems()[2][0];
        public static final TextureRegion BRONZE_ARMOR = loadItems()[1][0];
        public static final TextureRegion BRONZE_HELMET = loadItems()[0][0];
        public static final TextureRegion IRON_SWORD = loadItems()[4][1];
        public static final TextureRegion IRON_BOOTS = loadItems()[3][1];
        public static final TextureRegion IRON_PANTS = loadItems()[2][1];
        public static final TextureRegion IRON_ARMOR = loadItems()[1][1];
        public static final TextureRegion IRON_HELMET = loadItems()[0][1];
        public static final TextureRegion APPLE = loadItems()[0][10];
        public static final TextureRegion BONE = loadItems()[1][12];
        public static final TextureRegion VEGGIES = loadItems()[0][9];

        private static TextureRegion[][] loadItems() {
            final int frameColumns = 16;
            final int frameRows = 16;

            return splitTextureRegion(frameColumns, frameRows, new Texture(Gdx.files.internal("icons/items.jpg")));
        }

        private static TextureRegion[][] loadInventory() {
            final int frameColumns = 4;
            final int frameRows = 3;

            return splitTextureRegion(frameColumns, frameRows, new Texture(Gdx.files.internal("icons/icons.jpg")));
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
