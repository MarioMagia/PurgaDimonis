package gdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

import gdx.game.screens.FirstScreen;

public class ScreenManager {

    private static Game game;

    public void initialize(Game game){
        this.game = game;
        game.setScreen(new FirstScreen());
    }

    public static void setScreen(Screen screen){
        game.setScreen(screen);
    }
}
