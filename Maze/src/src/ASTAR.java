package src;

import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;

public class ASTAR extends Traversal{
	
	static Traversal obj;

	ASTAR(Maze maze) {
		super(new PQueue(), maze);
		// TODO Auto-generated constructor stub
	}
//	@Override
	public void findPath2()
	{
		resetVisited();
		setStepsToZero();
		frointer.resetFrointer();
		if (maze.getStart().equals(maze.getStop()))
		{
			return;
		}
		maze.getStart().h = computeH(maze.getStart());
		frointer.add(maze.getStart());   
		while(!frointer.isEmpty())
		{
			Cell currentCell = frointer.remove();
			List<Cell> nextCells = maze.getAdj(currentCell);
			visited.add(currentCell);
			for (Cell c: nextCells)
			{
				if (!isVisited(c) && !frointer.isIn(c))
				{
					if (c.x == maze.getStop().x && c.y == maze.getStop().y)
					{
						visitedLength = visited.size();
						getPath(c);
						animate();
						showPath(c);
						//maze.isCleaned = false;
						return;
					}
					
					if (frointer.isIn(c))
					{
						if (currentCell.steps + 1 < c.steps)
						{
							c.steps = currentCell.steps + 1;
							c.h = computeH(c);
							frointer.add(c);
							c.parent = currentCell;
						}
					}
					
					else if (!frointer.isIn(c))
					{
						c.steps = currentCell.steps + 1;
						c.h = computeH(c);
						frointer.add(c);
						c.parent = currentCell;
					}	

				}
			}			
		}
	}
	
	public synchronized void findPath()
	{
		resetVisited();
		setStepsToZero();
		frointer.resetFrointer();	
		if (maze.getStart().equals(maze.getStop()))
		{
			return;
		}
		maze.getStart().h = computeH(maze.getStart());
		frointer.add(maze.getStart());
		AnimationTimer timer = new AnimationTimer()
				{
					long prevTime = 0;
					@Override
					public void handle(long arg0) {
						// TODO Auto-generated method stub
						if (!frointer.isEmpty() && arg0 - prevTime > 100000)
						{
							Cell currentCell = frointer.remove();
							List<Cell> nextCells = maze.getAdj(currentCell);
							visited.add(currentCell);
							Cell cc = currentCell;
							// for every iteration clean the previous maze and redraw the maze
							cleanAndRedraw();
							// draw colored rect for next cells
							drawNextCells(Color.AQUAMARINE);
							// draw colored rect for explored cells
							drawExploredCells(Color.INDIGO);		
							// draw colored rect for current best path
							drawCurrentBestPath(currentCell, Color.GOLD);
							
							for (Cell c: nextCells)
							{
								if (!isVisited(c))
								{
									if (c.x == maze.getStop().x && c.y == maze.getStop().y)
									{
										handleFoundCase(c, currentCell);
										stop();
										return;
									}
									if (!frointer.isIn(c))
									{
										c.steps = currentCell.steps + 1;
										c.h = computeH(c);
										frointer.add(c);
										c.parent = currentCell;
									}
									
								}
							}
						}
						else {
							cleanAndRedraw();
							stop();
						}
						prevTime = arg0;
					}};
			timer.start();		
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
