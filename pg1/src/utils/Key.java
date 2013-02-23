package utils;

public enum Key {
	UP('W'), DOWN('S'), LEFT('A'), RIGHT('D');
	
	Key(char c) { _val = c;	}
	
	public char GetValue() { return _val; }
	
	public static Key toEnum(char val)
	{
		for (Key v : Key.values())
			if (v.GetValue() == val)
				return v;
		
		return null;
	}
	
	private char _val;
}
