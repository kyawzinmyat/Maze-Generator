package src;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.Canvas;

public class GenerateMaze {
	Frointer frointer;
	Maze maze;
	Canvas canvas;
	List<Cell> visited = new ArrayList<Cell> ();
	State state;
	State start;
	State end;
	List<Cell> path;
	
	
	
	
	GenerateMaze(Frointer frointer, Maze maze)
	{
		this.frointer = frointer;
		this.maze = maze;
	}
	
	public void traverse()
	{
		frointer.add(maze.maze[(int)maze.rows/2][(int)maze.cols/2]);               
		while(!frointer.isEmpty())
		{
			Cell currentCell = frointer.remove();
			List<Cell> nextCells = maze.getNeighs(currentCell);
			if (currentCell.parent != null 
					)
			{
				removeWall(currentCell.parent, currentCell);
				currentCell.parent.adjs.add(currentCell);				
			}
			visited.add(currentCell);
			for (Cell c: nextCells)
			{
				if (!isVisited(c))
				{
					c.parent = currentCell;
					frointer.add(c);
				}
			}			
		}
		resetVisited();
		frointer.resetFrointer();
	}
	
	public boolean isVisited(Cell cell)
	{
		for (Cell c: visited)
		{
			if (c == cell || c.equals(cell))
			{
				return true;
			}
		}
		return false;
	}
	
	public void resetVisited()
	{
		visited.clear();
	}
	
	
	public void removeWall(Cell currentCell, Cell nextCell)
	{
		
		// 0 1 2 3
		// 2 3 0 1
		int openNextCellWall = nextCell.action == 0 ? 2: 
			nextCell.action == 1 ? 3 : nextCell.action == 2 ? 0 : 1 ;
		currentCell.walls[nextCell.action] = false;
		nextCell.walls[openNextCellWall] = false;
	}
	
}
