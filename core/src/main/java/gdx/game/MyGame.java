package gdx.game;

import com.badlogic.gdx.Game;
import gdx.game.helpers.AssetManager;
import gdx.game.utils.GamePreferences;

public class MyGame extends Game {
    private GamePreferences preferences;

    ScreenManager screenManager;

    public void create() {
        this.preferences = new GamePreferences();
        AssetManager.load(this);
        ScreenManager screenManager = new ScreenManager();
        this.screenManager = screenManager;
        screenManager.initialize(this);
    }

    public void dispose() {
        super.dispose();
        AssetManager.dispose();
    }

    public GamePreferences getPreferences() {
        return this.preferences;
    }
}
