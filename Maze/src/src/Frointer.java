package src;


public abstract class Frointer {
	
	
	
	abstract void add(Cell cell);
	abstract Cell remove();
	abstract boolean isEmpty();
	abstract void resetFrointer();
	abstract int getSize();
	
	abstract boolean isIn(Cell c);
}
