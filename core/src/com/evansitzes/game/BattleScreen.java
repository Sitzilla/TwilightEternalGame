package com.evansitzes.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.evansitzes.game.entity.enemy.Enemy;
import com.evansitzes.game.loaders.BattleLevelLoader;

/**
 * Created by evan on 9/10/16.
 */
public class BattleScreen implements Screen {

    private final TwilightEternal game;
    private final GameScreen gameScreen;
    private final OrthographicCamera camera;
    private float gameTime = 0;
    private final Configuration configuration;
    private Level level;

    private TiledMapRenderer tiledMapRenderer;

    private final Array<Enemy> enemies = new Array();

    public BattleScreen(final TwilightEternal game, final GameScreen gameScreen) {
        this.game = game;
        this.gameScreen = gameScreen;
        configuration = new Configuration();

        this.level = BattleLevelLoader.load(Vector2.Zero, game, this, "forest-battle-map");

        camera = new OrthographicCamera();
        camera.setToOrtho(false, level.mapWidth * level.tileWidth, level.mapHeight * level.tileHeight); // 1.5 of w and h
        camera.position.set(320, 240, 0);
        camera.update();

        this.tiledMapRenderer = new OrthogonalTiledMapRenderer(level.map);
        tiledMapRenderer.setView(camera);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(final float delta) {
        Gdx.gl.glClearColor(0, 0, 0.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        game.batch.setProjectionMatrix(camera.combined);
        System.out.println(gameTime);

        game.batch.begin();


        for (final Enemy enemy : enemies) {
            enemy.draw();
        }

        game.batch.end();

        gameTime += delta;

        if (gameTime > 5) {
            game.setScreen(gameScreen);
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
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public Array<Enemy> getEnemies() {
        return enemies;
    }

}
