package src;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class Maze {
	public int rows;
	public int cols;
	public int mazeWidth;
	public int mazeHeight;
	public int partition = 3;
	public double marginTop;
	public double marginLeft;
	public Canvas canvas;
	GraphicsContext g;
	private Cell start;
	private Cell stop;	
	private double cellSize;
	public boolean isCleaned;
	public boolean isGridBox;
	
	
	public void resetStartAndStop()
	{
		start = null;
		stop = null;
	}
	 
	
	public Cell getStart() {
		return start;
	}

	public void setStart(Cell start) {
		this.start = start;
	}

	public Cell getStop() {
		return stop;
	}

	public void setStop(Cell stop) {
		this.stop = stop;
	}

	public Cell[][] maze;
	
	
	public Maze(int rows, int cols, GraphicsContext g, Canvas c)
	{
		this.rows = rows;
		this.cols = cols;
		this.g = g;
		this.canvas = c;
		maze = new Cell[rows][cols];
		initMazeSize();
		setCellSizeAndMargin();
		initCellObj();
	}
	
	private void setCellSizeAndMargin()
	{
		marginTop = mazeHeight / 30.0;
		marginLeft = mazeWidth / 30.0;
		double height = mazeHeight - (2 * marginTop);
		cellSize = height / cols;
	}
	
	public void setRowsAndCols(int d)
	{
		this.rows = d;
		this.cols = d;
		initMazeSize();
		setMazeRows();
		//initCellObj();
		setCellSizeAndMargin();
	}
	
	public void setMazeRows()
	{
		maze = new Cell[rows][cols];
	}
	
	public void initMazeSize()
	{
		int height = (int) canvas.getHeight();
		mazeHeight = (int) canvas.getHeight();
		//mazeWidth =  height / 4 * partition;
		mazeWidth = 600;
	}
	
	public void clearMaze()
	{
		g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		isCleaned = true;
	}
	
	public void resetCell()
	{
		initCellObj();
		isCleaned = true;
	}
	
	
	public void fillRandomWeight()
	{
		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < cols; j++)
			{
				maze[i][j].weight = (int) (Math.random() * 10);
			}
		}
	}
	
	public void initCellObj()
	{
		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < cols; j++)
			{
				maze[i][j] = new Cell(j, i, cellSize);
			}
		}
	}
	
	public void drawMaze()
	{
		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < cols; j++)
			{
				if (!isGridBox)
				{
					maze[i][j].draw(g, marginTop, marginLeft);
				}
				else maze[i][j].drawG(g, marginTop, marginLeft);
			}
		}
	}
	
	public List<Cell> getNeighs(Cell currentCell)
	{
		List<Cell> neighs = new ArrayList<Cell> ();
		int x = currentCell.y;
		int y = currentCell.x;
		if (validateEdgeCase(x, y, 3)) // left
		{
			maze[x][y - 1].action = 3;
			neighs.add(maze[x][y - 1]);
		}
		if (validateEdgeCase(x, y, 0)) // top
		{
			maze[x - 1][y].action = 0;
			neighs.add(maze[x - 1][y]);
		}
		if (validateEdgeCase(x, y, 1)) // right
		{
			maze[x][y + 1].action = 1;
			neighs.add(maze[x][y + 1]);
		}
		if (validateEdgeCase(x, y, 2)) // 
		{
			maze[x + 1][y].action = 2;
			neighs.add(maze[x + 1][y]);
		}
		Collections.shuffle(neighs);
		return neighs;
	}
	
	public List<Cell> getAdj(Cell currentCell)
	{
		List<Cell> ads = new ArrayList<Cell>();
		int x = currentCell.y;
		int y = currentCell.x;
	
		if (!currentCell.walls[0] && x - 1 >= 0) // if top open
		{
			if (!maze[x-1][y].walls[2])
			{
				ads.add(maze[x-1][y]);
			}
		}
		if (!currentCell.walls[2] && x + 1 < rows) // if bot open
		{
			if (!maze[x+1][y].walls[0])
			{
				ads.add(maze[x+1][y]);
			}
		}
		if (!currentCell.walls[1] && y + 1 < cols) // if right open
		{
			if (!maze[x][y + 1].walls[3])
			{
				ads.add(maze[x][y + 1]);
			}
		}
		if (!currentCell.walls[3] && y - 1 >= 0) // if left open
		{
			if (!maze[x][y - 1].walls[1])
			{
				ads.add(maze[x][y - 1]);
			}
		}
		Collections.shuffle(ads);
		return ads;
	}
	 
	private boolean validateEdgeCase(int x, int y, int direction)
	{
		switch (direction)
		{
			case 3 ->
			{
				if (y - 1 >= 0 && maze[x][y-1].walls[1])
					// if left open next cell should right open s
				{
					return true;
				}
			}
			case 1 ->
			{
				if (y + 1 < cols && maze[x][y+1].walls[3]) // left should open
				{
					return true;
				}
			}
			case 0 ->
			{
				// button should open
				if (x - 1 >= 0 && maze[x - 1][y].walls[2])
				{
					return true;
				}
			}
			case 2 ->
			{
				if (x + 1 < rows && maze[x + 1][y].walls[0])
				{
					return true;
				}
			}
		}
		return false;
	}
}
