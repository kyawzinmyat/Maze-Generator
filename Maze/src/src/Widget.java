package src;

import javafx.event.ActionEvent;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class Widget {
	
	BorderPane root;
	Canvas canvas;
	GraphicsContext g;
	Traversal current;
	Maze maze;
	GenerateMaze gen;
	GenerateMaze gridBox;
	VBox hbox;
	VBox labelBox;
	Label lengthOfPath;
	Label mazeSize;
	Label explored;
	Label type;
	Label sizeLabel;
	RadioButton spaceBut;
	RadioButton wallBut;
	
	
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
		
		gridBox= new GridBox(maze);
		
		//maze.fillRandomWeight();
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
		handleLeftSideButtons();
	}
	 
	private void handleTraversal()
	{		
		ToggleGroup butGroup = new ToggleGroup();
	    RadioButton  dfs = new RadioButton("DFS");   // Create a dfs button.
	    RadioButton  bfs = new RadioButton("BFS");  // Create a bfs button. 
	    RadioButton gbfs = new RadioButton("GBFS");
	    RadioButton astar = new RadioButton("A*");
	    RadioButton editGrid = new RadioButton("Edit grid");
	    wallBut = new RadioButton("Wall");
	    spaceBut = new RadioButton("Space");
	    dfs.setOnMouseClicked(null);
	    bfs.setOnMouseClicked(null);
	    dfs.setToggleGroup(butGroup); // Add it to the ToggleGroup.
		bfs.setToggleGroup(butGroup);	    
		gbfs.setToggleGroup(butGroup);
		astar.setToggleGroup(butGroup);
		editGrid.setToggleGroup(butGroup);
		spaceBut.setToggleGroup(butGroup);
		wallBut.setToggleGroup(butGroup);
		hbox.setAlignment(Pos.BASELINE_CENTER);
	    hbox.setPadding(new Insets(20, 40, 20, 10)); // space between elements and window border
	    hbox.relocate(maze.mazeWidth + 200, maze.marginTop);
	    hbox.resize(150, 150);
		hbox.setManaged(false);
		dfs.setPadding(new Insets(10, 20, 10, 20));
		hbox.getChildren().addAll(dfs, bfs, gbfs, astar, editGrid);
		root.setRight(hbox);
	    dfs.setOnAction(
	    			evt -> {
	    				handleTraversalBut(DFS.getObj());
	    				setType("DFS");	    				
	    			}
	    		);
	    bfs.setOnAction(
	    			evt -> {
	    				handleTraversalBut(BFS.getObj());
	    				setType("BFS");
	    			}
	    		);
	    gbfs.setOnAction(
    			evt -> {
    				handleTraversalBut(GBFS.getObj());
    				setType("GBFS");
    			}
    		);
	    
	    astar.setOnAction(
    			evt -> {
    				handleTraversalBut(ASTAR.getObj());
    				setType("A*");
    			}
    		);
	    
	    editGrid.setOnAction(
	    			evt -> {
	    				{
	    					if (maze.isGridBox)
	    					{
	    						// grid will not draw wall if this set to false
		    					((GridBox) gridBox).isEdit = true;
		    					handleWallAndSpaceBut();
				    			((GridBox) gridBox).handleDrag();
				    			hbox.getChildren().addAll(spaceBut, wallBut);
				    			// stop state of traversal to draw start and end
				    			current.isEdit = true;	
	    					}
	    				}
	    			}
	    		);	    
	}	
	
	private void handleWallAndSpaceBut()
	{
		spaceBut.setOnAction(evt -> handleSpace(evt, SpaceCell.getObj()));
		wallBut.setOnAction(evt -> handleWall(evt, WallCell.getObj()));
	}
	
	// handle space radio but event
	private void handleSpace(ActionEvent evt, CellState s) {
		// TODO Auto-generated method stub
		((GridBox)gridBox).cellState = s;
	}

	// handle wall radio but event
	private void handleWall(ActionEvent evt, CellState s) {
		// TODO Auto-generated method stub
		((GridBox)gridBox).cellState = s;
	}


	// remove wall but and space but
	private void removeBut()
	{
		hbox.getChildren().remove(spaceBut);
		hbox.getChildren().remove(wallBut);
	}
	
	private void handleTraversalBut(Traversal obj)
	{
		removeBut();
		// grid will draw wall if this set to true
		((GridBox) gridBox).isEdit = false;
		// state will draw start and end if traversal.isEdit is false
		current.isEdit = false;
		current = obj;
		current.clickCellEvent();
		helperTraversal();
		if (maze.isGridBox)
		{
			setMazeSize(countCellsForGrid());
		}
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
	
	private void handleLeftSideButtons()
	{
		VBox vbox = new VBox(10);
		vbox.setManaged(false);
		vbox.relocate(10, maze.marginTop);
		vbox.setPadding(new Insets(20, 20, 20 ,20));
		vbox.resize(100,100);
		root.setBottom(vbox);
		
		// handle generating maze, grid and clearing maze
		handleMaze(vbox);
		handleGrid(vbox);
		handleClear(vbox);
		// show and hide visited cells
		handleClearAniCells(vbox);
		handleShowAniCells(vbox);
	}
	
	// for creating and setting event handler for grid button
	private void handleGrid(VBox hbox)
	{
		 Button generateGrid = new Button("Grid");
			hbox.getChildren().add(generateGrid);
		    generateGrid.setOnMouseClicked(
	    			evt -> {
	    				maze.clearMaze();
	    				maze.resetCell();
	    				((GridBox)gridBox).setBox();
	    				maze.isGridBox = true;
	    				maze.drawMaze();
	    				setMazeSize();
	    				setPathLength(0);
	    				setExplored(0);
	    			}
	    		);
	}
	
	private void handleMaze(VBox hbox)
	{
		Button generate = new Button("Maze");
		hbox.getChildren().add(generate);
	    generate.setOnMouseClicked(
    			evt -> {
    				maze.isGridBox = false;
    				maze.clearMaze();
    				maze.resetCell();
    				gen.traverseAnimate();
    				maze.drawMaze();
    				setMazeSize();
    				setPathLength(0);
    				setExplored(0);
    			}
    		);
	}
	
	// for clearing the canvas
	private void handleClear(VBox box)
	{
		Button clear = new Button("Clear");
		box.getChildren().add(clear);
		clear.setOnAction(evt -> 
			{
				maze.clearMaze();
				maze.drawMaze();
			}
		);
	}
	
	// handle clear animated cell
	private void handleClearAniCells(VBox box)
	{
		Label vc = new Label("Visited \nCells");
		Button clear = new Button("Hide");
		box.getChildren().add(vc);
		box.getChildren().add(clear);
		clear.setOnAction(
					evt -> {
						if (current.visited != null && current.path != null)
						{
							// clear the cell in visited
					    	maze.maze[0][0].clearRect(current.visited, maze.g, maze.getStart());
					    	// draw again the cell in path
					    	maze.maze[0][0].strokeRect(current.path, 
					    			1, maze.maze[0][0].cellHeight , maze.g, Color.DODGERBLUE);						}
					}
				);
	}
	
	private void handleShowAniCells(VBox box)
	{
		Button clear = new Button("Show");
		box.getChildren().add(clear);
		clear.setOnAction(
					evt -> {
						if (current.visited != null)
						{
					    	// draw again the cell in path
					    	maze.maze[0][0].strokeRect(current.visited, 
					    			maze.g, 
					    			Color.rgb(00, 255, 255),4, 
					    			maze.getStart());
					    	maze.maze[0][0].strokeRect(current.path, 
					    			1, maze.maze[0][0].cellHeight , maze.g, Color.DODGERBLUE);
;
						}
					}
				);
	}
	
	private int countCellsForGrid()
	{
		int size = 0;
		for (int i = 0; i < maze.rows; i++)
		{
			for (int j = 0; j < maze.cols; j++)
			{
				if (!maze.maze[i][j].walls[0])
				{
					size ++;
				}
			}
		}
		return size;
	}
	
	private void handleSlider()
	{
        sizeLabel = new Label("Size " + maze.cols + " X" + maze.cols);
		hbox.getChildren().addAll(sizeLabel);
        Slider slider1 = new Slider(5,60,25);
        slider1.setPadding(new Insets(0, 20, 0, 20));
        slider1.setMajorTickUnit(10);
        slider1.setPrefWidth(10);
        slider1.setShowTickLabels(true);
        slider1.valueProperty().addListener( e -> sliderValueChanged(slider1) );
        hbox.getChildren().add(slider1);
	}
	
	private void sliderValueChanged(Slider s)
	{

		if (s.getValue() > 0 && s.getValue() < 61)
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
	
	private void setMazeSize(int size)
	{

		mazeSize.setText("Cells - " + size);
	}
	
	private void setType()
	{
		if (type == null)
		{
			type = new Label("Type - " + "");
		}
	}
	
	private void setType(String type)
	{
		
		this.type.setText("Type - " + type);
	}
}
