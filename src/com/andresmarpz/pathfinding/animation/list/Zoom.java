package com.andresmarpz.pathfinding.animation.list;

import com.andresmarpz.pathfinding.animation.Animation;
import com.andresmarpz.pathfinding.animation.AnimationManager;
import com.andresmarpz.pathfinding.framework.Square;
import javafx.animation.ScaleTransition;
import javafx.util.Duration;

public class Zoom extends Animation{

    private final Square square;

    public Zoom(Square square){
        this.square = square;
    }

    public void start(){
        if(AnimationManager.getInstance().hasAnimation(square, this))
            return;

        AnimationManager.getInstance().add(square,this);

        ScaleTransition st = new ScaleTransition(Duration.millis(200), square);
        st.setByX(-0.2);
        st.setByY(-0.2);
        st.setCycleCount(2);
        st.setAutoReverse(true);
        st.play();
        st.setOnFinished(event -> AnimationManager.getInstance().remove(square, this));
    }
}
