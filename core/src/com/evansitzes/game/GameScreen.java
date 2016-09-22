package com.evansitzes.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.evansitzes.game.entity.Entity;
import com.evansitzes.game.entity.Player;
import com.evansitzes.game.entity.Portal;
import com.evansitzes.game.entity.Wall;
import com.evansitzes.game.entity.enemy.Enemy;
import com.evansitzes.game.entity.npc.Npc;
import com.evansitzes.game.loaders.TmxLevelLoader;
import com.evansitzes.game.resources.Sounds;

import java.util.Iterator;

import static com.evansitzes.game.entity.Player.Facing.*;

/**
 * Created by evan on 6/8/16.
 */
public class GameScreen implements Screen, InputProcessor {
    private final Configuration configuration;
    private final OrthographicCamera camera; // player Camera

    private TiledMapRenderer tiledMapRenderer;

    private final TwilightEternal game;
    private final Player player;

    private Level level;
    private boolean battleMode;

    private final Array<Entity> obstructables = new Array();
    private final Array<Enemy> enemies = new Array();
    private final Array<Npc> npcs = new Array();
    private final Array<Wall> walls = new Array();
    private final Array<Portal> portals = new Array();

    private State state = State.RUN;

    private final float mapMinX = 0;
    private final float mapMaxX;
    private final float mapMinY = 0;
    private final float mapMaxY;

    private Skin skin;
    private final Stage stage;

    public GameScreen(final TwilightEternal game) {
        this.game = game;
        configuration = new Configuration();
        final float w = (float) Gdx.graphics.getWidth();
        final float h = (float) Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 864, 576); // 1.5 of w and h
        camera.position.set(configuration.startingPositionX, configuration.startingPositionY, 0);
        camera.update();

        skin = new Skin(Gdx.files.internal("skins/golden-ui-skin.json"));
        stage = new Stage();

        Gdx.input.setInputProcessor(stage);

        player = new Player(game, this);
        this.battleMode = false;
        this.level = TmxLevelLoader.load(Vector2.Zero, game, this, "frozen-level");
        mapMaxX = level.mapWidth * level.tileWidth;
        mapMaxY = level.mapHeight * level.tileHeight;
        this.tiledMapRenderer = new OrthogonalTiledMapRenderer(level.map);
        tiledMapRenderer.setView(camera);

