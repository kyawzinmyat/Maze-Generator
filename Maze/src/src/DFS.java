package src;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;

public class DFS extends Traversal{
	
	static Traversal obj;
	
	DFS(Maze maze) {
		super(new StackFrointer(), maze);
		// TODO Auto-generated constructor stub
	}

	
	public static Traversal getObj(Maze maze) {
		// TODO Auto-generated method stub
		if (obj == null)
		{
			obj = new DFS(maze);
		}
		return obj;
	}
	
	public static Traversal getObj()
	{
		return obj;
	}
	
	
}