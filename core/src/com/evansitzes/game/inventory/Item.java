package com.evansitzes.game.inventory;

/**
 * Created by evan on 9/27/16.
 */
public enum Item {
    RUBY("ruby"), GOLD("gold"), DIAMOND("diamond");

    private String textureRegion;

    private Item(final String textureRegion) {
        this.textureRegion = textureRegion;
    }

    public String getTextureRegion() {
        return textureRegion;
    }
}
