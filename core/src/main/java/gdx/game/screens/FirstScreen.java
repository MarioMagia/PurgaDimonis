package gdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

import gdx.game.ScreenManager;
import gdx.game.utils.Settings;

/** First screen of the application. Displayed after the application is created. */
public class FirstScreen implements Screen {

    private Stage stage;
    private Skin skin;

    @Override
    public void show() {
        stage = new Stage(new FitViewport(Settings.GAME_WIDTH, Settings.GAME_HEIGHT));
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);

        Table window = new Table(skin);
        window.defaults().pad(10);
        root.add(window);
        //window.setDebug(true);
        window.setBackground("window-round");
        Label label = new Label("Juegarraco", skin, "title");
        label.setAlignment(Align.center);
        label.setColor(Color.RED);
        window.add(label).pad(40).row();
        final TextButton button = new TextButton("NOVA PARTIDA", skin);
        button.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {

                RunnableAction run = new RunnableAction();
                run.setRunnable(new Runnable() {
                    @Override
                    public void run() {
                        ScreenManager.setScreen(new GameScreen());
                    }
                });
                window.addAction(Actions.sequence(Actions.alpha(1f), Actions.fadeOut(1f),run));
            }
        });
        window.add(button).row();
        final TextButton optionsButton = new TextButton("OPCIONS", skin);
        optionsButton.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                optionsButton.setText("Clicked.");
            }
        });
        window.add(optionsButton).row();
        final TextButton exitButton = new TextButton("SORTIR", skin);
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
        window.add(exitButton).row();
        window.pack();
        // We round the window position to avoid awkward half-pixel artifacts.
        // Casting using (int) would also work.
        window.setPosition(MathUtils.roundPositive(stage.getWidth() / 2f - window.getWidth() / 2f),
            MathUtils.roundPositive(stage.getHeight() / 2f - window.getHeight() / 2f));
        window.addAction(Actions.sequence(Actions.alpha(0f), Actions.fadeIn(1f)));

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        // Draw your screen here. "delta" is the time since last render in seconds.
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // Resize your screen here. The parameters represent the new window size.
        stage.getViewport().update(width, height);
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

    @Override
    public void dispose() {
        // Destroy screen's assets here.
        stage.dispose();
        skin.dispose();
    }
}
