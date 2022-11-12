package src;


public class BFS extends Traversal{
	
	static Traversal obj;

	BFS(Maze maze) {
		super(new QueueFrointer(), maze);
		// TODO Auto-generated constructor stub
	}
	
	

	public static Traversal getObj(Maze maze) {
		// TODO Auto-generated method stub
		if (obj == null)
		{
			obj = new BFS(maze);
		}
		return obj;
	}

	public static Traversal getObj() {
		// TODO Auto-generated method stub
		return obj;
	}
}
