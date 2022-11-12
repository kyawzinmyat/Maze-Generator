package src;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class GridBox extends GenerateMaze{
	
	boolean isDragged;
	public boolean isEdit;
	CellState cellState;
	CellState space = SpaceCell.getObj();
	CellState wall = WallCell.getObj();
	// store last position of x and y in the form of array of maze
	
	GridBox(Maze maze) {
		super(maze);
		// TODO Auto-generated constructor stub
		//setBox();
	}
	
	public void setBox()
	{
		maze.isGridBox = true;
		for(int i = 0; i < maze.cols; i++)
		{
			for (int j = 0; j < maze.cols; j++)
			{
				for (int z = 0; z < 4; z++)
				{
					maze.maze[i][j].walls[z] = false;
				}
			}
		}
	}
	
	
	public void handleDrag()
	{
		if (isEdit)
		{
			maze.canvas.setOnMousePressed(evt -> handlePressedEvent(evt));
			maze.canvas.setOnMouseDragged(evt -> handleDraggedEvent(evt));
			maze.canvas.setOnMouseReleased(evt -> handleReleasedEvent(evt));
		}		
	}
	
	public void handlePressedEvent(MouseEvent evt)
	{
		if (isDragged)
		{
			return;
		}
		int x = (int) evt.getX();
		int y = (int) evt.getY();
		Cell cell = maze.maze[0][0];
		x = (int) ((x - maze.marginLeft - cell.lineWidth) / (cell.cellWidth));
		y = (int) ((y - maze.marginTop - cell.lineWidth) / (cell.cellWidth));
		if (validateIndex(x, y))
		{
			isDragged = true;
			drawCellRect(x, y);
		}
	}
	
	public void handleReleasedEvent(MouseEvent evt)
	{
		isDragged = false;
	}
	
	public void handleDraggedEvent(MouseEvent evt)
	{
		if (isDragged)
		{
			int x = (int) evt.getX();
			int y = (int) evt.getY();
			Cell cell = maze.maze[0][0];
			x = (int) ((x - maze.marginLeft - cell.lineWidth) / (cell.cellWidth));
			y = (int) ((y - maze.marginTop - cell.lineWidth) / (cell.cellWidth));
			drawCellRect(x, y);
		}
	}

	// it will draw cell depending on the cell state
	// if cell is wall that 
	/*
	 * 			cellState.setCell(cell) will set the cell obj walls to true
	 * */
	private void drawCellRect(int x, int y)
	{
		if (validateIndex(x, y) && isEdit && maze.isGridBox && cellState != null)
		{
			Cell cell = maze.maze[y][x];
			cellState.setCell(cell);
			Color c = cell.walls[0] ? Color.BLACK : Color.WHITE;		
			cell.strokeRect(cell.left, cell.top, maze.g,c, null);			
		}
	}
	private boolean validateIndex(int row, int col)
	{
		return row < maze.rows && col < maze.cols && row >= 0 && col >= 0;
	}
}
