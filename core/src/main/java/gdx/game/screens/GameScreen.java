package gdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import gdx.game.helpers.AssetManager;
import gdx.game.helpers.InputHandler;
import gdx.game.objects.Enemy;
import gdx.game.objects.Player;
import gdx.game.objects.ScrollHandler;
import gdx.game.utils.Settings;

public class GameScreen implements Screen {
    private OrthographicCamera camera;
    private Stage stage;
    private Player player;
    private Body playerbody;
    private ScrollHandler sh;
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private ShapeRenderer shapeRenderer;
    private Batch batch;
    private InputHandler inputHandler;
    private Skin skin;
    private Table root;
    private Array<Enemy> enemies;
    private long lastEnemyTime;
    private long nextEnemyTime;
    public GameScreen(){
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        Box2D.init();
        camera = new OrthographicCamera(Settings.GAME_WIDTH, Settings.GAME_HEIGHT);
        camera.setToOrtho(false, Settings.GAME_WIDTH,Settings.GAME_HEIGHT);
        FitViewport fitViewport = new FitViewport(Settings.GAME_WIDTH,Settings.GAME_HEIGHT);
        shapeRenderer = new ShapeRenderer();
        stage = new Stage(fitViewport);
        batch = stage.getBatch();
        sh = new ScrollHandler();
        stage.addActor(sh);
        player = new Player( Settings.PLAYER_STARTX,Settings.PLAYER_STARTY,4);
        stage.addActor(player);
        createWorld();

        //set the input processors
        inputHandler = new InputHandler(this);
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(inputHandler);
        Gdx.input.setInputProcessor(multiplexer);

        root = new Table();
        root.setFillParent(true);
        stage.addActor(root);

        // Set the table's alignment to bottom-left
        root.bottom().left();

        Button button = new Button(skin, "arcade");
        root.add(button).pad(60);

        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                player.attack();
            }
        });
        enemies = new Array<>();
        Enemy enemy = new Enemy(Settings.GAME_WIDTH+100,100,3, Settings.BG_SPEED);
        nextEnemyTime = MathUtils.random(Settings.ENEMY_MIN_COUNTER,Settings.ENEMY_MAX_COUNTER);
        player.setZIndex(1);
        button.setZIndex(3);
    }

    private void createWorld() {
        world = new World(new Vector2(0, -900), false);
        debugRenderer = new Box2DDebugRenderer();

        //Ground
        BodyDef groundBodyDef = new BodyDef();
        // Set its world position
        groundBodyDef.position.set(new Vector2(0, 10));

        // Create a body from the definition and add it to the world
        Body groundBody = world.createBody(groundBodyDef);

        // Create a polygon shape
        PolygonShape groundBox = new PolygonShape();
        // Set the polygon shape as a box which is twice the size of our view port and 20 high
        // (setAsBox takes half-width and half-height as arguments)
        groundBox.setAsBox(camera.viewportWidth, 90.0f);
        // Create a fixture from our polygon shape and add it to our ground body
        groundBody.createFixture(groundBox, 0.0f);

        // Create our body in the world using our body definition
        playerbody = world.createBody(player.getBodyDef());
        // Create our fixture and attach it to the body
        Fixture fixture = playerbody.createFixture(player.getFixtureDef());
        player.setBody(playerbody);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.draw();
        stage.act(delta);
        doPhysicsStep(delta);
        Vector2 position = playerbody.getPosition();
        player.setPosition(position,true);
        for(int i = 0; i<enemies.size; i++){
            Gdx.app.log("enemy", i+"");
            if(player.attackCollides(enemies.get(i))){
                enemies.get(i).hurt();
                Gdx.app.log("colision","colision detected");
            }else if(enemies.get(i).isOutOfScreen()){
                enemies.get(i).remove();
                enemies.removeIndex(i);
            }
        }
        for(int i = 0; i<enemies.size; i++) {
            if (enemies.get(i).collides(player)) {
                player.hurt();
                enemies.get(i).attack();
            }
        }
        if(TimeUtils.nanoTime() - lastEnemyTime > nextEnemyTime) spawnEnemy();
        //drawElements();

    }
    private float accumulator = 0;

    private void doPhysicsStep(float deltaTime) {
        // fixed time step
        // max frame time to avoid spiral of death (on slow devices)
        float frameTime = Math.min(deltaTime, 0.25f);
        accumulator += frameTime;
        while (accumulator >= 1/240f) {
            world.step(1/240f, 60, 2);
            accumulator -= 1/240f;
        }
    }

    private void spawnEnemy() {
        Enemy enemy = new Enemy(Settings.GAME_WIDTH+100,100,3, Settings.BG_SPEED);
        enemies.add(enemy);
        stage.addActor(enemy);
        enemy.setZIndex(2);
        lastEnemyTime = TimeUtils.nanoTime();
        nextEnemyTime = MathUtils.random(Settings.ENEMY_MIN_COUNTER,Settings.ENEMY_MAX_COUNTER);
    }

    private void drawElements(){
        root.setDebug(true);
        debugRenderer.render(world, camera.combined);
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(player.getX(),player.getY(),player.getWidth(),player.getHeight());
        Rectangle attackCollisionRect = player.getAttackCollisionRect();
        for(int i = 0; i<enemies.size; i++){
            Enemy enemy = enemies.get(i);
            shapeRenderer.rect(enemy.getX(),enemy.getY(),enemy.getWidth(),enemy.getHeight());
        }
        if(attackCollisionRect != null){
            shapeRenderer.setColor(Color.YELLOW);
            shapeRenderer.rect(attackCollisionRect.x,attackCollisionRect.y,attackCollisionRect.width,attackCollisionRect.height);
        }

        shapeRenderer.end();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
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
        AssetManager.dispose();
    }

    public Player getPlayer() {
        return player;
    }

    public Stage getStage() {
        return stage;
    }
}
