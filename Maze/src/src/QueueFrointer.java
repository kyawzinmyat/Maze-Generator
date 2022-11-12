package src;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class QueueFrointer extends Frointer{
	Queue<Cell> frointer = new LinkedList<Cell> ();


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
		frointer = new LinkedList<Cell>();
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
		return new ArrayList<Cell>(frointer);
	}

}
