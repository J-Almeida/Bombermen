package logic;

import java.awt.Point;

import logic.GameState.WallCollision;
import logic.events.Event;
import logic.events.ExplodeEvent;

public abstract class Bomb extends WorldObject
{
    public Bomb(int guid, Point position, int playerOwnerId, int radius, int strength, int time)
    {
        super(WorldObjectType.Bomb, guid, position);

        Radius = radius;
        Strength = strength;
        Time = time;
        PlayerOwnerId = playerOwnerId;
    }

    public int Radius; // Number of cells in each direction
    public int Strength; // Damage in number of hitpoints
    public int Time; // Time to explode
    public int PlayerOwnerId; // Player who planted the bomb
    
    protected int _northRadius = -1;
    protected int _southRadius = -1;
    protected int _eastRadius = -1;
    protected int _westRadius = -1;
    protected boolean _northTip = false;
    protected boolean _southTip = false;
    protected boolean _eastTip = false;
    protected boolean _westTip = false;
    
    private boolean _drawEast = true;
    private boolean _drawWest = true;
    private boolean _drawNorth = true;
    private boolean _drawSouth = true;

    protected int _bombTimer = 0;
    protected int _explosionTimer = 0;
    protected boolean _shouldExplode = false;
    protected boolean _explosionEnded = false;

    @Override
    public void Update(GameState gs, int diff)
    {
        _bombTimer += diff;

        if (_bombTimer > Time * 1000)
        {
            _shouldExplode = true;
            _bombTimer = -1;
        }
        
        if (_shouldExplode)
        {
            _explosionTimer += diff;
            
            if (_explosionTimer > 600)
            {
                _explosionEnded = true;
                _explosionTimer = -1;
            }

            _westRadius = 0;
            _eastRadius = 0;
            _northRadius = 0;
            _southRadius = 0;
            
            _northTip = false;
            _southTip = false;
            _eastTip = false;
            _westTip = false;

            for (int i = 1; i < Radius; ++i)
            {
                if (_drawWest)
                {
                    Point westP = new Point(Position.x - i, Position.y);
                    WallCollision wc = gs.CollidesWall(westP);
                    if (!wc.Collision)
                        _westRadius++;
                    else
                    {
                        _drawWest = false;
                        gs.PushEvent(wc.Wall, this, new ExplodeEvent(this));
                    }
                }

                if (_drawEast)
                {
                    Point eastP = new Point(Position.x + i, Position.y);
                    WallCollision wc = gs.CollidesWall(eastP);
                    if (!wc.Collision)
                        _eastRadius++;
                    else
                    {
                        _drawEast = false;
                        gs.PushEvent(wc.Wall, this, new ExplodeEvent(this));
                    }
                }

                if (_drawNorth)
                {
                    Point northP = new Point(Position.x, Position.y - i);
                    WallCollision wc = gs.CollidesWall(northP);
                    if (!wc.Collision)
                        _northRadius++;
                    else
                    {
                        _drawNorth = false;
                        gs.PushEvent(wc.Wall, this, new ExplodeEvent(this));
                    }
                }

                if (_drawSouth)
                {
                    Point southP = new Point(Position.x, Position.y + i);
                    WallCollision wc = gs.CollidesWall(southP);
                    if (!wc.Collision)
                        _southRadius++;
                    else
                    {
                        _drawSouth = false;
                        gs.PushEvent(wc.Wall, this, new ExplodeEvent(this));
                    }
                }
            }

            if (_drawWest)
            {
                Point westP = new Point(Position.x - Radius, Position.y);
                WallCollision wc = gs.CollidesWall(westP);
                _westTip = true;
                if (!wc.Collision)
                    gs.PushEvent(wc.Wall, this, new ExplodeEvent(this));
            }
            
            if (_drawEast)
            {
                Point eastP = new Point(Position.x + Radius, Position.y);
                WallCollision wc = gs.CollidesWall(eastP);
                _eastTip = true;
                if (wc.Collision)
                    gs.PushEvent(wc.Wall, this, new ExplodeEvent(this));
            }
            
            if (_drawNorth)
            {
                Point northP = new Point(Position.x, Position.y - Radius);
                WallCollision wc = gs.CollidesWall(northP);
                _northTip = true;
                if (wc.Collision)
                    gs.PushEvent(wc.Wall, this, new ExplodeEvent(this));
            }
            
            if (_drawSouth)
            {
                Point southP = new Point(Position.x, Position.y + Radius);
                WallCollision wc = gs.CollidesWall(southP);
                _southTip = true;
                if (wc.Collision)
                    gs.PushEvent(wc.Wall, this, new ExplodeEvent(this));
            }
        }
    }

    @Override
    public boolean IsAlive()
    {
        return !_explosionEnded;
    }

    public boolean ShouldExplode()
    {
         return _shouldExplode;
    }

    @Override
    public void HandleEvent(GameState gs, WorldObject src, Event event)
    {
        // TODO Auto-generated method stub

    }
}
