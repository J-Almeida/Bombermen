package pt.up.fe.pt.lpoo.bombermen;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class Assets
{
    public static AssetManager Manager;
    public static float SoundVolume = 0.5f;
    public static float MusicVolume = 0.5f;

    public static Sound PlaySound(String name)
    {
        Sound s = Manager.get("data/sounds/" + name + ".wav");
        s.play(SoundVolume);
        return s;
    }

    public static Music PlayMusic(String name, boolean looping)
    {
        Music m = Manager.get("data/musics/" + name + ".mp3");
        m.setLooping(looping);
        m.setVolume(SoundVolume);
        return m;
    }

    public static Texture GetTexture(String name)
    {
        return Manager.get("data/images/" + name + ".png");
    }
}
