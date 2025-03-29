package gdx.game.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import gdx.game.MyGame;
import gdx.game.utils.Settings;

public class AssetManager {
    public static Animation attack;
    public static Animation attack2;
    public static Texture background;
    public static Texture base_background;
    public static Sound boneCrackSound;
    public static Texture dead;
    public static Animation demonAttack;
    public static Animation demonAttackWhite;
    public static Animation demonChargingAttack;
    public static Animation demonChargingAttackWhite;
    public static Animation demonIdle;
    public static Animation demonIdleWhite;
    public static Animation dying;
    public static Sound evilLaughSound;
    public static Animation explosion;
    public static Animation fall;
    public static Sound fallGroundSound;
    public static Texture far_background;
    public static Animation fireballExplosion;
    public static Sound fireballHitSound;
    public static Animation fireballMoving;
    public static Sound fireballShotSound;
    public static Texture foreground;
    public static Texture grass_background;
    public static Texture hit;
    public static Sound hitSound;
    public static Animation jump;
    public static Animation jumpFallInBetween;
    public static Sound jumpingSound;
    public static Texture leaves_background;
    public static Texture medium_background;
    public static Music music;
    public static Texture near_background;
    public static Animation running;
    public static Animation skeletonAttack;
    public static Animation skeletonChargingAttack;
    public static Texture skeletonDead;
    public static Animation skeletonDying;
    public static Animation skeletonIdle;
    public static Sound slash2Sound;
    public static Sound slashSound;
    public static float soundVolume;
    public static Animation wormAttack;
    public static Animation wormChargingAttack;
    public static Texture wormDead;
    public static Sprite wormDeadSprite;
    public static Animation wormDying;
    public static Animation wormIdle;
    public static Sound wormSquishSound;

