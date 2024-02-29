package gdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;

import gdx.game.helpers.AssetManager;
import gdx.game.utils.Settings;

public class Enemy extends Actor {
    private Vector2 position;
    private int width, height;
    private float speed;
    private float scale;
    private Rectangle collisionRect;
    private float stateTime;

    public Enemy(float x, float y, float scale, float speed) {
        this.position = new Vector2(x,y);
        this.width = Math.round(Settings.SKELETON_WIDTH * scale);
        this.height = Math.round(Settings.SKELETON_HEIGHT * scale);
        this.scale = scale;
        this.speed = speed;
        collisionRect = new Rectangle();
        setBounds(position.x, position.y, width, height);
        setTouchable(Touchable.enabled);
        stateTime = 0f;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        stateTime += Gdx.graphics.getDeltaTime();
        batch.draw(((TextureRegion)AssetManager.skeletonIdle.getKeyFrame(stateTime,true)),
            position.x - (Settings.MONSTER_CANVAS_SIZE * scale / 2) + (width / 2),
            position.y - (Settings.MONSTER_CANVAS_SIZE * scale / 2) + (height / 2),Settings.MONSTER_CANVAS_SIZE*scale, Settings.MONSTER_CANVAS_SIZE*scale);
    }

    @Override
    public void act(float delta){
        position.x += speed*delta;
        setBounds(position.x, position.y, width, height);
        if(position.x + width < 0){

        }
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Rectangle getCollisionRect() {
        return collisionRect;
    }

}
