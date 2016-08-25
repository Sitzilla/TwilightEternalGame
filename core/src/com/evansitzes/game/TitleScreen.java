package com.evansitzes.game;

/**
 * Created by evan on 6/9/16.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.TimeUtils;
import com.evansitzes.game.resources.Sounds;
import com.evansitzes.game.resources.Textures;

public class TitleScreen implements Screen {

    private final TwilightEternal game;

    private final OrthographicCamera openingCamera;

    private long startTime = 0;
    private boolean buttonPressed;
    private BitmapFont font;

    public TitleScreen(final TwilightEternal game) {
        this.game = game;
        openingCamera = new OrthographicCamera();
        openingCamera.setToOrtho(false, (float) (game.config.width * 0.75), (float) (game.config.height * 0.75));
        startTime = TimeUtils.millis();
        buttonPressed = false;

        font = new BitmapFont(Gdx.files.internal("letterings/font.fnt"),
                Gdx.files.internal("letterings/font.png"), false);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        openingCamera.update();
//        game.batch.setProjectionMatrix(openingCamera.combined);

        if (!buttonPressed) {
            game.batch.begin();
//            game.batch.draw(Textures.TitleScreen.LOGO, (float) (game.config.width * 0.75 / 2 - Textures.TitleScreen.LOGO.getWidth() / 2), (float) (game.config.height * 0.75 - 200));
//            game.batch.draw(Textures.TitleScreen.PRESS_ANY_BUTTON, (float) (game.config.width * 0.75 / 2 - Textures.TitleScreen.PRESS_ANY_BUTTON.getWidth() * 0.75 / 1.5), 250);
            game.batch.draw(Textures.TitleScreen.LOGO, (float) (game.config.width / 1.95 - Textures.TitleScreen.LOGO.getWidth() / 2), (float) (game.config.height - 300));
            game.batch.draw(Textures.TitleScreen.PRESS_ANY_BUTTON, (float) (game.config.width / 1.7 - Textures.TitleScreen.PRESS_ANY_BUTTON.getWidth() / 1.5), 300);

            game.batch.end();

            if (TimeUtils.timeSinceMillis(startTime) > 500) {
                if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
//                    game.setScreen(new GameScreen(game));
                    dispose();
                    buttonPressed = true;
                }
            }
        } else {

            openingCamera.translate(0, (float) -1.3);
            game.batch.begin();
//            font.setColor(Color.WHITE);
//            font.setColor(100, 0, 0, (float) 0.6);

            font.draw(game.batch,
                    "This land has been \n" +
                            "ravaged by a war between \n" +
                            "the forces of good and evil, \n" +
                            "the neverending combat \n" +
                            "between the armies of light \n" +
                            "and the servants of darkness \n" +
                            "has thrown the world into an \n" +
                            "Eternal Twilight. \n" +
                            "Young hero... your destiny awaits!"
                    , 50, 800);

            game.batch.end();

            if (openingCamera.position.y < -600) {
                dispose();
                game.setScreen(new GameScreen(game));

            }
        }
    }

    @Override
    public void resize(final int width, final int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void show() {
        Sounds.TITLE_THEME.play();
    }

    @Override
    public void hide() {
        Sounds.TITLE_THEME.stop();
    }

    @Override
    public void dispose() {
    }

}
