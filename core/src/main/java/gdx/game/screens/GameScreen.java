package gdx.game.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import gdx.game.objects.Background;
import gdx.game.objects.Player;
import gdx.game.objects.ScrollHandler;
import gdx.game.utils.Settings;

public class GameScreen implements Screen {
    private Stage stage;
    private Player player;
    private ScrollHandler sh;

    private ShapeRenderer shapeRenderer;
    private Batch batch;
    public GameScreen(){
        OrthographicCamera camera = new OrthographicCamera(Settings.GAME_WIDTH, Settings.GAME_HEIGHT);
        camera.setToOrtho(false, Settings.GAME_WIDTH,Settings.GAME_HEIGHT);
        FitViewport fitViewport = new FitViewport(Settings.GAME_WIDTH,Settings.GAME_HEIGHT);
        shapeRenderer = new ShapeRenderer();
        stage = new Stage(fitViewport);
        batch = stage.getBatch();
        sh = new ScrollHandler();
        stage.addActor(sh);
        player = new Player( Settings.PLAYER_STARTX,Settings.PLAYER_STARTY,4);
        stage.addActor(player);

    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.draw();
        stage.act(delta);
        drawElements();
    }

    private void drawElements(){
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
