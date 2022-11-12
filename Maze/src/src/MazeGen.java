package src;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MazeGen extends Application{
	
	Canvas canvas;
	GraphicsContext g;
	Maze maze;
	
	public static void main(String[] args) {
		launch();
	}
	 
	public void start(Stage stage)
	{
		canvas = new Canvas(800, 600);	 
		g = canvas.getGraphicsContext2D();
	    BorderPane root = new BorderPane(canvas);
		Scene scene = new Scene(root, 1000, 500);
		
	    
		
		Widget w = new Widget(root, g, canvas);
		w.facade();		
		//Traversal dfs1 = DFS.getObj(maze);
		//Traversal bfs1 = BFS.getObj(maze);	    
		stage.setScene(scene);
		stage.setResizable(true);
		stage.setTitle("Simple Paint");
		stage.show();
	}

}
