package gdx.game.utils;

public class Settings {

    public static final float PLAYER_VELOCITY = 200;
    public static final int GAME_WIDTH = 1280;
    public static final int GAME_HEIGHT = 960;
    public static final int PLAYER_WIDTH = 15;
    public static final int PLAYER_HEIGHT = 38;
    public static final int PLAYER_CANVAS_WIDTH = 80;
    public static final int PLAYER_CANVAS_HEIGHT = 120;
    public static final float PLAYER_STARTX = GAME_WIDTH / 2 - PLAYER_WIDTH / 2;;
    public static final float PLAYER_STARTY =  20;

    public static final int DROPLET_SIZE = 64;
    public static final float DROPLET_VELOCITY = -170;
    public static final long DROPLET_MIN_COUNTER = 500000000;
    public static final long DROPLET_MAX_COUNTER = 1000000000L *2;
    public static final int HP = 9;
    public static final int LIVE_SIZE = 64;
    public static final int LIVE_SPACE_BETWEEN = 16;
    public static final long ENEMY_MIN_COUNTER = 1500000000;
    public static final long ENEMY_MAX_COUNTER = 1000000000L * 5;
    public static final float ENEMY_VELOCITY = -300;
    public static final float BG_SPEED = -100;
}
