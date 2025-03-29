package gdx.game.objects;

import com.badlogic.gdx.scenes.scene2d.Group;
import gdx.game.helpers.AssetManager;
import java.util.Random;

public class ForegroundScrollHandler extends Group {
    Background bg_near = new Background(0.0F, 0.0F, 2.0F * 1853.0F, 2.0F * 77.0F, -240.00002F, AssetManager.foreground);

    Background bg_near2 = new Background(this.bg_near.getTailX(), 0.0F, 2.0F * 1853.0F, 2.0F * 77.0F, -240.00002F, AssetManager.foreground);

    Random r;

    public ForegroundScrollHandler() {
        addActor(this.bg_near);
        addActor(this.bg_near2);
        this.bg_near.toFront();
        this.bg_near2.toFront();
    }

    private void bg_behaviour(Background paramBackground1, Background paramBackground2) {
        if (paramBackground1.isLeftOfScreen()) {
            paramBackground1.reset(paramBackground2.getTailX());
        } else if (paramBackground2.isLeftOfScreen()) {
            paramBackground2.reset(paramBackground1.getTailX());
        }
    }

    public void act(float paramFloat) {
        super.act(paramFloat);
        bg_behaviour(this.bg_near, this.bg_near2);
    }

    public Background getBg_near() {
        return this.bg_near;
    }

    public Background getBg_near2() {
        return this.bg_near2;
    }

    public void near_backgroundToFront() {
        this.bg_near.toFront();
        this.bg_near2.toFront();
    }

    public void setBgSpeed(float paramFloat) {
        this.bg_near.setVelocity(0.0F);
        this.bg_near2.setVelocity(0.0F);
    }

    public void setBg_near(Background paramBackground) {
        this.bg_near = paramBackground;
    }

    public void setBg_near2(Background paramBackground) {
        this.bg_near2 = paramBackground;
    }
}
