package src;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class PQueue extends Frointer{
	PriorityQueue<Cell> frointer = new PriorityQueue<Cell> ();


	@Override
	void add(Cell cell) {
		// TODO Auto-generated method stub
		frointer.add(cell);
	}

	@Override
	Cell remove() {
		// TODO Auto-generated method stub
		return frointer.remove();
	}

	@Override
	boolean isEmpty() {
		// TODO Auto-generated method stub
		return frointer.isEmpty();
	}

	@Override
	void resetFrointer() {
		// TODO Auto-generated method stub
		frointer.clear();
	}

	@Override
	int getSize() {
		// TODO Auto-generated method stub
		return frointer.size();
	}

	@Override
	boolean isIn(Cell c) {
		// TODO Auto-generated method stub
		for (Cell cell: frointer)
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
		return new ArrayList<Cell>(frointer);
	}

}
