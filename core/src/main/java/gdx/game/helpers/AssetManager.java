package gdx.game.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import gdx.game.utils.Settings;

public class AssetManager {

    public static Texture background, skeletonDead, hit;

    public static Animation running, jump, jumpFallInBetween, fall, attack, attack2, skeletonIdle,
        skeletonDying, skeletonAttack;

    public static void load(){

        //running
        running = makeAnimation(new Texture(Gdx.files.internal("player/120x80_PNGSheets/_Run.png")),
            10, Settings.PLAYER_CANVAS_WIDTH,Settings.PLAYER_CANVAS_HEIGHT,0.07f,false);
        running.setPlayMode(Animation.PlayMode.LOOP);

        //jump
        jump = makeAnimation(new Texture(Gdx.files.internal("player/120x80_PNGSheets/_Jump.png")),
            3, Settings.PLAYER_CANVAS_WIDTH,Settings.PLAYER_CANVAS_HEIGHT, 0.07f,false);
        jump.setPlayMode(Animation.PlayMode.NORMAL);

        //jumpFallInBetween
        jumpFallInBetween = makeAnimation(new Texture(Gdx.files.internal("player/120x80_PNGSheets/_JumpFallInbetween.png")),
            2,Settings.PLAYER_CANVAS_WIDTH,Settings.PLAYER_CANVAS_HEIGHT,  0.07f,false);
        jumpFallInBetween.setPlayMode(Animation.PlayMode.NORMAL);

        //fall
        fall = makeAnimation(new Texture(Gdx.files.internal("player/120x80_PNGSheets/_Fall.png")),
            3, Settings.PLAYER_CANVAS_WIDTH,Settings.PLAYER_CANVAS_HEIGHT, 0.07f,false);
        fall.setPlayMode(Animation.PlayMode.NORMAL);

        //attack
        attack = makeAnimation(new Texture(Gdx.files.internal("player/120x80_PNGSheets/_Attack.png")),
            4, Settings.PLAYER_CANVAS_WIDTH,Settings.PLAYER_CANVAS_HEIGHT, 0.07f,false);
        attack.setPlayMode(Animation.PlayMode.NORMAL);

        //attack2
        attack2 = makeAnimation(new Texture(Gdx.files.internal("player/120x80_PNGSheets/_Attack2.png")),
            6, Settings.PLAYER_CANVAS_WIDTH,Settings.PLAYER_CANVAS_HEIGHT, 0.07f,false);
        attack2.setPlayMode(Animation.PlayMode.NORMAL);

        hit = new Texture(Gdx.files.internal("player/120x80_PNGSheets/_Hit.png"));

        skeletonIdle = makeAnimation(new Texture(Gdx.files.internal("Monsters_Creatures_Fantasy/Skeleton/Idle.png")),4,
            150,150, 0.10f,true);
        skeletonIdle.setPlayMode(Animation.PlayMode.LOOP);

        skeletonDying = makeAnimation(new Texture(Gdx.files.internal("Monsters_Creatures_Fantasy/Skeleton/Death.png")), 4,
            Settings.MONSTER_CANVAS_SIZE,Settings.MONSTER_CANVAS_SIZE,0.10f,true);
        skeletonDying.setPlayMode(Animation.PlayMode.NORMAL);
        skeletonDead = new Texture(Gdx.files.internal("Monsters_Creatures_Fantasy/Skeleton/Dead.png"));
        skeletonAttack = makeAnimation(new Texture(Gdx.files.internal("Monsters_Creatures_Fantasy/Skeleton/FastAttack.png")), 3,
            Settings.MONSTER_CANVAS_SIZE,Settings.MONSTER_CANVAS_SIZE,0.07f,true);

        background = new Texture(Gdx.files.internal("backgrounds/background_repeat.png"));
    }

    private static Animation<TextureRegion> makeAnimation(Texture sheet, int frames, int width, int height, float duration, boolean flipX){
        TextureRegion[] textureRegions = new TextureRegion[frames];
        sheet.setFilter(Texture.TextureFilter.Nearest,Texture.TextureFilter.Nearest);
        for(int i = 0; i<frames; i++){
            textureRegions[i] = new TextureRegion(sheet, width*i,0,width,height);
            textureRegions[i].flip(flipX,false);
        }
        return new Animation<TextureRegion>(duration,textureRegions);
    }

    public static void dispose(){
        background.dispose();
        hit.dispose();
        skeletonDead.dispose();

        // Dispose textures used in animations
        disposeAnimationTextures(running);
        disposeAnimationTextures(jump);
        disposeAnimationTextures(jumpFallInBetween);
        disposeAnimationTextures(fall);
        disposeAnimationTextures(attack);
        disposeAnimationTextures(attack2);
        disposeAnimationTextures(skeletonIdle);
        disposeAnimationTextures(skeletonDying);
        disposeAnimationTextures(skeletonAttack);
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
