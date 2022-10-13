package src;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class Widget {
	
	BorderPane root;
	Canvas canvas;
	GraphicsContext g;
	Traversal current;
	Maze maze;
	GenerateMaze gen;
	VBox hbox;
	VBox labelBox;
	Label lengthOfPath;
	Label mazeSize;
	Label explored;
	Label type;
	Label sizeLabel;
	
	public Widget(BorderPane r, GraphicsContext g, Canvas c)
	{
		root = r;
		this.g = g;
		canvas = c;
	}
	
	
	public void facade()
	{
		
		hbox = new VBox(10);
		maze = new Maze(25, 25, g, canvas);
		gen = new GenerateMaze (new StackFrointer(), maze);	
		gen.traverse();
		maze.drawMaze();
		Traversal dfs1 = DFS.getObj(maze);
		Traversal bfs1 = BFS.getObj(maze);
		Traversal gbfs1 = GBFS.getObj(maze);
		Traversal astar = ASTAR.getObj(maze);
		current = bfs1;
	    root.setPadding(new Insets(10)); // space between elements and window border
		dfs1.canvas = canvas;
		bfs1.canvas = canvas;
		gbfs1.canvas = canvas;
		astar.canvas = canvas;
		handleSlider();
		handleLabel();
		handleTraversal();
		handleGenerate();
	}
	 
	private void handleTraversal()
	{		
		ToggleGroup butGroup = new ToggleGroup();
	    RadioButton  dfs = new RadioButton("DFS");   // Create a dfs button.
	    RadioButton  bfs = new RadioButton("BFS");  // Create a bfs button. 
	    RadioButton gbfs = new RadioButton("GBFS");
	    RadioButton astar = new RadioButton("A*");
	    dfs.setOnMouseClicked(null);
	    bfs.setOnMouseClicked(null);
	    dfs.setToggleGroup(butGroup); // Add it to the ToggleGroup.
		bfs.setToggleGroup(butGroup);	    
		gbfs.setToggleGroup(butGroup);
		astar.setToggleGroup(butGroup);
		hbox.setAlignment(Pos.BASELINE_CENTER);
	    hbox.setPadding(new Insets(20, 40, 20, 10)); // space between elements and window border
		current.clickCellEvent();
	    hbox.relocate(500, 100);
	    hbox.resize(150, 150);
		hbox.setManaged(false);
		dfs.setPadding(new Insets(10, 20, 10, 20));
		hbox.getChildren().addAll(dfs, bfs, gbfs, astar);
		root.setRight(hbox);
	    dfs.setOnAction(
	    			evt -> {
	    				current = DFS.getObj();
	    				current.clickCellEvent();
	    				helperTraversal();
	    				setType("DFS");
	    				
	    			}
	    		);
	    bfs.setOnAction(
	    			evt -> {
	    				current = BFS.getObj();
	    				current.clickCellEvent();
	    				helperTraversal();
	    				setType("BFS");
	    			}
	    		);
	    gbfs.setOnAction(
    			evt -> {
    				current = GBFS.getObj();
    				(current).clickCellEvent();
    				helperTraversal();
    				setType("GBFS");
    			}
    		);
	    
	    astar.setOnAction(
    			evt -> {
    				current = ASTAR.getObj();
    				(current).clickCellEvent();
    				helperTraversal();
    				setType("A*");
    			}
    		);
	}	
	
	// that will assign the lable 
	// explored and legnthofPath 
	// to the state objects Start and End
	// to display the path length and explored cells label dynamically
	private void helperTraversal()
	{
		current.end.explored = explored;
		current.end.lengthOfPath = lengthOfPath;
		current.start.explored = explored;
		current.start.lengthOfPath = lengthOfPath;
	}
	
	private void handleGenerate()
	{
		Button generate = new Button("Generate");
		hbox.getChildren().add(generate);
	    generate.setOnMouseClicked(
    			evt -> {
    				maze.clearMaze();
    				maze.resetCell();
    				gen.traverse();
    				maze.drawMaze();
    				setMazeSize();
    				setPathLength(0);
    				setExplored(0);
    			}
    		);

	}
	
	private void handleSlider()
	{
        sizeLabel = new Label("Size " + maze.cols + " X" + maze.cols);
		hbox.getChildren().addAll(sizeLabel);
        Slider slider1 = new Slider(5,45,25);
        slider1.setPadding(new Insets(0, 20, 0, 20));
        slider1.setMajorTickUnit(10);
        slider1.setPrefWidth(10);
        slider1.setShowTickLabels(true);
        slider1.valueProperty().addListener( e -> sliderValueChanged(slider1) );
        hbox.getChildren().add(slider1);
	}
	
	private void sliderValueChanged(Slider s)
	{

		if (s.getValue() > 0 && s.getValue() < 50)
		{
			maze.setRowsAndCols((int)s.getValue());
			sizeLabel.setText("Size " + maze.cols + " X" + maze.cols);
		}
	}
	
	private void handleLabel()
	{
		labelBox = new VBox(20);
		labelBox.relocate(500, 50);
	    labelBox.resize(150, 150);
		labelBox.setManaged(false);
		setExplored();
		setPathLength();
		setMazeSize();
		setType();
		current.end.explored = explored;
		current.end.lengthOfPath = lengthOfPath;
		current.start.explored = explored;
		current.start.lengthOfPath = lengthOfPath;
		labelBox.getChildren().add(lengthOfPath);
		hbox.getChildren().addAll(lengthOfPath, mazeSize, explored, type);
	}
	
	private void setExplored()
	{
		if (explored == null)
		{
			explored = new Label("Explored - " + current.visitedLength);
		}
		else explored.setText("Explored - " + current.visitedLength);

	}
	
	private void setPathLength()
	{
		if (lengthOfPath == null)
		{
			lengthOfPath = new Label("Steps - " + current.getPathLength());
		}
		else lengthOfPath.setText("Steps - " + current.getPathLength());
	}
	
	private void setExplored(int num)
	{
		explored.setText("Explored - " + num);

	}
	
	private void setPathLength(int num)
	{
		lengthOfPath.setText("Steps - " + num);
	}
	
	private void setMazeSize()
	{
		if (mazeSize == null)
		{
			mazeSize = new Label("Cells - " + (maze.cols * maze.cols));
		}
		else mazeSize.setText("Cells - " + (maze.cols * maze.cols));
	}
	
	private void setType()
	{
		if (type == null)
		{
			type = new Label("Type - " + "BFS");
		}
	}
	
	private void setType(String type)
	{
		
		this.type.setText("Type - " + type);
	}
}
