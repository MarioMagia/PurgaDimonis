package gdx.game.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import gdx.game.MyGame;

/** Launches the Android application. */
public class AndroidLauncher extends AndroidApplication {
    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration configuration = new AndroidApplicationConfiguration();
        configuration.useImmersiveMode = true;
        configuration.useAccelerometer = false;
        configuration.useCompass = false;
        initialize(new MyGame(), configuration);
    }
}
