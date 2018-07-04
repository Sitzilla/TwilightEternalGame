package com.evansitzes.game.screens;

/**
 * Created by evan on 6/9/16.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.TimeUtils;
import com.evansitzes.game.GameflowController;
import com.evansitzes.game.TwilightEternal;
import com.evansitzes.game.helpers.Sounds;
import com.evansitzes.game.helpers.Textures;

public class TitleScreen extends TwilightEternalScreen implements Screen, InputProcessor {

    private final OrthographicCamera openingCamera;

    private int currentStage;
    private long startTime = 0;
    private boolean buttonPressed;
    private BitmapFont font;
    private GameflowController gameflowController;

    public TitleScreen(final TwilightEternal game, final GameflowController gameflowController) {
        this.game = game;
        this.gameflowController = gameflowController;
        openingCamera = new OrthographicCamera();
        openingCamera.setToOrtho(false, (float) (game.config.WIDTH * 0.75), (float) (game.config.HEIGHT * 0.75));
        startTime = TimeUtils.millis();
        buttonPressed = false;
        currentStage = 0;

        final InputMultiplexer multiplexer = new InputMultiplexer();
        stage = new Stage();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(multiplexer);

        font = new BitmapFont(Gdx.files.internal("letterings/font.fnt"),
                Gdx.files.internal("letterings/font.png"), false);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        openingCamera.update();
        game.batch.begin();

        switch (currentStage) {

            case 0:

                game.batch.draw(Textures.TitleScreen.LOGO, (float) (game.config.WIDTH / 1.95 - Textures.TitleScreen.LOGO.getWidth() / 2), (float) (game.config.HEIGHT - 300));
                game.batch.draw(Textures.TitleScreen.PRESS_ANY_BUTTON, (float) (game.config.WIDTH / 1.7 - Textures.TitleScreen.PRESS_ANY_BUTTON.getWidth() / 1.5), 300);

                break;

            case 1:

                openingCamera.translate(0, (float) -1.3);
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
                break;

            default:
                gameflowController.setGameScreen();
                dispose();

        }

        game.batch.end();

        stage.act(delta);
        stage.draw();

//            if (openingCamera.position.y < -600) {
//                dispose();
//                gameflowController.setGameScreen();
//
//            }

    }

    @Override
    public boolean keyDown(final int keycode) {
        currentStage++;
        System.out.println(currentStage);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
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
