package gdx.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class GamePreferences {
    private static final String PREFS_NAME = "b2dtut";

    private static final String PREF_MUSIC_ENABLED = "music.enabled";

    private static final String PREF_MUSIC_VOLUME = "volume";

    private static final String PREF_SOUND_ENABLED = "sound.enabled";

    private static final String PREF_SOUND_VOL = "sound";

    public float getMusicVolume() {
        return getPrefs().getFloat("volume", 0.5F);
    }

    protected Preferences getPrefs() {
        return Gdx.app.getPreferences("b2dtut");
    }

    public float getSoundVolume() {
        return getPrefs().getFloat("sound", 0.5F);
    }

    public boolean isMusicEnabled() {
        return getPrefs().getBoolean("music.enabled", true);
    }

    public boolean isSoundEffectsEnabled() {
        return getPrefs().getBoolean("sound.enabled", true);
    }

    public void setMusicEnabled(boolean paramBoolean) {
        getPrefs().putBoolean("music.enabled", paramBoolean).flush();
    }

    public void setMusicVolume(float paramFloat) {
        getPrefs().putFloat("volume", paramFloat).flush();
    }

    public void setSoundEffectsEnabled(boolean paramBoolean) {
        getPrefs().putBoolean("sound.enabled", paramBoolean).flush();
    }

    public void setSoundVolume(float paramFloat) {
        getPrefs().putFloat("sound", paramFloat).flush();
    }
}
