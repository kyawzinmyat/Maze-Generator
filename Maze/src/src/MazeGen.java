package src;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MazeGen extends Application{
	
	Canvas canvas;
	GraphicsContext g;
	Maze maze;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch();
	}
	 
	public void start(Stage stage)
	{
		canvas = new Canvas(600, 500);	 
		g = canvas.getGraphicsContext2D();
	    BorderPane root = new BorderPane(canvas);
		Scene scene = new Scene(root);
		
	    
		
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
