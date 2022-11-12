package src;

public abstract class CellState {
	public abstract void setCell(Cell cell);
}


class WallCell extends CellState
{
	static 	CellState obj;
	
	@Override
	public void setCell(Cell cell) {
		// TODO Auto-generated method stub
		for (int i = 0; i < 4; i++)
		{
			cell.walls[i] = true;
		}
	}

	public static CellState getObj() {
		// TODO Auto-generated method stub
		if (obj == null)
		{
			obj = new WallCell();
		}
		return obj;
	}
	
}

class SpaceCell extends CellState
{
	static 	CellState obj;

	@Override
	public void setCell(Cell cell) {
		// TODO Auto-generated method stub
		for (int i = 0; i < 4; i++)
		{
			cell.walls[i] = false;
		}
	}

	public static CellState getObj() {
		// TODO Auto-generated method stub
		if (obj == null)
		{
			obj = new SpaceCell();
		}
		return obj;
	}
	
}
