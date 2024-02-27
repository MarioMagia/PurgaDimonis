package gdx.game.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetManager {

    public static Texture background, run_sheet;
    public static  TextureRegion[] player;

    public static Animation running;

    public static void load(){
        player = new TextureRegion[10];
        run_sheet = new Texture(Gdx.files.internal("player/120x80_PNGSheets/_Run.png"));
        run_sheet.setFilter(Texture.TextureFilter.Nearest,Texture.TextureFilter.Nearest);
        for(int i = 0; i<10; i++){
            player[i] = new TextureRegion(run_sheet,120*i,0,120,80);
        }
        running = new Animation<TextureRegion>(0.07f,player);
        running.setPlayMode(Animation.PlayMode.LOOP);
        background = new Texture(Gdx.files.internal("backgrounds/background_repeat.png"));
    }

    public static void dispose(){
        run_sheet.dispose();
        background.dispose();

    }
}
