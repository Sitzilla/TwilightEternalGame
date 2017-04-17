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
import com.evansitzes.game.*;
import com.evansitzes.game.conversation.Conversation;
import com.evansitzes.game.conversation.ConversationChoice;
import com.evansitzes.game.entity.Entity;
import com.evansitzes.game.entity.enemy.Enemy;
import com.evansitzes.game.entity.environment.ConversationZone;
import com.evansitzes.game.entity.environment.Landing;
import com.evansitzes.game.entity.environment.Portal;
import com.evansitzes.game.entity.environment.Wall;
import com.evansitzes.game.entity.npc.Guard;
import com.evansitzes.game.entity.npc.Merchant;
import com.evansitzes.game.entity.npc.Npc;
import com.evansitzes.game.entity.npc.Villager;
import com.evansitzes.game.entity.sprites.PlayerSprite;
import com.evansitzes.game.icons.Icon;
import com.evansitzes.game.icons.IconActor;
import com.evansitzes.game.loaders.TmxLevelLoader;

import java.util.Iterator;

import static com.evansitzes.game.entity.sprites.PlayerSprite.Facing.*;

/**
 * Created by evan on 6/8/16.
 */
public class GameScreen extends TwilightEternalScreen implements Screen, InputProcessor {
    private final Configuration configuration;
    private final OrthographicCamera camera; // playerSprite Camera
    final Skin skin;

    private TiledMapRenderer tiledMapRenderer;

    private final TwilightEternal game;
    private GameflowController gameflowController;
    private final PlayerSprite playerSprite;

    private IconActor inventoryIconActor;
    private Icon inventoryIcon;
    private IconActor spellsIconActor;
    private Icon spellsIcon;

    private Level level;
    private boolean battleMode;
    private int[] layerBackground = {0, 1, 2, 3, 4, 5}; // Ground and Wall layers (behind player)
    private int[] layerAfterBackground = {6, 7, 8}; // Foreground Layers (in front of player)

    private final Array<Entity> obstructables = new Array();
    private final Array<Enemy> enemies = new Array();
    private final Array<Npc> npcs = new Array();
    private final Array<Wall> walls = new Array();
    private final Array<Portal> portals = new Array();
    private final Array<Landing> landings = new Array();
    private final Array<ConversationZone> conversationZones = new Array();

    private State state = State.RUN;

    private final float mapMinX = 0;
    private final float mapMaxX;
    private final float mapMinY = 0;
    private final float mapMaxY;

//    private final Stage stage;

    public GameScreen(final TwilightEternal game, final GameflowController gameflowController) {
        this.game = game;
        this.gameflowController = gameflowController;
        configuration = new Configuration();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, configuration.CAMERA_WIDTH, configuration.CAMERA_HEIGHT);
        camera.position.set(configuration.STARTING_POSITION_X, configuration.STARTING_POSITION_Y, 0);
        camera.update();

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        // Bottom of the screen icons
        skin = new Skin(Gdx.files.internal("skins/uiskin.json"));

        createIcons();

