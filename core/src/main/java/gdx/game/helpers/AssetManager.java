package gdx.game.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class AssetManager {

    public static Texture player, background;

    public static void load(){

        player = new Texture(Gdx.files.internal("player/120x80_PNGSheets/_Crouch.png"));
        background = new Texture(Gdx.files.internal("backgrounds/background_repeat.png"));
    }

    public static void dispose(){
        player.dispose();
        background.dispose();

    }
}
