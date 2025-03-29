package gdx.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Background extends Actor {
    private float height;

    private boolean leftOfScreen;

    private Vector2 position;

    private final Texture texture;

    private float velocity;

    private float width;

    public Background(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, Texture paramTexture) {
        this.position = new Vector2(paramFloat1, paramFloat2);
        this.width = paramFloat3;
        this.velocity = paramFloat5;
        this.height = paramFloat4;
        this.leftOfScreen = false;
        this.texture = paramTexture;
    }

    public void act(float paramFloat) {
        Vector2 vector2 = this.position;
        vector2.x += this.velocity * paramFloat;
        if (this.position.x + this.width < 0.0F)
            this.leftOfScreen = true;
    }

    public void draw(Batch paramBatch, float paramFloat) {
        super.draw(paramBatch, paramFloat);
        paramBatch.draw(this.texture, this.position.x, this.position.y, this.width, this.height);
    }

    public float getTailX() {
        return this.position.x + this.width;
    }

    public float getVelocity() {
        return this.velocity;
    }

    public boolean isLeftOfScreen() {
        return this.leftOfScreen;
    }

    public void reset(float paramFloat) {
        this.position.x = paramFloat;
        this.leftOfScreen = false;
    }

    public void setVelocity(float paramFloat) {
        this.velocity = paramFloat;
    }
}
