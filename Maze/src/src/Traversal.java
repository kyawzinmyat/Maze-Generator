package src;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;

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
	boolean pathEnd;
	public int pathLength;
	
	// to keep track of whether the grid is editing
	// set by widget
	public boolean isEdit;
	
	
	Traversal(Frointer frointer, Maze maze)
	{
		this.frointer = frointer;
		this.maze = maze;
		state = new Start(maze.g, this);
		start = new Start(maze.g, this);
		end = new End(maze.g, this);
	}
	
	
	public void findPath2()
	{
		resetVisited();
		frointer.resetFrointer();
		frointer.add(maze.getStart());
		if (maze.getStart().equals(maze.getStop()))
		{
			path = null;
			return;
		}
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
						state.setExplored(visited.size());
						state.setPathLength(path.size());
						return;
					}
					frointer.add(c);
					c.parent = currentCell;
				}
			}			
		}
	}
	
	
	
	public void showPath(Cell lastCell)
	{
		AnimationTimer timer = new AnimationTimer () {
			long prevTime;
			boolean cleaned;
			int counter = path.size() - 1;	
			public void handle (long time){
                if (time - prevTime > 100_000 && counter >= 0 && pathEnd) {
                	Cell c = path.get(counter);
                	// if animated cells are not cleaned
                	// clean
                	if (!cleaned)
                	{
                		//c.clearRect(visited, getG(), maze.getStart());
                		cleaned = true;
                	}
        			{
        				c.strokeRect(c.left, c.top, 5, c.cellWidth - 5, getG(), Color.BLACK, null);

        				//c.strokeRect(c.left,
        						//c.top,4,
        						///getG(), Color.rgb(77, 120, 226, 1), null);
        			}
                	prevTime = time;
                	counter --;
                }
                else if (counter < 0) {
                	stop();
                	pathEnd = false;
                	}
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
				if (c.equals(parent) && !c.equals(maze.getStart()))
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
			Cell prevCell;
			int size = visited.size();
			public void handle (long time){
                if (time - prevTime > 100_000_0 && counter < size && !pathEnd) {
                	Cell c = visited.get(counter);
                	if (!c.equals(maze.getStart()) && !c.equals(maze.getStop()))
        			{
        				c.strokeRect(c.left,
        						c.top,4,
        						getG(), Color.rgb(0, 255, 200), null);
        				if (prevCell != null && !prevCell.equals(maze.getStart()) && !prevCell.equals(maze.getStop()))
        				{
        					prevCell.strokeRect(prevCell.left,
        					prevCell.top, 4,
        					getG(), Color.rgb(0, 255, 255, 1), null);
        				}
        			}
                	prevCell = c;
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
	
	public void findPath()
	{
		resetVisited();
		frointer.resetFrointer();	
		if (maze.getStart().equals(maze.getStop()))
		{
			return;
		}
		frointer.add(maze.getStart());
		AnimationTimer timer = new AnimationTimer()
				{
					long prevTime = 0;
					@Override
					public void handle(long arg0) {
						// TODO Auto-generated method stub
						//System.out.println("f");
						if (!frointer.isEmpty() && arg0 - prevTime > 100000)
						{
							Cell currentCell = frointer.remove();
							List<Cell> nextCells = maze.getAdj(currentCell);
							visited.add(currentCell);
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
								if (!isVisited(c) && !frointer.isIn(c))
								{
									if (c.x == maze.getStop().x && c.y == maze.getStop().y)
									{
										// first argument is child and currentCell is current or parent
										handleFoundCase(c, currentCell);
										stop();
										return;
									}
									frointer.add(c);
									c.parent = currentCell;
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
	
	
	// helper fun for clean and redraw maze
	public void cleanAndRedraw()
	{
		maze.clearMaze();
		maze.drawMaze();
		maze.getStart().strokeRect(maze.getStart().left, 
				maze.getStart().top, getG(), 
				Color.RED, null);
		maze.getStop().strokeRect(maze.getStop().left, 
				maze.getStop().top, getG(), 
				Color.GREEN, null);	
	}
	
	// a function to handle the case when the target has been found
	public void handleFoundCase(Cell c, Cell currentCell)
	{
		getPath(c);
		//pathEnd = true;
		//showPath(c);
		cleanAndRedraw();
		drawCurrentBestPath(currentCell, Color.CYAN);
		maze.isCleaned = false;
		state.setExplored(visited.size());
		state.setPathLength(pathLength);
		visitedLength = visited.size();
	}
	
	/*
	 * three helper method to draw current best path, next cells and explored cells
	 * */
	
	public void drawNextCells(Color color)
	{
		Cell start = maze.getStart();
		Cell stop = maze.getStop();
		for (Cell c: frointer.getNextCells())
		{
			if (!start.equals(c) && !stop.equals(c)) {c.strokeRect(c.left, c.top, 1, c.cellHeight, getG(), color, null);}
		}
	}
	
	public void drawExploredCells(Color color)
	{
		Cell start = maze.getStart();
		Cell stop = maze.getStop();
		for (Cell c: visited)
		{
			if (!start.equals(c) && !stop.equals(c)) {c.strokeRect(c.left, c.top, 1, c.cellHeight , getG(), color, null);}
		}
	}
	
	public void drawCurrentBestPath(Cell cc, Color c)
	{
		pathLength = 0;
		Cell start = maze.getStart();
		Cell stop = maze.getStop();
		while(cc != maze.getStart())	
		{
			pathLength++;
			if (!start.equals(cc) && !stop.equals(cc)) 
			{
				cc.strokeRect(cc.left, cc.top, 1, cc.cellHeight , getG(), c, null);
				}
			cc = cc.parent;
		}
	}
	
	
	////////////////////////////////////////////////////////////////////
	
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
		if (validateIndex(x, y) && !isEdit)
		{
			state.draw(maze.maze[y][x], maze.marginLeft, maze.marginTop);		
		}
	}
	
	public GraphicsContext getG()
	{
		return maze.g;
	}

}
