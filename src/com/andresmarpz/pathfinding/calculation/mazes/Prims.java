package com.andresmarpz.pathfinding.calculation.mazes;

import com.andresmarpz.pathfinding.framework.Grid;
import com.andresmarpz.pathfinding.framework.Square;
import com.andresmarpz.pathfinding.framework.Type;
import com.andresmarpz.pathfinding.util.Stopwatch;
import com.andresmarpz.pathfinding.util.Utilities;
import com.andresmarpz.pathfinding.window.Window;

import java.util.HashSet;
import java.util.Set;

public class Prims{

    private Stopwatch stopwatch = new Stopwatch();

    public void generate(){
        stopwatch.reset();

        Grid grid = Window.getInstance().getController().getGrid();

        Set<Square> in = new HashSet<>();
        Set<Square> frontier = new HashSet<>();
        Set<Square> out = new HashSet<>(grid.getSquares());

        Square start = out.stream().findAny().get();
        in.add(start);
        out.remove(start);
        Utilities.getNeighbours(grid, start, false).forEach(neighbour -> {
            if(out.contains(neighbour)){
                out.remove(neighbour);
                frontier.add(neighbour);
            }
        });

        while(!frontier.isEmpty()){
            if(!stopwatch.hasReached(30))
                continue;

            Square random = frontier.stream().findAny().get();
            for(Square neighbour : Utilities.getNeighbours(grid, random, false)){
                if(in.contains(neighbour)){
                    neighbour.setType(Type.WALL);
                    in.add(random);
                    frontier.remove(random);
                    Utilities.getNeighbours(grid, random, false).forEach(neighbour1 -> {
                        if(out.contains(neighbour1)){
                            out.remove(neighbour1);
                            frontier.add(neighbour1);
                        }
                    });
                    break;
                }
            }
            stopwatch.reset();
        }
    }
}
