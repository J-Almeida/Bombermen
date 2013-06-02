package pt.up.fe.pt.lpoo.bombermen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ObjectMap;

public class TextureManager implements Disposable
{
    private ObjectMap<String, Texture> _textures;

    public TextureManager()
    {
        _textures = new ObjectMap<String, Texture>();
    }

    public boolean Load(String name) // assumes .png and data/ folder
    {
        FileHandle fh = Gdx.files.internal("data/" + name + ".png");
        if (!fh.exists() || fh.isDirectory())
            return false;

        Texture t = new Texture(fh);
        _textures.put(name, t);
        return true;
    }

    public Texture Get(String name)
    {
        return _textures.get(name);
    }

    @Override
    public void dispose()
    {
        for (Texture t : _textures.values()) t.dispose();
        _textures.clear();
        _textures = null;
    }
}
