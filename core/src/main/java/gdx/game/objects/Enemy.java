package gdx.game.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class Enemy extends Actor {
    private boolean outOfScreen;

    private Vector2 position;

    private float scale;

    private float speed;

    private float stateTime;

    public Enemy(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
        this.position = new Vector2(paramFloat1, paramFloat2);
        this.scale = paramFloat3;
        this.speed = paramFloat4;
        this.stateTime = 0.0F;
        this.outOfScreen = false;
    }

    public void act(float paramFloat) {
        super.act(paramFloat);
    }

    public abstract boolean attack();

    public abstract boolean canAttack(Player paramPlayer);

    public abstract void chargeAttack();

    public abstract boolean collides(Player paramPlayer);

    public void draw(Batch paramBatch, float paramFloat) {
        super.draw(paramBatch, paramFloat);
    }

    public abstract Rectangle getAttackCollisionRect();

    public abstract Rectangle getCollisionRect();

    public Vector2 getPosition() {
        return this.position;
    }

    public float getScale() {
        return this.scale;
    }

    public float getSpeed() {
        return this.speed;
    }

    public float getStateTime() {
        return this.stateTime;
    }

    public abstract void hurt();

    public boolean isOutOfScreen() {
        return this.outOfScreen;
    }

    public void setOutOfScreen(boolean paramBoolean) {
        this.outOfScreen = paramBoolean;
    }

    public void setPosition(Vector2 paramVector2) {
        this.position = paramVector2;
    }

    public void setScale(float paramFloat) {
        this.scale = paramFloat;
    }

    public void setSpeed(float paramFloat) {
        this.speed = paramFloat;
    }

    public void setStateTime(float paramFloat) {
        this.stateTime = paramFloat;
    }
}
