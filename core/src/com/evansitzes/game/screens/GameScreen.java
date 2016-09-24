package com.evansitzes.game.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.evansitzes.game.Configuration;
import com.evansitzes.game.Level;
import com.evansitzes.game.State;
import com.evansitzes.game.TwilightEternal;
import com.evansitzes.game.conversation.Conversation;
import com.evansitzes.game.entity.Entity;
import com.evansitzes.game.entity.PlayerSprite;
import com.evansitzes.game.entity.Portal;
import com.evansitzes.game.entity.Wall;
import com.evansitzes.game.entity.enemy.Enemy;
import com.evansitzes.game.entity.npc.Npc;
import com.evansitzes.game.loaders.TmxLevelLoader;
import com.evansitzes.game.resources.Sounds;

import java.util.Iterator;

import static com.evansitzes.game.entity.PlayerSprite.Facing.*;

/**
 * Created by evan on 6/8/16.
 */
public class GameScreen implements Screen, InputProcessor {
    private final Configuration configuration;
    private final OrthographicCamera camera; // playerSprite Camera

    private TiledMapRenderer tiledMapRenderer;

    private final TwilightEternal game;
    private final PlayerSprite playerSprite;

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

        playerSprite = new PlayerSprite(game, this);
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
                    playerSprite.reversePosition();
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
                    if (!areCollisions(PlayerSprite.Facing.RIGHT)) {
                        playerSprite.state = PlayerSprite.State.WALKING;
                        playerSprite.direction = PlayerSprite.Facing.RIGHT;
                    }
                } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                    if (!areCollisions(PlayerSprite.Facing.LEFT)) {
                        playerSprite.state = PlayerSprite.State.WALKING;
                        playerSprite.direction = PlayerSprite.Facing.LEFT;
                    }
                } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                    if (!areCollisions(PlayerSprite.Facing.UP)) {
                        playerSprite.state = PlayerSprite.State.WALKING;
                        playerSprite.direction = PlayerSprite.Facing.UP;
                    }
                } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                    if (!areCollisions(PlayerSprite.Facing.DOWN)) {
                        playerSprite.state = PlayerSprite.State.WALKING;
                        playerSprite.direction = PlayerSprite.Facing.DOWN;
                    }
                }

                playerSprite.handle(delta);

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
        if (playerSprite.position.x - camera.viewportWidth / 2 < mapMinX) {
            return camera.viewportWidth / 2;
        }

        if (playerSprite.position.x > (mapMaxX - (camera.viewportWidth / 2))) {
            return mapMaxX - (camera.viewportWidth / 2);
        }

        return playerSprite.position.x;
    }

    private float calculateCameraPositionY() {
        if (playerSprite.position.y - camera.viewportHeight / 2 < mapMinY) {
            return camera.viewportHeight / 2;
        }

        if (playerSprite.position.y > (mapMaxY - (camera.viewportHeight / 2))) {
            return mapMaxY - (camera.viewportHeight / 2);
        }
        return playerSprite.position.y;
    }

    private boolean isPortal() {
        if(portals.size == 0) { return false; }

        final Iterator<Portal> portalIterator = portals.iterator();

        while(portalIterator.hasNext()) {
            final Portal portal = portalIterator.next();

            if (portal.overlaps(playerSprite)) {
                return true;
            }

        }
        return false;
    }

    private boolean areCollisions(final PlayerSprite.Facing direction) {
        final int movementSpeed = 5;

        // Check for edge of map
        if (direction == LEFT && playerSprite.position.x < mapMinX + 1) {
            return true;
        }
        // TODO figure out this collision
        if (direction == RIGHT && playerSprite.position.x > mapMaxX - 30) {
            return true;
        }

        if (direction == DOWN && playerSprite.position.y < mapMinY + 1) {
            return true;
        }
        // TODO figure out this collision
        if (direction == UP && playerSprite.position.y > mapMaxY - 30) {
            return true;
        }

        // Check for Wall
//        final Iterator<Wall> wallIterator = walls.iterator();
        final Iterator<Entity> obstructablesIterator = obstructables.iterator();

        while (obstructablesIterator.hasNext()) {
            final Entity entity = obstructablesIterator.next();

            if (direction == RIGHT) {
                playerSprite.rectangle.x += movementSpeed;
            } else if (direction == LEFT) {
                playerSprite.rectangle.x -= movementSpeed;
            }
            if (direction == UP) {
                playerSprite.rectangle.y += movementSpeed;
            } else if (direction == DOWN) {
                playerSprite.rectangle.y -= movementSpeed;
            }

            if (entity.overlaps(playerSprite)) {
                if (direction == RIGHT) {
                    playerSprite.rectangle.x -= movementSpeed;
                } else if (direction == LEFT) {
                    playerSprite.rectangle.x += movementSpeed;
                }
                if (direction == UP) {
                    playerSprite.rectangle.y -= movementSpeed;
                } else if (direction == DOWN) {
                    playerSprite.rectangle.y += movementSpeed;
                }
                return true;
            }

            if (direction == RIGHT) {
                playerSprite.rectangle.x -= movementSpeed;
            } else if (direction == LEFT) {
                playerSprite.rectangle.x += movementSpeed;
            }
            if (direction == UP) {
                playerSprite.rectangle.y -= movementSpeed;
            } else if (direction == DOWN) {
                playerSprite.rectangle.y += movementSpeed;
            }

        }

        return false;
    }

    private void handleEnemies(final float deltaTime) {
        if(enemies.size == 0) { return; }

        final Iterator<Enemy> enemyIterator = enemies.iterator();

        while(enemyIterator.hasNext()) {
            final Enemy enemy = enemyIterator.next();

            if (enemy.overlaps(playerSprite) && !enemy.dead) {
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

            if (npc.overlapsConversationZone(playerSprite)) {
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

    public PlayerSprite getPlayerSprite() {
        return playerSprite;
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

}
