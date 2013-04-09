package ui.gui.graphical.game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

/**
 * The Class TiledImage.
 */
public class TiledImage
{
    /** The image with all tiles. */
    private BufferedImage _image;

    /** The width (px) of each tile. */
    private final int _tileWidth;

    /** The height (px) of each tile. */
    private final int _tileHeight;

    /**
     * Instantiates a new tiled image.
     *
     * @param path the path to file
     * @param tileWidth the tile width
     * @param tileHeight the tile height
     */
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

    /**
     * The Class KeyTile.
     */
    private class KeyTile
    {
        /** The Column. */
        private final int Column;

        /** The Line. */
        private final int Line;

        /**
         * Instantiates a new key tile.
         *
         * @param col the column
         * @param line the line
         */
        public KeyTile(int col, int line) { Column = col; Line = line; }

        /* (non-Javadoc)
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals(Object obj)
        {
            if (obj == null || !(obj instanceof KeyTile))
                return false;

            KeyTile k = (KeyTile)obj;

            return this.Column == k.Column && this.Line == k.Line;
        }

        /* (non-Javadoc)
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode()
        {
            int hash = 23;
            hash = hash * 31 + Column;
            hash = hash * 37 + Line;
            return hash;
        }
    }

    /** The cached tiles. */
    private final Map<KeyTile, BufferedImage> _tiles = new HashMap<KeyTile, BufferedImage>();

    /**
     * Gets the original image.
     *
     * @return the buffered image
     */
    BufferedImage GetBufferedImage() { return _image; }

    /**
     * Gets the tile at position (col, line)
     *
     * Multiple calls to this method with the same arguments will NOT load the image multiple times, only once (cached).
     *
     * @param col the column
     * @param line the line
     * @return the buffered image
     */
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
