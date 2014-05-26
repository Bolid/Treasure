package ru.omdroid.game.ChaosWord;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

public class AppAnimation {
    private Animation animationChangeLayoutParam;
    private Animation animationNotSelectTextView;
    private Animation animationValidWord;
    private Animation animationNotValidWord;

    private LayoutAnimationController animationController;

    public AppAnimation(Context context){
        animationChangeLayoutParam = AnimationUtils.loadAnimation(context, R.anim.textview_anim);
        animationNotSelectTextView = AnimationUtils.loadAnimation(context, R.anim.not_selected_textview_anim);
        animationValidWord =  AnimationUtils.loadAnimation(context, R.anim.apply_word_anim);
        animationNotValidWord =  AnimationUtils.loadAnimation(context, R.anim.not_apply_word_anim);
        animationController = AnimationUtils.loadLayoutAnimation(context, R.anim.grid_animation);
    }

    public Animation getAnimationChangeLayoutParam(){
        return animationChangeLayoutParam;
    }

    public Animation getAnimationNotSelectTextView(){
        return animationNotSelectTextView;
    }

    public Animation getAnimationValidWord(){
        return animationValidWord;
    }

    public Animation getAnimationNotValidWord(){
        return animationNotValidWord;
    }

    public LayoutAnimationController getAnimationController(){
        return animationController;
    }
}
