/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

/**
 *
 * @author mohamed
 */


import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.Image;

public class Character extends GameObject
{

    private int initX;
    public int currentAnim;

    private Score score;

    private int [][] sequence={ {0,1},
                                {2,3},
                                {4,5},
                                {6,7},
                                {8,9},
                                {10,11},
                                {12,13}};


    public Character(Image gameImage , int boundLeft , int boundRight , int initialX , int AnimIndex , int speed ) throws Exception
    {
        super(gameImage, boundLeft, boundRight, speed, initialX, Resources.groundY, Resources.playerWidth , Resources.playerHeight,Resources.None , -2 , 16, false , Resources.groundY);

        //initial poisition in x Axis
        initX = initialX;
     

        objSprite.defineCollisionRectangle(16, 16, 32, 48);
        objSprite.setFrameSequence(sequence[AnimIndex]);
        objSprite.setFrame(0);

        //current animation of the character
        currentAnim = 0;
        
        //initial player Score
        score = new Score(0);

    }

    public int getScore()
    {
        return score.getValue();
    }

    public void setScore( int value)
    {
        score.setValue(value);
    }

    public void increaseScore()
    {
        score.setValue(score.getValue() +1);
    }


    public void setSequence( int AnimIndex )
    {
       if( currentAnim != AnimIndex && !isJumping )
       {

         objSprite.setFrameSequence(sequence[AnimIndex]);
         currentAnim = AnimIndex;
       }
    }

    public void AIUpdate(int ballX , int ballY)
    {


        if ( ballX  > 0 && ballX < boundLeft-width/2)
        {
            // go to center and stay
            goToCenter();
        }
        else
        {
        
           if (ballY > y -20 && ballY < y +20 )
                setSequence(6);
           else if( Math.abs(x - ballX )  < 5 && ballX  > boundRight /2 && ballY < y - 20 && !isJumping)
            {


                setSequence(5);
                isJumping =true;
                velocityY = 16;

            }
            else if (ballY > y +20 )
                setSequence(2);

            if( ballX  > x)
            {
                currentDirect = Resources.Right;

            }
            else
            {
                 currentDirect = Resources.Left;
                  setSequence(6);
            }
        }


    }
    
    public void goToCenter()
    {

         x = Math.min(initX, x + 7);
         if(x == initX)
         {
             setSequence(0);
         }
    }
    
    public void Action()
    {
      isJumping = false;
    }

}