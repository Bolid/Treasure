package ru.omdroid.game.ChainTest;

import android.app.Application;

public class App extends Application {
    public static App application;
    public App(){
        if (application == null)
            application = this;
    }
    public static App getInstance(){
        return application;
    }

}
