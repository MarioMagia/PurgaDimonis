package gdx.game.objects;

import com.badlogic.gdx.scenes.scene2d.Group;
import gdx.game.helpers.AssetManager;
import java.util.Random;

public class BackgroundScrollHandler extends Group {
    Background bg = new Background(0.0F, 0.0F, 2.0F * 1854.0F, 2.0F * 70.0F, -200.0F, AssetManager.grass_background);

    Background bg2 = new Background(this.bg.getTailX(), 0.0F, 2.0F * 1854.0F, 2.0F * 70.0F, -200.0F, AssetManager.grass_background);

    Background bg_base;

    Background bg_base2;

    Background bg_far_tree = new Background(0.0F, 2.0F * 35.0F, 2.0F * 1854.0F, 2.0F * 300.0F, -40.0F, AssetManager.far_background);

    Background bg_far_tree2 = new Background(this.bg.getTailX(), 2.0F * 35.0F, 2.0F * 1854.0F, 2.0F * 300.0F, -40.0F, AssetManager.far_background);

    Background bg_leaves;

    Background bg_leaves2;

    Background bg_mid_tree = new Background(0.0F, 2.0F * 63.0F, 2.0F * 1854.0F, 2.0F * 284.0F, -80.0F, AssetManager.medium_background);

    Background bg_mid_tree2 = new Background(this.bg.getTailX(), 2.0F * 63.0F, 2.0F * 1854.0F, 2.0F * 284.0F, -80.0F, AssetManager.medium_background);

    Background bg_near = new Background(0.0F, 2.0F * 63.0F, 2.0F * 1798.0F, 2.0F * 272.0F, -200.0F, AssetManager.near_background);

    Background bg_near2 = new Background(this.bg.getTailX(), 2.0F * 63.0F, 2.0F * 1798.0F, 2.0F * 272.0F, -200.0F, AssetManager.near_background);

    Background bg_tree = new Background(0.0F, 2.0F * 63.0F, 2.0F * 1854.0F, 2.0F * 271.0F, -160.0F, AssetManager.background);

    Background bg_tree2 = new Background(this.bg.getTailX(), 2.0F * 63.0F, 2.0F * 1854.0F, 2.0F * 271.0F, -160.0F, AssetManager.background);

    Random r;

    public BackgroundScrollHandler() {
        this.bg_far_tree = new Background(0.0F, 2.0F * 35.0F, 2.0F * 1854.0F, 2.0F * 300.0F, -40.0F, AssetManager.far_background);
        this.bg_far_tree2 = new Background(this.bg.getTailX(), 2.0F * 35.0F, 2.0F * 1854.0F, 2.0F * 300.0F, -40.0F, AssetManager.far_background);
        this.bg_base = new Background(0.0F, 0.0F, 2.0F * 1854.0F, 2.0F * 300.0F, 0.0F, AssetManager.base_background);
        this.bg_base2 = new Background(0.0F, 0.0F, 2.0F * 1854.0F, 2.0F * 300.0F, 0.0F, AssetManager.base_background);
        this.bg_leaves = new Background(0.0F, 2.0F * 315.0F, 2.0F * 1854.0F, 2.0F * 277.0F, -240.00002F, AssetManager.leaves_background);
        this.bg_leaves2 = new Background(this.bg.getTailX(), 2.0F * 315.0F, 2.0F * 1854.0F, 2.0F * 277.0F, -240.00002F, AssetManager.leaves_background);
        addActor(this.bg);
        addActor(this.bg2);
        addActor(this.bg_tree);
        addActor(this.bg_tree2);
        addActor(this.bg_near);
        addActor(this.bg_near2);
        addActor(this.bg_mid_tree);
        addActor(this.bg_mid_tree2);
        addActor(this.bg_far_tree);
        addActor(this.bg_far_tree2);
        addActor(this.bg_base);
        addActor(this.bg_base2);
        addActor(this.bg_leaves);
        addActor(this.bg_leaves2);
        this.bg.toBack();
        this.bg2.toBack();
        this.bg_leaves.toBack();
        this.bg_leaves2.toBack();
        this.bg_near.toBack();
        this.bg_near2.toBack();
        this.bg_tree.toBack();
        this.bg_tree2.toBack();
        this.bg_mid_tree.toBack();
        this.bg_mid_tree2.toBack();
        this.bg_far_tree.toBack();
        this.bg_far_tree2.toBack();
        this.bg_base.toBack();
        this.bg_base2.toBack();
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
        bg_behaviour(this.bg, this.bg2);
        bg_behaviour(this.bg_near, this.bg_near2);
        bg_behaviour(this.bg_leaves, this.bg_leaves2);
        bg_behaviour(this.bg_tree, this.bg_tree2);
        bg_behaviour(this.bg_mid_tree, this.bg_mid_tree2);
        bg_behaviour(this.bg_base, this.bg_base2);
    }

    public void backgroundToBack() {
        this.bg.toBack();
        this.bg2.toBack();
        this.bg_leaves.toBack();
        this.bg_leaves2.toBack();
        this.bg_near.toBack();
        this.bg_near2.toBack();
        this.bg_tree.toBack();
        this.bg_tree2.toBack();
        this.bg_mid_tree.toBack();
        this.bg_mid_tree2.toBack();
        this.bg_far_tree.toBack();
        this.bg_far_tree2.toBack();
        this.bg_base.toBack();
        this.bg_base2.toBack();
    }

    public void setBgSpeed(float paramFloat) {
        this.bg.setVelocity(0.0F);
        this.bg2.setVelocity(0.0F);
        this.bg_near.setVelocity(0.0F);
        this.bg_near2.setVelocity(0.0F);
        this.bg_leaves.setVelocity(0.0F);
        this.bg_leaves2.setVelocity(0.0F);
        this.bg_tree.setVelocity(0.0F);
        this.bg_tree2.setVelocity(0.0F);
        this.bg_mid_tree.setVelocity(0.0F);
        this.bg_mid_tree2.setVelocity(0.0F);
        this.bg_far_tree.setVelocity(0.0F);
        this.bg_far_tree2.setVelocity(0.0F);
        this.bg_base.setVelocity(0.0F);
        this.bg_base2.setVelocity(0.0F);
    }
}
