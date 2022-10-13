package src;

import java.util.List;

public class GBFS extends Traversal{
	static Traversal obj;

	GBFS(Maze maze) {
		super(new PQueue(), maze);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void findPath()
	{
		resetVisited();
		frointer.resetFrointer();
		explored = 0;
		maze.getStart().h = 0;
		frointer.add(maze.getStart());   
		while(!frointer.isEmpty())
		{
			explored ++;
			Cell currentCell = frointer.remove();
			if (currentCell.x == maze.getStop().x && currentCell.y == maze.getStop().y)
			{
				visited.add(currentCell);
				visitedLength = visited.size();
				animate();
				showPath(currentCell);
				return;
			}
			List<Cell> nextCells = maze.getAdj(currentCell);
			visited.add(currentCell);
			for (Cell c: nextCells)
			{
				if (!isVisited(c) && !frointer.isIn(c))
				{
					c.h = computeH(c);
					frointer.add(c);
					c.parent = currentCell;
				}
			}			
		}
	}
	
	private int computeH(Cell currentCell)
	{
		int h = Math.abs(maze.getStop().x - currentCell.x) + Math.abs(maze.getStop().y - currentCell.y);
		return h;
	}
	
	public static Traversal getObj(Maze maze) {
		// TODO Auto-generated method stub
		if (obj == null)
		{
			obj = new GBFS(maze);
		}
		return obj;
	}
	
	public static Traversal getObj()
	{
		return obj;
	}
	
}
