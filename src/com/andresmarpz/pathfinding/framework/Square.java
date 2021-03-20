package com.andresmarpz.pathfinding.framework;

import com.andresmarpz.pathfinding.animation.list.Zoom;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.util.LinkedHashSet;
import java.util.Set;

public class Square extends StackPane implements Comparable<Square>{

    private int column, row;

    private Label label;
    private Square parent;
    private float distanceToStart, distanceToEnd, cost;

    private Type type;

    public Square(int column, int row){
        this.column = column; this.row = row;
        setType(Type.NORMAL);

//        label = new Label(toString());
//        getChildren().add(label);
    }

    public void setLabel(String text){
        label.setText(text);
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    /**
     * Set and get the parent square to define steps
     * @param parent -
     */
    public void setParent(Square parent) {
        this.parent = parent;
    }

    public Square getParentSquare() {
        return parent;
    }

    public void setDistanceToStart(float distanceToStart) {
        this.distanceToStart = distanceToStart;
    }

    public float getDistanceToStart() {
        return distanceToStart;
    }

    public void setDistanceToEnd(float distanceToEnd) {
        this.distanceToEnd = distanceToEnd;
    }

    public float getDistanceToEnd() {
        return distanceToEnd;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public float getCost() {
        return cost;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type){
        this.type = type;

        switch(type){
            case NORMAL:
                setStyle("-fx-background-color:white; -fx-border-style:solid; -fx-border-width: 0.3; -fx-border-color: #8f8f8f;");
                break;
            case START:
                setStyle("-fx-background-color:#29e361; -fx-border-style:solid; -fx-border-width: 0.3; -fx-border-color: #8f8f8f;");
                break;
            case END:
                setStyle("-fx-background-color:#d65336; -fx-border-style:solid; -fx-border-width: 0.3; -fx-border-color: #8f8f8f;");
                break;
            case CALCULATED:
                setStyle("-fx-background-color:#5ac3e6; -fx-border-style:solid; -fx-border-width: 0.3; -fx-border-color: #8f8f8f;");
                break;
            case STEP:
                setStyle("-fx-background-color:#f2e466; -fx-border-style:solid; -fx-border-width: 0.3; -fx-border-color: #8f8f8f;");
                break;
            case WALL:
                setStyle("-fx-background-color:#0c3547; -fx-box-border: transparent; -fx-border-width: 0.0;");
                break;
        }

        new Zoom(this).start();
    }

    public String toString(){
        return "" +getCost() +"\n" +getDistanceToEnd();
    }

    @Override
    public int compareTo(Square other){
        return Float.compare(getCost(), other.getCost());
    }

    public void reset(){
        setParent(null);
        setDistanceToStart(-1);
        setDistanceToEnd(-1);
        setCost(-1);
        setType(Type.NORMAL);
    }
}
