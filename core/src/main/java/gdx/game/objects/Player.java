package gdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
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
        fixtureDef.density = 90;
        fixtureDef.friction = 0;
        fixtureDef.restitution = 0;
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
        stateTime += Gdx.graphics.getDeltaTime();
        batch.draw((TextureRegion)AssetManager.running.getKeyFrame(stateTime, true), position.x-(Settings.PLAYER_CANVAS_WIDTH*scale/2) + (width/2), position.y,
            Settings.PLAYER_CANVAS_WIDTH*scale, Settings.PLAYER_CANVAS_HEIGHT*scale);
        /*ShapeRenderer sr = new ShapeRenderer();
        sr.setProjectionMatrix(batch.getProjectionMatrix());
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setColor(Color.GREEN);
        sr.rect(position.x-(Settings.PLAYER_CANVAS_WIDTH*scale/2) + (width/2),position.y,Settings.PLAYER_CANVAS_WIDTH*scale,Settings.PLAYER_CANVAS_HEIGHT*scale);
        sr.end();*/
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
}

