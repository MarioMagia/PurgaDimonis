package gdx.game;

import com.badlogic.gdx.Screen;
import gdx.game.screens.FirstScreen;

public class ScreenManager {
    private static MyGame game;

    public static MyGame getGame() {
        return game;
    }

    public static void setScreen(Screen paramScreen) {
        game.setScreen(paramScreen);
    }

    public void initialize(MyGame paramMyGame) {
        game = paramMyGame;
        paramMyGame.setScreen((Screen)new FirstScreen());
    }
}
