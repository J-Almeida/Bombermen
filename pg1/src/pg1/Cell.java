package pg1;

public class Cell<T>
{
    public Cell(T val)
    {
        _value = val;
        _visited = false;
    }

    public T GetValue() { return _value; }
    public void SetValue(T val) { _value = val; }

    @Override
    public String toString()
    {
        return _value.toString();
    }

    public boolean WasVisited() { return _visited; }
    public void Visit() { _visited = true; }

    private T _value;
    private boolean _visited;
}
