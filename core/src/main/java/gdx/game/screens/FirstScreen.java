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
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import gdx.game.ScreenManager;

public class FirstScreen implements Screen {
    private Skin skin;
    private Stage stage;

    public void show() {
        this.stage = new Stage(new FitViewport(1280.0f, 720.0f));
        this.skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        Table root = new Table();
        root.setFillParent(true);
        this.stage.addActor(root);
        final Table window = new Table(this.skin);
        window.defaults().pad(10.0f);
        root.add(window);
        window.setBackground("window-round");
        Label label = new Label("Purga al dimoni", this.skin, "title");
        label.setAlignment(1);
        label.setColor(Color.RED);
        window.add(label).pad(40.0f).row();
        TextButton button = new TextButton("NOVA PARTIDA", this.skin);
        button.addListener(new ChangeListener() {
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                RunnableAction run = new RunnableAction();
                run.setRunnable(new Runnable() {
                    public void run() {
                        ScreenManager.setScreen(new GameScreen());
                    }
                });
                window.addAction(Actions.sequence(Actions.alpha(1.0f), Actions.fadeOut(1.0f), run));
            }
        });
        window.add(button).row();
        TextButton optionsButton = new TextButton("OPCIONS", this.skin);
        optionsButton.addListener(new ChangeListener() {
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                ScreenManager.setScreen(new PreferencesScreen());
            }
        });
        window.add(optionsButton).row();
        TextButton exitButton = new TextButton("SORTIR", this.skin);
        exitButton.addListener(new ChangeListener() {
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
        window.add(exitButton).row();
        window.pack();
        window.setPosition((float) MathUtils.roundPositive((this.stage.getWidth() / 2.0f) - (window.getWidth() / 2.0f)), (float) MathUtils.roundPositive((this.stage.getHeight() / 2.0f) - (window.getHeight() / 2.0f)));
        window.addAction(Actions.sequence(Actions.alpha(0.0f), Actions.fadeIn(1.0f)));
        Gdx.input.setInputProcessor(this.stage);
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.stage.act(Gdx.graphics.getDeltaTime());
        this.stage.draw();
    }

    public void resize(int width, int height) {
        this.stage.getViewport().update(width, height);
    }

    public void pause() {
    }

    public void resume() {
    }

    public void hide() {
    }

    public void dispose() {
        this.stage.dispose();
        this.skin.dispose();
    }
}
