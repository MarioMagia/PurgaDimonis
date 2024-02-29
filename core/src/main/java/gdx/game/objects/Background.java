package gdx.game.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import gdx.game.helpers.AssetManager;

public class Background extends Actor {
    private boolean leftOfScreen;
    private Vector2 position;
    private float velocity;
    private float width;
    private float height;

    public Background(float x, float y, float width, float height, float speed){
        position = new Vector2(x,y);
        this.width = width;
        this.velocity = speed;
        this.height = height;
        leftOfScreen = false;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.disableBlending();
        batch.draw(AssetManager.background, position.x, position.y, width, height);
        batch.enableBlending();
    }

    public boolean isLeftOfScreen() {
        return leftOfScreen;
    }

    public float getTailX() {
        return position.x + width;
    }

    public void reset(float tailX) {
        position.x = tailX;
        leftOfScreen = false;
    }

    public void act(float delta){
        position.x += velocity*delta;
        if(position.x + width < 0){
            leftOfScreen = true;
        }
    }

    public float getVelocity() {
        return velocity;
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }
}
