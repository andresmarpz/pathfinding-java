package com.andresmarpz.pathfinding.window;

import com.andresmarpz.pathfinding.calculation.mazes.Prims;
import com.andresmarpz.pathfinding.calculation.pathfinding.Astar;
import com.andresmarpz.pathfinding.framework.Grid;
import com.andresmarpz.pathfinding.framework.Square;
import com.andresmarpz.pathfinding.framework.Type;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Arrays;
import java.util.Objects;

public class Controller{

    private Astar astar = new Astar();

    @FXML
    private BorderPane pane;

    @FXML
    private VBox vbox;

    @FXML
    private HBox upperLine;

    @FXML
    private HBox bottomLine;

    @FXML
    private Slider speedSlider;

    @FXML
    private Label speedLabel;

    private Grid grid;

    @FXML
    private Button reset;

    @FXML
    private MenuButton mazeSelect;

    int cols = 35, rows = 30;

    public void setup(){
        MenuItem prims = new MenuItem("Prim's Algorithm");
        prims.setOnAction(event -> {
            new Thread(() -> {
                resetAll(Type.CALCULATED); resetAll(Type.STEP);
                new Prims().generate();
            }).start();
        });

        mazeSelect.getItems().add(prims);

        speedSlider.valueProperty().addListener((observable, oldValue, newValue) -> speedLabel.setText("Speed " +Math.round((Double) newValue) +"ms"));

        createGrid();
    }

    private Square dragging;

    public void createGrid(){
        // create grid with x cols and rows
        grid = new Grid(cols, rows, pane.getWidth(), ((pane.getWidth() / cols) * rows) -vbox.getHeight());
        pane.setCenter(grid);

        for(int column = 0; column < cols; column++){
            for(int row = 0; row < rows; row++){
                Square square = new Square(column, row);

                square.setOnDragDetected(event -> square.startFullDrag());
                square.setOnMousePressed(event -> {
                    if(square.getType() == Type.START || square.getType() == Type.END)
                        dragging = square;
                });
                square.setOnMouseDragOver(event -> {
                    if(event.isPrimaryButtonDown() && !Objects.nonNull(dragging) && !(square.getType() == Type.START) && !(square.getType() == Type.END))
                        square.setType(Type.WALL);
                    else if(event.isPrimaryButtonDown() && Objects.nonNull(dragging)){
                        if(dragging.getType() == Type.START && square.getType() == Type.END || dragging.getType() == Type.END && square.getType() == Type.START)
                            return;

                        Type type = dragging.getType();
                        resetAll(type);
                        square.setType(type);
                        dragging = square;

                        if(dragging.getType() == Type.START) grid.setStart(dragging); else grid.setEnd(dragging);
                    }

                    if(event.isSecondaryButtonDown() && (!(square.getType() == Type.START) && !(square.getType() == Type.END)))
                        square.setType(Type.NORMAL);
                });
                square.setOnMouseDragReleased(event -> dragging = null);
                square.setOnMouseClicked(event -> {
                    if(square.getType() == Type.NORMAL)
                        square.setType(Type.WALL);
                });

                grid.add(square, column, row);
            }
        }

        resetGoals();
    }

    public void handleReset(){
        Platform.runLater(() -> {
            astar.setRunning(false);
            Arrays.stream(Type.values()).forEach(this::resetAll);
            resetGoals();
        });
    }

    public void resetGoals(){
        Square start = grid.get((cols / 2) / 2, rows / 2);
        start.setType(Type.START);
        grid.setStart(start);

        Square end = grid.get((cols / 2) + ((cols / 2) / 2), rows / 2);
        end.setType(Type.END);
        grid.setEnd(end);
    }

    public void resetAll(Type type){
        grid.getChildren().stream().filter(sq -> ((Square) sq).getType() == type).forEach(sq -> ((Square) sq).reset());
    }

    public void handleFind(){
        if(Objects.nonNull(grid.getStart()) && Objects.nonNull(grid.getEnd())) {
            new Thread(() -> {
                resetAll(Type.CALCULATED); resetAll(Type.STEP);
                astar.pathfind(grid, speedSlider.getValue(), grid.getStart(), grid.getEnd());
            }).start();
        }
    }

    public Grid getGrid() {
        return grid;
    }
}