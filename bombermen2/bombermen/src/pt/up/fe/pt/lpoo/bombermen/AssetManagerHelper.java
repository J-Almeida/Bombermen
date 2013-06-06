package pt.up.fe.pt.lpoo.bombermen;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class AssetManagerHelper
{
    public static AssetManager Manager;

    public static Sound GetSound(String name)
    {
        return Manager.get("data/sounds/" + name + ".wav");
    }

    public static Music GetMusic(String name)
    {
        return Manager.get("data/musics/" + name + ".mp3");
    }

    public static Texture GetTexture(String name)
    {
        return Manager.get("data/images/" + name + ".png");
    }
}
