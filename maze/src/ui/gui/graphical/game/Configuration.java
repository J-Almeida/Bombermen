package ui.gui.graphical.game;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import logic.Dragon;

/**
 * The Class Configuration.
 */
public class Configuration
{
    /* (non-Javadoc)
     * @see java.lang.Object#clone()
     */
    @Override
    protected Object clone()
    {
        Configuration res = new Configuration();
        res._fileName = this._fileName;
        res._mazeSize = this._mazeSize;
        res._numberOfDragons = this._numberOfDragons;

        for (Map.Entry<Integer, Action> elem : _keys.entrySet())
            res._keys.put(elem.getKey(), elem.getValue());

        return res;
    }

    /**
     * Instantiates a new configuration.
     */
    private Configuration() { }

    /**
     * Instantiates a new configuration.
     *
     * @param fileName the file name
     */
    public Configuration(String fileName)
    {
        _fileName = fileName;

        ObjectInputStream is = null;
        try
        {
            is = new ObjectInputStream(new FileInputStream(_fileName));
            ReadFromFile(is);
        }
        catch (/*FileNotFoundException | */ClassNotFoundException | IOException e)
        {
            initializeWithDefaults();
            TrySaveToFile();
        }
        finally
        {
            if (is != null) try { is.close(); } catch (IOException e) {    }
        }
    }

    /**
     * Try save to file.
     *
     * @return true, if successful
     */
    public boolean TrySaveToFile()
    {
        ObjectOutputStream os = null;
        try
        {
            os = new ObjectOutputStream(new FileOutputStream(_fileName, false));
            WriteToFile(os);
        }
        catch (IOException e)
        {
            return true;
        }
        finally
        {
            if (os != null) try { os.close(); } catch (IOException e) {    }
        }

        return false;
    }

    /**
     * Write to file.
     *
     * @param os the os
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void WriteToFile(ObjectOutputStream os) throws IOException
    {
        os.writeObject(this._keys);
        os.writeObject(this._mazeSize);
        os.writeObject(this._numberOfDragons);
        os.writeObject(this._dragonMode);
    }

    /**
     * Read from file.
     *
     * @param is the is
     * @throws ClassNotFoundException the class not found exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @SuppressWarnings("unchecked")
    private void ReadFromFile(ObjectInputStream is) throws ClassNotFoundException, IOException
    {
        this._keys = (HashMap<Integer, Action>)is.readObject();
        this._mazeSize = (Integer) is.readObject();
        this._numberOfDragons = (Integer) is.readObject();
        this._dragonMode = (Dragon.Behaviour) is.readObject();
    }

    /**
     * Initialize with defaults.
     */
    private void initializeWithDefaults()
    {
        for (Action a : Action.Actions)
            _keys.put(a.DefaultKey, a);

        _mazeSize = 10;
        _numberOfDragons = 2;
        _dragonMode = Dragon.Behaviour.RandomMovement;
    }


    /**
     * Gets the action.
     *
     * @param key the key
     * @return the action
     */
    public Action GetAction(int key)
    {
        return _keys.get(key);
    }

    /**
     * Gets the maze size.
     *
     * @return the int
     */
    public int GetMazeSize() { return _mazeSize; }

    /**
     * Gets the number of dragons.
     *
     * @return the int
     */
    public int GetNumberOfDragons() { return _numberOfDragons; }

    /**
     * Gets the dragon mode.
     *
     * @return the dragon. behaviour
     */
    public Dragon.Behaviour GetDragonMode() { return _dragonMode; }

    /** The _file name. */
    private String _fileName;

    /** The _keys. */
    public Map<Integer, Action> _keys = new LinkedHashMap<Integer, Action>();

    /** The _maze size. */
    Integer _mazeSize;

    /** The _number of dragons. */
    Integer _numberOfDragons;

    /** The _dragon mode. */
    Dragon.Behaviour _dragonMode;

    /**
     * Sets the number of dragons.
     *
     * @param value the value
     */
    public void SetNumberOfDragons(int value)
    {
        _numberOfDragons = value;
    }

    /**
     * Sets the dragon mode.
     *
     * @param value the value
     */
    public void SetDragonMode(Dragon.Behaviour value)
    {
        _dragonMode = value;
    }

    /**
     * Sets the maze size.
     *
     * @param value the value
     */
    public void SetMazeSize(int value)
    {
        _mazeSize = value;
    }

    /**
     * Gets the keys.
     *
     * @return the map
     */
    public Map<Integer, Action> GetKeys() { return _keys; }

    /**
     * Sets the keys.
     *
     * @param value the value
     * @return the map
     */
    public Map<Integer, Action> SetKeys(LinkedHashMap<Integer, Action> value) { return _keys = value; }
}