    public static void load(MyGame myGame) {
        soundVolume = myGame.getPreferences().getSoundVolume();
        if (!myGame.getPreferences().isSoundEffectsEnabled()) {
            soundVolume = 0.0f;
        }
        Animation<TextureRegion> makeAnimation = makeAnimation(new Texture(Gdx.files.internal("player/120x80_PNGSheets/_Run.png")), 10, 120, 80, 0.07f, false);
        running = makeAnimation;
        makeAnimation.setPlayMode(Animation.PlayMode.LOOP);
        Animation<TextureRegion> makeAnimation2 = makeAnimation(new Texture(Gdx.files.internal("player/120x80_PNGSheets/_Jump.png")), 3, 120, 80, 0.07f, false);
        jump = makeAnimation2;
        makeAnimation2.setPlayMode(Animation.PlayMode.NORMAL);
        Animation<TextureRegion> makeAnimation3 = makeAnimation(new Texture(Gdx.files.internal("player/120x80_PNGSheets/_JumpFallInbetween.png")), 2, 120, 80, 0.07f, false);
        jumpFallInBetween = makeAnimation3;
        makeAnimation3.setPlayMode(Animation.PlayMode.NORMAL);
        Animation<TextureRegion> makeAnimation4 = makeAnimation(new Texture(Gdx.files.internal("player/120x80_PNGSheets/_Fall.png")), 3, 120, 80, 0.07f, false);
        fall = makeAnimation4;
        makeAnimation4.setPlayMode(Animation.PlayMode.NORMAL);
        Animation<TextureRegion> makeAnimation5 = makeAnimation(new Texture(Gdx.files.internal("player/120x80_PNGSheets/_Attack.png")), 4, 120, 80, 0.07f, false);
        attack = makeAnimation5;
        makeAnimation5.setPlayMode(Animation.PlayMode.NORMAL);
        Animation<TextureRegion> makeAnimation6 = makeAnimation(new Texture(Gdx.files.internal("player/120x80_PNGSheets/_Attack2.png")), 6, 120, 80, 0.07f, false);
        attack2 = makeAnimation6;
        makeAnimation6.setPlayMode(Animation.PlayMode.NORMAL);
        Animation<TextureRegion> makeAnimation7 = makeAnimation(new Texture(Gdx.files.internal("player/120x80_PNGSheets/_Death.png")), 10, 120, 80, 0.07f, false);
        dying = makeAnimation7;
        makeAnimation7.setPlayMode(Animation.PlayMode.NORMAL);
        hit = new Texture(Gdx.files.internal("player/120x80_PNGSheets/_Hit.png"));
        dead = new Texture(Gdx.files.internal("player/120x80_PNGSheets/_Dead.png"));
        Animation<TextureRegion> makeAnimation8 = makeAnimation(new Texture(Gdx.files.internal("Monsters_Creatures_Fantasy/Skeleton/Idle.png")), 4, 150, 150, 0.1f, true);
        skeletonIdle = makeAnimation8;
        makeAnimation8.setPlayMode(Animation.PlayMode.LOOP);
        Animation<TextureRegion> makeAnimation9 = makeAnimation(new Texture(Gdx.files.internal("Monsters_Creatures_Fantasy/Skeleton/Death.png")), 4, 150, 150, 0.1f, true);
        skeletonDying = makeAnimation9;
        makeAnimation9.setPlayMode(Animation.PlayMode.NORMAL);
        skeletonDead = new Texture(Gdx.files.internal("Monsters_Creatures_Fantasy/Skeleton/Dead.png"));
        skeletonAttack = makeAnimation(new Texture(Gdx.files.internal("Monsters_Creatures_Fantasy/Skeleton/FastAttack.png")), 3, 150, 150, 0.07f, true);
        skeletonChargingAttack = makeAnimation(new Texture(Gdx.files.internal("Monsters_Creatures_Fantasy/Skeleton/Attack.png")), 5, 150, 150, 0.07f, true);
        wormIdle = makeAnimation(new Texture(Gdx.files.internal("Fire_Worm/Sprites/Worm/Walk.png")), 9, 90, 90, 0.07f, true);
        wormDying = makeAnimation(new Texture(Gdx.files.internal("Fire_Worm/Sprites/Worm/Death.png")), 8, 90, 90, 0.07f, true);
        wormChargingAttack = makeAnimation(new Texture(Gdx.files.internal("Fire_Worm/Sprites/Worm/Attack.png")), 16, 90, 90, 0.07f, true);
        wormAttack = makeAnimation(new Texture(Gdx.files.internal("Fire_Worm/Sprites/Worm/FastAttack.png")), 5, 90, 90, 0.07f, true);
        wormDead = new Texture(Gdx.files.internal("Fire_Worm/Sprites/Worm/Dead.png"));
        Sprite sprite = new Sprite(wormDead);
        wormDeadSprite = sprite;
        sprite.flip(true, false);
        demonIdle = makeAnimation(new Texture(Gdx.files.internal("Demon_Boss/demon-idle.png")), 6, 160, 144, 0.07f, false);
        demonChargingAttack = makeAnimation(new Texture(Gdx.files.internal("Demon_Boss/demon-attack.png")), 6, Settings.DEMON_ATTACK_WIDTH_CANVAS_SIZE, 192, 0.1f, false);
        demonAttack = makeAnimation(new Texture(Gdx.files.internal("Demon_Boss/demon-fast-attack.png")), 5, Settings.DEMON_ATTACK_WIDTH_CANVAS_SIZE, 192, 0.07f, false);
        demonIdleWhite = makeAnimation(new Texture(Gdx.files.internal("Demon_Boss/demon-idle-white.png")), 6, 160, 144, 0.07f, false);
        demonChargingAttackWhite = makeAnimation(new Texture(Gdx.files.internal("Demon_Boss/demon-attack-white.png")), 6, Settings.DEMON_ATTACK_WIDTH_CANVAS_SIZE, 192, 0.1f, false);
        demonAttackWhite = makeAnimation(new Texture(Gdx.files.internal("Demon_Boss/demon-fast-attack-white.png")), 5, Settings.DEMON_ATTACK_WIDTH_CANVAS_SIZE, 192, 0.07f, false);
        fireballMoving = makeAnimation(new Texture(Gdx.files.internal("Fire_Worm/Sprites/Fire_Ball/Move.png")), 6, 46, 46, 0.07f, true);
        fireballExplosion = makeAnimation(new Texture(Gdx.files.internal("Fire_Worm/Sprites/Fire_Ball/Explosion.png")), 7, 46, 46, 0.07f, true);
        explosion = makeAnimation(new Texture(Gdx.files.internal("explosion/Fire-bomb.png")), 14, 64, 64, 0.07f, true);
        grass_background = new Texture(Gdx.files.internal("backgrounds/infinite_background/8.png"));
        leaves_background = new Texture(Gdx.files.internal("backgrounds/infinite_background/7.png"));
        near_background = new Texture(Gdx.files.internal("backgrounds/infinite_background/6.png"));
        background = new Texture(Gdx.files.internal("backgrounds/infinite_background/5.png"));
        medium_background = new Texture(Gdx.files.internal("backgrounds/infinite_background/4.png"));
        far_background = new Texture(Gdx.files.internal("backgrounds/infinite_background/3.png"));
        base_background = new Texture(Gdx.files.internal("backgrounds/infinite_background/2.png"));
        foreground = new Texture(Gdx.files.internal("backgrounds/infinite_background/9.png"));
        Music newMusic = Gdx.audio.newMusic(Gdx.files.internal("MusicWithCopy.mp3"));
        music = newMusic;
        newMusic.setLooping(true);
        music.setVolume(myGame.getPreferences().getMusicVolume());
        if (!myGame.getPreferences().isMusicEnabled()) {
            music.setVolume(0.0f);
        }
        slashSound = Gdx.audio.newSound(Gdx.files.internal("sfx/slash.mp3"));
        slash2Sound = Gdx.audio.newSound(Gdx.files.internal("sfx/slash2.mp3"));
        hitSound = Gdx.audio.newSound(Gdx.files.internal("sfx/power-punch.mp3"));
        boneCrackSound = Gdx.audio.newSound(Gdx.files.internal("sfx/bone-crack.mp3"));
        evilLaughSound = Gdx.audio.newSound(Gdx.files.internal("sfx/evil-laugh.mp3"));
        fireballShotSound = Gdx.audio.newSound(Gdx.files.internal("sfx/fireball-shot.mp3"));
        fireballHitSound = Gdx.audio.newSound(Gdx.files.internal("sfx/fireball-hit.mp3"));
        fallGroundSound = Gdx.audio.newSound(Gdx.files.internal("sfx/human-impact-on-ground.mp3"));
        jumpingSound = Gdx.audio.newSound(Gdx.files.internal("sfx/player-jumping.mp3"));
        wormSquishSound = Gdx.audio.newSound(Gdx.files.internal("sfx/enemy-squish.mp3"));
    }

