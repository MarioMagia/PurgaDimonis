package gdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;

import gdx.game.helpers.AssetManager;
import gdx.game.utils.Settings;

public class Player extends Actor {

    private float stateTime;
    private Vector2 position;
    private int width, height;

    private float scale;
    private Rectangle collisionRect;

    public static final int PLAYER_STRAIGHT = 0;
    public static final int PLAYER_LEFT = 1;
    public static final int PLAYER_RIGHT = 2;

    private int direction;

    private BodyDef bodyDef;
    private FixtureDef fixtureDef;
    private Body body;
    private boolean isJumping;

    public Player(float x, float y, float scale) {
        this.width = Math.round(Settings.PLAYER_WIDTH*scale);
        this.height = Math.round(Settings.PLAYER_HEIGHT*scale);
        this.scale = scale;
        position = new Vector2(x, y);

        collisionRect = new Rectangle();
        setBounds(position.x, position.y, width, height);
        setTouchable(Touchable.enabled);
        stateTime = 0f;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/2,height/2);
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(position.x+(width/2),position.y+(height/2));
        fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1;
        fixtureDef.friction = 0;
        fixtureDef.restitution = 0;
        isJumping = false;
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        collisionRect.set(position.x, position.y, width, 64);
        setBounds(position.x, position.y, width, height);
        float velocityY = body.getLinearVelocity().y;

        // Determine player state
        if (velocityY > 1) {
            playerState = PlayerState.JUMPING;
        } else if (velocityY < -1) {
            if (playerState == PlayerState.JUMP_FALL_TRANSITION) {
                // If still in transition state, wait for a delay before transitioning to falling

                timeSinceTransition += Gdx.graphics.getDeltaTime();
                if (timeSinceTransition >= jumpFallTransitionDelay) {
                    Gdx.app.log("fallin", "fallin");
                    playerState = PlayerState.FALLING;
                    timeSinceTransition = 0; // Reset the timeSinceTransition
                }
            } else if(playerState == PlayerState.JUMPING) {
                playerState = PlayerState.JUMP_FALL_TRANSITION;
                stateTime = 0;
                timeSinceTransition = 0; // Reset the timeSinceTransition
            }
        } else {
            playerState = PlayerState.RUNNING;
        }
    }

    // Define player states
    enum PlayerState {
        RUNNING,
        JUMPING,
        FALLING,
        JUMP_FALL_TRANSITION
    }

    // Initialize player state
    PlayerState playerState = PlayerState.RUNNING;
    float jumpFallTransitionDelay = 0.07f; // Adjust as needed
    float timeSinceTransition = 0;

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        stateTime += Gdx.graphics.getDeltaTime();
        Gdx.app.log("vel", body.getLinearVelocity()+"");

        // Draw based on player state
        switch (playerState) {
            case RUNNING:
                batch.draw((TextureRegion) AssetManager.running.getKeyFrame(stateTime, true), position.x - (Settings.PLAYER_CANVAS_WIDTH * scale / 2) + (width / 2), position.y,
                    Settings.PLAYER_CANVAS_WIDTH * scale, Settings.PLAYER_CANVAS_HEIGHT * scale);
                break;
            case JUMPING:
                batch.draw((TextureRegion) AssetManager.jump.getKeyFrame(stateTime, true), position.x - (Settings.PLAYER_CANVAS_WIDTH * scale / 2) + (width / 2), position.y,
                    Settings.PLAYER_CANVAS_WIDTH * scale, Settings.PLAYER_CANVAS_HEIGHT * scale);
                break;
            case FALLING:
                batch.draw((TextureRegion) AssetManager.fall.getKeyFrame(stateTime, true), position.x - (Settings.PLAYER_CANVAS_WIDTH * scale / 2) + (width / 2), position.y,
                    Settings.PLAYER_CANVAS_WIDTH * scale, Settings.PLAYER_CANVAS_HEIGHT * scale);
                break;
            case JUMP_FALL_TRANSITION:
                batch.draw((TextureRegion) AssetManager.jumpFallInBetween.getKeyFrame(stateTime, true), position.x - (Settings.PLAYER_CANVAS_WIDTH * scale / 2) + (width / 2), position.y,
                    Settings.PLAYER_CANVAS_WIDTH * scale, Settings.PLAYER_CANVAS_HEIGHT * scale);
                break;
        }
    }

    public Rectangle getCollisionRect() {
        return collisionRect;
    }


    public BodyDef getBodyDef() {
        return bodyDef;
    }

    public void setBodyDef(BodyDef bodyDef) {
        this.bodyDef = bodyDef;
    }

    public FixtureDef getFixtureDef() {
        return fixtureDef;
    }

    public void setFixtureDef(FixtureDef fixtureDef) {
        this.fixtureDef = fixtureDef;
    }

    public void setPosition(Vector2 position,boolean center) {
        this.position = position;
        if(center){
            this.position.x = position.x - (width/2);
            this.position.y = position.y - (height/2);
        }
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }
}

