package pt.up.fe.lpoo.bombermen;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ObjectMap;

public class Assets
{
    public static AssetManager Manager;
    private static float _soundVolume = 0.5f;
    private static float _musicVolume = 0.5f;
    private static ObjectMap<String, Music> _musics = new ObjectMap<String, Music>();

    public static float GetSoundVolume()
    {
        return _soundVolume;
    }

    public static float GetMusicVolume()
    {
        return _musicVolume;
    }

    public static void ChangeSoundVolume(float volume)
    {
        _soundVolume = volume;
    }

    public static void ChangeMusicVolume(float volume)
    {
        _musicVolume = volume;
        for (Music m : _musics.values())
            if (m.isPlaying())
                m.setVolume(volume);
    }

    public static Sound PlaySound(String name)
    {
        Sound s = Manager.get("data/sounds/" + name + ".wav");
        s.play(_soundVolume);
        return s;
    }

    public static Music PlayMusic(String name, boolean looping)
    {
        Music m = Manager.get("data/musics/" + name + ".mp3");
        m.setLooping(looping);
        m.setVolume(_musicVolume);
        m.play();

        if (!_musics.containsKey(name))
            _musics.put(name, m);

        return m;
    }

    public static Texture GetTexture(String name)
    {
        return Manager.get("data/images/" + name + ".png");
    }

    public static Skin GetSkin(String name)
    {
        return Manager.get("data/skins/" + name + ".json");
    }
}