    private static Animation<TextureRegion> makeAnimation(Texture sheet, int frames, int width, int height, float duration, boolean flipX) {
        TextureRegion[] textureRegions = new TextureRegion[frames];
        sheet.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        for (int i = 0; i < frames; i++) {
            textureRegions[i] = new TextureRegion(sheet, width * i, 0, width, height);
            textureRegions[i].flip(flipX, false);
        }
        return new Animation<>(duration, textureRegions);
    }

    public static void dispose() {
        grass_background.dispose();
        leaves_background.dispose();
        background.dispose();
        medium_background.dispose();
        far_background.dispose();
        base_background.dispose();
        foreground.dispose();
        hit.dispose();
        dead.dispose();
        skeletonDead.dispose();
        wormDead.dispose();
        music.dispose();
        slashSound.dispose();
        slash2Sound.dispose();
        hitSound.dispose();
        boneCrackSound.dispose();
        evilLaughSound.dispose();
        fireballShotSound.dispose();
        fireballHitSound.dispose();
        fallGroundSound.dispose();
        jumpingSound.dispose();
        wormSquishSound.dispose();
        disposeAnimationTextures(running);
        disposeAnimationTextures(jump);
        disposeAnimationTextures(jumpFallInBetween);
        disposeAnimationTextures(fall);
        disposeAnimationTextures(attack);
        disposeAnimationTextures(attack2);
        disposeAnimationTextures(dying);
        disposeAnimationTextures(skeletonIdle);
        disposeAnimationTextures(skeletonDying);
        disposeAnimationTextures(skeletonAttack);
        disposeAnimationTextures(skeletonChargingAttack);
        disposeAnimationTextures(wormIdle);
        disposeAnimationTextures(wormDying);
        disposeAnimationTextures(wormChargingAttack);
        disposeAnimationTextures(wormAttack);
        disposeAnimationTextures(fireballMoving);
        disposeAnimationTextures(fireballExplosion);
        disposeAnimationTextures(demonIdle);
        disposeAnimationTextures(demonChargingAttack);
        disposeAnimationTextures(demonAttack);
        disposeAnimationTextures(demonIdleWhite);
        disposeAnimationTextures(demonChargingAttackWhite);
        disposeAnimationTextures(demonAttackWhite);
    }

    private static void disposeAnimationTextures(Animation<TextureRegion> animation) {
        if (animation != null) {
            for (TextureRegion textureRegion : (TextureRegion[]) animation.getKeyFrames()) {
                if (textureRegion.getTexture() != null) {
                    textureRegion.getTexture().dispose();
                }
            }
        }
    }
}
