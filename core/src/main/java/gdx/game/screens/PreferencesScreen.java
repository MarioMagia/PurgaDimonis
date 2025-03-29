package gdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import gdx.game.MyGame;
import gdx.game.ScreenManager;
import gdx.game.helpers.AssetManager;

public class PreferencesScreen implements Screen {
    private Label musicOnOffLabel;
    /* access modifiers changed from: private */
    public MyGame parent = ScreenManager.getGame();
    private Label soundOnOffLabel;
    private Stage stage = new Stage(new ScreenViewport());
    private Label titleLabel;
    private Label volumeMusicLabel;
    private Label volumeSoundLabel;

    public void show() {
        this.stage.clear();
        Gdx.input.setInputProcessor(this.stage);
        Skin skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        Table root = new Table(skin);
        Table table = new Table(skin);
        root.setFillParent(true);
        root.add(table);
        table.setBackground("window-round");
        table.pad(50.0f);
        this.stage.addActor(root);
        Skin skin2 = skin;
        final Slider soundMusicSlider = new Slider(0.0f, 1.0f, 0.1f, false, skin2);
        soundMusicSlider.setValue(this.parent.getPreferences().getMusicVolume());
        soundMusicSlider.addListener(new EventListener() {
            public boolean handle(Event event) {
                PreferencesScreen.this.parent.getPreferences().setMusicVolume(soundMusicSlider.getValue());
                return false;
            }
        });
        final Slider soundMusicSlider2 = new Slider(0.0f, 1.0f, 0.1f, false, skin2);
        soundMusicSlider2.setValue(this.parent.getPreferences().getSoundVolume());
        soundMusicSlider2.addListener(new EventListener() {
            public boolean handle(Event event) {
                PreferencesScreen.this.parent.getPreferences().setSoundVolume(soundMusicSlider2.getValue());
                return false;
            }
        });
        final CheckBox musicCheckbox = new CheckBox((String) null, skin);
        musicCheckbox.setChecked(this.parent.getPreferences().isMusicEnabled());
        musicCheckbox.addListener(new EventListener() {
            public boolean handle(Event event) {
                PreferencesScreen.this.parent.getPreferences().setMusicEnabled(musicCheckbox.isChecked());
                return false;
            }
        });
        final CheckBox soundEffectsCheckbox = new CheckBox((String) null, skin);
        soundEffectsCheckbox.setChecked(this.parent.getPreferences().isSoundEffectsEnabled());
        soundEffectsCheckbox.addListener(new EventListener() {
            public boolean handle(Event event) {
                PreferencesScreen.this.parent.getPreferences().setSoundEffectsEnabled(soundEffectsCheckbox.isChecked());
                return false;
            }
        });
        TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(new ChangeListener() {
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                AssetManager.load(PreferencesScreen.this.parent);
                ScreenManager.setScreen(new FirstScreen());
            }
        });
        this.titleLabel = new Label((CharSequence) "Preferences", skin);
        this.volumeMusicLabel = new Label((CharSequence) "Music Volume", skin);
        this.volumeSoundLabel = new Label((CharSequence) "Sound Volume", skin);
        this.musicOnOffLabel = new Label((CharSequence) "Music", skin);
        this.soundOnOffLabel = new Label((CharSequence) "Sound Effect", skin);
        table.add(this.titleLabel).colspan(2);
        table.row().pad(10.0f, 0.0f, 0.0f, 10.0f);
        table.add(this.volumeMusicLabel).left();
        table.add(soundMusicSlider).width(300.0f);
        table.row().pad(10.0f, 0.0f, 0.0f, 10.0f);
        table.add(this.musicOnOffLabel).left();
        table.add(musicCheckbox);
        table.row().pad(10.0f, 0.0f, 0.0f, 10.0f);
        table.add(this.volumeSoundLabel).left();
        table.add(soundMusicSlider2).width(300.0f);
        table.row().pad(10.0f, 0.0f, 0.0f, 10.0f);
        table.add(this.soundOnOffLabel).left();
        table.add(soundEffectsCheckbox);
        table.row().pad(10.0f, 0.0f, 0.0f, 10.0f);
        table.add(backButton).colspan(2);
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.stage.act(Math.min(Gdx.graphics.getDeltaTime(), 0.033333335f));
        this.stage.draw();
    }

    public void resize(int width, int height) {
        this.stage.getViewport().update(width, height, true);
    }

    public void pause() {
    }

    public void resume() {
    }

    public void hide() {
    }

    public void dispose() {
    }
}
