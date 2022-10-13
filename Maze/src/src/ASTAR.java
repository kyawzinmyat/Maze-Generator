package src;

import java.util.List;

public class ASTAR extends Traversal{
	
	static Traversal obj;

	ASTAR(Maze maze) {
		super(new PQueue(), maze);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void findPath()
	{
		resetVisited();
		setStepsToZero();
		frointer.resetFrointer();
		explored = 0;
		maze.getStart().h = computeH(maze.getStart());
		frointer.add(maze.getStart());   
		while(!frointer.isEmpty())
		{
			explored ++;
			Cell currentCell = frointer.remove();
			System.out.println(currentCell.h);
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
					c.steps = currentCell.steps + 1;
					c.h = computeH(c);
					c.parent = currentCell;
					frointer.add(c);
				}
			}			
		}
	}
	
	
	private void setStepsToZero()
	{
		for (int i = 0; i < maze.cols; i++)
		{
			for (int j = 0; j < maze.rows; j++)
			{
				maze.maze[i][j].steps = 0;
			}
		}
	}
	
	private int computeH(Cell currentCell)
	{
		int h = Math.abs(maze.getStop().x - currentCell.x) + Math.abs(maze.getStop().y - currentCell.y) + currentCell.steps;
		return h;
	}
	
	public static Traversal getObj(Maze maze) {
		// TODO Auto-generated method stub
		if (obj == null)
		{
			obj = new ASTAR(maze);
		}
		return obj;
	}
	
	public static Traversal getObj()
	{
		return obj;
	}
}
