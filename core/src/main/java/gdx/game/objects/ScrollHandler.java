package gdx.game.objects;

import com.badlogic.gdx.scenes.scene2d.Group;

import java.util.Random;

import gdx.game.utils.Settings;

public class ScrollHandler extends Group {

    Background bg, bg_back;
    Random r;

    @Override
    public void act(float delta) {
        super.act(delta);
        if(bg.isLeftOfScreen()){
            bg.reset(bg_back.getTailX());
        } else if (bg_back.isLeftOfScreen()) {
            bg_back.reset(bg.getTailX());

        }
    }

    public ScrollHandler(){
        bg = new Background(0,0, Settings.GAME_WIDTH * 2,Settings.GAME_HEIGHT*2, Settings.BG_SPEED);
        bg_back = new Background(bg.getTailX(),0,Settings.GAME_WIDTH * 2,Settings.GAME_HEIGHT*2, Settings.BG_SPEED);
        addActor(bg);
        addActor(bg_back);
    }

    public Background getBg() {
        return bg;
    }

    public void setBg(Background bg) {
        this.bg = bg;
    }

    public Background getBg_back() {
        return bg_back;
    }

    public void setBg_back(Background bg_back) {
        this.bg_back = bg_back;
    }
}
