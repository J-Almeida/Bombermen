package utils;

public enum Key {
	UP('W', 1), DOWN('S', 2), LEFT('A', 3), RIGHT('D', 4);
	
	Key(char c, int i) { _val = c;	_num = i; }
	
	public char GetValue() { return _val; }
	
	public static Key toEnum(char val)
	{
		for (Key v : Key.values())
			if (v._val == val)
				return v;
		
		return null;
	}
	
	public static Key toEnum(int num)
	{
		for (Key v : Key.values())
			if (v._num == num)
				return v;
		
		return null;
	}
	
	private char _val;
	private int _num;
}
