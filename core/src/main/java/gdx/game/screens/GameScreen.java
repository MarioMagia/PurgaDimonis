package gdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import gdx.game.helpers.InputHandler;
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
    public GameScreen(){
        Box2D.init();
        camera = new OrthographicCamera(Settings.GAME_WIDTH, Settings.GAME_HEIGHT);
        camera.setToOrtho(false, Settings.GAME_WIDTH,Settings.GAME_HEIGHT);
        FitViewport fitViewport = new FitViewport(Settings.GAME_WIDTH,Settings.GAME_HEIGHT);
        shapeRenderer = new ShapeRenderer();
        stage = new Stage(fitViewport);
        batch = stage.getBatch();
        sh = new ScrollHandler();
        stage.addActor(sh);
        player = new Player( Settings.PLAYER_STARTX,Settings.PLAYER_STARTY+200,4);
        stage.addActor(player);
        createWorld();
        inputHandler = new InputHandler(this);
        Gdx.input.setInputProcessor(inputHandler);
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

    private void drawElements(){
        debugRenderer.render(world, camera.combined);
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(player.getX(),player.getY(),player.getWidth(),player.getHeight());
        shapeRenderer.end();
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

    }

    @Override
    public void dispose() {

    }

    public Player getPlayer() {
        return player;
    }

    public Stage getStage() {
        return stage;
    }
}
