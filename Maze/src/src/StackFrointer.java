package src;

import java.util.ArrayList;
import java.util.List;

public class StackFrointer extends Frointer{
	
	List<Cell> frointer = new ArrayList<Cell> ();

	@Override
	void add(Cell cell) {
		// TODO Auto-generated method stub
		frointer.add(cell);
	}

	@Override
	Cell remove() {
		// TODO Auto-generated method stub
		int size = frointer.size();
		Cell lastCell = frointer.get(size - 1);
		frointer.remove(size - 1);
		return lastCell;
	}

	@Override
	boolean isEmpty() {
		// TODO Auto-generated method stub
		return frointer.size() == 0;
	}

	@Override
	void resetFrointer() {
		// TODO Auto-generated method stub
		frointer = new ArrayList<Cell>();
	}

	@Override
	int getSize() {
		// TODO Auto-generated method stub
		return frointer.size();
	}

	@Override
	boolean isIn(Cell cell) {
		// TODO Auto-generated method stub
		for (Cell c : frointer)
		{
			if (c.equals(cell))
			{
				return true; 
			}
		}
		return false;
	}

	@Override
	List<Cell> getNextCells() {
		// TODO Auto-generated method stub
		return frointer;
	}

}
