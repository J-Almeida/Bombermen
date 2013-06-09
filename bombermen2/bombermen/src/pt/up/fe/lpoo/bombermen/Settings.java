package pt.up.fe.lpoo.bombermen;

import java.util.HashMap;
import java.util.Map;

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
    public static Map<String, String> SavedServers;

    public static void Save()
    {
        Preferences prefs = Gdx.app.getPreferences("bombermen_settings");

        prefs.putFloat("sound_volume", SoundVolume);
        prefs.putFloat("music_volume", MusicVolume);
        prefs.putBoolean("fullscreen", Fullscreen);
        prefs.putInteger("keys", Keys);
        prefs.put(SavedServers);

        prefs.flush();
    }

    public static void Load()
    {
        Preferences prefs = Gdx.app.getPreferences("bombermen_settings");

        SoundVolume = prefs.getFloat("sound_volume", 0.5f);
        MusicVolume = prefs.getFloat("music_volume", 0.5f);
        Fullscreen = prefs.getBoolean("fullscreen", false);
        Keys = prefs.getInteger("keys", KEYS_WASD);

        SavedServers = new HashMap<String, String>();
        for (int i = 0; i < Constants.MAX_SERVERS_STORED; ++i)
        {
            String s = prefs.getString("server" + i, null);
            if (s != null)
                SavedServers.put("server" + i, s);
        }
    }

    public static void SaveServers(String[] ipAddresses)
    {
        for (int i = 0; i < ipAddresses.length && i < Constants.MAX_SERVERS_STORED; ++i)
            SavedServers.put("server" + i, ipAddresses[i]); // overrides
        Save();
    }

    private Settings()
    {
    }
}
