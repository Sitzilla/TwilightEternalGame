package com.evansitzes.game.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

/**
 * Created by evan on 6/9/16.
 */
public class Sounds {

    public static final Music MAIN_THEME = Gdx.audio.newMusic(Gdx.files.internal("sound/song-of-healing.mp3"));
    static { MAIN_THEME.setLooping(true); }

    public static final Music TITLE_THEME = Gdx.audio.newMusic(Gdx.files.internal("sound/TITLE-theme.mp3"));
    static { TITLE_THEME.setLooping(true); }

    public static final Music REALISTIC_PUNCH = Gdx.audio.newMusic(Gdx.files.internal("sound/realistic-punch.mp3"));

    public void dispose() {
        TITLE_THEME.dispose();
    }
}