        playerSprite = new PlayerSprite(game, this);
        this.battleMode = false;
        this.level = TmxLevelLoader.load(Vector2.Zero, game, this, configuration.STARTING_LEVEL);
        gameflowController.setCurrentGameZone(configuration.STARTING_LEVEL);
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
    public void show() {
        // TODO is this realllllly necessary?
        final InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    public void render(final float delta) {
        Gdx.gl.glClearColor(0, 0, 0.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(game.player.dead) {
            final Conversation conversation = new Conversation(false, gameflowController);
            conversation.setText("You have died. The world is forever fated \n to remain stuck between shadow and light.");
            conversation.show(stage);
        }

        switch (state) {

            case RUN:
                if (isPortal()) {
                    final Portal currentPortal = getCurrentPortal();
                    final String destination = currentPortal.getDestination();
                    final String landingPosition = currentPortal.getLanding();
                    Landing currentLanding = landings.get(0);

                    resetObjects();
                    this.level = TmxLevelLoader.load(Vector2.Zero, game, this, destination);
                    gameflowController.setCurrentGameZone(destination);

                    for (final Landing landing : landings) {
                        if (landingPosition.equals(landing.getPosition())) {
                            currentLanding = landing;
                        }
                    }

                    playerSprite.setToLandingPage(currentLanding.rectangle.getX(), currentLanding.rectangle.getY());
                    this.tiledMapRenderer = new OrthogonalTiledMapRenderer(level.map);

                }

                camera.position.set(calculateCameraPositionX(), calculateCameraPositionY(), 0);
                camera.update();
                tiledMapRenderer.setView(camera);
                tiledMapRenderer.render(layerBackground);
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

//                inventoryIconActor.drawIcon();
                game.batch.end();

                tiledMapRenderer.render(layerAfterBackground);
                handleEnemies(delta);
                break;

            case PAUSE:
//                System.out.println("Currently Paused....");
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

    //TODO combine portal methods
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

    private Portal getCurrentPortal() {
        final Iterator<Portal> portalIterator = portals.iterator();

        while(portalIterator.hasNext()) {
            final Portal portal = portalIterator.next();

            if (portal.overlaps(playerSprite)) {
                return portal;
            }

        }
        return null;
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
                enemyIterator.remove();
                gameflowController.setBattleScreen();
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

                if (npc instanceof Villager) {
                    final Conversation conversation = new Conversation(false, gameflowController);
                    conversation.setText(new ConversationChoice().getRandomConversation());
                    conversation.show(stage);
                }

                if (npc instanceof Guard) {
                    final Conversation conversation = new Conversation(false, gameflowController);
                    conversation.setText(npc.conversationText);
                    conversation.show(stage);
                }

                if (npc instanceof Merchant) {
                    final Conversation conversation = new Conversation(true, gameflowController);
                    conversation.setText(npc.conversationText);
                    conversation.show(stage);

                    if (conversation.isSeeWares()) {
                        System.out.println("askjfahsdlkfjhlksjdhf");
                    }
                }

                return;
            }
        }
    }

    private void handleConversationZone() {
        if(conversationZones.size == 0) { return; }

        final Iterator<ConversationZone> conversationZoneIterator = conversationZones.iterator();

        while(conversationZoneIterator.hasNext()) {
            final ConversationZone conversationZone = conversationZoneIterator.next();

            if (conversationZone.overlapsConversationZone(playerSprite)) {
                System.out.println("Overlap conversation zone: " + conversationZone);

                final Conversation conversation = new Conversation(false, gameflowController);
                conversation.setText(conversationZone.getText());
                conversation.show(stage);

                return;
            }
        }
    }

    private void createIcons() {
        inventoryIcon = new Icon(skin, "backpack");
        inventoryIconActor = new IconActor(this, inventoryIcon, skin);
        inventoryIconActor.setPosition(20, 20);
        stage.addActor(inventoryIconActor);
        inventoryIconActor.setVisible(true);
        inventoryIconActor.setMovable(false);

        spellsIcon = new Icon(skin, "spells");
        spellsIconActor = new IconActor(this, spellsIcon, skin);
        spellsIconActor.setPosition(140, 20);
        stage.addActor(spellsIconActor);
        spellsIconActor.setVisible(true);
        spellsIconActor.setMovable(false);
    }

    private void resetObjects() {
        enemies.clear();
        npcs.clear();
        walls.clear();
        portals.clear();
        obstructables.clear();
        landings.clear();
        conversationZones.clear();
    }

    public void setGameState(final State s){
        this.state = s;
    }

    public void setInventoryScreen() {
        gameflowController.setInventoryScreen();
    }

    public void setConversationWindow(final String text) {
        final Conversation conversation = new Conversation(false, gameflowController);
        conversation.setText(text);
        conversation.show(stage);
    }

    @Override
    public void resize(final int width, final int height) {

    }

    @Override
    public void pause() {
//        this.state = State.PAUSE;
    }

    @Override
    public void resume() {
        this.state = State.RUN;
    }

    @Override
    public void hide() {
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
        System.out.println(keycode);
        if (keycode == Input.Keys.SPACE || keycode == Input.Keys.ENTER) {
            handleNpc();
            handleConversationZone();
        }

        if (keycode == Input.Keys.I) {
            System.out.println("Showing screen~");
            setInventoryScreen();
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

    public Array<Landing> getLandings() {
        return landings;
    }

    public Array<ConversationZone> getConversationZones() {
        return conversationZones;
    }
}
