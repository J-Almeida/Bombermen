package pt.up.fe.lpoo.bombermen;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * Class built on top of AssetManager to store and retrieve
 *  all the required assets (textures, musics, sounds, skins)
 */
public class Assets
{

    /** The Manager. */
    public static AssetManager Manager;

    /** The sound volume. */
    private static float _soundVolume = 0.5f;

    /** The music volume. */
    private static float _musicVolume = 0.5f;

    /** Stored musics so we can change their volume while they are playing */
    private static ObjectMap<String, Music> _musics = new ObjectMap<String, Music>();

    /**
     * Gets the sound volume.
     *
     * @return the float
     */
    public static float GetSoundVolume()
    {
        return _soundVolume;
    }

    /**
     * Gets the music volume.
     *
     * @return the float
     */
    public static float GetMusicVolume()
    {
        return _musicVolume;
    }

    /**
     * Change sound volume.
     *
     * @param volume the volume
     */
    public static void ChangeSoundVolume(float volume)
    {
        _soundVolume = volume;
    }

    /**
     * Change music volume.
     *
     * @param volume the volume
     */
    public static void ChangeMusicVolume(float volume)
    {
        _musicVolume = volume;
        for (Music m : _musics.values())
            if (m.isPlaying()) m.setVolume(volume);
    }

    /**
     * Play sound (.wav)
     *
     * @param name the name
     * @return the sound
     */
    public static Sound PlaySound(String name)
    {
        Sound s = Manager.get("data/sounds/" + name + ".wav");
        s.play(_soundVolume);
        return s;
    }

    /**
     * Play music (.mp3)
     *
     * @param name the name
     * @param looping the looping
     * @return the music
     */
    public static Music PlayMusic(String name, boolean looping)
    {
        Music m = Manager.get("data/musics/" + name + ".mp3");
        m.setLooping(looping);
        m.setVolume(_musicVolume);
        m.play();

        if (!_musics.containsKey(name)) _musics.put(name, m);

        return m;
    }

    /**
     * Gets the texture (.png)
     *
     * @param name the name
     * @return the texture
     */
    public static Texture GetTexture(String name)
    {
        return Manager.get("data/images/" + name + ".png");
    }

    /**
     * Gets the skin (.json)
     *
     * @param name the name
     * @return the skin
     */
    public static Skin GetSkin(String name)
    {
        return Manager.get("data/skins/" + name + ".json");
    }
}
