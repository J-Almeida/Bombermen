package pt.up.fe.lpoo.bombermen;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * The Class Settings.
 */
public final class Settings
{
    /** The Constant KEYS_WASD. */
    public static final int KEYS_WASD = 0;

    /** The Constant KEYS_IJKL. */
    public static final int KEYS_IJKL = 1;

    /** The Constant KEYS_ARROWS. */
    public static final int KEYS_ARROWS = 2;

    /** The Sound volume. */
    public static float SoundVolume;

    /** The Music volume. */
    public static float MusicVolume;

    /** True if fullscreen. */
    public static boolean Fullscreen;

    /** The Keys. */
    public static int Keys;

    /** The Saved servers. */
    public static Map<String, String> SavedServers;

    /**
     * Save settings to file.
     */
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

    /**
     * Load settings from file.
     */
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
            if (s != null) SavedServers.put("server" + i, s);
        }
    }

    /**
     * Save servers.
     *
     * @param ipAddresses the ip addresses
     */
    public static void SaveServers(String[] ipAddresses)
    {
        for (int i = 0; i < ipAddresses.length && i < Constants.MAX_SERVERS_STORED; ++i)
            SavedServers.put("server" + i, ipAddresses[i]); // overrides
        Save();
    }

    /**
     * Private constructor.
     */
    private Settings()
    {
    }
}
