package ui.gui.graphical;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import logic.Dragon;

public class Configuration
{
	@Override
	protected Object clone() {
		Configuration res = new Configuration();
		res._fileName = this._fileName;
		res._mazeSize = this._mazeSize;
		res._numberOfDragons = this._numberOfDragons;

		for (Map.Entry<Integer, Action> elem : _keys.entrySet())
		{
			res._keys.put(elem.getKey().intValue(), elem.getValue());
		}

		return res;
	}

	private Configuration()
	{
	}

	public Configuration(String fileName)
	{
		_fileName = fileName;

		ObjectInputStream is = null;
		try
		{
			is = new ObjectInputStream(new FileInputStream(_fileName));
			ReadFromFile(is);
		} catch (/*FileNotFoundException | */ClassNotFoundException | IOException e) {
			initializeWithDefaults();
			TrySaveToFile();
		} finally {
			if (is != null) try { is.close(); } catch (IOException e) {	}
		}
	}

	public boolean TrySaveToFile()
	{
		ObjectOutputStream os = null;
		try {
			os = new ObjectOutputStream(new FileOutputStream(_fileName, false));
			WriteToFile(os);
		} catch (IOException e) {
			return true;
		} finally {
			if (os != null) try { os.close(); } catch (IOException e) {	}
		}
		return false;
	}

	private void WriteToFile(ObjectOutputStream os) throws IOException {
		os.writeObject(this._keys);
		os.writeObject(this._mazeSize);
		os.writeObject(this._numberOfDragons);
		os.writeObject(this._dragonMode);
	}

	@SuppressWarnings("unchecked")
	private void ReadFromFile(ObjectInputStream is) throws ClassNotFoundException, IOException {
		this._keys = (HashMap<Integer, Action>)is.readObject();
		this._mazeSize = (Integer) is.readObject();
		this._numberOfDragons = (Integer) is.readObject();
		this._dragonMode = (Dragon.Behaviour) is.readObject();
	}

	private void initializeWithDefaults()
	{
		for (Action a : Action.values())
			_keys.put(a.DefaultKey, a);

		_mazeSize = 10;
		_numberOfDragons = 2;
		_dragonMode = Dragon.Behaviour.RandomMovement;
	}


	public Action GetAction(int key)
	{
		return _keys.get(key);
	}

	public int GetMazeSize() { return _mazeSize; }
	public int GetNumberOfDragons() { return _numberOfDragons; }
	public Dragon.Behaviour GetDragonMode() { return _dragonMode; }

	private String _fileName;

	public Map<Integer, Action> _keys = new LinkedHashMap<Integer, Action>();
	Integer _mazeSize;
	Integer _numberOfDragons;
	Dragon.Behaviour _dragonMode;

	public void SetNumberOfDragons(int value) {
		_numberOfDragons = value;
	}

	public void SetDragonMode(Dragon.Behaviour value) {
		_dragonMode = value;
	}

	public void SetMazeSize(int value) {
		_mazeSize = value;
	}

	public Map<Integer, Action> GetKeys() { return _keys; }
	public Map<Integer, Action> SetKeys(LinkedHashMap<Integer, Action> value) { return _keys = value; }
}
