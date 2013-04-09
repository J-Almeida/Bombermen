package ui.gui.graphical;

public class SpriteState
{
	public SpriteState(int nF, int cI, int lI, int dX, int dY)
	{
		_numFrames = nF;
		_colInit   = cI;
		_lineInit  = lI;
		_deltaX    = dX;
		_deltaY    = dY;
	}

	public int GetNumFrames() { return _numFrames; }
	public int GetInitialColumn() { return _colInit; }
	public int GetInitialLine() { return _lineInit; }
	public int GetDeltaX() { return _deltaX; }
	public int GetDeltaY() { return _deltaY; }

	private final int _numFrames;
	private final int _colInit;
	private final int _lineInit;
	private final int _deltaX;
	private final int _deltaY;
}
