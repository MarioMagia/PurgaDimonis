package gdx.game;

import com.badlogic.gdx.Game;

import gdx.game.helpers.AssetManager;
import gdx.game.screens.FirstScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class MyGame extends Game {
    ScreenManager screenManager;
    @Override
    public void create() {
        AssetManager.load();
        screenManager = new ScreenManager();
        screenManager.initialize(this);
    }
}
