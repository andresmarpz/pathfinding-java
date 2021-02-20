package com.andresmarpz.pathfinding.calculation;

import com.andresmarpz.pathfinding.framework.Grid;
import com.andresmarpz.pathfinding.framework.Square;
import com.andresmarpz.pathfinding.framework.Type;
import com.andresmarpz.pathfinding.util.Stopwatch;
import javafx.application.Platform;

import java.util.*;

public class Algorithm{

    private boolean running;

    public void pathfind(Grid grid, double speed, Square start, Square end){
        boolean found = false;
        running = true;
        Stopwatch stopwatch = new Stopwatch();

        /* create queue and add the starting point */
        List<Square> open = new LinkedList<>(Collections.singletonList(start));
        List<Square> closed = new ArrayList<>();

        Square current = end;
        while(!open.isEmpty() && running) {
            if (!stopwatch.hasReached((float) (speed)))
                continue;

            open.sort(Comparator.comparing(Square::getCost));
            current = open.get(0);
            open.remove(current);
            closed.add(current);

            if (current == end){
                found = true;
                break;
            }else{
                for(int column = -1; column < 2; column++){
                    for(int row = -1; row < 2; row++){
                        if(column == -1 && row == 0 || column == 0 && row == -1 || column == 0 && row == 1 || column == 1 && row == 0){
                            Square neighbor = grid.get(current.getColumn() +column, current.getRow() +row);
                            if(neighbor == null || neighbor.getType() == Type.WALL || closed.contains(neighbor))
                                continue;

                            float distanceToStart = (float) distance(neighbor, start) * 10;
                            float distanceToEnd = (float) distance(neighbor, end) * 10;
                            float cost = distanceToStart + distanceToEnd;

                            if(!open.contains(neighbor)){
                                open.add(neighbor);
                                neighbor.setDistanceToEnd(distanceToEnd);
                                neighbor.setCost(cost);
                                neighbor.setParent(current);
                                stopwatch.reset();

                                Platform.runLater(() -> {
                                    if(neighbor.getType() != Type.START && neighbor.getType() != Type.END)
                                        neighbor.setType(Type.CALCULATED);
                                });
                            }
                        }
                    }
                }
            }
        }

        stopwatch.reset();

        if(found){
            List<Square> steps = new ArrayList<>();
            while(current.getParentSquare() != null){
                steps.add(current);
                current = current.getParentSquare();
            }

            Collections.reverse(steps);
            steps.removeIf(step -> step.getType() == Type.START || step.getType() == Type.END);
            while(!steps.isEmpty() && running){
                if(!stopwatch.hasReached(25))
                    continue;

                if(!steps.stream().findFirst().isPresent())
                    break;

                Square step = steps.stream().findFirst().get();
                Platform.runLater(() -> {
                    step.setType(Type.STEP);
                    steps.remove(step);
                });

                stopwatch.reset();
            }
        }
    }


    public double distance(Square a, Square b){
        return Math.abs(b.getColumn() - a.getColumn()) + Math.abs(b.getRow() - a.getRow());
    }

    public boolean isRunning(){
        return running;
    }

    public void setRunning(boolean running){
        this.running = running;
    }
}
