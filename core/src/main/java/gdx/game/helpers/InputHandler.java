package gdx.game.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Stage;

import gdx.game.objects.Player;
import gdx.game.screens.GameScreen;

public class InputHandler implements InputProcessor {

    private Player player;
    private GameScreen screen;

    private Stage stage;
    int previousX = 0;

    public InputHandler(GameScreen screen) {

        // Obtenim tots els elements necessaris
        this.screen = screen;
        player = screen.getPlayer();
        stage = screen.getStage();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        player.goStraight();
        return true;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (Math.abs(previousX - screenX) > 2)
            if (previousX < screenX) {
                Gdx.app.log("Right", player.getName());
                player.goRight();
            } else {
                Gdx.app.log("Left", player.getName());
                player.goLeft();
            }
        previousX = screenX;
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
