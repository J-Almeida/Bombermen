package pt.up.fe.lpoo.bombermen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;


public final class Settings
{
    public static final int KEYS_WASD = 0;
    public static final int KEYS_IJKL = 1;
    public static final int KEYS_ARROWS = 2;

    public static float SoundVolume;
    public static float MusicVolume;
    public static boolean Fullscreen;
    public static int Keys;

    public static void Save()
    {
        Preferences prefs = Gdx.app.getPreferences("bombermen_settings");

        prefs.putFloat("sound_volume", SoundVolume);
        prefs.putFloat("music_volume", MusicVolume);
        prefs.putBoolean("fullscreen", Fullscreen);
        prefs.putInteger("keys", Keys);

        prefs.flush();
    }

    public static void Load()
    {
        Preferences prefs = Gdx.app.getPreferences("bombermen_settings");

        SoundVolume = prefs.getFloat("sound_volume", 0.5f);
        MusicVolume = prefs.getFloat("music_volume", 0.5f);
        Fullscreen = prefs.getBoolean("fullscreen", false);
        Keys = prefs.getInteger("keys", KEYS_WASD);
    }

    private Settings()
    {
    }
}
