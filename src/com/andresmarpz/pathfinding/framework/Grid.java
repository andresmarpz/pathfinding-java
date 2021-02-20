package com.andresmarpz.pathfinding.framework;

import javafx.scene.layout.Pane;

public class Grid extends Pane{

    private int columns, rows;
    private double width, height;
    private Square[][] squares;
    private Square start, end;

    public Grid(int columns, int rows, double width, double height){
        this.columns = columns; this.rows = rows;
        this.width = width; this.height = height;

        squares = new Square[columns][rows];
    }

    public void add(Square square, int column, int row){
        squares[column][row] = square;

        double w = width / columns;
        double h = height / rows;
        double x = w * column;
        double y = h * row;

        square.setLayoutX(x);
        square.setLayoutY(y);
        square.setPrefWidth(w);
        square.setPrefHeight(h);

        getChildren().add(square);
    }

    public void setStart(Square start) {
        this.start = start;
    }

    public Square getStart(){
        return start;
    }

    public void setEnd(Square end) {
        this.end = end;
    }

    public Square getEnd() {
        return end;
    }

    public Square get(int column, int row){
        return (column < this.columns && column >= 0 && row < this.rows && row >= 0) ? squares[column][row] : null;
    }
}
