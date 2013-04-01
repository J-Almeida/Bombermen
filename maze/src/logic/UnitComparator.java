package logic;

import java.util.Comparator;

public class UnitComparator implements Comparator<Unit> {

	@Override
	public int compare(Unit arg0, Unit arg1) {
	// TODO: Change this implementation to the one of EventComparator
		if (arg0.IsHero() && arg1.IsHero())
			return 0;
		else if (arg0.IsHero())
			return -1;
		else
			return 1;
	}

}
