package pt.up.fe.pt.lpoo.utils;

public class Point
{
    public Point(int x, int y)
    {
        _x = x;
        _y = y;
    }

    public Point()
    {
        this(0, 0);
    }

    private int _x;
    private int _y;

    public void SetX(int x)
    {
        _x = x;
    }

    public void SetY(int y)
    {
        _y = y;
    }

    public int GetX()
    {
        return _x;
    }

    public int GetY()
    {
        return _y;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + _x;
        result = prime * result + _y;
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Point other = (Point) obj;
        if (_x != other._x) return false;
        if (_y != other._y) return false;
        return true;
    }
}
