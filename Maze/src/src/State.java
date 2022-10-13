package src;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public abstract class State {
	GraphicsContext g;
	Traversal t;
	public abstract void draw(Cell cell, double marginLeft, double marginTop);
	Label lengthOfPath;
	Label explored;
	
	State(GraphicsContext g, Traversal t)
	{
		this.g = g;
		this.t = t;
	}
	
	protected void setExplored()
	{
		explored.setText("Explored - " + t.visitedLength);
	}
	
	protected void setPathLength()
	{
		lengthOfPath.setText("Steps - " + t.getPathLength());
	}
	
	protected void setExplored(int num)
	{
		explored.setText("Explored - " + num);
	}
	
	protected void setPathLength(int num)
	{
		lengthOfPath.setText("Steps" + num);
	}
}


class Start extends State
{	
	Start(GraphicsContext g, Traversal t) {
		super(g, t);
		// TODO Auto-generated constructor stub
	}

	public void draw(Cell cell, double marginLeft, double marginTop)
	{
		if (t.state instanceof Start)
		{
			t.state = t.end;
			t.resetVisited();
			t.frointer.resetFrointer();
			if (!t.maze.isCleaned)
			{
				t.maze.clearMaze();
				t.maze.drawMaze();
				if (explored != null)
				{
					setExplored(0);
					setPathLength(0);
				}
			}
			g.setFill(Color.RED);
			g.fillRect(cell.left		
					, cell.top,
					cell.cellHeight, cell.cellWidth);
			t.maze.setStart(cell);
			g.setFill(Color.BLACK);

		}
	}
}


class End extends State
{	
	End(GraphicsContext g, Traversal t) {
		super(g, t);
		// TODO Auto-generated constructor stub
	}

	public void draw(Cell cell, double marginLeft, double marginTop)
	{
		if (t.state instanceof End)
		{	
			t.maze.isCleaned = false;
			g.setFill(Color.GREEN);
			g.fillRect(cell.left,
					cell.top, 
					cell.cellHeight, cell.cellWidth);
			t.state = t.start;
			t.maze.setStop(cell);
			t.findPath();
			setExplored();
			setPathLength();
		}
	}
	

}