package gdx.game.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetManager {

    public static Texture background;

    public static Animation running, jump, jumpFallInBetween, fall, attack, attack2;

    public static void load(){

        //running
        running = makeAnimation(new Texture(Gdx.files.internal("player/120x80_PNGSheets/_Run.png")),10, 0.07f);
        running.setPlayMode(Animation.PlayMode.LOOP);

        //jump
        jump = makeAnimation(new Texture(Gdx.files.internal("player/120x80_PNGSheets/_Jump.png")),3, 0.07f);
        jump.setPlayMode(Animation.PlayMode.NORMAL);

        //jumpFallInBetween
        jumpFallInBetween = makeAnimation(new Texture(Gdx.files.internal("player/120x80_PNGSheets/_JumpFallInbetween.png")),2, 0.07f);
        jumpFallInBetween.setPlayMode(Animation.PlayMode.NORMAL);

        //fall
        fall = makeAnimation(new Texture(Gdx.files.internal("player/120x80_PNGSheets/_Fall.png")),3, 0.07f);
        fall.setPlayMode(Animation.PlayMode.NORMAL);

        //attack
        attack = makeAnimation(new Texture(Gdx.files.internal("player/120x80_PNGSheets/_Attack.png")),4,0.07f);
        attack.setPlayMode(Animation.PlayMode.NORMAL);

        //attack2
        attack2 = makeAnimation(new Texture(Gdx.files.internal("player/120x80_PNGSheets/_Attack2.png")),6,0.07f);
        attack2.setPlayMode(Animation.PlayMode.NORMAL);

        background = new Texture(Gdx.files.internal("backgrounds/background_repeat.png"));
    }

    private static Animation<TextureRegion> makeAnimation(Texture sheet, int frames, float duration){
        TextureRegion[] textureRegions = new TextureRegion[frames];
        sheet.setFilter(Texture.TextureFilter.Nearest,Texture.TextureFilter.Nearest);
        for(int i = 0; i<frames; i++){
            textureRegions[i] = new TextureRegion(sheet, 120*i,0,120,80);
        }
        return new Animation<TextureRegion>(duration,textureRegions);
    }

    public static void dispose(){
        background.dispose();

        // Dispose textures used in animations
        disposeAnimationTextures(running);
        disposeAnimationTextures(jump);
        disposeAnimationTextures(jumpFallInBetween);
        disposeAnimationTextures(fall);
        disposeAnimationTextures(attack);
        disposeAnimationTextures(attack2);
    }

    private static void disposeAnimationTextures(Animation<TextureRegion> animation) {
        if (animation != null) {
            TextureRegion[] keyFrames = animation.getKeyFrames();
            for (TextureRegion textureRegion : keyFrames) {
                if (textureRegion.getTexture() != null) {
                    textureRegion.getTexture().dispose();
                }
            }
        }
    }
}
