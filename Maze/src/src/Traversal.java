package src;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public abstract class Traversal {
	Frointer frointer;
	Maze maze;
	Canvas canvas;
	List<Cell> visited = new ArrayList<Cell> ();
	State state;
	State start;
	public State end; 
	List<Cell> path;
	public int visitedLength;
	public int explored;
	private boolean pathEnd;
	
	
	
	Traversal(Frointer frointer, Maze maze)
	{
		this.frointer = frointer;
		this.maze = maze;
		state = new Start(maze.g, this);
		start = new Start(maze.g, this);
		end = new End(maze.g, this);
	}
	
	
	public void findPath()
	{
		resetVisited();
		frointer.resetFrointer();
		explored = 0;
		frointer.add(maze.getStart());   
		while(!frointer.isEmpty())
		{
			explored ++;
			Cell currentCell = frointer.remove();
			//System.out.println(currentCell.parent);
			if (currentCell.x == maze.getStop().x && currentCell.y == maze.getStop().y)
			{
				visited.add(currentCell);
				visitedLength = visited.size();
				animate();
				showPath(currentCell);
				return;
			}
			List<Cell> nextCells = maze.getAdj(currentCell);
			//List<Cell> nextCells = currentCell.adjs;
			visited.add(currentCell);
			for (Cell c: nextCells)
			{
				if (!isVisited(c) && !frointer.isIn(c))
				{
					frointer.add(c);
					c.parent = currentCell;
				}
			}			
		}
	}
	
	
	
	public void showPath(Cell lastCell)
	{
		List<Cell> path = getPath(lastCell);
		AnimationTimer timer = new AnimationTimer () {
			long prevTime;
			boolean cleaned;
			int counter = path.size() - 1;	
			public void handle (long time){
                if (time - prevTime > 100_000 && counter >= 0 && pathEnd) {
                	Cell c = path.get(counter);
                	if (!cleaned)
                	{
                		c.clearRect(visited, getG());
                		cleaned = true;
                	}
        			{
        				c.strokeRect(c.left,
        						c.top,
        						getG(), Color.BLUE , null);
        			}
                	prevTime = time;
                	counter --;
                }
                else if (counter < 0) {
                	stop();
                	pathEnd = false;
                	}; 
			}
		};		
		timer.start();
	}
	
	
	public int getPathLength()
	{
		if (path != null)
		{
			return path.size();
		}
		return 0;
	}
	
	public List<Cell> getPath(Cell lastCell)
	{
			
		List<Cell> path = new ArrayList<Cell> ();
		List<Cell> tempVisited = new ArrayList<Cell> (visited);
		Collections.reverse(tempVisited);
		Cell parent = null;
		for (Cell c: tempVisited)
		{
			if (parent == null) {
				parent = c.parent;
				path.add(c);
			}
			else
			{
				if (c.equals(parent))
				{
					path.add(c);
					parent = c.parent;
				}
			}
		}
		this.path = path;
		return path;
	}
	
	public void animate()
	{
		AnimationTimer timer = new AnimationTimer () {
			long prevTime;
			int counter = 0;
			int size = visited.size();
			public void handle (long time){
			
                if (time - prevTime > 100_000 && counter < size && !pathEnd) {
                	Cell c = visited.get(counter);
                	if (!c.equals(maze.getStart()) && !c.equals(maze.getStop()))
        			{
        				c.strokeRect(c.left, c.top, getG(), Color.RED , null);
        			}
                	counter ++;
                	prevTime = time;
                }
                else if (counter >= size)
                {
                	pathEnd = true;
                	stop();
                }
			}
		};
		timer.start();
	}
	
	public void resetVisited()
	{
		visited.clear();
	}
	
	public boolean isVisited(Cell cell)
	{
		for (Cell c: visited)
		{
			if (c.equals(cell))
			{
				return true;
			}
		}
		return false;
	}
	
	
	
	
	void clickCellEvent()
	{
		canvas.setOnMouseClicked(
					evt -> {
						handleClickEvent(evt);
					}
				);
	}
	
	private boolean validateIndex(int row, int col)
	{
		return row < maze.rows && col < maze.cols;
	}
	
	private void handleClickEvent(MouseEvent evt)
	{
		int x = (int) evt.getX();
		int y = (int) evt.getY();
		Cell cell = maze.maze[0][0];
		x = (int) ((x - maze.marginLeft - cell.lineWidth) / (cell.cellWidth));
		y = (int) ((y - maze.marginTop - cell.lineWidth) / (cell.cellWidth));
		if (validateIndex(x, y))
		{
			state.draw(maze.maze[y][x], maze.marginLeft, maze.marginTop);		
		}
	}
	
	public GraphicsContext getG()
	{
		return maze.g;
	}

}
