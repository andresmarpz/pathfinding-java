package com.andresmarpz.pathfinding.animation;

import com.andresmarpz.pathfinding.framework.Square;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AnimationManager{

    private static final AnimationManager manager = new AnimationManager();

    public static AnimationManager getInstance(){
        return manager;
    }

    private Map<Square, List<Animation>> animations = new LinkedHashMap<>();

    public List<Animation> getAnimations(Square square){
        if(animations.containsKey(square) && animations.get(square) != null)
            return animations.get(square);
        else
            return new LinkedList<>();
    }

    public void setAnimations(Square square, List<Animation> list){
        animations.put(square, list);
    }

    public boolean hasAnimation(Square square, Animation animation){
        return getAnimations(square).stream().anyMatch(anim -> anim.getClass() == animation.getClass());
    }

    public void add(Square square, Animation animation){
        List<Animation> animations = getAnimations(square);
        animations.add(animation);
        setAnimations(square, animations);
    }

    public void remove(Square square, Animation animation){
        List<Animation> animations = getAnimations(square);
        animations.remove(animation);
        setAnimations(square, animations);
    }
}