        final InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    public void render(final float delta) {
        Gdx.gl.glClearColor(0, 0, 0.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        switch (state) {

            case RUN:
                if (isPortal()) {
                    resetObjects();
                    player.reversePosition();
                    this.level = TmxLevelLoader.load(Vector2.Zero, game, this, "town");
                    this.tiledMapRenderer = new OrthogonalTiledMapRenderer(level.map);
                }

                camera.position.set(calculateCameraPositionX(), calculateCameraPositionY(), 0);
                camera.update();
                tiledMapRenderer.setView(camera);
                tiledMapRenderer.render();
                game.batch.setProjectionMatrix(camera.combined);

                game.batch.begin();

                if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                    if (!areCollisions(Player.Facing.RIGHT)) {
                        player.state = Player.State.WALKING;
                        player.direction = Player.Facing.RIGHT;
                    }
                } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                    if (!areCollisions(Player.Facing.LEFT)) {
                        player.state = Player.State.WALKING;
                        player.direction = Player.Facing.LEFT;
                    }
                } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                    if (!areCollisions(Player.Facing.UP)) {
                        player.state = Player.State.WALKING;
                        player.direction = Player.Facing.UP;
                    }
                } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                    if (!areCollisions(Player.Facing.DOWN)) {
                        player.state = Player.State.WALKING;
                        player.direction = Player.Facing.DOWN;
                    }
                }

                player.handle(delta);

                for (final Enemy enemy : enemies) {
                    enemy.draw();
                }

                for (final Npc npc : npcs) {
                    npc.draw();
                }

                    game.batch.end();
                handleEnemies(delta);
                break;

            case PAUSE:
                System.out.println("Currently Paused....");
                break;

            case RESUME:
                System.out.println("Currently Resuming....");
                break;

            default:
                System.out.println("Currently Defaulting....");
                break;
        }

            stage.act(delta);
            stage.draw();
    }

    private float calculateCameraPositionX() {
        if (player.position.x - camera.viewportWidth / 2 < mapMinX) {
            return camera.viewportWidth / 2;
        }

        if (player.position.x > (mapMaxX - (camera.viewportWidth / 2))) {
            return mapMaxX - (camera.viewportWidth / 2);
        }

        return player.position.x;
    }

    private float calculateCameraPositionY() {
        if (player.position.y - camera.viewportHeight / 2 < mapMinY) {
            return camera.viewportHeight / 2;
        }

        if (player.position.y > (mapMaxY - (camera.viewportHeight / 2))) {
            return mapMaxY - (camera.viewportHeight / 2);
        }
        return player.position.y;
    }

    private boolean isPortal() {
        if(portals.size == 0) { return false; }

        final Iterator<Portal> portalIterator = portals.iterator();

        while(portalIterator.hasNext()) {
            final Portal portal = portalIterator.next();

            if (portal.overlaps(player)) {
                return true;
            }

        }
        return false;
    }

    private boolean areCollisions(final Player.Facing direction) {
        final int movementSpeed = 5;

        // Check for edge of map
        if (direction == LEFT && player.position.x < mapMinX + 1) {
            return true;
        }
        // TODO figure out this collision
        if (direction == RIGHT && player.position.x > mapMaxX - 30) {
            return true;
        }

        if (direction == DOWN && player.position.y < mapMinY + 1) {
            return true;
        }
        // TODO figure out this collision
        if (direction == UP && player.position.y > mapMaxY - 30) {
            return true;
        }

        // Check for Wall
//        final Iterator<Wall> wallIterator = walls.iterator();
        final Iterator<Entity> obstructablesIterator = obstructables.iterator();

        while (obstructablesIterator.hasNext()) {
            final Entity entity = obstructablesIterator.next();

            if (direction == RIGHT) {
                player.rectangle.x += movementSpeed;
            } else if (direction == LEFT) {
                player.rectangle.x -= movementSpeed;
            }
            if (direction == UP) {
                player.rectangle.y += movementSpeed;
            } else if (direction == DOWN) {
                player.rectangle.y -= movementSpeed;
            }

            if (entity.overlaps(player)) {
                if (direction == RIGHT) {
                    player.rectangle.x -= movementSpeed;
                } else if (direction == LEFT) {
                    player.rectangle.x += movementSpeed;
                }
                if (direction == UP) {
                    player.rectangle.y -= movementSpeed;
                } else if (direction == DOWN) {
                    player.rectangle.y += movementSpeed;
                }
                return true;
            }

            if (direction == RIGHT) {
                player.rectangle.x -= movementSpeed;
            } else if (direction == LEFT) {
                player.rectangle.x += movementSpeed;
            }
            if (direction == UP) {
                player.rectangle.y -= movementSpeed;
            } else if (direction == DOWN) {
                player.rectangle.y += movementSpeed;
            }

        }

        return false;
    }

    private void handleEnemies(final float deltaTime) {
        if(enemies.size == 0) { return; }

        final Iterator<Enemy> enemyIterator = enemies.iterator();

        while(enemyIterator.hasNext()) {
            final Enemy enemy = enemyIterator.next();

            if (enemy.overlaps(player) && !enemy.dead) {
                enemy.kill();
                game.setScreen(new BattleScreen(game, this));
            }
        }
    }

    private void handleNpc() {
        if(npcs.size == 0) { return; }

        final Iterator<Npc> npcIterator = npcs.iterator();

        while(npcIterator.hasNext()) {
            final Npc npc = npcIterator.next();

            if (npc.overlapsConversationZone(player)) {
                System.out.println("Overlap npc: " + npc);
                final Conversation conversation = new Conversation("", skin);
                conversation.setText(npc.conversationText);
                conversation.show(stage);
//                setGameState(State.PAUSE);

                return;
            }
        }
    }

    private void resetObjects() {
        enemies.clear();
        walls.clear();
        portals.clear();
        obstructables.clear();
    }

    public void setGameState(final State s){
        this.state = s;
    }

    @Override
    public void show() {
            Sounds.MAIN_THEME.play();
    }

    @Override
    public void resize(final int width, final int height) {

    }

    @Override
    public void pause() {
        this.state = State.PAUSE;
    }

    @Override
    public void resume() {
        this.state = State.RUN;
    }

    @Override
    public void hide() {
        Sounds.MAIN_THEME.stop();
        dispose();
    }

    @Override
    public void dispose() {
//        stage.dispose();
//        skin.dispose();
    }

    // Key Strokes
    @Override
    public boolean keyDown(final int keycode) {
        if (keycode == Input.Keys.SPACE) {
            handleNpc();
        }
        return false;
    }

    @Override
    public boolean keyUp(final int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(final char character) {
        return false;
    }

    @Override
    public boolean touchDown(final int screenX, final int screenY, final int pointer, final int button) {
        return false;
    }

    @Override
    public boolean touchUp(final int screenX, final int screenY, final int pointer, final int button) {
        return false;
    }

    @Override
    public boolean touchDragged(final int screenX, final int screenY, final int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(final int screenX, final int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(final int amount) {
        return false;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public TiledMapRenderer getTiledMapRenderer() {
        return tiledMapRenderer;
    }

    public void setTiledMapRenderer(final TiledMapRenderer tiledMapRenderer) {
        this.tiledMapRenderer = tiledMapRenderer;
    }

    public TwilightEternal getGame() {
        return game;
    }

    public Player getPlayer() {
        return player;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(final Level level) {
        this.level = level;
    }

    public boolean isBattleMode() {
        return battleMode;
    }

    public void setBattleMode(final boolean battleMode) {
        this.battleMode = battleMode;
    }

    public Array<Entity> getObstructables() {
        return obstructables;
    }

    public Array<Enemy> getEnemies() {
        return enemies;
    }

    public Array<Npc> getNpcs() {
        return npcs;
    }

    public Array<Wall> getWalls() {
        return walls;
    }

    public Array<Portal> getPortals() {
        return portals;
    }

    public class Conversation extends Dialog {

        public Conversation(final String title, final Skin skin) {
            super(title, skin);
        }

        public Conversation(final String title, final Skin skin, final String windowStyleName) {
            super(title, skin, windowStyleName);
        }

        {
            skin = new Skin(Gdx.files.internal("skins/golden-ui-skin.json"));
            setMovable(false);
            setResizable(false);

//            Label label = new Label("", skin);
//            label.setWrap(true);
//            label.setFontScale(.8f);
//            label.setAlignment(Align.center);

            padTop(50).padBottom(50);
//            getContentTable().add(label).width(250).row();
            getButtonTable().padTop(50);
            setHeight(100);
            setWidth(150);

//            TextButton dbutton = new TextButton("Yes", skin);
            button("yes", true);

            key(Input.Keys.ENTER, true);
//            invalidateHierarchy();
//            invalidate();
//            layout();
        }

        @Override
        public float getPrefWidth() {
            // force dialog width
            return 800f;
        }

        @Override
        public float getPrefHeight() {
            // force dialog height
            return 400f;
        }

        @Override
        public void result(final Object object) {
//            setGameState(State.RUN);
            System.out.println(object);
        }

        public void setText(final String text) {
            this.text(text);
        }

    }
}
