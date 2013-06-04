package pt.up.fe.pt.lpoo.bombermen;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class MainActivity extends AndroidApplication
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useGL20 = Constants.USE_GL20;
        cfg.useAccelerometer = false;
        cfg.useCompass = false;
        cfg.useWakelock = false;

        initialize(new Bombermen2(), cfg);
    }
}
