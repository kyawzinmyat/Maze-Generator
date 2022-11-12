package src;

import java.util.List;

public abstract class Frointer {
	
	List<Cell> nextCells;
	abstract void add(Cell cell);
	abstract Cell remove();
	abstract boolean isEmpty();
	abstract void resetFrointer();
	abstract int getSize();
	abstract List<Cell> getNextCells();
	abstract boolean isIn(Cell c);
}
