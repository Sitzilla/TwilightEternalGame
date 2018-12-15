package com.evansitzes.game.component;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.evansitzes.game.TwilightEternal;
import com.evansitzes.game.helpers.Textures.Stats;

public class StatsBar {

    private final BitmapFont font;
    private final NinePatch stat;
    private final NinePatch container;
    private float width;
    private final float totalWidth;
    private final String name;

    public StatsBar(final TextureRegion barGradient, final float totalWidth, final String name) {
        this.font = new BitmapFont();
        this.totalWidth = totalWidth;
        this.stat = new NinePatch(barGradient, 0, 0, 0, 0);
        this.container = new NinePatch(Stats.STATS_BAR_CONTAINER, 5, 5, 2, 2);
        this.name = name;
    }

    public void setWidth(final float current, final float total) {
        width = current / total * totalWidth;
    }

    public void draw(final TwilightEternal game, final float x, final float y) {
        font.draw(game.batch, "Current " + name + ":", x, y);
        container.draw(game.batch, x - 5, y - 35, totalWidth + 10, 20);
        stat.draw(game.batch, x, y - 30, width, 10);
    }

}
