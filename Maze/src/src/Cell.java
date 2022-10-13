package src;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

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
		g.setLineWidth(2);
		double x = this.x * cellHeight + 10 + lineWidth;
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
	
	public void strokeRect(double x, double y, GraphicsContext g, Color c, String steps)
	{
		g.setFill(c);
		//g.strokeRect(x , y, x + cellWidth, y + cellHeight);
		g.fillOval(x + 4, y + 4, cellWidth/3, cellWidth/3);
		//g.setStroke(Color.BLACK);
		//g.strokeLine(x, y, x + cellHeight, y);
		//g.strokeLine(x + cellHeight, y, x + cellHeight, y + cellWidth);
		//g.strokeLine(x, y + cellWidth, x + cellWidth, y + cellHeight);
		//g.strokeLine(x, y, x, y + cellWidth);

	}
	
	public void clearRect(double x, double y, GraphicsContext g)
	{
		g.setFill(Color.WHITE);
		//g.strokeRect(x , y, x + cellWidth, y + cellHeight);
		g.fillOval(x + 4, y + 4, cellWidth/3, cellWidth/3);
		
	}
	
	public void clearRect(List<Cell> path, GraphicsContext g)
	{
		for (Cell c: path)
		{
			clearRect(c.left, c.top, g);
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
