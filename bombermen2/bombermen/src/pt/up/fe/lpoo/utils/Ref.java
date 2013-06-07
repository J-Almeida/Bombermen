package pt.up.fe.lpoo.utils;

public class Ref<T>
{
    private T value;

    public Ref(T value)
    {
        this.value = value;
    }

    public T Get()
    {
        return value;
    }

    public void Set(T other)
    {
        value = other;
    }

    @Override
    public String toString()
    {
        return value.toString();
    }

    @Override
    public boolean equals(Object obj)
    {
        return value.equals(obj);
    }

    @Override
    public int hashCode()
    {
        return value.hashCode();
    }
}
