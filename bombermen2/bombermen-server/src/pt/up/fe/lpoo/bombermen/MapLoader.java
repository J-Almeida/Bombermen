package pt.up.fe.lpoo.bombermen;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import pt.up.fe.lpoo.bombermen.Constants;
import pt.up.fe.lpoo.bombermen.entities.Wall;
import pt.up.fe.lpoo.utils.Ref;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class MapLoader
{
    protected BombermenServer _server;

    private int _maxWidth = 0;
    private int _maxHeight = 0;

    public int GetMaxWidth()
    {
        return _maxWidth;
    }

    public int GetMaxHeight()
    {
        return _maxHeight;
    }

    public MapLoader(BombermenServer sv)
    {
        _server = sv;
    }

    public boolean TryLoad(String mapName, Ref<Integer> width, Ref<Integer> height)
    {
        BufferedReader br = null;

        try
        {
            br = new BufferedReader(new FileReader("maps/" + mapName + ".bombmap"));
        }
        catch (FileNotFoundException e)
        {
            return false;
        }

        ArrayList<String> lines = new ArrayList<String>();

        String currLine;
        try
        {
            while ((currLine = br.readLine()) != null)
                lines.add(currLine);
        }
        catch (IOException e)
        {
            return false;
        }

        try
        {
            br.close();
        }
        catch (IOException e)
        {
            // really?
        }

        _maxHeight = lines.size();

        for (int y = 0; y < lines.size(); ++y)
        {
            char[] chars = lines.get(y).toCharArray();

            if (chars.length > _maxWidth) _maxWidth = chars.length;

            for (int x = 0; x < chars.length; ++x)
            {
                switch (chars[x])
                {
                    case ' ': // empty space, can be destroyable wall
                        if (MathUtils.random() < Constants.WALL_CHANCE) AddWall(1, x, lines.size() - 1 - y);
                        break;
                    case 'X': // undestroyable wall
                        AddWall(-1, x, lines.size() - 1 - y);
                        break;
                    case '#': // space reserved for players
                        ReservePlayerSpace(x, lines.size() - 1 - y);
                        break;
                }
            }
        }

        _server.ShufflePlayerPositions();

        return true;
    }

    private void ReservePlayerSpace(int tileX, int tileY)
    {
        _server.AddNewPlayerPosition(new Vector2(tileX, tileY));
    }

    private void AddWall(int hp, int tileX, int tileY)
    {
        int id = _server.IncLastId();
        _server.GetEntities().put(id, new Wall(id, hp, new Vector2(tileX * Wall.Size.x, tileY * Wall.Size.y), _server));
    }
}
