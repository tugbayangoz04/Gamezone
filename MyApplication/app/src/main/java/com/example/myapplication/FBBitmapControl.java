package com.example.myapplication;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class FBBitmapControl {
    Bitmap[] FlyingBird;
    Bitmap background;
    Bitmap upTube;
    Bitmap downTube;
    Bitmap upColoredTube, downColoredTube;

    public FBBitmapControl(Resources res) {
        background = BitmapFactory.decodeResource(res,R.drawable.fbbackground);
        background = imageScale(background);
        FlyingBird = new Bitmap[3];
        FlyingBird[0] = BitmapFactory.decodeResource(res,R.drawable.bird1);
        FlyingBird[1] = BitmapFactory.decodeResource(res,R.drawable.bird2);
        FlyingBird[2] = BitmapFactory.decodeResource(res,R.drawable.bird3);
        upTube = BitmapFactory.decodeResource(res,R.drawable.up_tube);
        downTube = BitmapFactory.decodeResource(res,R.drawable.down_tube);
        upColoredTube = BitmapFactory.decodeResource(res,R.drawable.colored_tube_up);
        downColoredTube = BitmapFactory.decodeResource(res,R.drawable.colored_tube_bottom);
    }

    public Bitmap getUpColoredTube(){
        return upColoredTube;
    }
    public Bitmap getDownColoredTube(){
        return downColoredTube;
    }

    public Bitmap getUpTube(){
        return upTube;
    }
    public Bitmap getDownTube(){
        return downTube;
    }
    public int getTubeWidth(){
        return upTube.getWidth();
    }
    public int getTubeHeight(){
        return upTube.getHeight();
    }



    public Bitmap getBird(int frame){
        return FlyingBird[frame];
    }
    public int getBirdWidth(){
        return FlyingBird[0].getWidth();
    }
    public int getBirdHeight(){
        return FlyingBird[0].getHeight();
    }

    public Bitmap getBackground(){
        return background;
    }
    public int getBackgroundWidth(){
        return background.getWidth();
    }
    public int getBackgroundHeight(){
        return background.getHeight();
    }
    public Bitmap imageScale(Bitmap bitmap){
        float width_heightRatio = getBackgroundWidth() / getBackgroundHeight();
        int bgScaleWidth = (int) width_heightRatio * FBAppHolder.SCRN_WIDTH_X;
        return Bitmap.createScaledBitmap(bitmap, bgScaleWidth, FBAppHolder.SCRN_HEIGHT_Y,false);
    }
}
