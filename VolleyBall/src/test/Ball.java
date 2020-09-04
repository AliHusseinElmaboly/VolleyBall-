/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

/**
 *
 * @author mohamed
 */
public class Ball extends GameObject{

    
    Ball(Image ballImage ,int boundLeft , int boundRight, int speed ,int x , int y ) throws Exception
    {
        super(ballImage, boundLeft, boundRight, speed, x, y, Resources.ballWidth, Resources.ballHeight, Resources.Right, -1, 17 , true,Resources.groundY+50);
    }
 
    public void Action(){
        if(ground == Resources.groundY+70 )
        {
            velocityY= 5;

            if(x < (boundLeft+boundRight) /2 )
            {
                Resources.CurrentState = Resources.Lose;
                int v = (boundLeft+boundRight) /2;
            }
            else
            {
                Resources.CurrentState = Resources.Win;
            }
        }
        else
           velocityY= 17;
    }



}
