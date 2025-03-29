package gdx.game.helpers;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Stage;
import gdx.game.objects.Player;
import gdx.game.screens.GameScreen;

public class InputHandler implements InputProcessor {
    private Player player;

    private Body playerBody;

    private Vector2 playerposition;

    int previousX = 0;

    private GameScreen screen;

    private Stage stage;

    public InputHandler(GameScreen paramGameScreen) {
        this.screen = paramGameScreen;
        this.player = paramGameScreen.getPlayer();
        this.stage = paramGameScreen.getStage();
        Body body = this.player.getBody();
        this.playerBody = body;
        this.playerposition = body.getPosition();
    }

    public boolean keyDown(int paramInt) {
        return false;
    }

    public boolean keyTyped(char paramChar) {
        return false;
    }

    public boolean keyUp(int paramInt) {
        return false;
    }

    public boolean mouseMoved(int paramInt1, int paramInt2) {
        return false;
    }

    public boolean scrolled(float paramFloat1, float paramFloat2) {
        return false;
    }

    public boolean touchCancelled(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        return false;
    }

    public boolean touchDown(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        if (!this.screen.isGameover() && !this.player.isJumping()) {
            this.playerBody.applyLinearImpulse(new Vector2(0.0F, 1.0E7F), new Vector2(this.playerposition.x + 40.0F, this.playerposition.y), false);
            AssetManager.jumpingSound.play(AssetManager.soundVolume);
            this.player.setJumping(true);
            this.player.setAttackCollisionRect(null);
        }
        return false;
    }

    public boolean touchDragged(int paramInt1, int paramInt2, int paramInt3) {
        return false;
    }

    public boolean touchUp(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        return true;
    }
}
