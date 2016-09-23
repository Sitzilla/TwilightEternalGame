package com.evansitzes.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.evansitzes.game.conversation.Conversation;
import com.evansitzes.game.entity.enemy.Enemy;
import com.evansitzes.game.loaders.BattleLevelLoader;

/**
 * Created by evan on 9/10/16.
 */
public class BattleScreen implements Screen, InputProcessor {

    private final TwilightEternal game;
    private final GameScreen gameScreen;
    private final OrthographicCamera camera;
    private float gameTime = 0;
    private final Configuration configuration;
    private Level level;

    private TiledMapRenderer tiledMapRenderer;

    private final Array<Enemy> enemies = new Array();

    private Skin skin;
    private Stage stage;
    private Conversation conversation;
    private List<String> choices;

    public BattleScreen(final TwilightEternal game, final GameScreen gameScreen) {
        this.game = game;
        this.gameScreen = gameScreen;
        configuration = new Configuration();

        this.level = BattleLevelLoader.load(Vector2.Zero, game, this, "forest-battle-map");

        camera = new OrthographicCamera();
        camera.setToOrtho(false, level.mapWidth * level.tileWidth, level.mapHeight * level.tileHeight); // 1.5 of w and h
        camera.position.set(320, 240, 0);
        camera.update();

        stage = new Stage();
        skin = new Skin(Gdx.files.internal("skins/golden-ui-skin.json"));
        conversation = new Conversation("", skin);

        Gdx.input.setInputProcessor(stage);
        final InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(multiplexer);

        this.tiledMapRenderer = new OrthogonalTiledMapRenderer(level.map);
        tiledMapRenderer.setView(camera);

        choices = new List<String>(new Skin(Gdx.files.internal("skins/golden-ui-skin.json")));

        Array choiceItems = new Array();
        choiceItems.add("Attack");
        choiceItems.add("Run");
        choiceItems.add("Pee Pants");
        choices.setItems(choiceItems);

        ScrollPane scrollPane = new ScrollPane(choices);
        scrollPane.setOverscroll(false, false);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setForceScroll(true, false);
        scrollPane.setScrollBarPositions(false, true);

        conversation.defaults().expand().fill();
        conversation.row();
        conversation.add(scrollPane).pad(10, 10, 10, 10);

        conversation.pack();

        choices.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                final String choice = choices.getSelected();

                System.out.println("You are: " + choice);
            }
        });
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

        game.batch.begin();

//        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
//            System.out.println(gameTime);
//            conversation.setText("Hello screen! ");
//            conversation.show(stage);
//
//        }


        for (final Enemy enemy : enemies) {
            enemy.draw();
        }

        game.batch.end();

        gameTime += delta;

        if (gameTime > 30) {
            game.setScreen(gameScreen);
        }

        stage.act(delta);
        stage.draw();
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

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.SPACE) {
            System.out.println("Time:" + gameTime);
//            conversation.setText(conversations.toString());
            conversation.show(stage);
        }
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
}
