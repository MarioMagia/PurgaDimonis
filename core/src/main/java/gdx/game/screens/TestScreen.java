package gdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class TestScreen implements Screen {
    private TextureAtlas atlas = new TextureAtlas("ui/uiskin.atlas");

    private SpriteBatch batch = new SpriteBatch();

    private OrthographicCamera camera = new OrthographicCamera();

    protected Skin skin = new Skin(Gdx.files.internal("ui/uiskin.json"), this.atlas);

    protected Stage stage;

    private Viewport viewport;

    public TestScreen() {
        FitViewport fitViewport = new FitViewport(480.0F, 480.0F, (Camera)this.camera);
        this.viewport = (Viewport)fitViewport;
        fitViewport.apply();
        this.camera.position.set(this.camera.viewportWidth / 2.0F, this.camera.viewportHeight / 2.0F, 0.0F);
        this.camera.update();
        this.stage = new Stage(this.viewport, (Batch)this.batch);
    }

    public void dispose() {
        this.skin.dispose();
        this.atlas.dispose();
    }

    public void hide() {}

    public void pause() {}

    public void render(float paramFloat) {
        Gdx.gl.glClearColor(0.1F, 0.12F, 0.16F, 1.0F);
        Gdx.gl.glClear(16384);
        this.stage.act();
        this.stage.draw();
    }

    public void resize(int paramInt1, int paramInt2) {
        this.viewport.update(paramInt1, paramInt2);
        this.camera.position.set(this.camera.viewportWidth / 2.0F, this.camera.viewportHeight / 2.0F, 0.0F);
        this.camera.update();
    }

    public void resume() {}

    public void show() {
        Gdx.input.setInputProcessor((InputProcessor)this.stage);
        Table table = new Table();
        table.setFillParent(true);
        table.top();
        TextButton textButton3 = new TextButton("Play", this.skin);
        TextButton textButton1 = new TextButton("Options", this.skin);
        TextButton textButton2 = new TextButton("Exit", this.skin);
        textButton3.addListener((EventListener)new ClickListener() {
            @Override

            public void clicked(InputEvent param1InputEvent, float param1Float1, float param1Float2) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new FirstScreen());
            }
        });
        textButton2.addListener((EventListener)new ClickListener() {
            @Override

            public void clicked(InputEvent param1InputEvent, float param1Float1, float param1Float2) {
                Gdx.app.exit();
            }
        });
        table.add((Actor)textButton3);
        table.row();
        table.add((Actor)textButton1);
        table.row();
        table.add((Actor)textButton2);
        this.stage.addActor((Actor)table);
    }
}
