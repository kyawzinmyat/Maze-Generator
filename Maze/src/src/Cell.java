package src;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Cell implements Comparable<Cell>{
	// store the cell wall
	// walls are top right bottom left
	//  index      0   1    2      3
	public boolean[] walls = new boolean[4];
	public double cellWidth = 30;
	public double cellHeight;
	public int x;
	public Cell parent;
	public Cell parent2;
	public int y;
	public double top;
	public double left;
	public int weight = -1;
	double lineWidth = 1;
	int steps;
	public int h;
	List<Cell> adjs = new ArrayList<Cell> ();
	
	


	// int indicate  
	// 0 -> top
	// 1 -> right
	// 2 -> bottom
	// 3 -> left
	public int action;
	
	public Cell(int x, int y)
	{
		this.x = x;
		this.y = y;
		cellHeight = cellWidth;
		setCellWallsTrue();
	}
	public Cell(int x, int y, double size)
	{
		this(x, y);
		cellWidth = size;
		cellHeight = size;
	}
	
	
	public void draw(GraphicsContext g)
	{
		draw(g, 0, 0);
	}
	
	
	public void draw(GraphicsContext g, double marginLeft, double marginTop)
	{
		// the cell top is open
		//g.setFill(Color.BLACK);	
		g.setStroke(Color.BLACK);
		g.setLineWidth(lineWidth);
		double x = this.x * cellHeight + marginLeft + lineWidth;
		left = x;
		double y = this.y * cellWidth + marginTop + lineWidth; 
		top = y;
		if (walls[0]) // bot
		{
			g.strokeLine(x, y, x + cellWidth, y);
		}
		if (walls[1]) // right wall
		{	
			g.strokeLine(x + cellWidth, y, x + cellWidth, y + cellHeight);
		}
		if (walls[2]) // bottom open
		{
			g.strokeLine(x, y + cellHeight, x + cellHeight, y + cellWidth);
		}
		if (walls[3]) // left wall
		{
			g.strokeLine(x, y, x, y + cellHeight);
		}
	}
	
	public void drawG(GraphicsContext g, double marginLeft, double marginTop)
	{
		double x = this.x * cellHeight + marginLeft + lineWidth ;
		left = x;
		double y = this.y * cellWidth + marginTop + lineWidth; 
		top = y;
		if (walls[0])
		{
			g.setFill(Color.BLACK);
			g.fillRect(x, y, cellHeight, cellWidth);
		}
		drawRect(x, y, g);
	}
	
	// draw rect without margin
	public void strokeRect(double x, double y, GraphicsContext g, Color c, String steps)
	{
		g.setFill(c);
		g.fillRect(x , y, cellWidth, cellWidth);
		drawRect(x, y, g);
	}
		
	// draw rect with margin
	public void strokeRect(double x, double y, double margin,GraphicsContext g, Color c, String steps)
	{
		g.setFill(c);
		g.fillRect(x + margin, y + margin, cellWidth/3, cellWidth/3);
		//drawRect(x, y, g);
	}
	
	public void strokeRect(double x, double y, double margin, double size, GraphicsContext g, Color c, String steps)
	{
		g.setFill(c);
		g.fillRect(x + margin, y + margin, size, size);
		//drawRect(x, y, g);
	}
	
	// draw rect for array list
	// margin is left sided
	// distance from left
	public void strokeRect(List<Cell> list, GraphicsContext g, Color color, double margin)
	{
		for (Cell c : list)
		{
			c.strokeRect(c.left, c.top, margin, g, color, null);
		}
	}
	
	/// draw rect with margin
	
	public void strokeRect(List<Cell> list, GraphicsContext g, Color color, double margin, Cell... excepts)
	{
		for (Cell c : list)
		{	
			for (Cell ec: excepts)
			{
				if(!c.equals(ec))
				{
					c.strokeRect(c.left, c.top, margin, g, color, null);		
				}
			}
		}
	}
	
	//// one for individual cell and another for list of cells
	// draw rect with size and margin
	public void strokeRect(List<Cell> list, double margin, double size, GraphicsContext g, Color color)
	{
		for (Cell c : list)
		{	
			c.strokeRect(c.left, c.top, margin, size, g, color, null);		
		}
	}
	
	public void strokeRect(double x, double y, GraphicsContext g, Color color, double margin, double size)
	{
		strokeRect(x, y, margin, size, g, color, null);		
	}
	///
	
	
	
	// draw rect with line
	private void drawRect(double x, double y, GraphicsContext g)
	{
		g.strokeLine(x, y, x, y + cellHeight);
		g.strokeLine(x, y + cellHeight, x + cellHeight, y + cellWidth);
		g.strokeLine(x, y, x + cellWidth, y);
		g.strokeLine(x + cellWidth, y, x + cellWidth, y + cellHeight);
	}
	
	public void clearRect(double x, double y, GraphicsContext g)
	{
		g.setFill(Color.WHITE);
		//g.strokeRect(x , y, x + cellWidth, y + cellHeight);
		g.clearRect(x + 4, y + 4, cellWidth/3, cellWidth/3);
		
	}
	
	public void clearRect(List<Cell> path, GraphicsContext g)
	{
		for (Cell c: path)
		{
			clearRect(c.left, c.top, g);
		}
	}
	
	public void clearRect(List<Cell> path, GraphicsContext g, Cell ...excepts)
	{
		for (Cell c: path)
		{
			for (Cell e: excepts)
			{
				if (!e.equals(c))
				{
					clearRect(c.left, c.top, g);
				}
			}
		}
	}

	
	private void setCellWallsTrue()
	{
		for (int i = 0; i < 4; i++)
		{
			walls[i] = true;
		}
	}

	public double getCellWidth() {
		return cellWidth;
	}

	public void setCellWidth(double cellWidth) {
		this.cellWidth = cellWidth;
	}

	public double getCellHeight() {
		return cellHeight;
	}

	public void setCellHeight(double cellHeight) {
		this.cellHeight = cellHeight;
	}
	@Override
	public int compareTo(Cell c) {
		// TODO Auto-generated method stub
		if (h > c.h)
		{
			return 1;
		}else 
		
			return -1;
		
	}
	
	
}
