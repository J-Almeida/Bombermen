package ui.gui.graphical.game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class TiledImage
{
    private BufferedImage _image;
    private int _tileWidth;
    private int _tileHeight;

    public TiledImage(String path, int tileWidth, int tileHeight)
    {
        try
        {
            InputStream input = java.lang.ClassLoader.getSystemResourceAsStream(path);
            _image = ImageIO.read(input);
        }
        catch (IOException e)
        {
            System.out.println("ERROR: Could not load image " + path + "."); // TODO: remove this log
        }

        _tileWidth = tileWidth;
        _tileHeight = tileHeight;
    }

    private class KeyTile
    {
        private int Column;
        private int Line;

        public KeyTile(int col, int line) { Column = col; Line = line; }

        @Override
        public boolean equals(Object obj)
        {
            if (obj == null || !(obj instanceof KeyTile))
                return false;

            KeyTile k = (KeyTile)obj;

            return this.Column == k.Column && this.Line == k.Line;
        }

        @Override
        public int hashCode()
        {
            int hash = 23;
            hash = hash * 31 + Column;
            hash = hash * 37 + Line;
            return hash;
        }
    }

    Map<KeyTile, BufferedImage> _tiles = new HashMap<KeyTile, BufferedImage>();

    public BufferedImage GetBufferedImage() { return _image; }

    public BufferedImage GetTile(int col, int line)
    {
        KeyTile k = new KeyTile(col, line);
        if (_tiles.containsKey(k))
            return _tiles.get(k);
        else
        {
            BufferedImage tile = _image.getSubimage(col * _tileWidth, line * _tileHeight, _tileWidth, _tileHeight);
            _tiles.put(k, tile);
            return tile;
        }
    }
}
