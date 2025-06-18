package com.example.myapplication;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

public class FBAppHolder {
    static FBBitmapControl bitmapControl;
    static FBGameManager gameManager;
    static int SCRN_WIDTH_X;
    static int SCRN_HEIGHT_Y;
    static int gravityPull;
    static int JUMP_VELOCITY;
    static int tubeGap;
    static int tube_numbers;
    static int tubeVelocity;
    static int minimumTubeCollection_Y;
    static int maximumTubeCollection_Y;
    static int tubeDistance;
    static Context gameActivityContext;
    static FBSoundPlayer soundPlay;



    public static void assign(Context context){
        mapScreenSize(context);
        bitmapControl = new FBBitmapControl(context.getResources());
        holdGameVariables();
        gameManager = new FBGameManager();
        soundPlay = new FBSoundPlayer(context);
    }

     public static FBSoundPlayer getSoundPlay(){
        return soundPlay;
     }

    public static void holdGameVariables(){
        FBAppHolder.gravityPull = 5;
        FBAppHolder.JUMP_VELOCITY = -40;
        FBAppHolder.tubeGap = 650;
        FBAppHolder.tube_numbers = 2;
        FBAppHolder.tubeVelocity = 24;
        FBAppHolder.minimumTubeCollection_Y = (int)(FBAppHolder.tubeGap / 2.0);
        FBAppHolder.maximumTubeCollection_Y = FBAppHolder.SCRN_HEIGHT_Y - FBAppHolder.minimumTubeCollection_Y - FBAppHolder.tubeGap;
        FBAppHolder.tubeDistance = FBAppHolder.SCRN_WIDTH_X*2/3;

    }

    public static FBBitmapControl getBitmapControl(){
        return bitmapControl;
    }
    public static FBGameManager getGameManager(){
        return  gameManager;
    }

    private static void mapScreenSize(Context context){
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display dsp = manager.getDefaultDisplay();
        DisplayMetrics dMetrics = new DisplayMetrics();
        dsp.getMetrics(dMetrics);
        int width = dMetrics.widthPixels;
        int height = dMetrics.heightPixels;
        FBAppHolder.SCRN_WIDTH_X = width;
        FBAppHolder.SCRN_HEIGHT_Y = height;
    }
}
