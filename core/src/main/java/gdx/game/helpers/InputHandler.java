package gdx.game.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;

import gdx.game.objects.Player;
import gdx.game.screens.GameScreen;
import gdx.game.utils.Settings;

public class InputHandler implements InputProcessor {

    private Player player;
    private GameScreen screen;
    private Body playerBody;
    private Vector2 playerposition;
    private Stage stage;
    int previousX = 0;

    public InputHandler(GameScreen screen) {

        // Obtenim tots els elements necessaris
        this.screen = screen;
        player = screen.getPlayer();
        stage = screen.getStage();
        this.playerBody = player.getBody();
        this.playerposition = playerBody.getPosition();
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

        Gdx.app.log("button", button+"");
        if(playerBody.getLinearVelocity().y<1 && playerBody.getLinearVelocity().y>-1){
            playerBody.applyLinearImpulse(new Vector2(0,10000000), new Vector2(playerposition.x+40,playerposition.y),false);
        }

        return false;
    }
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return true;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
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
