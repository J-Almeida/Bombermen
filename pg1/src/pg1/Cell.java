package pg1;

public class Cell<T>
{
    public Cell(T val)
    {
        _value = val;
    }

    public T GetValue() { return _value; }
    public void SetValue(T val) { _value = val; }

    @Override
    public String toString()
    {
        return _value.toString();
    }

    private T _value;
}
