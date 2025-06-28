package com.example.myapplication;

import android.content.Context;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class FBGamePlay extends SurfaceView implements SurfaceHolder.Callback {
    FBMainThread mainThread;

    public FBGamePlay(Context context) {
        super(context);

        SurfaceHolder myHolder = getHolder();
        myHolder.addCallback(this);
        mainThread = new FBMainThread(myHolder);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mainThread.setIsRunning(true);
        mainThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        boolean retry = true;
        while (retry){
            try {
                mainThread.setIsRunning(false);
                mainThread.join();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (FBAppHolder.getGameManager().gameState ==0){
            FBAppHolder.getGameManager().gameState = 1;
            FBAppHolder.getSoundPlay().playSwoosh();
        }else {
            FBAppHolder.getSoundPlay().playWing();
        }

        FBAppHolder.getGameManager().bird.setVelocity(FBAppHolder.JUMP_VELOCITY);
        return  true;
    }
}