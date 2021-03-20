package com.andresmarpz.pathfinding.util;

import com.andresmarpz.pathfinding.framework.Grid;
import com.andresmarpz.pathfinding.framework.Square;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class Utilities{

    public static Set<Square> getNeighbours(Grid grid, Square square, boolean diagonal){
        Set<Square> neighbours = new LinkedHashSet<>();
        for(int column = -1; column < 2; column++) {
            for (int row = -1; row < 2; row++) {
                Square neighbour = grid.get(square.getColumn() +column, square.getRow() + row);
                if(Objects.isNull(neighbour))
                    continue;

                if(diagonal)
                    neighbours.add(neighbour);
                else if(column == -1 && row == 0 || column == 0 && row == -1 || column == 0 && row == 1 || column == 1 && row == 0){
                    neighbours.add(neighbour);
                }
            }
        }
        return neighbours;
    }
}
