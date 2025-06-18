package com.example.myapplication;

import android.graphics.Canvas;
import android.os.SystemClock;
import android.view.SurfaceHolder;

public class FBMainThread extends Thread{
    SurfaceHolder mySurfaceHolder;
    long timeSpent;
    long kickOffTime;
    long WAIT = 31;
    boolean Running;
    private static Canvas canvas;
    public FBMainThread(SurfaceHolder surfaceHolder) {
        this.mySurfaceHolder = surfaceHolder;
        Running = true;
    }

    @Override
    public void run() {
        while (Running){
            kickOffTime = SystemClock.uptimeMillis();
            canvas = null;
            try{

               synchronized (mySurfaceHolder){
                   canvas = mySurfaceHolder.lockCanvas();
                   FBAppHolder.getGameManager().backgroundAnimation(canvas);
                   FBAppHolder.getGameManager().birdAnimation(canvas);
                   FBAppHolder.getGameManager().scrollingTube(canvas);
               }
            }catch (Exception e){
                e.printStackTrace();
            }
            finally {
                if (canvas != null){
                    try {
                        mySurfaceHolder.unlockCanvasAndPost(canvas);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
            timeSpent = SystemClock.uptimeMillis() - kickOffTime;
            if (timeSpent < WAIT){
                try {
                    Thread.sleep(WAIT - timeSpent);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

            }
        }
    }
    public boolean isRunning(){
        return  Running;
    }
    public void setIsRunning(boolean state){
        Running = state;
    }
}
