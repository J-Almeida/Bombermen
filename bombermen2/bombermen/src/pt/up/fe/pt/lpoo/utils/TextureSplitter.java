package pt.up.fe.pt.lpoo.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextureSplitter
{
    public static TextureRegion[][] SplitTexture(Texture t, int lines, int columns)
    {
        TextureRegion regions[][] = new TextureRegion[columns][lines];

        int width = t.getWidth() / columns;
        int height = t.getHeight() / lines;

        for (int i = 0, x = 0; x < t.getWidth(); x += width, i++)
            for (int j = 0, y = 0; y < t.getHeight(); y += height, j++)
                regions[i][j] = new TextureRegion(t, x, y, width, height);

        return regions;
    }
}
