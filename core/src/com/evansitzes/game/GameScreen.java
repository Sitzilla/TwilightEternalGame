package com.evansitzes.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.evansitzes.game.entity.Player;
import com.evansitzes.game.entity.Portal;
import com.evansitzes.game.entity.Wall;
import com.evansitzes.game.entity.enemy.Enemy;
import com.evansitzes.game.entity.npc.Npc;
import com.evansitzes.game.resources.Sounds;

import java.util.Iterator;

import static com.evansitzes.game.entity.Player.Facing.*;

/**
 * Created by evan on 6/8/16.
 */
public class GameScreen implements Screen {
    public final Configuration configuration;
    public final OrthographicCamera camera; // player Camera
//    public final OrthographicCamera mapCamera;
//    public final OrthographicCamera battleCamera;

    public TiledMapRenderer tiledMapRenderer;
    public TiledMap tiledMap;

    public final TwilightEternal game;
    public final Player player;

    public Level level;
    public boolean battleMode;

    public final Array<Enemy> enemies = new Array();
    public final Array<Npc> npcs = new Array();
    public final Array<Wall> walls = new Array();
    public final Array<Portal> portals = new Array();

    private boolean canMove;

    private final float mapMinX = 0;
    private final float mapMaxX;
    private final float mapMinY = 0;
    private final float mapMaxY;

    public GameScreen(final TwilightEternal game) {
        this.game = game;
        configuration = new Configuration();
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 864, 576); // 1.5 of w and h
        camera.position.set(configuration.startingPositionX, configuration.startingPositionY, 0);
        camera.update();

        player = new Player(game, this);
        battleMode = false;
        level = TmxLevelLoader.load(Vector2.Zero, game, this, "frozen-level");
        mapMaxX = level.mapWidth * level.tileWidth;
        mapMaxY = level.mapHeight * level.tileHeight;
        tiledMapRenderer = new OrthogonalTiledMapRenderer(level.map);
        tiledMapRenderer.setView(camera);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (isPortal()) {
            resetObjects();
            player.reversePosition();
            level = TmxLevelLoader.load(Vector2.Zero, game, this, "town");
            tiledMapRenderer = new OrthogonalTiledMapRenderer(level.map);
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

        for (Enemy enemy : enemies) {
            enemy.draw();
        }

        for (Npc npc : npcs) {
            npc.draw();
        }

        game.batch.end();

        handleEnemies(delta);
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
        int movementSpeed = 5;

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
        final Iterator<Wall> wallIterator = walls.iterator();

        while (wallIterator.hasNext()) {
            final Wall wall = wallIterator.next();

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

            if (wall.overlaps(player)) {
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

            if (enemy.overlaps(player)) {
                enemy.kill();
            }
        }
    }

    private void resetObjects() {
        enemies.clear();
        walls.clear();
        portals.clear();
    }

    @Override
    public void show() {
            Sounds.MAIN_THEME.play();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        Sounds.MAIN_THEME.stop();
    }

    @Override
    public void dispose() {

    }
}
