package com.example.myapplication;

public class FBFlyingBird {
    private int birdX;
    private int birdY;
    private int currentFrame;
    private int velocity;
    public static int maximumFrame;
    public FBFlyingBird() {
        birdX = FBAppHolder.SCRN_WIDTH_X/2 - FBAppHolder.getBitmapControl().getBirdWidth()/2;
        birdY = FBAppHolder.SCRN_HEIGHT_Y/2 - FBAppHolder.getBitmapControl().getBirdHeight()/2;
        currentFrame = 0;
        maximumFrame = 2;
    }
    public int getCurrentFrame(){
        return currentFrame;
    }
    public void setCurrentFrame(int currentFrame){
        this.currentFrame = currentFrame;
    }
    public int getX(){
        return birdX;
    }
    public int getY(){
        return birdY;
    }
    public void setX(int birdX){
        this.birdX = birdX;
    }
    public void setY(int birdY){
        this.birdY = birdY;
    }
    public int getVelocity(){
        return velocity;
    }
    public void setVelocity(int velocity){
        this.velocity = velocity;
    }
}