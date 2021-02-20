package com.andresmarpz.pathfinding.util;

public class Stopwatch{

    private long time;

    public Stopwatch(){
        reset();
    }

    public float timeElapsed(){
        return System.currentTimeMillis() - time;
    }

    public boolean hasReached(float ms){
        return timeElapsed() >= ms;
    }

    public void reset(){
        this.time = System.currentTimeMillis();
    }
}
