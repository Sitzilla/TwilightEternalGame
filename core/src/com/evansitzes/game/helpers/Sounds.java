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

    public static final Music SWORD_SWING = Gdx.audio.newMusic(Gdx.files.internal("sound/swing.mp3"));

    public static final Music SWORD_UNSHEATHE = Gdx.audio.newMusic(Gdx.files.internal("sound/sword-unsheathe.mp3"));

    public static final Music MONSTER = Gdx.audio.newMusic(Gdx.files.internal("sound/mnstr2.mp3"));

    public static final Music COINS = Gdx.audio.newMusic(Gdx.files.internal("sound/coin3.mp3"));

    public static final Music BOTTLE = Gdx.audio.newMusic(Gdx.files.internal("sound/bottle.mp3"));

    public static final Music INTERFACE = Gdx.audio.newMusic(Gdx.files.internal("sound/interface2.mp3"));

    public void dispose() {
        TITLE_THEME.dispose();
    }
}
