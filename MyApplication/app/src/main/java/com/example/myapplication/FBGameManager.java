package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.Random;

public class FBGameManager{
    FBFlyingBird bird;
    FBBgImage bgImage;
    static int gameState;
    ArrayList<FBTubeCollection>tubeCollections;
    Random rand;
    int scoreCount; //this will be used to store the score
    int winningTube; // this will uesd to determine the winning tube obsctacle
    Paint designPaint;

    // the constructor
    public FBGameManager() {
        bgImage = new FBBgImage();
        bird = new FBFlyingBird();
        gameState = 0;
        tubeCollections = new ArrayList<>();
        rand = new Random();
        generateTubeObject();
        initScoreVariables();
    }

    public void initScoreVariables(){
        scoreCount = 0;
        winningTube = 0;
        designPaint = new Paint();
        designPaint.setColor(Color.YELLOW);
        designPaint.setTextSize(250);
        designPaint.setStyle(Paint.Style.FILL);
        designPaint.setFakeBoldText(true);
        designPaint.setShadowLayer(5.0f, 20.0f,20.0f, Color.BLACK);
    }

   /*
      gameState == 0 : not  runniing
      gameState == 1 : the game is running
      gameState == 2 : The game is over
   */

      public void generateTubeObject(){
          for (int j = 0; j< FBAppHolder.tube_numbers; j++){
              int tubeX = FBAppHolder.SCRN_WIDTH_X + j*FBAppHolder.tubeDistance;
              int upTubeCollectionY = FBAppHolder.minimumTubeCollection_Y;
              rand.nextInt(FBAppHolder.maximumTubeCollection_Y - FBAppHolder.minimumTubeCollection_Y + 1);
              FBTubeCollection tubeCollection = new FBTubeCollection(tubeX,upTubeCollectionY);
              tubeCollections.add(tubeCollection);
          }
      }

      public void scrollingTube(Canvas can){
          if (gameState == 1){

              if ((tubeCollections.get(winningTube).getXtube() < bird.getX() + FBAppHolder.getBitmapControl().getBirdWidth())
              &&(tubeCollections.get(winningTube).getUpTubeCollection_Y() > bird.getY()
                      ||tubeCollections.get(winningTube).getDownTube_Y() < (bird.getY() +
                      FBAppHolder.getBitmapControl().getBirdHeight()))){
                  gameState = 2;
                  FBAppHolder.getSoundPlay().playCrash();
                  Context mContext = FBAppHolder.gameActivityContext;
                  Intent mIntent = new Intent(mContext,FBGameOver.class);
                  mIntent.putExtra("score",scoreCount);
                  mContext.startActivity(mIntent);
                  ((Activity)mContext).finish();
              }

              if (tubeCollections.get(winningTube).getXtube() < bird.getX() - FBAppHolder.getBitmapControl().getTubeWidth()){
                  scoreCount ++;
                  winningTube ++;
                  FBAppHolder.getSoundPlay().playPing();
                  if (winningTube > FBAppHolder.tube_numbers - 1){
                      winningTube = 0;
                  }
              }
              for (int j = 0; j<FBAppHolder.tube_numbers; j++){
                  if (tubeCollections.get(j).getXtube()<-FBAppHolder.getBitmapControl().getTubeWidth()){
                      tubeCollections.get(j).setXtube(tubeCollections.get(j).getXtube()
                              +FBAppHolder.tube_numbers*FBAppHolder.tubeDistance);
                      int upTubeCollectionY = FBAppHolder.minimumTubeCollection_Y +
                              rand.nextInt(FBAppHolder.maximumTubeCollection_Y - FBAppHolder.minimumTubeCollection_Y+1);
                      tubeCollections.get(j).setUpTubeCollection_Y(upTubeCollectionY);
                      tubeCollections.get(j).setColorTube();
                  }
                 tubeCollections.get(j).setXtube(tubeCollections.get(j).getXtube() - FBAppHolder.tubeVelocity);
                 if (tubeCollections.get(j).getColorTube() == 0){
                     can.drawBitmap(FBAppHolder.getBitmapControl().getUpTube(), tubeCollections.get(j).getXtube(),tubeCollections.get(j).getUpTube_Y(),null);
                     can.drawBitmap(FBAppHolder.getBitmapControl().getDownTube(),tubeCollections.get(j).getXtube(),tubeCollections.get(j).getDownTube_Y(),null);
                 }else{
                     can.drawBitmap(FBAppHolder.getBitmapControl().getUpColoredTube(), tubeCollections.get(j).getXtube(), tubeCollections.get(j).getUpTube_Y(),null);
                     can.drawBitmap(FBAppHolder.getBitmapControl().getDownColoredTube(),tubeCollections.get(j).getXtube(),tubeCollections.get(j).getDownTube_Y(),null);
                 }
              }
              can.drawText("" +scoreCount, 620,400, designPaint);
          }
      }

      // Method responsible for the Flying Bird
      public void birdAnimation(Canvas canvas){
          if (gameState == 1){
              if (bird.getY() <(FBAppHolder.SCRN_HEIGHT_Y -FBAppHolder.getBitmapControl().getBirdHeight()) || bird.getVelocity()<0 ){
                  bird.setVelocity(bird.getVelocity() + FBAppHolder.gravityPull );
                  bird.setY(bird.getY() + bird.getVelocity() );
              }
          }
        int currentFrame = bird.getCurrentFrame();
        canvas.drawBitmap(FBAppHolder.getBitmapControl().getBird(currentFrame), bird.getX(),bird.getY(),null);
        currentFrame++;
        if (currentFrame > bird.maximumFrame){
            currentFrame = 0;
        }
        bird.setCurrentFrame(currentFrame);
    }
      //Method responsible for the background side scrolling animation
      public void backgroundAnimation(Canvas canvas){
        bgImage.setX(bgImage.getX() - bgImage.getVelocity());
        if (bgImage.getX() < -FBAppHolder.getBitmapControl().getBackgroundWidth()){
            bgImage.setX(0);
        }
        canvas.drawBitmap(FBAppHolder.getBitmapControl().getBackground(), bgImage.getX(), bgImage.getY(), null);
        if (bgImage.getX() < -(FBAppHolder.getBitmapControl().getBackgroundWidth() - FBAppHolder.SCRN_WIDTH_X)){
            canvas.drawBitmap(FBAppHolder.getBitmapControl().getBackground(), bgImage.getX()
            +FBAppHolder.getBitmapControl().getBackgroundWidth(),bgImage.getY(),null);
        }
    }
}
