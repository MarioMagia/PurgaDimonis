package gdx.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;

import gdx.game.helpers.AssetManager;
import gdx.game.utils.Settings;

public class Player extends Actor {

    private Vector2 position;
    private int width, height;

    private float scale;
    private Rectangle collisionRect;

    public static final int PLAYER_STRAIGHT = 0;
    public static final int PLAYER_LEFT = 1;
    public static final int PLAYER_RIGHT = 2;

    private int direction;

    public Player(float x, float y, float scale) {
        this.width = Math.round(Settings.PLAYER_WIDTH*scale);
        this.height = Math.round(Settings.PLAYER_HEIGHT*scale);
        this.scale = scale;
        position = new Vector2(x, y);

        collisionRect = new Rectangle();
        setBounds(position.x, position.y, width, height);
        setTouchable(Touchable.enabled);

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        // Movem l'spacecraft depenent de la direcció controlant que no surti de la pantalla
        switch (direction) {
            case PLAYER_LEFT:
                if (this.position.x - Settings.PLAYER_VELOCITY * delta >= 0) {
                    this.position.x -= Settings.PLAYER_VELOCITY * delta;
                }
                break;
            case PLAYER_RIGHT:
                if (this.position.x + height + Settings.PLAYER_VELOCITY * delta <= Settings.GAME_WIDTH) {
                    this.position.x += Settings.PLAYER_VELOCITY * delta;
                }
                break;
            case PLAYER_STRAIGHT:
                break;
        }
        collisionRect.set(position.x, position.y, width, 64);
        setBounds(position.x, position.y, width, height);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(AssetManager.player, position.x-(Settings.PLAYER_CANVAS_WIDTH*scale/2) + (width/2), position.y,
            Settings.PLAYER_CANVAS_WIDTH*scale, Settings.PLAYER_CANVAS_HEIGHT*scale);
    }


    public Rectangle getCollisionRect() {
        return collisionRect;
    }

    public void goLeft() {
        direction = PLAYER_LEFT;
    }

    // Canviem la direcció de l'Spacecraft: Baixa
    public void goRight() {
        direction = PLAYER_RIGHT;
    }

    // Posem l'Spacecraft al seu estat original
    public void goStraight() {
        direction = PLAYER_STRAIGHT;
    }
}

